package com.geidea.ams.models.dso;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "apps")
public class Apps {
    @Id
    private ObjectId id;
    private String packageName;
    private List<Integer> supportOSVersions;
    private String integrityDecryptionKey;
    private String integrityVerificationKey;
}
