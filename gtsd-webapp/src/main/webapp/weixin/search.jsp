<%@page import="java.util.Date"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.yuxxer.gtsd.util.WeixinUtil"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
	<title>高铁速递</title>
<meta name="viewport"
	content="user-scalable=no,initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height" />
<link rel="stylesheet"
	href="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script
	src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
</head>
<%
	String caseNo = request.getParameter("caseNo");
	String time = request.getParameter("time");
	if(StringUtils.isBlank(time)){
		time=String.valueOf(new Date().getTime());
	}
%>
<body>
	<div data-role="page">
		<div data-role="header" data-position="fixed">
			<h1>运单号查询</h1>
		</div>
		<div data-role="content">
			提示:请输入运单编号,输入的数据中不能包含数字字母之外的非法字符。 <input type="text" name="caseId"
				placeholder="输入运单号" id="caseId-<%=time %>" value="<%=StringUtils.isBlank(caseNo)?"":caseNo%>"/> <a
				href="javascript:search();" id="searchBtn" data-role="button">查询</a>
				
			<table data-role="table" data-mode="columntoggle"
				class="ui-body-d ui-shadow table-stripe ui-responsive"
				data-column-btn-theme="c" data-column-btn-text=""
				data-column-popup-theme="c">
				<thead>
					<tr>
						<th data-priority="1">时间</th>
						<th>跟踪记录</th>
					</tr>
				</thead>
				<tbody>
					<%
						JSONArray array = WeixinUtil.getRecordByOrderId(caseNo);
						for (int i = 0; i < array.length(); i++) {
							JSONObject obj = (JSONObject) array.get(i);
					%>
					<tr>
						<th><%=obj.getString("time")%></th>
						<td><%=obj.getString("location")%></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function search(){
		var caseId=$("#caseId-<%=time%>").val();
		if(caseId){
			location.href="search.jsp?caseNo="+caseId+"&time="+new Date().getTime();
		};
	}
	</script>
</body>
</html>