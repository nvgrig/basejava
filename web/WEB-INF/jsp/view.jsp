<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlView" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <h3>Контакты:</h3>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="content" value="${sectionEntry.value}"/>
            <jsp:useBean id="content" type="ru.javawebinar.basejava.model.AbstractSection"/>
    <h3>${type.title}</h3>
    <c:choose>
        <c:when test="${type == 'PERSONAL' or type == 'OBJECTIVE'}">
            <%=((TextSection) content).getContent()%><br/>
        </c:when>
        <c:when test="${type == 'ACHIEVEMENT' or type == 'QUALIFICATIONS'}">
            <c:forEach var="listItem" items="<%=((ListSection) content).getItems()%>">
                <jsp:useBean id="listItem" type="java.lang.String"/>
                <%=listItem%><br/>
            </c:forEach>
        </c:when>
        <c:when test="${type == 'EXPERIENCE' or type == 'EDUCATION'}">
            <c:forEach var="orgItem" items="<%=((OrganizationSection) content).getOrganizations()%>">
                <jsp:useBean id="orgItem" type="ru.javawebinar.basejava.model.Organization"/>
                <tr>
                    <td>
                        <h3><%=orgItem.getHomePage().getName() + " " + HtmlView.formatBrackets(orgItem.getHomePage().getUrl())%>
                        </h3></td>
                </tr>
                <div style="margin-left: 30px">
                    <c:forEach var="position" items="${orgItem.positions}">
                        <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <tr>
                            <td><%=DateUtil.format(position.getBeginDate()) + " - " + DateUtil.format(position.getFinishDate()) + ": "%>
                            </td>
                            <td><%=position.getTitle() + " " + HtmlView.formatBrackets(position.getDescription())%>
                            </td>
                            <br>
                        </tr>
                    </c:forEach>
                </div>
            </c:forEach>
        </c:when>
    </c:choose>
    </c:forEach>
    <p>
        <button onclick="window.history.back()">Назад</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
