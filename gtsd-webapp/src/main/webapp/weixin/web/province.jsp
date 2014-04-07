<%@page import="com.yuxxer.gtsd.domain.Province"%>
<%@page import="com.yuxxer.gtsd.manager.ProvinceManager"%>
<%@ page language="java" pageEncoding="UTF-8"%><%@ include file="/include/page.jspf"%>
<!Doctype html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link href="<%=request.getContextPath()%>/weixin/css/style.css" rel="stylesheet" type="text/css">
	<title>网点查询</title>
	<style type="text/css">
<!--
.STYLE5 {font-family: Geneva, Arial, Helvetica, sans-serif}
-->
    </style>
	</head>

	<body>
	 <h1>高铁速递全国网点查询</h1>
	 <h2>请选择省份</h2>
	 <ul class="ul_list" id="p_list">
	 <%
	 ProvinceManager provinceManager=helper.getBean(ProvinceManager.class);
	 List<Province> provinces=provinceManager.getAllProvinces();
	 for(Province province:provinces){
	 %>
	 <li><a href="list.jsp?provinceId=<%=province.getId()%>"><%=province.getName() %></a></li>
	 <%} %>
	 </ul>
	</body>
</html>