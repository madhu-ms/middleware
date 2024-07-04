package com.geidea.tms.model.dso;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="terminals")
public class Terminal {
    @Id
    private ObjectId id;
    private String mid;
    private String tid;
    private String phoneNumber;
    private String userName;
    private String mpin;
}
