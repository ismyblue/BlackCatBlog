<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Error</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style>
h1 {
	margin: 0 auto;
	text-align: center;
}

h3 {
	margin:0 auto;
	display: block;
	width: 90%;	
}
</style>

</head>

<body>
	<h1>Error!</h1>
	<h2>Information</h2>
	<h3>
		<%
			out.write("Exception: " + exception + "<br>");
			Enumeration<String> attributeNames = request.getAttributeNames();
			while (attributeNames.hasMoreElements()) {
				String attributeName = attributeNames.nextElement();
				Object attribute = request.getAttribute(attributeName);
				out.println("request.attribute['" + attributeName + "'] = " + attribute + "<br>");
			}
		%>
	</h3>
</body>

</html>
