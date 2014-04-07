<%@page import="com.yuxxer.gtsd.domain.Zoom"%>
<%@page import="com.yuxxer.gtsd.domain.City"%>
<%@page import="com.yuxxer.gtsd.condition.CityCondition"%>
<%@page import="com.yuxxer.gtsd.manager.CityManager"%>
<%@page import="com.yuxxer.gtsd.condition.ZoomCondition"%>
<%@page import="com.yuxxer.gtsd.manager.ProvinceManager"%>
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
CityManager cityManager=helper.getBean(CityManager.class);
ProvinceManager provinceManager=helper.getBean(ProvinceManager.class);
String provinceId=request.getParameter("provinceId");
if(StringUtils.isBlank(provinceId)){
	out.println("未选择省份！！");
	return;
}
CityCondition cityCondition=new CityCondition();
cityCondition.setProvinceId(provinceId);
List<City> cities=cityManager.list(cityCondition);
boolean flag=false;
%>
	<body>
	 <h1>高铁速递全国网点详情查询</h1>
	 <%for(City city:cities){
		 ZoomCondition zoomCondition=new ZoomCondition();
		 zoomCondition.setCityId(city.getId());
		 List<Zoom> zooms=zoomManager.list(zoomCondition);
		 if(zooms.size()==0){
			 continue;
		 }
		 out.print("<h2>"+city.getName()+"有<strong>"+zooms.size()+"</strong> 个网点</h2>");
		 out.print("<ul class=\"ul_list\">");
		 for(Zoom zoom:zooms){
			 out.print("<li><a href=\"detail.jsp?zoomId="+zoom.getId()+"\"title=\"“"+zoom.getName()+"”详情\">"+zoom.getName()+"</a></li>");
		 }
		 out.print("</ul>");
		 flag=true;
	 } 
	 if(!flag){
		 response.sendRedirect("province.jsp");
	 }
	 %>
	</body>
</html>