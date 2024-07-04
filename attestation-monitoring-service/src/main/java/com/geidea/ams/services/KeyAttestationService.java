package com.geidea.ams.services;

import org.springframework.stereotype.Component;

@Component
public interface KeyAttestationService {
    public boolean verifyKeyAttestation(String[] keyAttestation, String deviceId);
}