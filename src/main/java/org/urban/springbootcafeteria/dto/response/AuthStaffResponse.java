package org.urban.springbootcafeteria.dto.response;

import lombok.Data;

@Data
public class AuthStaffResponse {
    private String mensaje;
    private String token;
    private String rol;
    private StaffResponse staff;
}
