package com.iXinfeng.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iXinfeng.Constants;
import com.iXinfeng.RedisKey;
import com.iXinfeng.beans.sensor.SensorDesc;
import com.iXinfeng.utils.RedisAPI;

public class Decision extends BaseServlet {
    private static final long serialVersionUID = -4364944112035044062L;

    public static String INFO_PREFIX = "_INFO_B_";
    public static String INFO_ENDFIX = "_INFO_E_";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = getParameter(request, "uid"); // 用户id
        String type = getParameter(request, "type"); // 家电类型
        String status = getParameter(request, "status"); //家电状态 
        
        int ret = 1;
        if (type.equalsIgnoreCase(Constants.EQUIPMENT_NEW_AIR)) { // 新风系统决策
            // 批量查询最后的传感器数据信息
            int co2 = doDecisionNeedDecrease(uid, Constants.SENSOR_CO2);
            int pm25 = doDecisionNeedDecrease(uid, Constants.SENSOR_PM25);
            int hcho = doDecisionNeedDecrease(uid, Constants.SENSOR_HCHO);
            if (co2 == 9 || pm25 == 9 || hcho == 9) { // 有一个是9，就是大风，没得说
                ret = 9;
            } else if (co2 == 1 || pm25 == 1 || hcho == 1) { // 有一个是1 就是中风，没得说
                ret = 1;
            } else if (co2 == 0 && pm25 == 0 && hcho == 0) { // 都不变，才不便
                ret = 0;
            } else if (co2 == -1 && pm25 == -1 && hcho == -1) { // 都关闭，才关闭
                ret = -1;
            }
        } else if (type.equalsIgnoreCase(Constants.EQUIPMENT_HEATER)) { // 加热器决策
            ret = doDecisionNeedIncrease(uid, Constants.SENSOR_T);
        } else if (type.equalsIgnoreCase(Constants.EQUIPMENT_HUMIDIFIER)) { // 加湿器决策
            ret = doDecisionNeedIncrease(uid, Constants.SENSOR_H);
        }

        // 心跳数据发送
        if (uid.length() > 0 && type.length() > 0 && status.length() > 0) {
            RedisAPI.set(RedisKey.HEART_BEAT_PREIFX + uid + type, System.currentTimeMillis() / 1000 + " " + status);
        }

        print(request, response, INFO_PREFIX + ret + INFO_ENDFIX, "utf-8");
        return;
    }

    /**
     * 需要人为减少数值的 比如温度，湿度
     * 
     * 依据上下限进行决策，低于下线启动，高于上线关闭，位于区间则保持原来的状态不变
     * 
     * @param uid
     * @param sensorName
     * @return -1 代表关闭; 0代表保持原状（原来是开的就开，原来是关的就还关）; (大于0就是开机)1代表运转， 9 代表强力运转
     */
    public int doDecisionNeedDecrease(String uid, String sensorName) {
        int ret = 1; // 默认是运转，但是不是最大风力
        String sDataStr = RedisAPI.get(uid + "_" + sensorName);
        if (sDataStr != null) {
            try {
                SensorDesc sensorDesc = Constants.SENSOR_INFO_MAP.get(sensorName);
                double sData = Double.parseDouble(sDataStr);
                if (sData < sensorDesc.getValueLow()) {
                    ret = -1;
                } else if (sData > sensorDesc.getValueWaring()) {
                    ret = 9;
                } else if (sData > sensorDesc.getValueLow()) {
                    ret = 1;
                } else {
                    ret = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;

    }

    /**
     * 需要认为增加数值的 比如温度，湿度
     * 
     * 依据上下限进行决策，低于下线启动，高于上线关闭，位于区间则保持原来的状态不变
     * 
     * @param uid
     * @param sensorName
     * @return -1 代表关闭; 0代表保持原状（原来是开的就开，原来是关的就还关）; (大于0就是开机)1代表运转， 9 代表强力运转
     */
    public int doDecisionNeedIncrease(String uid, String sensorName) {
        int ret = 1; // 默认是运转，但是不是最大风力
        String sDataStr = RedisAPI.get(uid + "_" + sensorName);
        if (sDataStr != null) {
            try {
                SensorDesc sensorDesc = Constants.SENSOR_INFO_MAP.get(sensorName);
                double sData = Double.parseDouble(sDataStr);
                if (sData > sensorDesc.getValueHigh()) {
                    ret = -1;
                } else if (sData < sensorDesc.getValueWaring()) {
                    ret = 9;
                } else if (sData < sensorDesc.getValueLow()) {
                    ret = 1;
                } else {
                    ret = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

}
