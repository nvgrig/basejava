package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.HtmlView;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (fullName.trim().replaceAll("\\s+", " ").matches("([A-za-zА-Яа-яЁё]+)|([A-za-zА-Яа-яЁё]+\\s)+([A-za-zА-Яа-яЁё]+)")) {
            boolean isResumeNew = false;
            Resume resume;
            try {
                resume = storage.get(uuid);
            } catch (NotExistStorageException e) {
                isResumeNew = true;
                resume = new Resume(uuid, fullName);
            }
            resume.setFullName(fullName);
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                if (value != null && value.trim().length() != 0) {
                    resume.addContact(type, value);
                } else {
                    resume.getContacts().remove(type);
                }
            }
            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.name());
                String[] orgs = request.getParameterValues(type.name() + "org");
                if (HtmlView.isEmpty(value) && HtmlView.isEmpty(orgs)) {
                    resume.getSections().remove(type);
                } else {
                    switch (type) {
                        case PERSONAL, OBJECTIVE -> resume.addSection(type, new TextSection(value));
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            resume.addSection(type, new ListSection(Arrays.stream(value.split("\n")).filter(x -> !(x.equals("\r")) && !(x.matches("\\s+"))).collect(Collectors.toList())));
                        }
                        case EXPERIENCE, EDUCATION -> {
                            String[] urls = request.getParameterValues(type.name() + "url");
                            List<Organization> organizations = new ArrayList<>();
                            for (int i = 0; i < orgs.length; i++) {
                                if (!orgs[i].equals("")) {
                                    String[] beginDates = request.getParameterValues(i + type.name() + "beginDate");
                                    String[] finishDates = request.getParameterValues(i + type.name() + "finishDate");
                                    String[] titles = request.getParameterValues(i + type.name() + "title");
                                    String[] descriptions = request.getParameterValues(i + type.name() + "description");
                                    List<Organization.Position> positions = new ArrayList<>();
                                    for (int j = 0; j < titles.length; j++) {
                                        if (!titles[j].equals("")) {
                                            positions.add(new Organization.Position(DateUtil.parse(beginDates[j]), DateUtil.parse(finishDates[j]), titles[j], descriptions[j]));
                                        }
                                    }
                                    organizations.add(new Organization(orgs[i], urls[i], positions.toArray(new Organization.Position[0])));
                                }
                            }
                            resume.addSection(type, new OrganizationSection(organizations));
                        }
                    }
                }
            }
            if (isResumeNew) storage.save(resume);
            else storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                r = new Resume(UUID.randomUUID().toString(), "");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
