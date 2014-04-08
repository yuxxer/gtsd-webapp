<%@ page language="java" pageEncoding="utf-8"%><%@ include file="page.jspf"%>
<!-- Left side content -->
<%
String active=request.getParameter("active");
if(StringUtils.isNotBlank(active)){
	session.setAttribute("_last_active_", active);
}else{
	active="index";
	Object old=session.getAttribute("_last_active_");
	if(old!=null){
		active=old.toString();
	}
}
%>
<div id="leftSide">
    <div class="logo"><a href="<%=request.getContextPath() %>/admin/index.jsp"><img src="<%=request.getContextPath() %>/images/logo.png" alt="" /></a></div>
    
    <div class="sidebarSep mt0"></div>
    
    <!-- Search widget -->
    <form action="#" class="sidebarSearch">
        <input type="text" name="search" placeholder="检索" id="search" />
        <input type="submit" value="" />
    </form>
    
    <div class="sidebarSep"></div>
    
    <!-- Left navigation -->
    <ul id="menu" class="nav">
        <li class="dash"><a href="<%=request.getContextPath() %>/admin/index.jsp?active=index" title="" <%=active.equals("index")?"class=\"active\"":"" %>><span>首页</span></a></li>
        <li class="widgets"><a href="#" title="" class="exp"<%
        if(active.equals("province")||active.equals("city")||active.equals("zoom"))out.print("id=\"current\"");
        %>><span>派送范围</span><strong>3</strong></a>
        	<ul class="sub">
                 <li  <%=active.equals("province")?"class=\"this\"":"" %>><a href="<%=request.getContextPath() %>/admin/province/list.jsp?active=province" title="">省份/直辖市</a></li>
                 <li  <%=active.equals("city")?"class=\"this\"":"" %>><a href="<%=request.getContextPath() %>/admin/city/list.jsp?active=city" title="">市/地区</a></li>
                 <li  <%=active.equals("zoom")?"class=\"this\"":"" %>><a href="<%=request.getContextPath() %>/admin/zoom/list.jsp?active=zoom" title="" >派送网点</a></li>
             </ul>
        </li>
        <li class="typo"><a href="<%=request.getContextPath() %>/admin/faq/list.jsp?active=faq" title="" <%=active.equals("faq")?"class=\"active\"":"" %>><span>常见问题</span></a></li>
    </ul>
</div>
