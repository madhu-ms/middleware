package com.geidea.tms.service;

import com.geidea.tms.model.dto.request.TxnRequest;
import com.geidea.tms.model.dto.response.BaseResponse;
import com.geidea.tms.model.dto.response.TxnResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="transactions-service")
public interface TxnServiceClient {

    @PostMapping("/txn/list")
    public BaseResponse<List<TxnResponse>> list(@RequestBody TxnRequest txnRequest);
}