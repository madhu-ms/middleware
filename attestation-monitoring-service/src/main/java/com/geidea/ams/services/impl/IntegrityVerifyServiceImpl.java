package com.geidea.ams.services.impl;

import com.geidea.ams.attestation.playintegrity.IntegrityPayload;
import com.geidea.ams.enums.AppRandomizationEvent;
import com.geidea.ams.models.dao.AppRandomEntriesRepository;
import com.geidea.ams.models.dao.AppsRepository;
import com.geidea.ams.models.dso.AppRandomEntries;
import com.geidea.ams.models.dso.Apps;
import com.geidea.ams.utils.GeideaCodes;
import com.geidea.ams.services.IntegrityVerifyService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwx.JsonWebStructure;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;

@Transactional
@Service
public class IntegrityVerifyServiceImpl implements IntegrityVerifyService {
    private final Gson gson;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AppsRepository appsRepository;
    private final AppRandomEntriesRepository appRandomEntriesRepository;

    @Autowired
    public IntegrityVerifyServiceImpl(
                                      Gson gson,
                                      AppsRepository appsRepository,
                                      AppRandomEntriesRepository appRandomEntriesRepository) {
        this.gson = gson;
        this.appsRepository = appsRepository;
        this.appRandomEntriesRepository = appRandomEntriesRepository;
    }

    @Override
    public GeideaCodes verifyToken(String token,
                                   String packageName,
                                   String deviceId) {

        Optional<Apps> apps = appsRepository.findByPackageName(packageName);
        if (apps.isEmpty()) {
            return GeideaCodes.INVALID_APP_PACKAGE_NAME;
        }

        if (StringUtils.isEmpty(apps.get().getIntegrityDecryptionKey())) {
            return GeideaCodes.APP_DECRYPTION_KEY_NOT_FOUND;
        }
        if (StringUtils.isEmpty(apps.get().getIntegrityVerificationKey())) {
            return GeideaCodes.APP_VERIFICATION_KEY_NOT_FOUND;
        }

        String base64OfEncodedDecryptionKey = apps.get().getIntegrityDecryptionKey();
        String base64OfEncodedVerificationKey = apps.get().getIntegrityVerificationKey();
        try {
            byte[] decryptionKeyBytes = Base64.decode(base64OfEncodedDecryptionKey);
            SecretKey decryptionKey = new SecretKeySpec(decryptionKeyBytes, 0, decryptionKeyBytes.length, "AES");
            byte[] encodedVerificationKey = Base64.decode(base64OfEncodedVerificationKey);
            PublicKey verificationKey = KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(encodedVerificationKey));

            JsonWebEncryption jwe = (JsonWebEncryption) JsonWebStructure.fromCompactSerialization(token);
            jwe.setKey(decryptionKey);

            String compactJws = jwe.getPayload();
            JsonWebSignature jws = (JsonWebSignature) JsonWebStructure.fromCompactSerialization(compactJws);
            jws.setKey(verificationKey);

            String payload = jws.getPayload();
            IntegrityPayload integrityPayload = gson.fromJson(payload, IntegrityPayload.class);

            if (integrityPayload == null) {
                return GeideaCodes.INVALID_INTEGRITY_TOKEN;
            }

            //Account Details
            if (!integrityPayload.getAccountDetails().getAppLicensingVerdict().equalsIgnoreCase("LICENSED")) {
                return GeideaCodes.INVALID_INTEGRITY_LICENSE;
            }

            //Request Details
            if (!integrityPayload.getRequestDetails().getRequestPackageName().equalsIgnoreCase(apps.get().getPackageName())) {
                return GeideaCodes.INTEGRITY_PACKAGE_MISMATCH;
            }

            String nonce = integrityPayload.getRequestDetails().getNonce()
                    .replace("_", "/")
                    .replace("-", "+");
            Optional<AppRandomEntries> optionalAppRandomEntries = appRandomEntriesRepository.findByRandomValue(nonce);
            if (optionalAppRandomEntries.isPresent()) {
                return GeideaCodes.INVALID_INTEGRITY_NONCE;
            }
            AppRandomEntries appRandomEntries = new AppRandomEntries();
            appRandomEntries.setRandomValue(nonce);
            appRandomEntries.setAppRandomizationEvent(AppRandomizationEvent.PLAY_INTEGRITY);
            appRandomEntries.setDeviceId(deviceId);
            appRandomEntriesRepository.save(appRandomEntries);

            //Application Integrity details
            if (integrityPayload.getAppIntegrity().getAppRecognitionVerdict().equalsIgnoreCase("UNEVALUATED")) {
                return GeideaCodes.INTEGRITY_APP_INTEGRITY_FAILED;
            }

            //Device Integrity details
            if (!integrityPayload.getDeviceIntegrity().getDeviceRecognitionVerdict().contains("MEETS_STRONG_INTEGRITY")) {
                return GeideaCodes.INTEGRITY_DEVICE_INTEGRITY_FAILED;
            }
            return GeideaCodes.SUCCESS;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            return GeideaCodes.INTEGRITY_FAILED;
        } catch (JoseException e) {
            logger.error(e.getMessage());
            return GeideaCodes.INTEGRITY_DEVICE_INTEGRITY_FAILED;
        }
    }
    }