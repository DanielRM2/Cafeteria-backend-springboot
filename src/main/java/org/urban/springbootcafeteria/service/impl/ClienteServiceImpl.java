package org.urban.springbootcafeteria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.dto.request.ClienteRequest;
import org.urban.springbootcafeteria.dto.request.LoginRequest;
import org.urban.springbootcafeteria.dto.response.AuthClienteResponse;
import org.urban.springbootcafeteria.dto.response.ClienteResponse;
import org.urban.springbootcafeteria.entitie.Cliente;
import org.urban.springbootcafeteria.entitie.Rol;
import org.urban.springbootcafeteria.repository.ClienteRepository;
import org.urban.springbootcafeteria.repository.RolRepository;
import org.urban.springbootcafeteria.security.JwtUtil;
import org.urban.springbootcafeteria.service.ClienteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String registrar(ClienteRequest request) {
        if (clienteRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNombreCompleto(request.getNombreCompleto());
        cliente.setCorreo(request.getCorreo());
        cliente.setContrasena(passwordEncoder.encode(request.getContrasena()));
        cliente.setTelefono(request.getTelefono());

        Rol rolCliente = rolRepository.findByNombre("CLIENTE");
        cliente.setRol(rolCliente);

        clienteRepository.save(cliente);
        return "Cliente registrado correctamente";
    }

    @Override
    public AuthClienteResponse login(LoginRequest request) {
        Cliente cliente = clienteRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if (!cliente.isActivo()) {
            throw new BadCredentialsException("La cuenta está bloqueada. Contacte al administrador.");
        }
        if (!passwordEncoder.matches(request.getContrasena(), cliente.getContrasena())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }
        String rol = cliente.getRol().getNombre();
        String token = jwtUtil.generateToken(cliente.getCorreo(), rol);
        ClienteResponse clienteResponse = mapToResponse(cliente);
        return new AuthClienteResponse("Login exitoso", token, rol, clienteResponse);
    }

    @Override
    public ClienteResponse obtenerPorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return mapToResponse(cliente);
    }

    @Override
    public ClienteResponse actualizar(Integer id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Verifica si el nuevo correo ya está en uso por otro cliente
        if (!cliente.getCorreo().equals(request.getCorreo()) &&
                clienteRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese correo");
        }

        cliente.setNombreCompleto(request.getNombreCompleto());
        cliente.setCorreo(request.getCorreo());
        cliente.setTelefono(request.getTelefono());

        if (request.getContrasena() != null && !request.getContrasena().isEmpty()) {
            cliente.setContrasena(passwordEncoder.encode(request.getContrasena()));
        }

        clienteRepository.save(cliente);
        return mapToResponse(cliente);
    }

    @Override
    public void eliminar(Integer id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void cambiarEstado(Integer id, boolean activo) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        cliente.setActivo(activo);
        clienteRepository.save(cliente);
    }

    private ClienteResponse mapToResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNombreCompleto(cliente.getNombreCompleto());
        response.setCorreo(cliente.getCorreo());
        response.setTelefono(cliente.getTelefono());
        response.setRol(cliente.getRol() != null ? cliente.getRol().getNombre() : null);
        response.setActivo(cliente.isActivo());
        return response;
    }
}