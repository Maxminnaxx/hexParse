package com.mks.mqtt.model.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.mks.mqtt.entity.MqttParseRuleEntity;
import com.mks.mqtt.util.ParseMksMqttUtil;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhb
 */

@Document(collection = "mqtt_history_data")
@CompoundIndexes({
        @CompoundIndex(name = "SN_idx", def = "{'SN':1}"),
        @CompoundIndex(name = "ts_idx", def = "{'ts':-1}"),
})
@EqualsAndHashCode(callSuper = true)
public class MksData extends JSONObject implements Serializable {

    private static final long serialVersionUID = 1L;

    public String getDeviceId(){
        return this.getString("deviceId");
    }

    public Integer getCmd(){
        return this.getInteger("cmd");
    }

    public long getTs(){
        return this.getLong("ts");
    }

    public Integer getBatteryCount(){
        return this.getInteger("batteryCount");
    }

    public void parseHeader(String message, List<MqttParseRuleEntity> entityList){

        List<MqttParseRuleEntity> big = entityList.parallelStream().filter(mqttParseRuleEntity -> mqttParseRuleEntity.getPos() >= 0).sorted(Comparator.comparing(MqttParseRuleEntity::getPos)).collect(Collectors.toList());
        setData(message, big, 0);

        List<MqttParseRuleEntity> small = entityList.parallelStream().filter(mqttParseRuleEntity -> mqttParseRuleEntity.getPos() < 0).sorted(Comparator.comparing(MqttParseRuleEntity::getPos).reversed()).collect(Collectors.toList());
        setData(message, small, 0);

    }

    public void parse(String message, List<MqttParseRuleEntity> entityList){

        List<MqttParseRuleEntity> big = entityList.parallelStream().filter(mqttParseRuleEntity -> mqttParseRuleEntity.getPos() >= 0).sorted(Comparator.comparing(MqttParseRuleEntity::getPos)).collect(Collectors.toList());
        setData(message, big, 52);

        List<MqttParseRuleEntity> small = entityList.parallelStream().filter(mqttParseRuleEntity -> mqttParseRuleEntity.getPos() < 0).sorted(Comparator.comparing(MqttParseRuleEntity::getPos).reversed()).collect(Collectors.toList());
        setData(message, small, 52);

    }
    private void setData(String message, List<MqttParseRuleEntity> entityList, int pos){
        int nav = message.length();
        for(MqttParseRuleEntity entity : entityList){
            int start = entity.getPos() < 0? nav - entity.getLength() : pos;
            if (entity.getModel() == null){
                if (entity.getPos() < 0){
                    nav -= entity.getLength();
                }
                else {
                    pos += entity.getLength();
                }
                continue;
            }
            switch (entity.getModel()){
                case "hex":
                    if (entity.getMultiply()!= null){
                        if (entity.getFieldName().equals("battery")){
                            int batteryCount = this.getBatteryCount();
                            for (int i = 0; i < batteryCount; i++){
                                BigDecimal value = ParseMksMqttUtil.parseHexToBigDecimal(message, start, 4, entity.getValueOffset(), entity.getMultiply());
                                this.put(entity.getFieldName() + i, value);
                                start += 4;
                            }
                            pos += batteryCount * 4;
                        }
                        else {
                            BigDecimal value = ParseMksMqttUtil.parseHexToBigDecimal(message, start, entity.getLength(), entity.getValueOffset(), entity.getMultiply());
                            this.put(entity.getFieldName(), value);
                        }
                    }
                    else if (entity.getLength() <= 4){
                        Integer value = ParseMksMqttUtil.parseHexToInteger(message, start, entity.getLength(), entity.getValueOffset() !=null?Math.toIntExact(entity.getValueOffset()):null);
                        this.put(entity.getFieldName(), value);
                    }
                    else {
                        Long value = ParseMksMqttUtil.parseHexToLong(message, start, entity.getLength(), entity.getValueOffset());
                        this.put(entity.getFieldName(), value);
                    }
                    break;
                case "string":
                    if (entity.getFieldName().equals("ts")){
                        LocalDateTime value = ParseMksMqttUtil.parseHexToTime(message, start, entity.getLength());
                        this.put(entity.getFieldName(), value);
                    }
                    else {
                        this.put(entity.getFieldName(), ParseMksMqttUtil.parseHexToString(message, start, entity.getLength()));
                    }
                    break;
                case "ascii":
                    String ascii = ParseMksMqttUtil.parseHexToAscii(message, start, entity.getLength());
                    this.put(entity.getFieldName(), ascii);
                    break;
                case "bit":
                    for (int i = 0; i < entity.getLength() * 4; i++){
                        Boolean bit = ParseMksMqttUtil.parseHexByBit(message, start, entity.getLength(), i);
                        this.put(entity.getFieldName() + i, bit);
                    }
                    break;
                default:
                    break;
            }
            if (entity.getPos() < 0){
                nav -= entity.getLength();
            }
            else {
                pos += entity.getLength();
            }
        }
    }
}
