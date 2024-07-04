package com.geidea.ams.utils;

public enum GeideaCodes {
    SUCCESS("3030", "Success"),
    FAILURE("3031", "Failure"),
    REQUIRES_APP_UPDATE("3032", "App update available"),
    OS_VERSION_NOT_SUPPORTED("3033", "OS version not supported"),
    OS_VERSION_NUMBER_NOT_FOUND("3034", "OS version number not found"),
    APP_DECRYPTION_KEY_NOT_FOUND("3035", "Integrity failed - Decryption key not found for package"),
    APP_VERIFICATION_KEY_NOT_FOUND("3036", "Integrity failed - Verification key not found for package"),
    INTEGRITY_TOKEN_IS_EMPTY("3037", "Integrity token field is empty"),
    INTEGRITY_FAILED("3038", "Integrity verification failed"),
    INVALID_INTEGRITY_TOKEN("3039", "Integrity failed - Invalid integrity token"),
    INVALID_INTEGRITY_LICENSE("3040", "Integrity failed - License is not valid"),
    INTEGRITY_PACKAGE_MISMATCH("3041", "Integrity failed - App package name mismatch"),
    INVALID_INTEGRITY_NONCE("3042", "Integrity failed - Invalid nonce found"),
    INTEGRITY_NONCE_EXPIRED("3043", "Integrity failed - Nonce expired"),
    INTEGRITY_APP_INTEGRITY_FAILED("3044", "Integrity failed - App integrity does not meet the requirements"),
    INTEGRITY_DEVICE_INTEGRITY_FAILED("3045", "Integrity failed - Device integrity does not meet the requirements"),
    KEY_ATTESTATION_IS_EMPTY("3046", "Attestation failed - Key attestation field is empty"),
    KEY_ATTESTATION_FAILED("3047", "Attestation failed - Key attestation failed"),
    DEBUG_MODE_ENABLED("3048", "Attestation failed - Debug mode is enabled"),
    USB_DEBUGGER_ENABLED("3049", "Attestation failed - USB Debugger is enabled"),
    INVALID_APP_PACKAGE_NAME("3050", "Invalid app package name");

    private final String code;
    private final String description;

    GeideaCodes(String code, String description) {
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
