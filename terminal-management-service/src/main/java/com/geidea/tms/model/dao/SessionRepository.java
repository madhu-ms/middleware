package com.geidea.tms.model.dao;

import com.geidea.tms.model.dso.Session;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SessionRepository  extends MongoRepository<Session, ObjectId> {
    Optional<Session> findByUserName(String userName);
}
