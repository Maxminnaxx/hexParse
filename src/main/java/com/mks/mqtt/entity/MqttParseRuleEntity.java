package com.mks.mqtt.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mybatis-plus-generator:3.4.1
 * @since 2021-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mqtt_parse_rule")
@ApiModel(value="MqttParseRuleEntity对象", description="")
public class MqttParseRuleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段中文名")
    private String fieldNameCn;

    @ApiModelProperty(value = "cmd帧0代表为头文件")
    private Integer cmd;

    @ApiModelProperty(value = "字段在hex字符串中的位置，pos>=0表示正序，pos<0表示倒序。数据帧的位置信息从0开始")
    private Integer pos;

    @ApiModelProperty(value = "字段在hex字符串中的长度，length = Byte数 * 2")
    private Integer length;

    @ApiModelProperty(value = "字段的解析方式：null = 不处理；hex = 转10进制数字；ascii = 转ascii字符；string = 直读成字符串；bit = 按bit位读")
    private String model;

    @ApiModelProperty(value = "偏移量，减法")
    private Long valueOffset;

    @ApiModelProperty(value = "倍率，乘法")
    private BigDecimal multiply;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "版本控制")
    @Version
    private Integer version;

    private Integer isDelete;


}
