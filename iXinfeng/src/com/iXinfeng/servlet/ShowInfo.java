package com.iXinfeng.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iXinfeng.Constants;
import com.iXinfeng.RedisKey;
import com.iXinfeng.beans.sensor.EquipmentStatus;
import com.iXinfeng.utils.RedisAPI;

/**
 * @example 
 *          http://www.ixinfeng.com/st/ShowInfo?uid=1&sid=co2&tFrom=201604261200&
 *          tTo=201604271200
 * 
 * @author lizhe07
 * 
 *         TODO 可以后台定期的去跑一下，小时均值，还有日均值
 */
@SuppressWarnings("serial")
public class ShowInfo extends BaseServlet {

    /**
     * 为了保证图形的可观察性，以及性能的优化，每张图上最多显示的点数
     */
    public static int MAX_POINTS = 400;

    public static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
    public static SimpleDateFormat fOut = new SimpleDateFormat("yyyy,MM,dd,HH,mm");
    public static SimpleDateFormat fShow = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uid = getParameter(request, "uid"); // 用户id
        String sid = getParameter(request, "sids"); // 传感器id 用 __ 来分割
        String timeFrom = getParameter(request, "tFrom"); // 时间
        String timeTo = getParameter(request, "tTo"); // 时间

        //获得心跳数据，来确认目前的系统运转情况
        request.setAttribute("EQUIPMENT_INFO_MAP", Constants.EQUIPMENT_INFO_MAP);
        HashMap<String, EquipmentStatus> equipStatMap = new HashMap<String, EquipmentStatus>();
        testHeartBeat(request, uid,equipStatMap,Constants.EQUIPMENT_NEW_AIR);
        testHeartBeat(request, uid,equipStatMap,Constants.EQUIPMENT_HUMIDIFIER);
        testHeartBeat(request, uid,equipStatMap,Constants.EQUIPMENT_HEATER);
        request.setAttribute("equipStatMap", equipStatMap);
        
        try {
            Date timeF = null;
            Date timeT = null;
            if (timeFrom.length() == 0) {
                timeT = new Date();
                timeF = new Date(timeT.getTime() - 86400000);
            } else {
                timeF = df.parse(timeFrom);
                timeT = df.parse(timeTo);
            }

            HashMap<String, String> infoMap = new HashMap<String, String>();
            HashMap<String, String> lastInfoMap = new HashMap<String, String>();
            String[] sids = sid.split("__");
            for (int i = 0; i < sids.length; i++) {
                // 获得最后的数据信息
                String keyLast = uid + "_" + sids[i];
                lastInfoMap.put(sids[i], RedisAPI.get(keyLast));

                String str = getSensorData(uid, sids[i], timeF, timeT);
                infoMap.put(sids[i], str);
            }
            request.setAttribute("lastInfoMap", lastInfoMap); // 每个传感器的最新的数据
            request.setAttribute("nowTime", fShow.format(new Date()));
            request.setAttribute("infoMap", infoMap); // 每个传感器的数据信息表
            request.setAttribute("classMap", Constants.SENSOR_INFO_MAP); // 单位信息对照表
            request.getRequestDispatcher("/view/showInfo.jsp").forward(request, response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    
    public void testHeartBeat(HttpServletRequest request,String uid,HashMap<String, EquipmentStatus> equipStatMap,String type) {
        String hbInfo = RedisAPI.get(RedisKey.HEART_BEAT_PREIFX + uid + type);
        try {
            String[] hbInfos = hbInfo.split(" ");
            long lastHeartBeatTime = Long.parseLong(hbInfos[0]);
            int lastStatus = Integer.parseInt(hbInfos[1]);
            EquipmentStatus equipmentStatus = new EquipmentStatus(lastHeartBeatTime, lastStatus);
            equipStatMap.put(type, equipmentStatus);
        } catch (Exception e) {
            equipStatMap.put(type, null);
        }
    }

    /**
     * 通过 uid sid 获取传感器数据，并拼接成需要的数据
     * 
     * @param uid
     * @param sid
     * @param timeF
     * @param timeT
     * @return
     */
    public String getSensorData(String uid, String sid, Date timeF, Date timeT) {
        Long timeSpan = (timeT.getTime() - timeF.getTime());
        timeSpan /= 60000; // 到分钟
        double step = 1; // 默认取样间隔1分钟 这个步长可以是小数
        if (timeSpan > MAX_POINTS) {
            step = (double) timeSpan / MAX_POINTS;
        }

        StringBuffer sb = new StringBuffer("");
        for (double i = 0; i < timeSpan; i += step) {
            Date tempDate = new Date(timeF.getTime() + 60000 * (int) i); // 就近选取最接近的时点的数据
            String key = uid + "_" + sid + "_" + df.format(tempDate);
            String data = RedisAPI.get(key);
            if (data != null) {
                sb.append("[Date.UTC(" + fOut.format(tempDate) + ")," + data + "],");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * just for test
     * 
     * @param args
     */
    public static void main(String[] args) {
        String a = "co2";

        System.out.println(a.split("__").length);
        System.out.println(a.split("__")[0]);

    }
}
