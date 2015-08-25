<%--
  Created by IntelliJ IDEA.
  User: lijie
  Date: 2015-06-16
  Time: 下午 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
</head>
<body>
<form id="file" method="post" enctype="multipart/form-data">
    <input type="file" name="ipFile"><br>
    <input type="button" value="sss" onclick="ff();">
</form>
<script>
    function ff() {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/ip/upload",
            data: $('#file').serialize(),
            error: function (request) {
                alert("Connection error");
            },
            success: function (data) {
                alert(data)
            }
        });
    }

</script>
</body>
</html>
