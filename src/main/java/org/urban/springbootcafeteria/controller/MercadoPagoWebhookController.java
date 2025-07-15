package org.urban.springbootcafeteria.controller;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urban.springbootcafeteria.entitie.Pago;
import org.urban.springbootcafeteria.entitie.Pedido;
import org.urban.springbootcafeteria.entitie.Venta;
import org.urban.springbootcafeteria.repository.PagoRepository;
import org.urban.springbootcafeteria.repository.PedidoRepository;
import org.urban.springbootcafeteria.repository.VentaRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import org.json.JSONObject;

@RestController
@RequestMapping("/api/mercadopago/webhook")
public class MercadoPagoWebhookController {
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private VentaRepository ventaRepository;

    @Value("${mercadopago.access-token}")
    private String mpAccessToken;

    private String getPreferenceIdFromPayment(String paymentId, String accessToken) {
        try {
            URL url = new URL("https://api.mercadopago.com/v1/payments/" + paymentId + "?access_token=" + accessToken);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();
                String jsonStr = content.toString();
                System.out.println("[WEBHOOK][DEBUG] JSON de respuesta de MP: " + jsonStr);
                JSONObject json = new JSONObject(jsonStr);
                return json.optString("preference_id", null);
            }
        } catch (Exception e) {
            System.out.println("[WEBHOOK][ERROR] No se pudo obtener preference_id del payment: " + e.getMessage());
        }
        return null;
    }

    private String getPreferenceIdFromMerchantOrder(String merchantOrderId, String accessToken) {
        try {
            URL url = new URL("https://api.mercadopago.com/merchant_orders/" + merchantOrderId + "?access_token=" + accessToken);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();
                String jsonStr = content.toString();
                System.out.println("[WEBHOOK][DEBUG] JSON de respuesta de MerchantOrder: " + jsonStr);
                JSONObject json = new JSONObject(jsonStr);
                return json.optString("preference_id", null);
            }
        } catch (Exception e) {
            System.out.println("[WEBHOOK][ERROR] No se pudo obtener preference_id del merchant_order: " + e.getMessage());
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<String> recibirWebhook(@RequestParam(required = false) String id,
                                                 @RequestParam(name = "data.id", required = false) String dataId,
                                                 @RequestParam(name = "type", required = false) String type,
                                                 @RequestBody(required = false) String body) {
        try {
            String paymentId = (dataId != null) ? dataId : id;
            if (type == null || !type.equals("payment") || paymentId == null) {
                return ResponseEntity.ok("Evento ignorado");
            }
            MercadoPagoConfig.setAccessToken(mpAccessToken);
            PaymentClient paymentClient = new PaymentClient();
            Payment payment = paymentClient.get(Long.parseLong(paymentId));
            String status = payment.getStatus();
            String mpPagoId = payment.getId().toString();

            // --- OBTENER EL preference_id REAL ---
            String preferenceId = null;
            String merchantOrderId = null;
            if (payment.getOrder() != null && payment.getOrder().getId() != null) {
                merchantOrderId = payment.getOrder().getId().toString();
            }
            if (merchantOrderId != null) {
                preferenceId = getPreferenceIdFromMerchantOrder(merchantOrderId, mpAccessToken);
            }
            System.out.println("[WEBHOOK] paymentId: " + paymentId + ", status: " + status + ", mpPagoId: " + mpPagoId + ", preferenceId: " + preferenceId);
            final String finalPreferenceId = preferenceId;
            Optional<Pago> pagoOpt = pagoRepository.findAll().stream()
                    .filter(p -> mpPagoId.equals(p.getMercadoPagoId()))
                    .findFirst();
            if (pagoOpt.isEmpty()) {
                pagoOpt = pagoRepository.findAll().stream()
                        .filter(p -> finalPreferenceId != null && finalPreferenceId.equals(p.getMercadoPagoPreferenceId()))
                        .findFirst();
                if (pagoOpt.isPresent()) {
                    Pago pago = pagoOpt.get();
                    pago.setMercadoPagoId(mpPagoId);
                    pagoRepository.save(pago);
                } else {
                    System.out.println("[WEBHOOK] Pago local no encontrado para mpPagoId ni preferenceId: " + mpPagoId + " / " + preferenceId);
                    return ResponseEntity.ok("Pago local no encontrado");
                }
            }
            Pago pago = pagoOpt.get();
            Pedido pedido = pago.getPedido();
            if (status.equals("approved")) {
                pago.setEstado(Pago.EstadoPago.COMPLETADO);
                pago.setMercadoPagoStatus("approved");
                pagoRepository.save(pago);
                if (pedido != null) {
                    pedido.setEstado("CONFIRMADO");
                    pedidoRepository.save(pedido);
                    boolean ventaExiste = ventaRepository.findAll().stream().anyMatch(v -> v.getPedido().getIdPedido().equals(pedido.getIdPedido()));
                    if (!ventaExiste) {
                        Venta venta = new Venta();
                        venta.setPedido(pedido);
                        venta.setTotal(pedido.getTotal());
                        venta.setFechaVenta(LocalDateTime.now());
                        venta.setEstado(Venta.EstadoVenta.FINALIZADA);
                        ventaRepository.save(venta);
                    }
                }
            } else if (status.equals("rejected")) {
                pago.setEstado(Pago.EstadoPago.RECHAZADO);
                pago.setMercadoPagoStatus("rejected");
                pagoRepository.save(pago);
                if (pedido != null) {
                    pedido.setEstado("RECHAZADO");
                    pedidoRepository.save(pedido);
                }
            } else if (status.equals("in_process")) {
                pago.setEstado(Pago.EstadoPago.EN_PROCESO);
                pago.setMercadoPagoStatus("in_process");
                pagoRepository.save(pago);
                if (pedido != null) {
                    pedido.setEstado("EN_PROCESO");
                    pedidoRepository.save(pedido);
                }
            }
            return ResponseEntity.ok("Webhook procesado");
        } catch (MPException | MPApiException | NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.ok("Error procesando webhook: " + e.getMessage());
        }
    }
}
