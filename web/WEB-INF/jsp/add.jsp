<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="java.util.UUID" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Заполните данные нового резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <jsp:useBean id="resume" class="ru.javawebinar.basejava.model.Resume"/>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="<%=UUID.randomUUID().toString()%>">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value=""></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <h3>${type.title}</h3>
            <c:choose>
                <c:when test="${type.name() == 'PERSONAL' or type.name() == 'OBJECTIVE'}">
                    <dl><input type="text" name="${type.name()}" size=30 value=""></dl>
                </c:when>
                <c:when test="${type.name() == 'ACHIEVEMENT' or type.name() == 'QUALIFICATIONS'}">
                    <dl><input type="text" name="${type.name()}" size=30 value=""></dl>
                    <dl><input type="text" name="${type.name()}" size=30 value=""></dl>
                    <dl><input type="text" name="${type.name()}" size=30 value=""></dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
