<%--
  Created by IntelliJ IDEA.
  User: AUROGON
  Date: 2015/12/6
  Time: 22:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:set value="${pageContext.request.contextPath}/emp" var="url"></c:set>
    <c:if test="${employee.id != null}">
        <c:set value="${pageContext.request.contextPath}/emp/${employee.id}" var="url"></c:set>
    </c:if>
    <form:form action="${url}" method="post" modelAttribute="employee">
        <c:if test="${employee.id != null }">
            <input type="hidden" id="_oldLastName" value="${employee.lastName }"/>
            <form:hidden path="id"/>
            <input type="hidden" name="_method" value="put"/>
        </c:if>
        <div><span id="msg"></span></div>
        Last Name:<form:input id="lastName" path="lastName"/>
        <br/>
        Email:<form:input path="email"/>
        <br/>
        Birth:<form:input path="birth"/>
        <br/>
        Department:
        <form:select path="department.id" items="${departments}" itemLabel="departmentName" itemValue="id"/>
        <br/>
        <input type="submit" value="Submit"/>
    </form:form>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("#lastName").change(function(){
            var val = $(this).val();
            val = $.trim(val);
            $(this).val(val);

            //若修改的 lastName 和之前的 lastName 一致, 则不发送 Ajax 请求, 直接 alert:lastName 可用!
            var _oldLastName = $("#_oldLastName").val();
            _oldLastName = $.trim(_oldLastName);
            if(_oldLastName != null && _oldLastName != "" && _oldLastName == val){
                $("#msg").html("用户名可用！");
                $("#msg").css("color","#0F0");
                return;
            }

            var url = "${pageContext.request.contextPath}/ajaxValidateLastName";
            var args = {"lastName": val, "date": new Date()};

            $.post(url, args, function(data, error){
                if (error) {
                    $("#msg").html("用户名验证失败！");
                }
                if (data == "0") {
                    $("#msg").html("用户名可用！");
                    $("#msg").css("color","#0F0");
                } else if (data == "1") {
                    $("#msg").html("用户名不可用！");
                    $("#msg").css("color","#F00");
                } else {
                    $("#msg").html("用户名验证失败！");
                }
            })
        });
    })
</script>

</html>
