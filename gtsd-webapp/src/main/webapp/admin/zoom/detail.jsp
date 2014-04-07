<%@page import="com.yuxxer.gtsd.domain.City"%>
<%@page import="com.yuxxer.gtsd.manager.CityManager"%>
<%@page import="com.yuxxer.gtsd.domain.Province"%>
<%@page import="com.yuxxer.gtsd.manager.ProvinceManager"%>
<%@page import="com.yuxxer.gtsd.domain.Zoom"%>
<%@page import="com.yuxxer.gtsd.manager.ZoomManager"%>
<%@page language="java" pageEncoding="utf-8"%><%@ include
	file="/include/page.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" /> -->
<title>派送网点-详细信息</title>
<jsp:include page="/include/common.jsp" />
</head>
<%
String entityId=request.getParameter("entityId");
ProvinceManager provinceManager=helper.getBean(ProvinceManager.class);
CityManager cityManager=helper.getBean(CityManager.class);
ZoomManager manager=helper.getBean(ZoomManager.class);
String action = "create";
Zoom zoom=null;
if (StringUtils.isNotBlank(entityId)) {
	action = "update";
	zoom = manager.getZoom(entityId);
}else {
	zoom = new Zoom();
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
					<h5><%="create".equals(action) ? "新增" : "修改" %>,派送网点</h5>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="line"></div>

		<div class="wrapper">
			<div class="widget">
			<%if("create".equals(action)){%>
				<a href="javascript:createZoom();" title="" class="button greenB" style="margin: 5px;"><span>新增</span></a>
			<%}else{%>
				<a href="javascript:editZoom('<%=entityId %>');" title="" class="button greenB" style="margin: 5px;"><span>修改</span></a>
			<%}%>
				<a href="<%=request.getContextPath() %>/admin/zoom/list.jsp" title="" class="button redB" style="margin: 5px;"><span>取消</span></a>
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
							<label>网点名:<span class="req">*</span></label>
							<div class="formRight">
								<input type="text" name="name"  id="name" class="validate[required]" value="<%=StringUtils.isNotBlank(zoom.getName())?zoom.getName():""%>"/>
							</div>
							<div class="clear"></div>
						</div>
						<div class="formRow">
							<label>从属省份/直辖市:<span class="req">*</span></label>
							<div class="formRight">
							<%if("update".equals(action)){ 
								if(StringUtils.isNotBlank(zoom.getProvinceId())){
									Province p=provinceManager.getProvince(zoom.getProvinceId());
									out.print(p!=null?"<span class=\"formNote\">"+p.getName()+"</span>":"");
								}
							}else{ %>
								<div class="selector">
								<span>--请选择省/直辖市--</span>
	                            <select name="provinceId" id="provinceId" class="validate[required]" onchange="LoadCity();">
	                                <option value="">--请选择省/直辖市--</option>
	                            <%List<Province> provinces=provinceManager.getAllProvinces();
	                            for(Province province:provinces){
	                            %>
	                                <option value="<%=province.getId()%>"><%=province.getName() %></option>
	                            <%} %>
	                            </select> 
	                            </div> 
                            <%} %>         
                        	</div>
							<div class="clear"></div>
						</div>
						
						<div class="formRow">
							<label>从属市/地区:<span class="req">*</span></label>
							<div class="formRight">
							<%if("update".equals(action)){ 
								if(StringUtils.isNotBlank(zoom.getCityId())){
									City c=cityManager.getCity(zoom.getCityId());
									out.print(c!=null?"<span class=\"formNote\">"+c.getName()+"</span>":"");
								}
							}else{ %>
								<div class="selector">
								<span>--省份/直辖市未选择--</span>
	                            <select name="cityId" id="cityId" class="validate[required]">
	                                <option value="">--省份/直辖市未选择--</option>
	                            </select> 
	                            </div>   
                            <%} %>       
                        	</div>
							<div class="clear"></div>
						</div>
						<div class="formRow">
							<label>负责人:</label>
							<div class="formRight">
								<input type="text" name="master"  id="master" class="" value="<%=StringUtils.isNotBlank(zoom.getMaster())?zoom.getMaster():""%>"/>
							</div>
							<div class="clear"></div>
						</div>
						<div class="formRow">
							<label>联系电话:</label>
							<div class="formRight">
								<input type="text" name="telephone"  id="telephone" value="<%=StringUtils.isNotBlank(zoom.getTelephone())?zoom.getTelephone():""%>"/>
							</div>
							<div class="clear"></div>
						</div>
						<div class="formRow">
							<label>传真号码:</label>
							<div class="formRight">
								<input type="text" name="fax"  id="fax" value="<%=StringUtils.isNotBlank(zoom.getFax())?zoom.getFax():""%>"/>
							</div>
							<div class="clear"></div>
						</div>
						<div class="formRow">
							<label>网点地址:</label>
							<div class="formRight">
								<input type="text" name="address"  id="address" value="<%=StringUtils.isNotBlank(zoom.getAddress())?zoom.getAddress():"" %>"/>
							</div>
							<div class="clear"></div>
						</div>
						<div class="formRow">
							<label>派送范围:</label>
							<div class="formRight">
								<textarea id="inZoom" name="inZoom"><%=StringUtils.isNotBlank(zoom.getInZoom())?zoom.getInZoom():"" %></textarea>
							</div>
							<div class="clear"></div>
						</div>
						<div class="formRow">
							<label>不派送范围:</label>
							<div class="formRight">
								<textarea id="outZoom" name="outZoom"><%=StringUtils.isNotBlank(zoom.getOutZoom())?zoom.getOutZoom():"" %></textarea>
							</div>
							<div class="clear"></div>
						</div>
						<div class="formRow">
							<label>描述:</label>
							<div class="formRight">
								<textarea rows="8" cols="" name="description" id="description"
									class="autoGrow"><%=StringUtils.isNotBlank(zoom.getDescription())?zoom.getDescription():"" %></textarea>
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
	$(function(){
		$("#inZoom").cleditor({
			width:"100%", 
			height:"100%",
			bodyStyle: "margin: 10px; font: 12px Arial,Verdana; cursor:text"
		});
		$("#outZoom").cleditor({
			width:"100%", 
			height:"100%",
			bodyStyle: "margin: 10px; font: 12px Arial,Verdana; cursor:text"
		});
		
	});
	function LoadCity(){
		var pid=$("#provinceId").val();
		if(pid){
			$.ajax({
				url:'<%=$restRoot%>/gtsd.City/collection/listbyProvince?provinceId='+pid,
				type:'get',
				dataType:'json',
				success:function(data){
					if(data && data.length>0){
						var options="<option value=\"\">--请选择市/地区--</option>";
						for(var i in data){
							var obj=data[i];
							options=options+"<option value=\""+obj.id+"\">"+obj.name+"</option>";
						}
						$("#cityId").html(options);
					};
				}
			});
		}else{
			$("#cityId").html("<option value=\"\">--省份/直辖市未选择--</option>");
		};
	}
	function createZoom(){
		var validate = $("#detail-form").validationEngine('validate');
		if (!validate) {return;}
		var obj=$("#detail-form").serializeObject();
		obj.javaClass="com.yuxxer.gtsd.domain.Zoom";
		$.ajax({
			url:'<%=$restRoot%>/gtsd.Zoom/collection/create',
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
	
	function editZoom(id){
		var validate = $("#detail-form").validationEngine('validate');
		if (!validate) {return;}
		var obj=$("#detail-form").serializeObject();
		obj.javaClass="com.yuxxer.gtsd.domain.Zoom";
		$.ajax({
			url:'<%=$restRoot%>/gtsd.Zoom/'+id+'/update',
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
