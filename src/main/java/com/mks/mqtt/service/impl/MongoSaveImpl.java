package com.mks.mqtt.service.impl;

import com.mks.mqtt.model.mqtt.MksData;
import com.mks.mqtt.service.MongoSave;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhb
 */
@Service
public class MongoSaveImpl implements MongoSave {
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public MksData save(MksData mksData) {

        return mongoTemplate.save(mksData);
    }
}
