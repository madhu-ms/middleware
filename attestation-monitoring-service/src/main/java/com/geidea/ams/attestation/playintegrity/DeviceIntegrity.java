package com.geidea.ams.attestation.playintegrity;

import lombok.Data;

import java.util.List;

/**
 * Created by sridhar on 31/03/23
 **/
@Data
public class DeviceIntegrity {
    private List<String> deviceRecognitionVerdict;
}
