<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>register</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>

	<form action="register.do" method="post">
		用户名：<input type="text" name="userLogin" /><br>
		密码：<input type="password" name="userPass" /><br>
		再次输入密码：<input type="password" name="reUserPass" /><br>
		昵称：<input type="text" name="userNicename" /><br>
		邮箱：<input type="text" name="userEmail" /><br>
		验证码:<input type="text" name="emailCaptcha" /><br>
		博客网站名称：<input type="text" name="siteName" /><br>
		博客网址：<input type="text" name="userUrl" /><br>
		<input type="submit" value="提交" />
	</form>
</body>
</html>
