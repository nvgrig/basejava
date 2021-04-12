<%@ page import="ru.javawebinar.basejava.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список всех резюме</title>
</head>
<body>
<section>
    <table>
        <tr>
            <th>ИМЯ</th>
            <th>E-MAIL</th>
            <th></th>
            <th></th>
        </tr>
        <%
            for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {
        %>
        <tr>
            <td><a href="resume?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%></a></td>
            <td><%=resume.getContact(ContactType.MAIL)%></td>
        </tr>
        <%
            }
        %>
    </table>
</section>
</body>
</html>
