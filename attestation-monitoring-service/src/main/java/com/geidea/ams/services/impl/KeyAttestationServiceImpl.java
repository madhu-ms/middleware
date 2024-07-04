package com.geidea.ams.services.impl;

import com.geidea.ams.android.ParsedAttestationRecord;
import com.geidea.ams.android.RootOfTrust;
import com.geidea.ams.attestation.KeyAttestationVerifier;
import com.geidea.ams.enums.AppRandomizationEvent;
import com.geidea.ams.models.dao.AppRandomEntriesRepository;
import com.geidea.ams.models.dso.AppRandomEntries;
import com.geidea.ams.services.KeyAttestationService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KeyAttestationServiceImpl implements KeyAttestationService {
    private static final Logger LOG = LoggerFactory.getLogger(KeyAttestationServiceImpl.class);
    private final AppRandomEntriesRepository appRandomEntriesRepository;

    @Autowired
    public KeyAttestationServiceImpl(
            AppRandomEntriesRepository appRandomEntriesRepository) {
        this.appRandomEntriesRepository = appRandomEntriesRepository;
    }

    @Override
    public boolean verifyKeyAttestation(String[] keyAttestation,
                                        String deviceId) {
        ParsedAttestationRecord keyAttest = KeyAttestationVerifier.verifyKeyAttestation(keyAttestation);
        if (keyAttest == null) {
            LOG.info("key attest parsing error");
            return false;
        }
        if (keyAttest.keymasterSecurityLevel == ParsedAttestationRecord.SecurityLevel.SOFTWARE) {
            LOG.info("key master security level software");
            return false;
        }
        if (keyAttest.attestationSecurityLevel == ParsedAttestationRecord.SecurityLevel.SOFTWARE) {
            LOG.info("attestation security level software");
            return false;
        }
        String challenge = Base64.encodeBase64String(keyAttest.attestationChallenge);
        Optional<AppRandomEntries> optionalAppRandomEntries = appRandomEntriesRepository.findByRandomValue(challenge);
        if (optionalAppRandomEntries.isPresent()) {
            LOG.info("Duplicate random value received from application");
            return false;
        }
        AppRandomEntries appRandomEntries = new AppRandomEntries();
        appRandomEntries.setRandomValue(challenge);
        appRandomEntries.setAppRandomizationEvent(AppRandomizationEvent.KEY_ATTESTATION);
        appRandomEntries.setDeviceId(deviceId);
        appRandomEntriesRepository.save(appRandomEntries);

        if (keyAttest.softwareEnforced.attestationApplicationId.isEmpty() && keyAttest.teeEnforced.attestationApplicationId.isEmpty()) {
            LOG.info("attestation application id not present");
            return false;
        }
        if (keyAttest.teeEnforced.attestationApplicationId.isPresent()) {
            for (byte[] digest : keyAttest.teeEnforced.attestationApplicationId.get().signatureDigests) {
                LOG.info("tee attestation application digest {}", Base64.encodeBase64String(digest));
            }
        } else {
            for (byte[] digest : keyAttest.softwareEnforced.attestationApplicationId.get().signatureDigests) {
                LOG.info("software attestation application digest {}", Base64.encodeBase64String(digest));
            }
        }
        if (keyAttest.teeEnforced.rootOfTrust.isEmpty()) {
            LOG.info("root of trust not present");
            return false;
        }
        if (!keyAttest.teeEnforced.rootOfTrust.get().deviceLocked) {
            LOG.info("device not locked");
            return false;
        }
        if (keyAttest.teeEnforced.rootOfTrust.get().verifiedBootState != RootOfTrust.VerifiedBootState.VERIFIED) {
            LOG.info("device boot state not verified");
            return false;
        }
        return true;
    }
}