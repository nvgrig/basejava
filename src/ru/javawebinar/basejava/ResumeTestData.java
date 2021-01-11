package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.getContacts().put(ContactType.PHONE, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.WEBPAGE, "http://gkislin.ru/");
        resume.getSections().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.getSections().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        List<String> achiveList = new ArrayList<>();
        achiveList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achiveList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.getSections().put(SectionType.ACHIEVEMENT, new ListSection<>(achiveList));
        List<String> qualiList = new ArrayList<>();
        qualiList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualiList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.getSections().put(SectionType.QUALIFICATIONS, new ListSection<>(qualiList));
        List<Place> workPlaces = new ArrayList<>();
        workPlaces.add(new Place("Luxoft (Deutsche Bank)", "https://luxoft.com/", YearMonth.of(2012, 10), YearMonth.of(2012, 4), "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        workPlaces.add(new Place("Yota", "https://www.yota.ru/", YearMonth.of(2008, 6), YearMonth.of(2010, 12), "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        resume.getSections().put(SectionType.EXPERIENCE, new ListSection<>(workPlaces));
        List<Place> eduPlaces = new ArrayList<>();
        eduPlaces.add(new Place("Coursera", "https://www.coursera.org/", YearMonth.of(2013, 3), YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky", null));
        eduPlaces.add(new Place("Luxoft", "https://luxoft.com/", YearMonth.of(2011, 3), YearMonth.of(2011, 4), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null));
        resume.getSections().put(SectionType.EDUCATION, new ListSection<>(eduPlaces));

        System.out.println(resume);
        System.out.println("----------------------------");
        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + resume.getContacts().get(type));
        }
        System.out.println("----------------------------");
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(resume.getSections().get(type));
            System.out.println();
        }

    }
}
