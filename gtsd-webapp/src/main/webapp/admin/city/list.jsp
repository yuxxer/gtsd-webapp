<%@page import="com.yuxxer.gtsd.domain.Province"%>
<%@page import="com.yuxxer.gtsd.manager.ProvinceManager"%>
<%@page import="com.yuxxer.gtsd.domain.City"%>
<%@page import="com.yuxxer.gtsd.manager.CityManager"%>
<%@page import="com.yuxxer.gtsd.condition.CityCondition"%>
<%@page language="java" pageEncoding="UTF-8"%><%@ include
	file="/include/page.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" /> -->
<title>市/地区列表</title>
<jsp:include page="/include/common.jsp" />
</head>
<%
String keyword=request.getParameter("keyword");
ProvinceManager provinceManager=helper.getBean(ProvinceManager.class);
CityManager manager=helper.getBean(CityManager.class);
CityCondition condition=new CityCondition();
if(StringUtils.isNotBlank(keyword)){
	keyword=new String(keyword.getBytes("iso-8859-1"),"utf-8");
	condition.setName(keyword);
}
Range range = ServletUtils.getRange(request);
PaginationSupport<City> ps=manager.searchCitys(condition, range, ServletUtils.getSorter(request, "provinceId"));
request.setAttribute("__default_paginationsupport__", ps);
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
					<h5>市/地区</h5>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="line"></div>

		<div class="wrapper">
			<div class="widget">
				<a href="detail.jsp" title="" class="button greenB" style="margin: 5px;"><span>新增</span></a>
				<div class="searchWidget" style="width:30%;float: right;margin-top: 2px;vertical-align: middle;">
					<form action="javascript:submitKeyword();" method="get">
						<input type="text" name="keyword" id="keyword" value="<%=StringUtils.isNotBlank(keyword)?keyword:"" %>"
							placeholder="输入关键字" /> <input type="submit"
							name="find" value="" />
					</form>
				</div>
			</div>

			<div class="widget">
				<div class="title">
					<span class="titleIcon"><input type="checkbox"
						id="titleCheck" name="titleCheck" /></span>
					<h6>市/地区列表</h6>
				</div>
				<table cellpadding="0" cellspacing="0" width="100%"
					class="sTable withCheck" id="checkAll">
					<thead>
						<tr>
							<td></td>
							<td width="20%">市/地区</td>
							<td width="20%">省份/直辖市</td>
							<td width="10%">代码</td>
							<td width="40%">描述</td>
							<td width="200"></td>
						</tr>
					</thead>
					<tbody>
					<%if(ps!=null && ps.getItems().size()>0){
						for(City city:ps.getItems()){%>
						<tr>
							<td><input type="checkbox" id="titleCheck" name="checkRow" value="<%=city.getId() %>" /></td>
							<td><%=city.getName() %></td>
							<td><%if(StringUtils.isNotBlank(city.getProvinceId())){
								Province province=provinceManager.getProvince(city.getProvinceId());
								out.println(province!=null?province.getName():"");
							} %></td>
							<td><%=city.getCode() %></td>
							<td><%=city.getDescription() %></td>
							<td class="actBtns"><a href="detail.jsp?entityId=<%=city.getId() %>" title="更新" class="tipS"><img
									src="<%=request.getContextPath()%>/images/icons/edit.png"
									alt="" /></a> <a href="javascript:deleteCity('<%=city.getId() %>');" title="删除" class="tipS"><img
									src="<%=request.getContextPath()%>/images/icons/remove.png"
									alt="" /></a></td>
						</tr>
						<%}
					}else{
						out.print("<tr><td colspan=\"6\">暂无数据！！</td></tr>");
					} %>
					</tbody>
				</table>
			</div>
			 <jsp:include page="/include/pagingtoolbar.jsp" />
		</div>

		<jsp:include page="/include/footer.jsp" />
	</div>
	<div class="clear"></div>
	<script type="text/javascript">
	function submitKeyword(){
		var keyword=$("#keyword").val();
		if(keyword){
			location.href="list.jsp?keyword="+encodeURIComponent(keyword);
		}else{
			location.href="list.jsp";
		}
	}
	
	function deleteCity(id){
		var returnBtn = window.confirm("确定要删除此记录吗？"); 
		if (!returnBtn) { 
			return;
		} 
		$.ajax({
			url:'<%=$restRoot%>/gtsd.City/'+id+'/delete',
			type:'post',
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
