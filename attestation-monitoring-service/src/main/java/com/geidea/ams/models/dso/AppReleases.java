package com.geidea.ams.models.dso;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "app_releases")
public class AppReleases {
    @Id
    private ObjectId id;
    private ObjectId appId;
    private String packageName;
    private int versionCode;
    private String versionName;
    @Setter(AccessLevel.NONE)
    private Instant createdTime;
    private Instant releasedAt;
}