package com.mks.mqtt.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhb
 */
@Data
@Builder
public class MyDto {

    private String first;

    private Long second;
}
