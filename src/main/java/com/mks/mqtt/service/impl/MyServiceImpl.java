package com.mks.mqtt.service.impl;

import com.mks.mqtt.config.MqttPushCallBack;
import com.mks.mqtt.config.properties.MqttProperty;
import com.mks.mqtt.dto.MyDto;
import com.mks.mqtt.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhb
 */
@Slf4j
@Service
public class MyServiceImpl implements MyService {
    @Resource
    MqttProperty mqttProperty;

    @Resource
    MqttPushCallBack mqCallback;

    private final static String CLIENT_ID = "myTestClient";

    @Override
    public MyDto func() {
        connect();
        MyDto myDto = MyDto.builder().first("makesns").second(60L).build();
        log.debug(myDto.toString());
        return myDto;
    }

    private Boolean connect(){
        boolean status = false;
        try {
            MqttClient mqttClient = new MqttClient(mqttProperty.getUrl(), CLIENT_ID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(30);
            options.setAutomaticReconnect(true);

            mqttClient.setCallback(mqCallback);
            mqttClient.connect(options);
            if (!mqttClient.isConnected()){
                log.info("mqtt链接"+ mqttProperty.getUrl() +"失败");
            }
            else {
                log.info("mqtt链接"+ mqttProperty.getUrl() +"成功");
                mqttClient.subscribe(mqttProperty.getTopic(), 2);
                status = true;
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return status;
    }
}
