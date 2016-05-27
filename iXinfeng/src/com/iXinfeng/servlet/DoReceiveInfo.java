package com.iXinfeng.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iXinfeng.Constants;
import com.iXinfeng.utils.RedisAPI;

/**
 * @example http://www.ixinfeng.com/send?uid=1&co2=988&t=35.0
 * @author lizhe07
 * 
 */
@SuppressWarnings("serial")
public class DoReceiveInfo extends BaseServlet {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = getParameter(request, "uid"); // 用户id

        if (uid.length() > 0) {
            setData(request, response, uid, Constants.SENSOR_CO2, 1);
            setData(request, response, uid, Constants.SENSOR_PM10, 1);
            setData(request, response, uid, Constants.SENSOR_PM25, 1);
            setData(request, response, uid, Constants.SENSOR_PM100, 1);
            setData(request, response, uid, Constants.SENSOR_HCHO, 2);
            setData(request, response, uid, Constants.SENSOR_T, 2);// 温度
            setData(request, response, uid, Constants.SENSOR_H, 2);// 湿度
        } else {
            print(request, response, "error uid", "utf-8");
        }

    }

    public void setData(HttpServletRequest request, HttpServletResponse response, String uid, String name, int dataType) throws IOException {
        Object data = null;
        switch (dataType) {
        case 1:
            data = getParameterInt(request, name);
            break;
        case 2:
            data = getParameterDouble(request, name);
            break;
        }

        if (data != null) {
            // 根据当前时间，计算出数值，每分钟最多记录1次数据(新数据会刷新旧数据)
            String key = uid + "_" + name + "_" + dateFormat.format(new Date());
            String keyLast = uid + "_" + name; // 最后的数据
            RedisAPI.set(key, data.toString());
            RedisAPI.set(keyLast, data.toString());
            // System.out.println("key=" + key);
            // System.out.println("received " + data + "ppm");
            print(request, response, "ok", "utf-8");
        }
    }
}