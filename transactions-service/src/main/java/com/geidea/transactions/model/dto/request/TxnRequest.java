package com.geidea.transactions.model.dto.request;

import lombok.Data;

@Data
public class TxnRequest {

    private String merchantId;
    private String terminalId;
    private Double amount;

}
