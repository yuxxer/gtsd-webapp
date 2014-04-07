<%@page import="com.yuxxer.gtsd.domain.Zoom"%>
<%@page import="com.yuxxer.gtsd.manager.ZoomManager"%>
<%@ page language="java" pageEncoding="UTF-8"%><%@ include file="/include/page.jspf"%>
<!Doctype html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link href="<%=request.getContextPath()%>/weixin/css/style.css" rel="stylesheet" type="text/css">
	<title>网点详情查询</title>
	<style type="text/css">
	
<!--
.STYLE5 {font-family: Geneva, Arial, Helvetica, sans-serif}
-->
    </style>
	</head>
<%
ZoomManager zoomManager=helper.getBean(ZoomManager.class);
String zoomId=request.getParameter("zoomId");
if(StringUtils.isBlank(zoomId)){
	out.print("未选择网点！！");
	return;
}
Zoom zoom=zoomManager.getZoom(zoomId);
if(zoom==null){
	out.print("您选择的网点不存在了！！");
	return;
}
%>
	<body>
	<h1>高铁速递全国网点信息</h1>
	<h2 class="h2_title"><%=zoom.getName() %>信息：</h2>
	<strong>负责人：</strong><%=zoom.getMaster() %>
	<br>
	<strong>联系电话：</strong><%=zoom.getTelephone() %>
	<br>
	<strong>传真号码：</strong><%=zoom.getFax() %>
	<br>
	<strong>网点地址：</strong><%=zoom.getAddress() %>
	<br>
	<strong>派送范围：</strong><%=zoom.getInZoom() %>
	<br>
	<strong>不派送区：</strong><%=zoom.getOutZoom() %>
	<br>
	<strong>备注信息：</strong><%=zoom.getDescription() %>
	
	</body>
</html>