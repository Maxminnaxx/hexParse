package com.mks.mqtt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhb
 */
@SpringBootApplication
@MapperScan("com.mks.mqtt.mapper")
public class MqttParseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqttParseApplication.class, args);
    }

}
