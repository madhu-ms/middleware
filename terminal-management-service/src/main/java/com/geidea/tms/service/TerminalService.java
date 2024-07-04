package com.geidea.tms.service;

import com.geidea.tms.model.dto.request.LoginRequest;
import com.geidea.tms.model.dto.request.RegisterRequest;
import com.geidea.tms.model.dto.request.TxnRequest;
import com.geidea.tms.model.dto.response.BaseResponse;
import com.geidea.tms.model.dto.response.LoginResponse;
import com.geidea.tms.model.dto.response.RegisterResponse;
import com.geidea.tms.model.dto.response.TxnResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TerminalService {


    BaseResponse<RegisterResponse> register(RegisterRequest registerRequest);

    BaseResponse<LoginResponse> login(LoginRequest loginRequest);

     BaseResponse<List<TxnResponse>> list(TxnRequest txnRequest);
}
