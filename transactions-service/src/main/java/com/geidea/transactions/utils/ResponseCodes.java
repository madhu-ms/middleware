package com.geidea.transactions.utils;

public enum ResponseCodes {
    SUCCESS("3030", "Success"),
    FAILURE("3031", "Failure"),
    TERMINAL_NOT_FOUND("3036", "Terminal not found. Please add a terminal to enable user access"),
    ;

    private final String code;
    private final String description;

    ResponseCodes(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
