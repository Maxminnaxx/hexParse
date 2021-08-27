package com.mks.mqtt.model.mongo;

import com.mks.mqtt.model.mqtt.MksData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhb
 */
@Repository
public interface SaveData extends MongoRepository<MksData, String> {
}
