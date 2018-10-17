<%--
  Created by IntelliJ IDEA.
  User: 461152465
  Date: 2018/10/17
  Time: 20:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>商城首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <!--<script src="js/jquery-1.11.0.js"></script>-->
    <!--最开始使用相对路径找不到js文件，发现使用绝对路径时可以显示BootStrap样式-->
    <script src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>

    <style>
        button{
            margin:5px
        }
    </style>
</head>
<body>
<div class="container">
    <a href="${pageContext.request.contextPath}/user?method=loginUI">
        <button type="button" class="btn btn-default pull-left">登录</button>
    </a>

    <a href="${pageContext.request.contextPath}/user?method=registUI">
        <button type="button" class="btn btn-primary pull-left">注册</button>
    </a>
</div>
</body>
</html>