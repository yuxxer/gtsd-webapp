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

						<div class="formRow">
                        <label>上一级:</label>
                        <div class="formRight">
                        	<ul class="ui-selector-list">
                        	<li class="display">上海市<input type="hidden" value="" />&nbsp;&nbsp; <a href="javascript:void(0);"><i class="icon-remove"></i></a></li>
                        	</ul>
                        	<button type="button" id="addQuestion" class="btn btn-mini" onclick="selectZoom($(this).parent().parent());">添加题目</button>
                            <%-- <a href="javascript:void(0);"id="addZoom" class="smallButton" style="margin: 5px;"><img src="<%=request.getContextPath() %>/images/icons/color/plus.png" title="添加上一级"/></a> --%>  
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
	
	function selectZoom(parent,item){
		var selectBtn = $('<button type="button" class="btn btn-mini btn-success"><i class="icon-cog icon-white"></i></button>');
			console.log(selectBtn);
			 $('<div>').dialog({
					modal:true,
					width: 750,
					title:'题目选择',
		            close: function(){
		            	$(this).remove();
		            },
		            buttons:{
		            	'确定':function(){
		                    var values = new Array();
		                    $('#table_question').find('input:checked').each(function(index){
		                    	values.push($(this).val());
		                    });
		                    alert(14);
		                    var value=null;	                    
		                    var isExist=false;
		                    for (var i = 0; i < values.length; i++) {
		                    	if (i > 0) {
		                    		break;
		                    	}
		                    	var ins=$('#questions').find('[name="questionId"]');
		                    	for(var k=0;k<ins.length;k++){
		                    		if($(ins[k]).val()==values[i]){
		                    			alert("題目已选，不可再选！");
			                   			isExist=true;
		                   		 	}
		                   	 	}
		                    	value=values[i];
		                    }
		                    if(value && !isExist){
		                    	selectBtn.parent().find('span').remove();
		                    	$('<span><b>' + $('#name_' + value).val() + '</b><input type="hidden" name="questionId" value="' + value + '"/></span>&nbsp;').insertBefore(selectBtn);
		                    	$(this).dialog("close");
		                    }
		                }
		            },
		            open:function(){
		            	$(this).load('selector.jsp?array=false&tableId=table_question');
		            }
			}); 
	};
	</script>
	<script type="text/javascript" src="openSelector.js"></script>
</body>
</html>
