<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.homolo.framework.util.FormatUtils"%>
<%@page import="com.homolo.framework.util.DateUtils"%>
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
	href="<%=request.getContextPath()%>/weixin/css/style1.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
</head>
<%
	String caseNo = request.getParameter("caseNo");
%>
<body>
	<div id="content" class="fix">
		<div style="margin:5px 0 15px 0"> 提示:请输入快递单号,输入的数据中不能包含数字字母之外的非法字符。</div>
		<form action="#" method="get" class="se-form">
			<div class="con-wrap">
				<input type="search" value="<%=StringUtils.isNotBlank(caseNo)?caseNo:"" %>" autocomplete="off" autocorrect="off"
					maxlength="64" id="word" name="word" class="se-input">
				<div class="se-inner">
					<button id="se_bn" class="se-bn" type="button" onclick="search();">
						<span>查询</span>
					</button>
				</div>
			</div>
		</form>
		<section id="box-his" class="box-history hide current"
			alog-group="g-1" style="display: block;">
			<h3>
				<span class="date">跟踪记录：</span>
			</h3>
			<div class="history-content">
				<ul>
				<%DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(StringUtils.isNotBlank(caseNo)){
						JSONArray array = WeixinUtil.getRecordByOrderId(caseNo);
						for (int i = 0; i < array.length(); i++) {
							JSONObject obj = (JSONObject) array.get(i);
							Date time=format.parse(obj.getString("time"));
					%>
					<li><em class="year"><%=FormatUtils.formatDate(time)%><span><%=FormatUtils.formatDate(time, "HH:mm:ss") %></span></em><span class="txt"><%=obj.getString("location")%></span>
					<%
						}
					}
					%>
				</ul>
			</div>
		</section>
	</div>
	<script type="text/javascript">
	function search(){
		var caseId=$("#word").val();
		var m = /^[0-9a-zA-Z]*$/g;
      	if(!m.test(caseId)){
      		alert("运单编号只能包含数字和字母！");
          	return;
        }
		if(caseId){
			location.href="index.jsp?caseNo="+caseId;
		};
	}
	</script>
</body>
</html>