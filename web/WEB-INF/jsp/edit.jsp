<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <c:if test="${section != null}">
                <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
                <c:choose>
                    <c:when test="${type == 'PERSONAL' or type == 'OBJECTIVE'}">
                        <h3>${type.title}</h3>
                        <dl><input type="text" name="${type.name()}" size=30
                                   value="<%=((TextSection) section).getContent()%>"></dl>
                    </c:when>
                    <c:when test="${type == 'ACHIEVEMENT' or type == 'QUALIFICATIONS'}">
                        <h3>${type.title}</h3>
                        <textarea name='${type.name()}' cols=35
                                  rows=5><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                    </c:when>
                </c:choose>
            </c:if>
            <c:if test="${section == null}">
                <c:choose>
                    <c:when test="${type == 'PERSONAL' or type == 'OBJECTIVE'}">
                        <h3>${type.title}</h3>
                        <dl><input type="text" name="${type.name()}" size=30 value=""></dl>
                    </c:when>
                    <c:when test="${type == 'ACHIEVEMENT' or type == 'QUALIFICATIONS'}">
                        <h3>${type.title}</h3>
                        <textarea name='${type.name()}' cols=35 rows=5></textarea>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

