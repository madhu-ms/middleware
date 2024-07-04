package com.geidea.ams.models.dao;

import com.geidea.ams.models.dso.Apps;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppsRepository extends MongoRepository<Apps, ObjectId> {
    Optional<Apps> findByPackageName(String packageName);

}