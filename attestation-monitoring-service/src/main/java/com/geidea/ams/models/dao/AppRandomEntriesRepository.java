package com.geidea.ams.models.dao;

import com.geidea.ams.models.dso.AppRandomEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppRandomEntriesRepository extends MongoRepository<AppRandomEntries, ObjectId> {
    Optional<AppRandomEntries> findByRandomValue(String randomValue);
}