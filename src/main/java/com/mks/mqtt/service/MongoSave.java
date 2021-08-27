package com.mks.mqtt.service;

import com.mks.mqtt.model.mqtt.MksData;

/**
 * @author zhb
 */
public interface MongoSave {

    /**
     * 存储mqtt数据
     * @param mksData
     * @return
     */
    MksData save(MksData mksData);
}
