package com.mks.mqtt.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhb
 */
@Component
@ConfigurationProperties(prefix = "mksmqtt")
@Data
public class MqttProperty {

    private String url;

    private String topic;
}
