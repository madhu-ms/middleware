package com.geidea.tms.model.dso;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "session")
public class Session {

    @Id
    private ObjectId id;
    private String userName;
    private String token;
    private Instant expiryTime;
    @Setter(AccessLevel.NONE)
    private Instant createdTime;
}
