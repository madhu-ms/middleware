package com.geidea.ams.services.impl;

import com.geidea.ams.models.dao.AppReleasesRepository;
import com.geidea.ams.models.dao.AppsRepository;
import com.geidea.ams.models.dso.Apps;
import com.geidea.ams.models.dto.request.AttestationVerifyRequest;
import com.geidea.ams.models.dto.response.AttestationVerifyResponse;
import com.geidea.ams.models.dto.response.BaseResponse;
import com.geidea.ams.utils.GeideaCodes;
import com.geidea.ams.services.AttestationService;
import com.geidea.ams.services.IntegrityVerifyService;
import com.geidea.ams.services.KeyAttestationService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttestationServiceImpl implements AttestationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AppsRepository appsRepository;
    private final AppReleasesRepository appReleasesRepository;
    private final IntegrityVerifyService integrityVerifyService;
    private final KeyAttestationService keyAttestationService;

    @Autowired
    public AttestationServiceImpl(AppsRepository appsRepository, AppReleasesRepository appReleasesRepository, IntegrityVerifyService integrityVerifyService, KeyAttestationService keyAttestationService) {
        this.appsRepository = appsRepository;
        this.appReleasesRepository = appReleasesRepository;
        this.integrityVerifyService = integrityVerifyService;
        this.keyAttestationService = keyAttestationService;
    }


    @Override
    public BaseResponse<AttestationVerifyResponse> verify(AttestationVerifyRequest attestationVerifyRequest) {

        if (StringUtils.isEmpty(attestationVerifyRequest.getAppPackageName())) {
            return new BaseResponse<>(GeideaCodes.INVALID_APP_PACKAGE_NAME, null);
        }

        Optional<Apps> apps = appsRepository.findByPackageName(attestationVerifyRequest.getAppPackageName());

        if (apps.isEmpty()) {
            return new BaseResponse<>(GeideaCodes.INVALID_APP_PACKAGE_NAME, null);
        }

//        if (apps.get().getsupportosversions() != null && !apps.get().getsupportosversions().contains(attestationverifyrequest.getandroidversion())) {
//            return new baseresponse<>(geideacodes.os_version_not_supported, null);
//        }
        GeideaCodes tokenVerificationResponse = integrityVerifyService.verifyToken(attestationVerifyRequest.getIntegrityToken(), attestationVerifyRequest.getAppPackageName(), attestationVerifyRequest.getDeviceId());
        logger.info("integrity verification result - " + tokenVerificationResponse.getDescription());

        if (tokenVerificationResponse != GeideaCodes.SUCCESS) {
            return new BaseResponse<>(GeideaCodes.INTEGRITY_FAILED, null);
        }

        if (!keyAttestationService.verifyKeyAttestation(attestationVerifyRequest.getKeyAttestation(), attestationVerifyRequest.getDeviceId())) {
            return new BaseResponse<>(GeideaCodes.KEY_ATTESTATION_FAILED, null);
        }

        return new BaseResponse<>(GeideaCodes.SUCCESS, null);
    }
}
