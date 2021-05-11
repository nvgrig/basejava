<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
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
                    <c:when test="${type == 'EXPERIENCE' or type == 'EDUCATION'}">
                        <h3>${type.title}</h3>
                        <dl>
                            <dt>Наименование организации (NEW):</dt>
                            <dd><input type="text" name="${type.name()}org" size=30 value=""></dd>
                        </dl>
                        <dl>
                            <dt>Сайт организации (NEW):</dt>
                            <dd><input type="text" name="${type.name()}url" size=30 value=""></dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <dl>
                                <dt>Дата начала (NEW):</dt>
                                <dd><input type="text" name="0${type.name()}beginDate" size=30 value=""></dd>
                            </dl>
                            <dl>
                                <dt>Дата окончания (NEW):</dt>
                                <dd><input type="text" name="0${type.name()}finishDate" size=30 value=""></dd>
                            </dl>
                            <dl>
                                <dt>Должность (NEW):</dt>
                                <dd><input type="text" name='0${type.name()}title' size=30 value=""></dd>
                            </dl>
                            <dl>
                                <dt>Описание (NEW):</dt>
                                <dd><textarea name="0${type.name()}description" rows=5 cols=30></textarea></dd>
                            </dl>
                            <br>
                        </div>
                        <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>"
                                   varStatus="orgNumber">
                            <dl>
                                <dt>Наименование организации:</dt>
                                <dd><input type="text" name="${type.name()}org" size=30
                                           value="${organization.homePage.name}"></dd>
                            </dl>
                            <dl>
                                <dt>Сайт организации:</dt>
                                <dd><input type="text" name="${type.name()}url" size=30
                                           value="${organization.homePage.url}"></dd>
                            </dl>
                            <br>
                            <div style="margin-left: 30px">
                                <dl>
                                    <dt>Дата начала (NEW):</dt>
                                    <dd><input type="text" name="${orgNumber.count}${type.name()}beginDate" size=30 value=""></dd>
                                </dl>
                                <dl>
                                    <dt>Дата окончания (NEW):</dt>
                                    <dd><input type="text" name="${orgNumber.count}${type.name()}finishDate" size=30 value=""></dd>
                                </dl>
                                <dl>
                                    <dt>Должность (NEW):</dt>
                                    <dd><input type="text" name='${orgNumber.count}${type.name()}title' size=30 value=""></dd>
                                </dl>
                                <dl>
                                    <dt>Описание (NEW):</dt>
                                    <dd><textarea name="${orgNumber.count}${type.name()}description" rows=5 cols=30></textarea></dd>
                                </dl>
                                <br>
                                <c:forEach var="position" items="${organization.positions}">
                                    <jsp:useBean id="position"
                                                 type="ru.javawebinar.basejava.model.Organization.Position"/>
                                    <dl>
                                        <dt>Дата начала:</dt>
                                        <dd><input type="text" name="${orgNumber.count}${type.name()}beginDate" size=30
                                                   value="<%=DateUtil.format(position.getBeginDate())%>"></dd>
                                    </dl>
                                    <dl>
                                        <dt>Дата окончания:</dt>
                                        <dd><input type="text" name="${orgNumber.count}${type.name()}finishDate" size=30
                                                   value="<%=DateUtil.format(position.getFinishDate())%>"></dd>
                                    </dl>
                                    <dl>
                                        <dt>Должность:</dt>
                                        <dd><input type="text" name='${orgNumber.count}${type.name()}title' size=30
                                                   value="${position.title}"></dd>
                                    </dl>
                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><textarea name="${orgNumber.count}${type.name()}description" rows=5
                                                      cols=30>${position.description}</textarea></dd>
                                    </dl>
                                    <br>
                                </c:forEach>
                            </div>
                        </c:forEach>
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
                    <c:when test="${type == 'EXPERIENCE' or type == 'EDUCATION'}">
                        <h3>${type.title}</h3>
                        <dl>
                            <dt>Наименование организации:</dt>
                            <dd><input type="text" name="${type.name()}org" size=30 value=""></dd>
                        </dl>
                        <dl>
                            <dt>Сайт организации:</dt>
                            <dd><input type="text" name="${type.name()}url" size=30 value=""></dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <dl>
                                <dt>Дата начала:</dt>
                                <dd><input type="text" name="0${type.name()}beginDate" size=30 value=""></dd>
                            </dl>
                            <dl>
                                <dt>Дата окончания:</dt>
                                <dd><input type="text" name="0${type.name()}finishDate" size=30 value=""></dd>
                            </dl>
                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name='0${type.name()}title' size=30 value=""></dd>
                            </dl>
                            <dl>
                                <dt>Описание:</dt>
                                <dd><textarea name="0${type.name()}description" rows=5 cols=30></textarea></dd>
                            </dl>
                            <br>
                        </div>
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

