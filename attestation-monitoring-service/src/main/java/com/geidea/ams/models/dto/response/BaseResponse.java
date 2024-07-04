package com.geidea.ams.models.dto.response;

import com.geidea.ams.utils.GeideaCodes;
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

    public BaseResponse(GeideaCodes geideaCodes, Object data) {
        this.responseCode = geideaCodes.getCode();
        this.data = data;
        this.responseMessage = geideaCodes.getDescription();
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