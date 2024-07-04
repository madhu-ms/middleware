package com.geidea.ams.attestation.playintegrity;

import lombok.Data;

/**
 * Created by sridhar on 31/03/23
 **/
@Data
public class IntegrityPayload {
    private RequestDetails requestDetails;
    private AppIntegrity appIntegrity;
    private DeviceIntegrity deviceIntegrity;
    private AccountDetails accountDetails;
}
