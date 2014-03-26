<%@ page language="java" pageEncoding="utf-8"%><%@ include file="page.jspf"%>
<!-- Top fixed navigation -->
    <div class="topNav">
        <div class="wrapper">
            <div class="welcome"><a href="#" title=""><img src="<%=request.getContextPath() %>/images/userPic.png" alt="" /></a><span>欢迎,<a href=""><%=$user.getName() %></a></span></div>
            <div class="userNav">
                <ul>
                    <li><a href="#" title=""><img src="<%=request.getContextPath() %>/images/icons/topnav/settings.png" alt="" /><span>设置</span></a></li>
                    <li><a href="<%=request.getContextPath() %>/logout" title=""><img src="<%=request.getContextPath() %>/images/icons/topnav/logout.png" alt="" /><span>注销</span></a></li>
                </ul>
            </div>
            <div class="clear"></div>
        </div>
    </div>