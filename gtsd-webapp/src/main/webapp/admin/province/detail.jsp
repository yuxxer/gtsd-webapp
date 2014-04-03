<%@page import="com.yuxxer.gtsd.domain.Province"%>
<%@page import="com.yuxxer.gtsd.manager.ProvinceManager"%>
<%@page language="java" pageEncoding="utf-8"%><%@ include
	file="/include/page.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" /> -->
<title>省份/直辖市-详细信息</title>
<jsp:include page="/include/common.jsp" />
</head>
<%
String entityId=request.getParameter("entityId");
ProvinceManager manager=helper.getBean(ProvinceManager.class);
String action = "create";
Province province=null;
if (StringUtils.isNotBlank(entityId)) {
	action = "update";
	province = manager.getProvince(entityId);
}else {
	province = new Province();
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
					<h5><%="create".equals(action) ? "新增" : "修改" %>,省份/直辖市</h5>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="line"></div>

		<div class="wrapper">
			<div class="widget">
			<%if("create".equals(action)){%>
				<a href="javascript:createProvince();" title="" class="button greenB" style="margin: 5px;"><span>新增</span></a>
			<%}else{%>
				<a href="javascript:editProvince('<%=entityId %>');" title="" class="button greenB" style="margin: 5px;"><span>修改</span></a>
			<%}%>
				<a href="<%=request.getContextPath() %>/admin/province/list.jsp" title="" class="button redB" style="margin: 5px;"><span>取消</span></a>
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
							<label>省份/直辖市:<span class="req">*</span></label>
							<div class="formRight">
								<input type="text" name="name"  id="name" class="validate[required]" value="<%=StringUtils.isNotBlank(province.getName())?province.getName():""%>"/>
							</div>
							<div class="clear"></div>
						</div>

						<div class="formRow">
							<label>代码:</label>
							<div class="formRight">
								<input type="text" name="code"  id="code" value="<%=StringUtils.isNotBlank(province.getCode())?province.getCode():""%>"/>
							</div>
							<div class="clear"></div>
						</div>
						<div class="formRow">
							<label>描述:</label>
							<div class="formRight">
								<textarea rows="8" cols="" name="description" id="description"
									class="autoGrow"><%=StringUtils.isNotBlank(province.getDescription())?province.getDescription():"" %></textarea>
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
	
	function createProvince(){
		var validate = $("#detail-form").validationEngine('validate');
		if (!validate) {return;}
		var obj= $("#detail-form").serializeObject();
		obj.javaClass="com.yuxxer.gtsd.domain.Province";
		$.ajax({
			url:'<%=$restRoot%>/gtsd.Province/collection/create',
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
	
	function editProvince(id){
		var validate = $("#detail-form").validationEngine('validate');
		if (!validate) {return;}
		var obj= $("#detail-form").serializeObject();
		obj.javaClass="com.yuxxer.gtsd.domain.Province";
		$.ajax({
			url:'<%=$restRoot%>/gtsd.Province/'+id+'/update',
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
