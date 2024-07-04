package com.geidea.tms.model.dao;

import com.geidea.tms.model.dso.Terminal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TerminalRepository  extends MongoRepository<Terminal, ObjectId> {
    Optional<Terminal> findByUserName(String userName);

    Optional<Terminal> findByTid(String terminalId);
}
