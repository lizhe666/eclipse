<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>爱心风-家庭数据中心</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
<script src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<style type="text/css">
.bigF{font-size: 80px;}
</style>
</head>

<body>
<div class="bigF">
最新数据：<br/>
${nowTime}<br/>
<table border="0">
	<c:forEach var="entry" items="${lastInfoMap}">
	<tr>
		<td class="bigF">${classMap[entry.key].nameCh}:</td>
		<td class="bigF">${entry.value}</td>
		<td class="bigF">${classMap[entry.key].unit}</td>
	</tr>
	</c:forEach>
</table>
</div>
<hr/>
<div class="bigF">
电器状态：<br/>
<table border="0">
	<c:forEach var="entry" items="${EQUIPMENT_INFO_MAP}">
	<tr>
		<td class="bigF">${entry.value.nameCh}</td>
		<td class="bigF">
			<c:if test="${equipStatMap[entry.key].normalRunning==true}">
				<c:if test="${equipStatMap[entry.key].lastStatus==1}">开启</c:if>
				<c:if test="${equipStatMap[entry.key].lastStatus==9}">开启Max模式</c:if>
				<c:if test="${equipStatMap[entry.key].lastStatus==-1}">关闭</c:if>
			</c:if>
			<c:if test="${equipStatMap[entry.key].normalRunning==false}"><font color="red">断网</font></c:if>
			<c:if test="${empty equipStatMap[entry.key]}">未启用</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
</div>
<c:forEach var="entry" items="${infoMap}">
<script type="text/javascript">
$(function () {
    $('#c_${entry.key}').highcharts({
        chart: {
            type: 'spline'
        },
        title: {
            text: '${classMap[entry.key].nameCh}变化曲线'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: { // don't display the dummy year
                month: '%e. %b',
                year: '%b'
            },
            title: {
                text: 'Date'
            }
        },
        yAxis: {
            title: {
                text: '${classMap[entry.key].nameCh}'
            },
            min: 0
        },
        tooltip: {
            headerFormat: '<b>{series.name}</b><br>',
            pointFormat: '{point.x:%e. %b}: {point.y:.2f} ${classMap[entry.key].unit}'
        },

        plotOptions: {
            series: {
                marker: {
                    fillColor: '#FFFFFF',
                    lineWidth: 1,
          			radius: 1,
                    lineColor: null // inherit from series
                }
            }
        },

        series: [
        	{
	            name: '${classMap[entry.key].nameCh}',
	            data: [${entry.value}]
        	}
        ]
    });
});
</script>
<div id="c_${entry.key}" style="min-width:400px;height:400px"></div>
</c:forEach>
</body>
</html>