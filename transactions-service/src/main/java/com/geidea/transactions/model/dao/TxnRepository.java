package com.geidea.transactions.model.dao;

import com.geidea.transactions.model.dso.Txn;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TxnRepository  extends MongoRepository<Txn, ObjectId> {

    List<Txn> findByTid(String tid);
}
