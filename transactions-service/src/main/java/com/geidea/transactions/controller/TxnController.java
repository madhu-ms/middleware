package com.geidea.transactions.controller;

import com.geidea.transactions.model.dto.request.TxnRequest;
import com.geidea.transactions.model.dto.response.BaseResponse;
import com.geidea.transactions.model.dto.response.TxnResponse;
import com.geidea.transactions.service.TxnService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/txn")
public class TxnController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Gson gson;
    private final TxnService txnService;

    public TxnController(Gson gson, TxnService txnService) {
        this.gson = gson;
        this.txnService = txnService;
    }

    @RequestMapping(value = "")
    public String hello(){
        return "hello world";
    }


    @PostMapping("/addTxn")
    public BaseResponse<TxnResponse> addTxn(@RequestBody TxnRequest txnRequest) {
        logger.info("Txn -> Request -- " + gson.toJson(txnRequest));
        BaseResponse<TxnResponse> response  = txnService.addTxn(txnRequest);
        logger.info("Txn -> response -- " + gson.toJson(response));
        return  response;
    }


    @PostMapping("/list")
    public BaseResponse<List<TxnResponse>> list(@RequestBody TxnRequest txnRequest) {
        logger.info("Txn -> Request -- " + gson.toJson(txnRequest));
        BaseResponse<List<TxnResponse>> response  = txnService.fetchAllTxn(txnRequest);
        logger.info("Txn -> response -- " + gson.toJson(response));
        return  response;
    }


}
