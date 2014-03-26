<%@page
	import="org.springframework.security.web.savedrequest.HttpSessionRequestCache"%>
<%@page
	import="org.springframework.security.web.savedrequest.SavedRequest"%>
<%@page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/include/common.jsp"%>
<title>后台登录</title>
</head>

<body class="nobg loginPage">
	<%
		String currentBlock = "login";
		String tipMsgTitle = "", tipMsgContent = "";
		String $ = request.getParameter("$");
		if ("success".equals($)) {
			String returnUrl = request.getContextPath()+"/admin/index.jsp";
			SavedRequest savedRequest = new HttpSessionRequestCache()
					.getRequest(request, response);
			if (savedRequest != null) {
				returnUrl = savedRequest.getRedirectUrl();
			}
			response.sendRedirect(returnUrl);
			return;
		} else if ("deny".equals($)) {
			currentBlock = "tipbox";
			tipMsgTitle = "验证失败";
			tipMsgContent = "输入的用户名或密码有误,登录失败!";
		}
	%>
	<!-- Top fixed navigation -->
	<div class="topNav">
		<div class="wrapper">
			<div class="userNav">
				<ul>
					<li><a href="#" title=""><img
							src="images/icons/topnav/mainWebsite.png" alt="" /><span>网站首页</span></a></li>
					<li><a href="#" title=""><img
							src="images/icons/topnav/profile.png" alt="" /><span>联系我们</span></a></li>
					<li><a href="#" title=""><img
							src="images/icons/topnav/settings.png" alt="" /><span>设置</span></a></li>
				</ul>
			</div>
			<div class="clear"></div>
		</div>
	</div>


	<!-- Main content wrapper -->
	<div class="loginWrapper">
		<div class="loginLogo">
			<img src="images/loginLogo.png" alt="" />
		</div>
		<%if(currentBlock.equals("tipbox")){ %>
		<div class="nNote nFailure hideit">
			<p>
				<strong><%=tipMsgTitle %>: </strong><%=tipMsgContent %>
			</p>
		</div>
		<%} %>
		<div class="widget">
			<div class="title">
				<img src="images/icons/dark/files.png" alt="" class="titleIcon" />
				<h6>用户登录平台</h6>
			</div>
			<form action="<%=request.getContextPath()%>/j_spring_security_check" method="post"
				id="validate" class="form">
				<fieldset>
					<div class="formRow">
						<label for="login">用户名:</label>
						<div class="loginInput">
							<input type="text" name="j_username" class="validate[required]"
								id="login" />
						</div>
						<div class="clear"></div>
					</div>

					<div class="formRow">
						<label for="pass">密码:</label>
						<div class="loginInput">
							<input type="password" name="j_password"
								class="validate[required]" id="pass" />
						</div>
						<div class="clear"></div>
					</div>

					<div class="loginControl">
						<div class="rememberMe">
							<input type="checkbox" id="remMe"
								name="_spring_security_remember_me" /><label for="remMe">记住密码</label>
						</div>
						<input type="submit" value="登录" class="dredB logMeIn" />
						<div class="clear"></div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>
