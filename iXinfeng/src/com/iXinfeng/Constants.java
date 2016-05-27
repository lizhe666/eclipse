package com.iXinfeng;

import java.util.HashMap;

import com.iXinfeng.beans.sensor.EquipmentDesc;
import com.iXinfeng.beans.sensor.SensorDesc;

public class Constants {
    /**
     * 传感器—温度
     */
    public static final String SENSOR_T = "t";
    /**
     * 传感器—湿度
     */
    public static final String SENSOR_H = "h";
    /**
     * 传感器—甲醛
     */
    public static final String SENSOR_HCHO = "hcho";
    /**
     * 传感器—PM1.0
     */
    public static final String SENSOR_PM10 = "pm10";
    /**
     * 传感器—PM2.5
     */
    public static final String SENSOR_PM25 = "pm25";
    /**
     * 传感器—PM10
     */
    public static final String SENSOR_PM100 = "pm100";
    /**
     * 传感器—二氧化碳
     */
    public static final String SENSOR_CO2 = "co2";
    /**
     * 电器—新风系统
     */
    public static final String EQUIPMENT_NEW_AIR = "EQUIP_1";
    /**
     * 电器-加热系统
     */
    public static final String EQUIPMENT_HEATER = "EQUIP_2";
    /**
     * 电器-加湿系统
     */
    public static final String EQUIPMENT_HUMIDIFIER = "EQUIP_3";
    /**
     * 传感器相关信息表
     */
    public static HashMap<String, SensorDesc> SENSOR_INFO_MAP = new HashMap<String, SensorDesc>();
    static {
        // 需要认为降低数值的
        // 低于下限：说明没人在家关闭新风；高于上限，启动机器；高于警告数值，启动Max模式；
        Constants.SENSOR_INFO_MAP.put(SENSOR_CO2, new SensorDesc("CO2", "CO2", "二氧化碳", "ppm", 450, 600, 800));
        Constants.SENSOR_INFO_MAP.put(SENSOR_HCHO, new SensorDesc("HCHO", "HCHO", "甲醛", "mg/L", 0.02, 0.04, 0.08));
        Constants.SENSOR_INFO_MAP.put(SENSOR_PM10, new SensorDesc("PM1.0", "PM1.0", "PM1.0", "μg/m3", 10, 20, 80));
        Constants.SENSOR_INFO_MAP.put(SENSOR_PM25, new SensorDesc("PM2.5", "PM2.5", "PM2.5", "μg/m3", 10, 20, 80));
        Constants.SENSOR_INFO_MAP.put(SENSOR_PM100, new SensorDesc("PM10", "PM10", "PM10", "μg/m3", 10, 20, 80));

        //需要人为加大数值的
        Constants.SENSOR_INFO_MAP.put(SENSOR_T, new SensorDesc("T", "temperature", "温度", "°C", 24, 25, 18));
        Constants.SENSOR_INFO_MAP.put(SENSOR_H, new SensorDesc("H", "humidity", "相对湿度", "%", 35, 40, 20));
    }
    /**
     * 电器相关信息表
     */
    public static HashMap<String, EquipmentDesc> EQUIPMENT_INFO_MAP = new HashMap<String, EquipmentDesc>();
    static {
        Constants.EQUIPMENT_INFO_MAP.put(EQUIPMENT_NEW_AIR, new EquipmentDesc("New Air System", "新风系统"));
        Constants.EQUIPMENT_INFO_MAP.put(EQUIPMENT_HEATER, new EquipmentDesc("Heater System", "加热系统"));
        Constants.EQUIPMENT_INFO_MAP.put(EQUIPMENT_HUMIDIFIER, new EquipmentDesc("Humidifier System", "加湿系统"));
    }

}
