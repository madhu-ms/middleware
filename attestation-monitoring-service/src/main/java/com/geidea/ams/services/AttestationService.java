package com.geidea.ams.services;

import com.geidea.ams.models.dto.request.AttestationVerifyRequest;
import com.geidea.ams.models.dto.response.AttestationVerifyResponse;
import com.geidea.ams.models.dto.response.BaseResponse;
import org.springframework.stereotype.Component;

@Component
public interface AttestationService {
    BaseResponse<AttestationVerifyResponse> verify(AttestationVerifyRequest attestationVerifyRequest);
}
