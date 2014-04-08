<%@page import="com.yuxxer.gtsd.domain.Faq"%>
<%@page import="com.yuxxer.gtsd.manager.FaqManager"%>
<%@page language="java" pageEncoding="utf-8"%><%@ include
	file="/include/page.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" /> -->
<title>常见问题-详细信息</title>
<jsp:include page="/include/common.jsp" />
</head>
<%
String entityId=request.getParameter("entityId");
FaqManager manager=helper.getBean(FaqManager.class);
String action = "create";
Faq faq=null;
if (StringUtils.isNotBlank(entityId)) {
	action = "update";
	faq = manager.getFaq(entityId);
}else {
	faq = new Faq();
}
%>
<body>

	<jsp:include page="/include/menus.jsp" />

	<!-- Right side -->
	<div id="rightSide">
		<jsp:include page="/include/header.jsp" />

		<!-- Title area -->
		<div class="titleArea">
			<div class="wrapper">
				<div class="pageTitle">
					<h5><%="create".equals(action) ? "新增" : "修改" %>,常见问题</h5>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="line"></div>

		<div class="wrapper">
			<div class="widget">
			<%if("create".equals(action)){%>
				<a href="javascript:createFaq();" title="" class="button greenB" style="margin: 5px;"><span>新增</span></a>
			<%}else{%>
				<a href="javascript:editFaq('<%=entityId %>');" title="" class="button greenB" style="margin: 5px;"><span>修改</span></a>
			<%}%>
				<a href="<%=request.getContextPath() %>/admin/Faq/list.jsp" title="" class="button redB" style="margin: 5px;"><span>取消</span></a>
			</div>
			<form action="#" class="form" id="detail-form">
				<fieldset>
					<div class="widget">
						<div class="title">
							<img
								src="<%=request.getContextPath()%>/images/icons/dark/list.png"
								alt="" class="titleIcon" />
							<h6>属性</h6>
						</div>
						<div class="formRow">
							<label>问题:<span class="req">*</span></label>
							<div class="formRight">
								<input type="text" name="question"  id="question" class="validate[required]" value="<%=StringUtils.isNotBlank(faq.getQuestion())?faq.getQuestion():""%>"/>
							</div>
							<div class="clear"></div>
						</div>

						<div class="formRow">
							<label>答案:<span class="req">*</span></label>
							<div class="formRight">
								<textarea rows="8" cols="" name="answer" id="answer"
									class="autoGrow"><%=StringUtils.isNotBlank(faq.getAnswer())?faq.getAnswer():"" %></textarea>
							</div>
							<div class="clear"></div>
						</div>

					</div>
				</fieldset>
			</form>
		</div>

		<jsp:include page="/include/footer.jsp" />
	</div>
	<div class="clear"></div>
	<script type="text/javascript">
	$("#answer").cleditor({
		width:"100%", 
		height:"100%",
		bodyStyle: "margin: 10px; font: 12px Arial,Verdana; cursor:text"
	});
	function createFaq(){
		var validate = $("#detail-form").validationEngine('validate');
		if (!validate) {return;}
		var obj= $("#detail-form").serializeObject();
		obj.javaClass="com.yuxxer.gtsd.domain.Faq";
		$.ajax({
			url:'<%=$restRoot%>/gtsd.Faq/collection/create',
			type:'post',
			data:JSON.stringify(obj),
			dataType:'json',
			success:function(data){
				if(data && data.code==1){
					location.href="list.jsp";
				}
			}
		});
	}
	
	function editFaq(id){
		var validate = $("#detail-form").validationEngine('validate');
		if (!validate) {return;}
		var obj= $("#detail-form").serializeObject();
		obj.javaClass="com.yuxxer.gtsd.domain.Faq";
		$.ajax({
			url:'<%=$restRoot%>/gtsd.Faq/'+id+'/update',
			type:'post',
			data:JSON.stringify(obj),
			dataType:'json',
			success:function(data){
				if(data && data.code==1){
					location.href="list.jsp";
				}
			}
		});
	}
	$.fn.serializeObject = function()
	{
	    var o = {};
	    var a = this.serializeArray();
	    $.each(a, function() {
	        if (o[this.name] !== undefined) {
	            if (!o[this.name].push) {
	                o[this.name] = [o[this.name]];
	            }
	            o[this.name].push(this.value || '');
	        } else {
	            o[this.name] = this.value || '';
	        }
	    });
	    return o;
	};
	</script>
</body>
</html>
