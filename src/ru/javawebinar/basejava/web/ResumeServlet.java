package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        if (uuid == null) {
            List<Resume> resumes = sqlStorage.getAllSorted();
            printTableHeader(response);
            resumes.forEach(resume -> printTableLine(response, resume));
        } else {
            printTableHeader(response);
            printTableLine(response, sqlStorage.get(uuid));
        }
        printTableEnding(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private void printTableHeader(HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table {\n" +
                "  font-family: arial, sans-serif;\n" +
                "  border-collapse: collapse;\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                "td, th {\n" +
                "  border: 1px solid #dddddd;\n" +
                "  text-align: left;\n" +
                "  padding: 8px;\n" +
                "}\n" +
                "\n" +
                "tr:nth-child(even) {\n" +
                "  background-color: #dddddd;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h2>RESUMES</h2>\n" +
                "\n" +
                "<table>\n" +
                "  <tr>\n" +
                "    <th>UUID</th>\n" +
                "    <th>FULL NAME</th>\n" +
                "  </tr>");
    }

    private void printTableLine(HttpServletResponse response, Resume resume) {
        try {
            response.getWriter().println("<tr>\n" +
                    "    <td>" + resume.getUuid() + "</td>\n" +
                    "    <td>" + resume.getFullName() + "</td>\n" +
                    "  </tr>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printTableEnding(HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>");
    }

}
