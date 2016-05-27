package com.iXinfeng.beans.sensor;

public class EquipmentStatus {
    /**
     * 上次心跳时间
     */
    private long lastHeartBeatTime;
    /**
     * 上次运转状态
     */
    private int lastStatus;

    /**
     * 是否正常运转
     * @return
     */
    public boolean isNormalRunning() {
        if (System.currentTimeMillis()/1000 - lastHeartBeatTime > 120) {
            return false;
        }else {
            return true;
        }
    }

    public EquipmentStatus(long lastHeartBeatTime, int lastStatus) {
        this.lastHeartBeatTime = lastHeartBeatTime;
        this.lastStatus = lastStatus;
    }

    public long getLastHeartBeatTime() {
        return lastHeartBeatTime;
    }

    public int getLastStatus() {
        return lastStatus;
    }
    
    
}