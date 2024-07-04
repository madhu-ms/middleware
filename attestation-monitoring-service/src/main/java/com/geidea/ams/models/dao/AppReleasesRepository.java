package com.geidea.ams.models.dao;

import com.geidea.ams.models.dso.AppReleases;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppReleasesRepository extends MongoRepository<AppReleases, ObjectId> {
    Optional<AppReleases> findTopByOrderByReleasedAtDesc();

    Optional<AppReleases> findByAppId(ObjectId objectId);

    Optional<AppReleases> findByPackageNameAndVersionCode(String packageName, int versionCode);

    Optional<AppReleases> findByPackageNameAndVersionCodeAndVersionName(String packageName,
                                                                        int versionCode, String versionName);
}