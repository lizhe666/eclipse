package com.iXinfeng.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {

    /**
     * 获得 bool 类型的变量，如果是 t 则返回 true 否则君返回 false
     * 
     * @param request
     * @param string
     * @return
     */
    public boolean getParameterBoolean(HttpServletRequest request, String string) {
        return getParameterBooleanStatic(request, string);
    }

    /**
     * 获得 bool 类型的变量，如果是1、 t 或者 y 则返回 true 否则君返回 false
     * 
     * @param request
     * @param string
     * @return
     */
    public static boolean getParameterBooleanStatic(HttpServletRequest request,
            String string) {
        String str = request.getParameter(string);
        if (str != null
                && (str.equalsIgnoreCase("1") || str.equalsIgnoreCase("t") || str
                        .equalsIgnoreCase("y"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取 request 中的参数 如果没有 返回 null
     * 
     * @param request
     * @param name
     * @return
     */
    public Integer getParameterInt(HttpServletRequest request, String name) {
        return getParameterIntStatic(request, name);
    }

    /**
     * 获取 request 中的参数 如果没有 返回 null
     * 
     * @param request
     * @param name
     * @return
     */
    public Byte getParameterByte(HttpServletRequest request, String name) {
        return getParameterByteStatic(request, name);
    }

    /**
     * 获取 request 中的参数 如果没有 返回 null
     * 
     * @param request
     * @param name
     * @return
     */
    public Double getParameterDouble(HttpServletRequest request, String name) {
        return getParameterDoubleStatic(request, name);
    }

    /**
     * 获取 request 中的参数 如果没有 返回 null
     * 
     * @param request
     * @param name
     * @return
     */
    public Long getParameterLong(HttpServletRequest request, String name) {
        return getParameterLongStatic(request, name);
    }

    /**
     * 获取 request 中的参数 如果没有或错误 返回 null
     * 
     * @param request
     * @param name
     * @return
     */
    public static Long getParameterLongStatic(HttpServletRequest request,
            String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return new Long(value);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取 request 中的参数 如果没有或错误 返回 null
     * 
     * @param request
     * @param name
     * @return
     */
    public static Integer getParameterIntStatic(HttpServletRequest request,
            String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return new Integer(value);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取 request 中的参数 如果没有或错误 返回 null
     * 
     * @param request
     * @param name
     * @return
     */
    public static Byte getParameterByteStatic(HttpServletRequest request,
            String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return new Byte(value);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取 request 中的参数 如果没有或错误 返回 null
     * 
     * @param request
     * @param name
     * @return
     */
    public static Double getParameterDoubleStatic(HttpServletRequest request,
            String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return new Double(value);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取 request 中的参数 保证不让返回的参数为 null
     * 
     * @param request
     * @param name
     * @return
     */
    public String getParameter(HttpServletRequest request, String name) {
        return getParameterStatic(request, name);
    }

    /**
     * 获取 request 中的参数 保证不让返回的参数为 null
     * 
     * @param request
     * @param name
     * @return
     */
    public static String getParameterStatic(HttpServletRequest request,
            String name) {
        return getParameterStatic(request, name, "");
    }

    /**
     * 获取 request 中的参数 可以带默认值
     * 
     * @param request
     * @param name
     * @param defaultValue
     * @return
     */
    public String getParameter(HttpServletRequest request, String name,
            String defaultValue) {
        return getParameterStatic(request, name, defaultValue);
    }

    /**
     * 获取 request 中的参数 可以带默认值
     * 
     * @param request
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getParameterStatic(HttpServletRequest request,
            String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }

    // /////////////////////////////////////////////////////
    /**
     * 获取request中的参数，是否从iso8859-1转成 特定字符集
     * 
     * @param request
     * @param name
     * @param defaultValue
     * @param charSet
     * @return
     */
    public String getParameter(HttpServletRequest request, String name,
            String defaultValue, String charSet) {
        return getParameterStatic(request, name, defaultValue, charSet);
    }

    /**
     * 获取request中的参数，是否从iso8859-1转成 特定字符集
     * 
     * @param request
     * @param name
     * @param defaultValue
     * @param charSet
     * @return
     */
    public static String getParameterStatic(HttpServletRequest request,
            String name, String defaultValue, String charSet) {
        String value = request.getParameter(name);
        value = convertStr(request, value, charSet);
        value = (value == null || value.length() == 0) ? defaultValue : value;
        return value;
    }

    /**
     * 转换字符串
     * 
     * @param request
     * @param value
     * @param charSet
     * @return 出现异常 返回 null （如果传递的是 null 也返回 null）
     */
    public static String convertStr(HttpServletRequest request, String value,
            String charSet) {
        if (value != null) {
            try {
                boolean needEncode = false;
                String explore = request.getHeader("user-agent");
                if (explore != null) { // 手机端的请求，没有这个 header
                    explore = explore.toLowerCase();
                }

                if (explore == null || explore.indexOf("msie") > 0
                        || explore.indexOf("webkit") > 0) { // IE webkit 浏览器都需要进行转码
                    needEncode = true;
                } else { // 在非 IE webkit 浏览器的情况下
                    String requestWay = request.getHeader("X-Requested-With"); // 判断请求途径
                    if (requestWay != null
                            && requestWay.equalsIgnoreCase("XMLHttpRequest")) { // 来自ajax提交
                        if (request.getMethod().equals("GET")) {
                            needEncode = true; // 来自ajax get提交,需要解码
                        } else {
                            needEncode = false; // 来自ajax post提交,不需要解码
                        }
                    } else {
                        needEncode = true; // 非ajax提交需要进行解码
                    }
                }

                if (needEncode) {
                    byte[] bytes = value.getBytes("ISO-8859-1"); // 将字符串用指定的编码集合解析成字节数组，完成Unicode－〉charsetName转换
                    value = new String(bytes, charSet); // 将字节数组以指定的编码集合构造成字符串，完成charsetName－〉Unicode转换
                }
                return value;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取request中的参数，是否从iso8859-1转成 utf-8 字符集
     * 
     * @param request
     * @param name
     * @param defaultValue
     * @return
     */
    public String getParameterUtf8(HttpServletRequest request, String name,
            String defaultValue) {
        return getParameterUtf8Static(request, name, defaultValue);
    }

    /**
     * 获取request中的参数，是否从iso8859-1转成 utf-8 字符集
     * 
     * @param request
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getParameterUtf8Static(HttpServletRequest request,
            String name, String defaultValue) {
        return getParameterStatic(request, name, defaultValue, "utf-8");
    }

    /**
     * 获取request中的参数，是否从iso8859-1转成 utf-8 字符集
     * 
     * @param request
     * @param name
     * @return
     */
    public String getParameterUtf8(HttpServletRequest request, String name) {
        return getParameterUtf8Static(request, name);
    }

    /**
     * 获取request中的参数，是否从iso8859-1转成 utf-8 字符集
     * 
     * @param request
     * @param name
     * @return
     */
    public static String getParameterUtf8Static(HttpServletRequest request,
            String name) {
        return getParameterUtf8Static(request, name, "");
    }

    // ///////////////////////////////////

    /**
     * 获得多选按钮的所有值 用空格组成字符串
     * 
     * @param request
     * @param name
     * @param defaultValue
     * @param charSet
     * @return
     */
    public String getParameterValues(HttpServletRequest request, String name,
            String defaultValue, String charSet) {
        return getParameterValuesStatic(request, name, defaultValue, charSet);
    }

    /**
     * 获得多选按钮的所有值 用空格组成字符串
     * 
     * @param request
     * @param name
     * @param defaultValue
     * @param charSet
     * @return
     */
    public static String getParameterValuesStatic(HttpServletRequest request,
            String name, String defaultValue, String charSet) {
        String[] value = request.getParameterValues(name);
        String ret = defaultValue;
        if (value != null) {
            for (int i = 0; i < value.length; i++) {
                ret += convertStr(request, value[i], charSet) + " ";
            }
        }
        return ret;
    }

    /**
     * 通过transfer页面转到正常页面，一般用来显示出错信息或者成功信息 调用这个函数需要有 /transfer.jsp 页面
     * 
     * @param request
     * @param response
     * @param msg
     *            提示信息
     * @param returnURL
     *            返回的 URL 如果为 null 则返回上一个地址
     * @param time
     *            页面自动跳转时间(毫秒)，如果是负数则直接跳转
     * @throws ServletException
     * @throws IOException
     */
    public void goTransfer(HttpServletRequest request,
            HttpServletResponse response, String msg, String returnURL,
            String time) throws ServletException, IOException {
        goTransferStatic(request, response, msg, returnURL, time);
    }

    /**
     * 通过transfer页面转到正常页面，一般用来显示出错信息或者成功信息 调用这个函数需要有 /transfer.jsp 页面
     * 
     * @param request
     * @param response
     * @param msg
     *            提示信息
     * @param returnURL
     *            返回的 URL 如果为 null 则返回上一个地址
     * @param time
     *            页面自动跳转时间(毫秒)，如果是负数则直接跳转
     * @throws ServletException
     * @throws IOException
     */
    public static void goTransferStatic(HttpServletRequest request,
            HttpServletResponse response, String msg, String returnURL,
            String time) throws ServletException, IOException {
        request.setAttribute("url", returnURL);
        request.setAttribute("time", time);
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("/transfer.jsp")
                .forward(request, response);
    }

    /**
     * 向页面输出信息 这个是 Servlet 的输出，需要定义 response.setContentType("text/html");
     * 方便定制缓存策略 Content-Type
     * 表示后面的文档属于什么MIME类型。Servlet默认为text/plain，但通常需要显式地指定为text
     * /html。由于经常要设置Content-Type，因此HttpServletResponse提供了一个专用的方法setContentTyep。
     * 
     * @param response
     * @param info
     * @throws IOException
     */
    public void print(HttpServletRequest request, HttpServletResponse response,
            String info, String charSet) throws IOException {
        printStatic(request, response, info, charSet);
    }

    public static void printStatic(HttpServletRequest request,
            HttpServletResponse response, String info, String charSet)
            throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding(charSet);
        PrintWriter out = response.getWriter();
        out.print(info);
        out.flush();
        out.close();
    }

    /**
     * 向页面输出信息 这个是 javascript 的输出，需要定义
     * response.setContentType("text/javascript"); 方便定制缓存策略 Content-Type
     * 表示后面的文档属于什么MIME类型
     * 。Servlet默认为text/plain，但通常需要显式地指定为text/html。由于经常要设置Content
     * -Type，因此HttpServletResponse提供了一个专用的方法setContentTyep。
     * 
     * @param response
     * @param info
     * @throws IOException
     */
    public void printJs(HttpServletRequest request,
            HttpServletResponse response, String info, String charSet)
            throws IOException {
        response.setContentType("text/javascript");
        response.setCharacterEncoding(charSet);
        PrintWriter out = response.getWriter();
        out.print(info);
        out.flush();
        out.close();
    }

    public void printCss(HttpServletRequest request,
            HttpServletResponse response, String info, String charSet)
            throws IOException {
        response.setContentType("text/css");
        response.setCharacterEncoding(charSet);
        PrintWriter out = response.getWriter();
        out.print(info);
        out.flush();
        out.close();
    }

    /**
     * 获得所有的参数列表,URL 一般用于调试
     * 
     * @param request
     */
    @SuppressWarnings("unchecked")
    public static String getRequestInfo(HttpServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer(request.getRequestURL()
                + "?");
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String string = (String) names.nextElement();
            stringBuffer.append(string + "=" + request.getParameter(string)
                    + "&");
        }
        return stringBuffer.toString();
    }
}