package com.geidea.transactions.model.dso;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection="txn")
public class Txn {
    @Id
    private ObjectId id;
    private String mid;
    private String tid;
    private Double amount;
    @Setter(AccessLevel.NONE)
    private Instant createdTime;}
