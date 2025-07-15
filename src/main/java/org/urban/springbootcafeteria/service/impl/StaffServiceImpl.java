package org.urban.springbootcafeteria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.dto.request.StaffRequest;
import org.urban.springbootcafeteria.dto.request.LoginRequest;
import org.urban.springbootcafeteria.dto.response.AuthStaffResponse;
import org.urban.springbootcafeteria.dto.response.StaffResponse;
import org.urban.springbootcafeteria.entitie.Staff;
import org.urban.springbootcafeteria.entitie.Rol;
import org.urban.springbootcafeteria.repository.StaffRepository;
import org.urban.springbootcafeteria.repository.RolRepository;
import org.urban.springbootcafeteria.security.JwtUtil;
import org.urban.springbootcafeteria.service.StaffService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String registrar(StaffRequest request) {
        if (staffRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Staff staff = new Staff();
        staff.setNombreCompleto(request.getNombreCompleto());
        staff.setCorreo(request.getCorreo());
        staff.setContrasena(passwordEncoder.encode(request.getContrasena()));
        staff.setActivo(true);

        Rol rol = rolRepository.findByNombre(request.getRol());
        staff.setRol(rol);

        staffRepository.save(staff);
        return "Staff registrado correctamente";
    }

    @Override
    public AuthStaffResponse login(LoginRequest request) {
        Staff staff = staffRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (!staff.isActivo()) {
            throw new BadCredentialsException("La cuenta está bloqueada. Contacte al administrador.");
        }

        if (!passwordEncoder.matches(request.getContrasena(), staff.getContrasena())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        String rol = staff.getRol().getNombre();
        String token = jwtUtil.generateToken(staff.getCorreo(), rol);
        StaffResponse staffResponse = convertToResponse(staff);
        AuthStaffResponse response = new AuthStaffResponse();
        response.setMensaje("Login exitoso");
        response.setToken(token);
        response.setRol(rol);
        response.setStaff(staffResponse);
        return response;
    }

    // Eliminado el método crear porque /registro lo cubre
    // @Override
    // public Integer crear(StaffRequest request) {
    //     Staff staff = new Staff();
    //     staff.setNombreCompleto(request.getNombreCompleto());
    //     staff.setCorreo(request.getCorreo());
    //     staff.setContrasena(passwordEncoder.encode(request.getContrasena()));
    //     staff.setActivo(true);
    //     Rol rol = rolRepository.findByNombre(request.getRol());
    //     staff.setRol(rol);

    //     staffRepository.save(staff);
    //     return staff.getId();
    // }

    @Override
    public StaffResponse obtenerPorId(Integer id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff no encontrado"));
        return convertToResponse(staff);
    }

    @Override
    public StaffResponse actualizar(Integer id, StaffRequest request) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff no encontrado"));

        if (!staff.getCorreo().equals(request.getCorreo()) &&
                staffRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese correo");
        }

        staff.setNombreCompleto(request.getNombreCompleto());
        staff.setCorreo(request.getCorreo());
        if (request.getContrasena() != null && !request.getContrasena().isEmpty()) {
            staff.setContrasena(passwordEncoder.encode(request.getContrasena()));
        }

        Rol rol = rolRepository.findByNombre(request.getRol());
        staff.setRol(rol);

        staffRepository.save(staff);
        return convertToResponse(staff);
    }

    @Override
    public void cambiarEstado(Integer id, boolean activo) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff no encontrado"));
        staff.setActivo(activo);
        staffRepository.save(staff);
    }

    @Override
    public void eliminar(Integer id) {
        staffRepository.deleteById(id);
    }

    @Override
    public List<StaffResponse> listarTodos() {
        List<Staff> staffs = staffRepository.findAll();
        if (staffs.isEmpty()) {
            throw new RuntimeException("No se encontraron staffs");
        }
        return staffs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private StaffResponse convertToResponse(Staff staff) {
        StaffResponse response = new StaffResponse();
        response.setId(staff.getId());
        response.setNombreCompleto(staff.getNombreCompleto());
        response.setCorreo(staff.getCorreo());
        response.setRol(staff.getRol() != null ? staff.getRol().getNombre() : null);
        response.setActivo(staff.isActivo());
        return response;
    }
}
