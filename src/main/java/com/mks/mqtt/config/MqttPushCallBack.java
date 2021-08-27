package com.mks.mqtt.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mks.mqtt.entity.MqttParseRuleEntity;
import com.mks.mqtt.model.mqtt.MksData;
import com.mks.mqtt.service.IMqttParseRuleService;
import com.mks.mqtt.util.ParseMksMqttUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhb
 */
@Configuration
@Slf4j
public class MqttPushCallBack implements MqttCallback {
    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    IMqttParseRuleService mqttParseRuleService;

    @Override
    public void connectionLost(Throwable throwable) {
        log.error("mqtt链接丢失");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        log.info("接收消息主题:" + topic);
        log.info("接收消息Qos：" + mqttMessage.getQos());
        log.info("接收消息内容：" + new String(mqttMessage.getPayload()));
        MksData mksData = new MksData();
        String message = new String(mqttMessage.getPayload());
        int version = ParseMksMqttUtil.parseHexToInteger(message, 0, 4, null);
        /* 头 **/
        List<MqttParseRuleEntity> header = getByCmdAndVersion(0, version);
        mksData.parseHeader(message, header);

        switch (mksData.getCmd()){
            case 1:
                /* 簇 **/
                List<MqttParseRuleEntity> cluster = getByCmdAndVersion(1, version);
                mksData.parse(message, cluster);
                break;
            case 2:
                /* 堆 **/
                List<MqttParseRuleEntity> stack = getByCmdAndVersion(2, version);
                mksData.parse(message, stack);
                break;
            default:
                /* 其他 **/
                break;
        }
        mksData.put("createTime", LocalDateTime.now());

        JSONObject ret = mongoTemplate.insert(mksData, "mqtt_history_data");
        log.info(ret.toString());
    }

    private List<MqttParseRuleEntity> getByCmdAndVersion(int value, int version){
        QueryWrapper<MqttParseRuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true, "cmd", value)
                .eq(true, "version", version)
                .eq(false, "is_delete", 1);
        return mqttParseRuleService.list(queryWrapper);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.debug("deliveryComplete:" + iMqttDeliveryToken.isComplete());
    }
}
