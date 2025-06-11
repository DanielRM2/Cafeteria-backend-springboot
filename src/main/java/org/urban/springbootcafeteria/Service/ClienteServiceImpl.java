package org.urban.springbootcafeteria.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.Dto.ClienteRequest;
import org.urban.springbootcafeteria.Dto.ClienteResponse;
import org.urban.springbootcafeteria.Entitie.Cliente;
import org.urban.springbootcafeteria.Repository.ClienteRepository;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository,
                              BCryptPasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClienteResponse registrarCliente(ClienteRequest clienteRequest) {
        if (clienteRepository.existsByEmail(clienteRequest.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNombreCompleto(clienteRequest.getNombreCompleto());
        cliente.setEmail(clienteRequest.getEmail());
        cliente.setContrasena(passwordEncoder.encode(clienteRequest.getContrasena()));
        cliente.setTelefono(clienteRequest.getTelefono());
        cliente.setFechaNacimiento(clienteRequest.getFechaNacimiento());

        Cliente clienteGuardado = clienteRepository.save(cliente);
        return convertirARespuesta(clienteGuardado);
    }

    @Override
    public boolean validarCredenciales(String email, String contrasena) {
        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);
        if (clienteOptional.isEmpty()) {
            return false;
        }
        return passwordEncoder.matches(contrasena, clienteOptional.get().getContrasena());
    }

    @Override
    public ClienteResponse obtenerClientePorEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertirARespuesta(cliente);
    }

    @Override
    public ClienteResponse obtenerClientePorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertirARespuesta(cliente);
    }

    @Override
    public void actualizarCliente(Integer id, ClienteRequest clienteRequest) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (clienteRequest.getNombreCompleto() != null) {
            cliente.setNombreCompleto(clienteRequest.getNombreCompleto());
        }
        if (clienteRequest.getTelefono() != null) {
            cliente.setTelefono(clienteRequest.getTelefono());
        }
        if (clienteRequest.getContrasena() != null) {
            cliente.setContrasena(passwordEncoder.encode(clienteRequest.getContrasena()));
        }

        clienteRepository.save(cliente);
    }

    @Override
    public void eliminarCliente(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }

    private ClienteResponse convertirARespuesta(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setIdCliente(cliente.getIdCliente());
        response.setNombreCompleto(cliente.getNombreCompleto());
        response.setEmail(cliente.getEmail());
        response.setTelefono(cliente.getTelefono());
        response.setFechaNacimiento(cliente.getFechaNacimiento());
        return response;
    }
}