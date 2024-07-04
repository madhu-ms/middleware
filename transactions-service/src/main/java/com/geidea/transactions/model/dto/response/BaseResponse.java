package com.geidea.transactions.model.dto.response;

import com.geidea.transactions.utils.ResponseCodes;
import lombok.Data;

@Data
public class BaseResponse<Object> {
    String responseCode;
    Object data;
    String responseMessage;

    public BaseResponse(String responseCode, Object data, String responseMessage) {
        this.responseCode = responseCode;
        this.data = data;
        this.responseMessage = responseMessage;
    }

    public BaseResponse(ResponseCodes responseCodes, Object data) {
        this.responseCode = responseCodes.getCode();
        this.data = data;
        this.responseMessage = responseCodes.getDescription();
    }

    public BaseResponse(String code, String responseMessage) {
        this.responseCode = code;
        this.responseMessage = responseMessage;
    }

    public BaseResponse() {
        this.responseCode = "";
        this.data = null;
        this.responseMessage = "";
    }

}