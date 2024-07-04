package com.geidea.tms.service.impl;

import com.geidea.tms.model.dao.MerchantRepository;
import com.geidea.tms.model.dao.SessionRepository;
import com.geidea.tms.model.dao.TerminalRepository;
import com.geidea.tms.model.dso.Merchant;
import com.geidea.tms.model.dso.Session;
import com.geidea.tms.model.dso.Terminal;
import com.geidea.tms.model.dto.request.LoginRequest;
import com.geidea.tms.model.dto.request.RegisterRequest;
import com.geidea.tms.model.dto.request.TxnRequest;
import com.geidea.tms.model.dto.response.BaseResponse;
import com.geidea.tms.model.dto.response.LoginResponse;
import com.geidea.tms.model.dto.response.RegisterResponse;
import com.geidea.tms.model.dto.response.TxnResponse;
import com.geidea.tms.security.JwtUtils;
import com.geidea.tms.service.TerminalService;
import com.geidea.tms.service.TxnServiceClient;
import com.geidea.tms.utils.ResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class TerminalServiceImpl implements TerminalService {

    private final MerchantRepository merchantRepository;
    private final TerminalRepository terminalRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionRepository sessionRepository;
    private final JwtUtils jwtUtils;
    private final TxnServiceClient txnServiceClient;
@Autowired
    public TerminalServiceImpl(MerchantRepository merchantRepository, TerminalRepository terminalRepository, BCryptPasswordEncoder bCryptPasswordEncoder, SessionRepository sessionRepository, JwtUtils jwtUtils, TxnServiceClient txnServiceClient) {
        this.merchantRepository = merchantRepository;
        this.terminalRepository = terminalRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionRepository = sessionRepository;
        this.jwtUtils = jwtUtils;
    this.txnServiceClient = txnServiceClient;
}


    @Override
    public BaseResponse<RegisterResponse> register(RegisterRequest registerRequest) {
        Merchant merchant;
        Optional<Merchant> merchantOptional = merchantRepository.findByMid(registerRequest.getMerchantId());
        if (merchantOptional.isEmpty()) {
            merchant = new Merchant();
            merchant.setMid(registerRequest.getMerchantId());
            merchant = merchantRepository.save(merchant);
        } else {
            merchant = merchantOptional.get();
        }

        Terminal terminal;
        Optional<Terminal> terminalOptional = terminalRepository.findByTid(registerRequest.getTerminalId());
        if (terminalOptional.isEmpty()) {
            terminal = new Terminal();
            terminal.setTid(registerRequest.getTerminalId());
            terminal.setUserName(registerRequest.getPhoneNumber());
            terminalRepository.save(terminal);
        } else {
            terminal = terminalOptional.get();
        }
        String AB = "0123456789";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUserName(terminal.getUserName());
        registerResponse.setMpin(sb.toString());
        System.out.println("---otp---" + sb.toString());
        terminal.setMpin(bCryptPasswordEncoder.encode(sb.toString()));
        terminalRepository.save(terminal);
        return new BaseResponse<>(ResponseCodes.SUCCESS, registerResponse);
    }

//    @Override
//    public BaseResponse<List<TxnWrapper>> fetchAllTxn(FetchAllTxnRequest fetchAllTxnRequest) {
//        return new BaseResponse<>(ResponseCodes.SUCCESS, txnInterface.fetchAll(fetchAllTxnRequest));
//    }

    @Override
    public BaseResponse<LoginResponse> login(LoginRequest loginRequest) {
        Optional<Terminal> terminalOptional = terminalRepository.findByUserName(loginRequest.getUserName());
        if (terminalOptional.isEmpty()) {
            return new BaseResponse<>(ResponseCodes.TERMINAL_NOT_FOUND, null);
        }
        Terminal terminal = terminalOptional.get();
        if (!bCryptPasswordEncoder.matches(loginRequest.getMpin(), terminal.getMpin())) {
            return new BaseResponse<>(ResponseCodes.MPIN_MISMATCH, null);
        }

        Optional<Session> sessionCheck;
        Session session = new Session();
        String token;
        sessionCheck = sessionRepository.findByUserName(loginRequest.getUserName());
        if (sessionCheck.isPresent()) {
            session = sessionCheck.get();
        }
        session.setUserName(loginRequest.getUserName());
        token = jwtUtils.generateJwtToken(loginRequest.getUserName());
        session.setToken(token);
        session.setExpiryTime(Instant.now().plus(1, ChronoUnit.HOURS));
        sessionRepository.save(session);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return new BaseResponse<>(ResponseCodes.SUCCESS, loginResponse);
    }


    @Override
    public BaseResponse<List<TxnResponse>> list(TxnRequest txnRequest) {
        Optional<Terminal> terminalOptional = terminalRepository.findByTid(txnRequest.getTerminalId());
        if (terminalOptional.isEmpty()) {
            return new BaseResponse<>(ResponseCodes.TERMINAL_NOT_FOUND, null);
        }
        return txnServiceClient.list(txnRequest);
    }
}
