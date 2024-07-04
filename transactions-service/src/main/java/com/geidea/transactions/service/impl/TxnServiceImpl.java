package com.geidea.transactions.service.impl;

import com.geidea.transactions.model.dao.TxnRepository;
import com.geidea.transactions.model.dso.Txn;
import com.geidea.transactions.model.dto.request.TxnRequest;
import com.geidea.transactions.model.dto.response.BaseResponse;
import com.geidea.transactions.model.dto.response.TxnResponse;
import com.geidea.transactions.service.TxnService;
import com.geidea.transactions.utils.ResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TxnServiceImpl implements TxnService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TxnRepository txnRepository;
    @Autowired
    public TxnServiceImpl(TxnRepository txnRepository) {
        this.txnRepository = txnRepository;
    }



    @Override
    public BaseResponse<TxnResponse> addTxn(TxnRequest txnRequest) {

        BaseResponse<TxnResponse> baseResponse = new BaseResponse<>();
        TxnResponse txnResponse = new TxnResponse();
        Txn txn = new Txn();
        txn.setMid(txnRequest.getMerchantId());
        txn.setTid(txnRequest.getTerminalId());
        txn.setAmount(txnRequest.getAmount());
        txnRepository.save(txn);

        txnResponse.setTid(txnRequest.getTerminalId());
        return new BaseResponse<>(ResponseCodes.SUCCESS, txnResponse);
    }

    @Override
    public BaseResponse<List<TxnResponse>> fetchAllTxn(TxnRequest txnRequest) {
        List<TxnResponse> txnResponsesList = new ArrayList<>();
        BaseResponse<List<TxnResponse>> response = new BaseResponse<>();

        List<Txn> txnList =  txnRepository.findByTid(txnRequest.getTerminalId());
        txnList.forEach(txn -> {
            try {
                TxnResponse txnResponse = new TxnResponse();
                txnResponse.setTid(txn.getTid());
                txnResponse.setAmount(txn.getAmount());
                txnResponsesList.add(txnResponse);
            } catch (Exception e) {
                logger.error("Exception : " + e);
            }
        });
        response.setData(txnResponsesList);
        response.setResponseCode(ResponseCodes.SUCCESS.getCode());
        response.setResponseMessage(ResponseCodes.SUCCESS.getDescription());
        return response;
    }
}
