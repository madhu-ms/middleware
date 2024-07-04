package com.geidea.transactions.service;


import com.geidea.transactions.model.dto.request.TxnRequest;
import com.geidea.transactions.model.dto.response.BaseResponse;
import com.geidea.transactions.model.dto.response.TxnResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TxnService {


    BaseResponse<TxnResponse> addTxn(TxnRequest txnRequest);

    BaseResponse<List<TxnResponse>> fetchAllTxn(TxnRequest txnRequest);

}
