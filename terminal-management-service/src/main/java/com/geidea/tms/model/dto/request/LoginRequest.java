package com.geidea.tms.model.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String mpin;
}
