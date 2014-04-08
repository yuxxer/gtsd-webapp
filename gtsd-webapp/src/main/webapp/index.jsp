<%@page import="com.yuxxer.gtsd.service.rest.WeixinService"%>
<%@ page language="java" pageEncoding="UTF-8"%><%@include file="/include/page.jspf"%>
<html>
<body>
<h2>Hello World!</h2>

<%WeixinService weixinService=helper.getBean(WeixinService.class);
Object obj=weixinService.clickFaq("openId", "originId");
out.print(obj.toString());
%>
</body>
</html>
