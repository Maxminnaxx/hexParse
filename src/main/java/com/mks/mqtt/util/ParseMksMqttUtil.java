package com.mks.mqtt.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhb
 */
@UtilityClass
public class ParseMksMqttUtil {

    public static BigDecimal parseHexToBigDecimal(String message, int start, int length, Long offset, BigDecimal multiply){
        long l = Long.valueOf(message.substring(start, start + length), 16);
        if (offset != null){
            l = l - offset;
        }
        if (multiply != null){
            return multiply.multiply(BigDecimal.valueOf(l));
        }
        return BigDecimal.valueOf(l);
    }

    public static  Long parseHexToLong(String message, int start, int length, Long offset){
        long l = Long.valueOf(message.substring(start, start + length), 16);
        if (offset != null){
            l = l - offset;
        }
        return l;
    }

    public static  Integer parseHexToInteger(String message, int start, int length, Integer offset){
        int i = Integer.valueOf(message.substring(start, start + length), 16);
        if (offset != null){
            i = i - offset;
        }
        return i;
    }

    public static  String parseHexToAscii(String message, int start, int length){
        StringBuilder builder = new StringBuilder();
        int end = start + length;
        while (start < end){
            int i = Integer.valueOf(message.substring(start, start + 2), 16);
            builder.append((char) i);
            start += 2;
        }
        return builder.toString();
    }

    public static  Boolean parseHexByBit(String message, int start, int length, int index){
        long l = Long.parseLong(message.substring(start, start + length), 16);
        l = l >> index;
        l = l & 1L;
        return l == 1;
    }

    public static  LocalDateTime parseHexToTime(String message, int start, int length){
        LocalDateTime time = LocalDateTime.now();
        String year = message.substring(start, start + 4);
        String month = message.substring(start + 4, start + 6);
        String day = message.substring(start + 6, start + 8);
        String hour = message.substring(start + 8, start + 10);
        String minute = message.substring(start + 10, start + 12);
        String second = message.substring(start + 12, start + 14);

        return time.withYear(Integer.parseInt(year)).withMonth(Integer.parseInt(month)).withDayOfMonth(Integer.parseInt(day)).withHour(Integer.parseInt(hour)).withMinute(Integer.parseInt(minute)).withSecond(Integer.parseInt(second)).withNano(0);
    }

    public static  String parseHexToString(String message, int start, int length){
        return message.substring(start, start + length);
    }
}
