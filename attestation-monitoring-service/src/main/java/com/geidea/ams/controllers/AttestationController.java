package com.geidea.ams.controllers;

import com.geidea.ams.models.dto.request.AttestationVerifyRequest;
import com.geidea.ams.models.dto.response.AttestationVerifyResponse;
import com.geidea.ams.models.dto.response.BaseResponse;
import com.geidea.ams.services.AttestationService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("attestationVerifier")
public class AttestationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AttestationService attestationService;
    private final Gson gson;

    @Autowired
    public AttestationController(AttestationService attestationService, Gson gson) {
        this.attestationService = attestationService;
        this.gson = gson;
    }

    @RequestMapping("/verify")
    public BaseResponse<AttestationVerifyResponse> verify(@RequestBody(required = false) AttestationVerifyRequest attestationVerifyRequest) {
        logger.info("verify -> request -- " + gson.toJson(attestationVerifyRequest));

        BaseResponse<AttestationVerifyResponse> response = attestationService.verify(attestationVerifyRequest);

        logger.info("verify -> response -- " + gson.toJson(response));
        return response;
    }
}
