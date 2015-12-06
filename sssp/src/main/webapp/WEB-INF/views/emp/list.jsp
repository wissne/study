<%--
  Created by IntelliJ IDEA.
  User: AUROGON
  Date: 2015/12/6
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${page == null || page.numberOfElements == 0}">
    没有任何记录！
</c:if>
<c:if test="${page != null && page.numberOfElements > 0}">
    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>Id</th>
            <th>Last Name</th>

            <th>Email</th>
            <th>Bitrh</th>

            <th>Department</th>

            <th>Create time</th>
            <th>Last update time</th>

            <th>Edit</th>
            <th>Delete</th>
        </tr>

        <c:forEach items="${page.content}" var="emp">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.lastName}</td>

                <td>${emp.email}</td>
                <td><fmt:formatDate value="${emp.birth}" pattern="yyyy-MM-dd"/></td>

                <td>${emp.department.departmentName}</td>

                <td><fmt:formatDate value="${emp.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                <td><fmt:formatDate value="${emp.lastUpdateTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>

                <td><a href="${pageContext.request.contextPath}/emp/${emp.id}">Edit</a></td>
                <td>
                    <a href="${pageContext.request.contextPath}/emp/${emp.id}" class="delete">Delete</a>
                    <input value="${emp.lastName}" type="hidden"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="9">
                共${page.totalElements}条记录
                共${page.totalPages}页
                当前第${page.number+1}页
                <c:if test="${page.number > 0}">
                    <a href="?pageNo=${page.number + 1 - 1}">上一页</a>
                </c:if>
                <c:if test="${page.number < page.totalPages - 1}">
                    <a href="?pageNo=${page.number + 1 + 1}">下一页</a>
                </c:if>
            </td>
        </tr>
    </table>
</c:if>
<form action="" method="POST" id="_form">
    <input type="hidden" id="_method" name="_method" value="delete"/>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
    $(function(){
        $(".delete").click(function(){
            var label = $(this).next(":hidden").val();
            var flag = confirm("确定要删除" + label + "的信息吗？");
            if (flag) {
                var url = $(this).attr("href");

                $("#_form").attr("action", url);
                $("#_form").submit();
            }
            return false;
        });
    })
</script>
</body>
</html>
