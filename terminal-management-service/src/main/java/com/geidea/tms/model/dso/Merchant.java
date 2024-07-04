package com.geidea.tms.model.dso;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="merchants")
public class Merchant {
    @Id
    private ObjectId id;
    private String mid;
}