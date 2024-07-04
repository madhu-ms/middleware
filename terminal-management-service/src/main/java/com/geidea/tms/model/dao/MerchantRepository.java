package com.geidea.tms.model.dao;

import com.geidea.tms.model.dso.Merchant;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MerchantRepository  extends MongoRepository<Merchant, ObjectId> {
    Optional<Merchant> findByMid(String merchantId);
}
