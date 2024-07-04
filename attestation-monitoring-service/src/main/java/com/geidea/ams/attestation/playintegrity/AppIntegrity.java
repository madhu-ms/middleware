package com.geidea.ams.attestation.playintegrity;

import lombok.Data;

/**
 * Created by sridhar on 31/03/23
 **/
@Data
public class AppIntegrity {
    private String appRecognitionVerdict;
    private String packageName;
}
