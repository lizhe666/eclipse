package com.iXinfeng.beans.sensor;

public class EquipmentDesc {
    /**
     * 英文名称
     */
    private String nameEn;
    /**
     * 中文名称
     */
    private String nameCh;
    
    public EquipmentDesc(String nameEn, String nameCh) {
        this.nameEn = nameEn;
        this.nameCh = nameCh;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public String getNameCh() {
        return nameCh;
    }
    public void setNameCh(String nameCh) {
        this.nameCh = nameCh;
    }

    
}
