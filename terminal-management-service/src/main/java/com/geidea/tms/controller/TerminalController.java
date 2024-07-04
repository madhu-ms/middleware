package com.geidea.tms.controller;

import com.geidea.tms.model.dto.request.LoginRequest;
import com.geidea.tms.model.dto.request.RegisterRequest;
import com.geidea.tms.model.dto.request.TxnRequest;
import com.geidea.tms.model.dto.response.BaseResponse;
import com.geidea.tms.model.dto.response.LoginResponse;
import com.geidea.tms.model.dto.response.RegisterResponse;
import com.geidea.tms.model.dto.response.TxnResponse;
import com.geidea.tms.service.TerminalService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terminal")
public class TerminalController {

    private TerminalService terminalService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Gson gson;

    @Autowired
    public TerminalController(TerminalService terminalService, Gson gson) {
        this.terminalService = terminalService;
        this.gson = gson;
    }

    @RequestMapping(value = "")
    public String hello(){
        return "hello world";
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        logger.info("Login Terminal -> Request-- "+gson.toJson(loginRequest));
        BaseResponse<LoginResponse> response  = terminalService.login(loginRequest);
        logger.info("Login Terminal -> response -- " + gson.toJson(response));
        return  response;

    }

    @PostMapping("/register")
    public BaseResponse<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        logger.info("Register Terminal -> Request -- " + gson.toJson(registerRequest));
        BaseResponse<RegisterResponse> response  = terminalService.register(registerRequest);
        logger.info("Register Terminal -> response -- " + gson.toJson(response));
        return  response;
    }

    @PostMapping("/txn/list")
    public BaseResponse<List<TxnResponse>> list(@RequestBody TxnRequest txnRequest) {
        logger.info("Txn  -> Request -- " + gson.toJson(txnRequest));
        BaseResponse<List<TxnResponse>> response  = terminalService.list(txnRequest);
        logger.info("Txn  -> response -- " + gson.toJson(response));
        return  response;
    }


}
