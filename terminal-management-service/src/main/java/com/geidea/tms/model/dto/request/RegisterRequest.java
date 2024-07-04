package com.geidea.tms.model.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {

    private String merchantId;
    private String terminalId;
    private String phoneNumber;

}
