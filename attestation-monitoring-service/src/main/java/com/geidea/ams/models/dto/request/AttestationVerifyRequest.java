package com.geidea.ams.models.dto.request;

import lombok.Data;

@Data
public class AttestationVerifyRequest {
    private String deviceId;
    private boolean isDebugMode;
    private boolean isUsbDebugger;
    private String integrityToken;
    private String[] keyAttestation;
    private String appPackageName;
    private int appVersionCode;
    private int androidVersion;

}
