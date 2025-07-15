package org.urban.springbootcafeteria.service.impl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.entitie.Pago;
import org.urban.springbootcafeteria.entitie.Pedido;
import org.urban.springbootcafeteria.repository.PagoRepository;
import org.urban.springbootcafeteria.repository.PedidoRepository;
import org.urban.springbootcafeteria.dto.request.PagoRequest;
import org.urban.springbootcafeteria.dto.response.PagoResponse;
import org.urban.springbootcafeteria.service.PagoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Value("${mercadopago.access-token}")
    private String mpAccessToken;
    @Value("${mercadopago.url-success}")
    private String urlSuccess;
    @Value("${mercadopago.url-failure}")
    private String urlFailure;
    @Value("${mercadopago.url-pending}")
    private String urlPending;

    // Crear pago e integrar Mercado Pago
    @Override
    public Integer crear(PagoRequest request) {
        // 1. Buscar el pedido asociado
        Pedido pedido = pedidoRepository.findById(request.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // 2. Configurar Mercado Pago
        MercadoPagoConfig.setAccessToken(mpAccessToken);
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title("Pedido #" + pedido.getIdPedido())
                .quantity(1)
                .currencyId("PEN")
                .unitPrice(request.getMonto())
                .build();
        System.out.println("BackURL success: " + urlSuccess);
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(urlSuccess)
                .failure(urlFailure)
                .pending(urlPending)
                .build();
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(Collections.singletonList(item))
                .backUrls(backUrls)
                .autoReturn("approved")
                .build();
        try {
            Preference preference = new PreferenceClient().create(preferenceRequest);
            // 3. Crear y guardar el pago
            Pago pago = new Pago();
            pago.setMonto(request.getMonto());
            pago.setFechaPago(LocalDateTime.now());
            pago.setEstado(Pago.EstadoPago.PENDIENTE);
            pago.setMercadoPagoId(null); // Se llenará cuando llegue el webhook (ID del pago real)
            pago.setMercadoPagoPreferenceId(preference.getId()); // Guarda el ID de la preferencia
            pago.setMercadoPagoStatus("init");
            pago.setPaymentLink(preference.getInitPoint());
            pago.setPayerEmail(request.getPayerEmail());
            pago = pagoRepository.save(pago);
            // 4. Relacionar el pago al pedido
            pedido.setPago(pago);
            pedidoRepository.save(pedido);
            return pago.getIdPago();
        } catch (MPException | MPApiException e) {
            if (e instanceof MPApiException) {
                MPApiException apiEx = (MPApiException) e;
                System.err.println("MP API ERROR: " + apiEx.getApiResponse().getContent());
            }
            throw new RuntimeException("Error al crear preferencia de pago: " + e.getMessage(), e);
        }
    }

    @Override
    public PagoResponse obtenerPorId(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        return mapToResponse(pago);
    }

    @Override
    public PagoResponse actualizar(Integer id, PagoRequest request) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        pago.setEstado(Pago.EstadoPago.valueOf(request.getEstado()));
        pago.setMercadoPagoStatus(request.getMercadoPagoStatus());
        pago.setPaymentLink(request.getPaymentLink());
        pago.setPayerEmail(request.getPayerEmail());
        pagoRepository.save(pago);
        return mapToResponse(pago);
    }

    @Override
    public void eliminar(Integer id) {
        pagoRepository.deleteById(id);
    }

    @Override
    public List<PagoResponse> listarTodos() {
        List<Pago> pagos = pagoRepository.findAll();
        List<PagoResponse> responses = new ArrayList<>();
        for (Pago pago : pagos) {
            responses.add(mapToResponse(pago));
        }
        return responses;
    }

    @Override
    public List<PagoResponse> listarPorCliente(Integer idCliente) {
        List<PagoResponse> responses = new ArrayList<>();
        List<Pedido> pedidos = pedidoRepository.findAll();
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente() != null && pedido.getCliente().getId().equals(idCliente) && pedido.getPago() != null) {
                responses.add(mapToResponse(pedido.getPago()));
            }
        }
        return responses;
    }

    @Override
    public Pago obtenerPorPreferenceId(String preferenceId) {
        return pagoRepository.findByMercadoPagoPreferenceId(preferenceId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado para preferenceId: " + preferenceId));
    }

    // --- Utilidad para mapear Pago a PagoResponse ---
    private PagoResponse mapToResponse(Pago pago) {
        PagoResponse response = new PagoResponse();
        response.setIdPago(pago.getIdPago());
        response.setIdPedido(pago.getPedido() != null ? pago.getPedido().getIdPedido() : null);
        response.setMonto(pago.getMonto());
        response.setEstado(pago.getEstado() != null ? pago.getEstado().name() : null);
        response.setMercadoPagoId(pago.getMercadoPagoId());
        response.setMercadoPagoStatus(pago.getMercadoPagoStatus());
        response.setPaymentLink(pago.getPaymentLink());
        response.setPayerEmail(pago.getPayerEmail());
        response.setFechaPago(pago.getFechaPago());
        return response;
    }
}
