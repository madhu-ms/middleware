package com.geidea.ams.models.dso;

import com.geidea.ams.enums.AppRandomizationEvent;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "app_random_entries")
public class AppRandomEntries {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String randomValue;
    private String deviceId;
    private AppRandomizationEvent appRandomizationEvent;

}