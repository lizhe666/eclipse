<%@page import="com.iXinfeng.utils.RedisAPI"%>
<%
out.print(RedisAPI.set("test", "111"));
out.print(RedisAPI.get("test"));
%>