package com.iXinfeng.beans.sensor;

/**
 * 传感器 信息
 * 
 * @author lizhe07
 * 
 */
public class SensorDesc {
    /**
     * 缩写
     */
    private String nameS;
    /**
     * 英文名称
     */
    private String nameEn;
    /**
     * 中文名称
     */
    private String nameCh;
    /**
     * 显示单位
     */
    private String unit;
    /**
     * 下限
     */
    private double valueLow;
    /**
     * 上限
     */
    private double valueHigh;
    /**
     * 报警 一般会启动max模式
     */
    private double valueWaring;
    
    
    public SensorDesc(String nameS, String nameEn, String nameCh, String unit, double valueLow, double valueHigh, double valueWaring) {
        this.nameS = nameS;
        this.nameEn = nameEn;
        this.nameCh = nameCh;
        this.unit = unit;
        this.valueLow = valueLow;
        this.valueHigh = valueHigh;
        this.valueWaring = valueWaring;
    }

    public String getNameS() {
        return nameS;
    }
    public String getNameEn() {
        return nameEn;
    }
    public String getNameCh() {
        return nameCh;
    }
    public String getUnit() {
        return unit;
    }
    public double getValueHigh() {
        return valueHigh;
    }
    public double getValueLow() {
        return valueLow;
    }
    public double getValueWaring() {
        return valueWaring;
    }    
}
