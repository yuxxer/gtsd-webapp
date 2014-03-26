<%@page import="com.yuxxer.gtsd.domain.Zoom"%>
<%@page import="com.yuxxer.gtsd.manager.ZoomManager"%>
<%@page language="java" pageEncoding="utf-8"%><%@ include
	file="/include/page.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" /> -->
<title>派送范围-详细信息</title>
<jsp:include page="/include/common.jsp" />
</head>
<%
String entityId=request.getParameter("entityId");
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
					<h5><%="create".equals(action) ? "新增" : "修改" %>,派送范围</h5>
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
			<form action="#" class="form" id="form">
				<fieldset>
					<div class="widget">
						<div class="title">
							<img
								src="<%=request.getContextPath()%>/images/icons/dark/list.png"
								alt="" class="titleIcon" />
							<h6>属性</h6>
						</div>
						<div class="formRow">
							<label>地名:<span class="req">*</span></label>
							<div class="formRight">
								<input type="text" name="name"  id="name" class="validate[required]" value="<%=StringUtils.isNotBlank(zoom.getName())?zoom.getName():""%>"/>
							</div>
							<div class="clear"></div>
						</div>

						<!-- <div class="formRow">
                        <label>上一级:</label>
                        <div class="formRight">
                            <select data-placeholder="Your Favorite Football Team" style="" class="chzn-select" multiple="multiple" tabindex="6">
                                <option value=""></option>
                                <optgroup label="NFC EAST">
                                    <option>Dallas Cowboys</option>
                                    <option selected="selected">New York Giants</option>
                                    <option>Philadelphia Eagles</option>
                                    <option>Washington Redskins</option>
                                </optgroup>
                                <optgroup label="NFC NORTH">
                                    <option selected="selected">Chicago Bears</option>
                                    <option>Detroit Lions</option>
                                    <option>Green Bay Packers</option>
                                    <option>Minnesota Vikings</option>
                                </optgroup>
                                <optgroup label="NFC SOUTH">
                                    <option selected="selected">Atlanta Falcons</option>
                                    <option>Carolina Panthers</option>
                                    <option>New Orleans Saints</option>
                                    <option>Tampa Bay Buccaneers</option>
                                </optgroup>
                                <optgroup label="NFC WEST">
                                    <option>Arizona Cardinals</option>
                                    <option>St. Louis Rams</option>
                                    <option>San Francisco 49ers</option>
                                    <option>Seattle Seahawks</option>
                                </optgroup>
                                <optgroup label="AFC EAST">
                                    <option>Buffalo Bills</option>
                                    <option>Miami Dolphins</option>
                                    <option>New England Patriots</option>
                                    <option>New York Jets</option>
                                </optgroup>
                                <optgroup label="AFC NORTH">
                                    <option>Baltimore Ravens</option>
                                    <option>Cincinnati Bengals</option>
                                    <option>Cleveland Browns</option>
                                    <option>Pittsburgh Steelers</option>
                                </optgroup>
                                <optgroup label="AFC SOUTH">
                                    <option>Houston Texans</option>
                                    <option>Indianapolis Colts</option>
                                    <option>Jacksonville Jaguars</option>
                                    <option>Tennessee Titans</option>
                                </optgroup>
                                <optgroup label="AFC WEST">
                                    <option>Denver Broncos</option>
                                    <option>Kansas City Chiefs</option>
                                    <option>Oakland Raiders</option>
                                    <option>San Diego Chargers</option>
                                </optgroup>
                            </select>  
                        </div>             
                        <div class="clear"></div>
                    </div> -->
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
	$("#form").validationEngine();
	function createZoom(){
		var name=$("#name").val();
		if(!name){
			alert("地名未填写!!");
			return;
		}
		var desc=$("#description").val();
		var obj={
				javaClass:"com.yuxxer.gtsd.domain.Zoom",
				name:name,
				description:desc
		};
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
		var name=$("#name").val();
		if(!name){
			alert("地名未填写!!");
			return;
		}
		var desc=$("#description").val();
		var obj={
				javaClass:"com.yuxxer.gtsd.domain.Zoom",
				name:name,
				description:desc
		};
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
	</script>
</body>
</html>
