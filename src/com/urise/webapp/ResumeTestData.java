package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {

    public static void main(String[] args) {

        Resume resume = new Resume("Григорий Кислин");
        resume.setContactInfo(ContactsType.MOBIL, "+7(921) 855-0482 ");
        resume.setContactInfo(ContactsType.HOME_PHONE, "");
        resume.setContactInfo(ContactsType.SKYPE, "grigory.kislin");
        resume.setContactInfo(ContactsType.E_MAIL, "gkislin@yandex.ru");
        resume.setContactInfo(ContactsType.PROFILE_LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.setContactInfo(ContactsType.PROFILE_GITHUB, "https://github.com/gkislin");
        resume.setContactInfo(ContactsType.PROFILE_STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.setContactInfo(ContactsType.HOME_PAGE, "http://gkislin.ru/");

        resume.setSectionInfo(SectionType.OBJECTIVE, new LineSections(
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.setSectionInfo(SectionType.PERSONAL, new LineSections(
                "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры"));
        resume.setSectionInfo(SectionType.ACHIEVEMENT, new ListSections(fillListAchievement()));
        resume.setSectionInfo(SectionType.QUALIFICATION, new ListSections(fillListQualification()));
        resume.setSectionInfo(SectionType.EXPERIENCE, new PlaceSections(fillListExperience()));
        resume.setSectionInfo(SectionType.EDUCATION, new PlaceSections(fillListEducation()));

        System.out.println(resume.getFullName());

        for (ContactsType type : ContactsType.values()) {
            System.out.println(type.getTitle() + ": " + resume.getContactInfo(type));
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + ": " + resume.getSectionInfo(type));
        }
    }

    public static List<String> fillListAchievement() {

        List<String> list = new ArrayList<>();
        list.add("\nС 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "даленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        list.add("\nРеализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        list.add("\nНалаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера");
        list.add("\nРеализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, " +
                "GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        list.add("\nСоздание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии " +
                "через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга " +
                "системы по JMX (Jython/ Django).");
        list.add("\nРеализация протоколов по приему платежей всех основных платежных системы России" +
                " (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        return list;
    }

    public static List<String> fillListQualification() {
        List<String> list = new ArrayList<>();
        list.add("\nJEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        list.add("\nVersion control: Subversion, Git, Mercury, ClearCase, Perforce");
        list.add("\nDB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, " +
                "MySQL, SQLite, MS SQL, HSQLDB");
        list.add("\nLanguages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy," +
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        list.add("\nJava Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), " +
                "Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, " +
                "Eclipse SWT, JUnit, Selenium (htmlelements).");
        list.add("\nJavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        list.add("\nScala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        list.add("\nТехнологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, " +
                "MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        list.add("\nИнструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        list.add("\nадминистрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, " +
                "OpenCmis, Bonita, pgBouncer.");
        list.add("\nОтличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, " +
                "UML, функционального программирования");
        list.add("\nРодной русский, английский \"upper intermediate\"");

        return list;
    }

    public static List<Place> fillListExperience() {
        List<Place> list = new ArrayList<>();
        list.add(new Place("\nSiemens AG",
                "\nhttps://www.siemens.com/ru/ru/home.html",
                Arrays.asList(new PlaceDescription(LocalDate.of(2005, 1, 1),
                        LocalDate.of(2007, 2, 1), "\nРазработчик ПО",
                        "\nРазработка информационной модели, проектирование интерфейсов, " +
                                "реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."))));
        return list;
    }

    public static List<Place> fillListEducation() {
        List<Place> list = new ArrayList<>();
        list.add(new Place(
                "\nСанкт-Петербургский национальный исследовательский университет информационных технологий, " +
                        "механики и оптики",
                "\nhttp://www.ifmo.ru/",
                Arrays.asList(new PlaceDescription(LocalDate.of(1993, 9, 1),
                        LocalDate.of(1996, 7, 1), "\nАспирантура (программист С, С++)",
                        ""), new PlaceDescription(LocalDate.of(1987, 9, 1),
                        LocalDate.of(1993, 7, 1), "\nИнженер (программист Fortran, C)",
                        ""))));
        return list;
    }

    //Part for create Resume from AbstractStorageTest
    private static List<String> mail = Arrays.asList("1@1", "2@2", "3@3", "4@4");
    private static List<String> mobil = Arrays.asList("111", "222", "333", "444");
    private static List<String> objective = Arrays.asList("position1", "position2", "position3", "position4");
    private static List<String> personal = Arrays.asList("uno", "dos", "tres", "quatro");
    private static List<String> achievement = Arrays.asList("one", "two", "three", "four");
    private static List<String> qualification = Arrays.asList("cinco", "seis", "siete", "ocho");
    private static List<String> experienceName = Arrays.asList("company1", "company2", "company3", "company4");
    private static List<String> experienceTitle = Arrays.asList("number1", "number2", "number3", "number4");
    private static List<String> educationName = Arrays.asList("school1", "school2", "school3", "school4");
    private static List<String> educationTitle = Arrays.asList("student1", "student2", "student3", "student4");
    private static List<LocalDate> startDate = Arrays.asList(DateUtil.of(1990, Month.FEBRUARY),
            DateUtil.of(1991, Month.FEBRUARY),
            DateUtil.of(1992, Month.FEBRUARY),
            DateUtil.of(1993, Month.FEBRUARY));
    private static List<LocalDate> endDate = Arrays.asList(DateUtil.of(1991, Month.FEBRUARY),
            DateUtil.of(1992, Month.FEBRUARY),
            DateUtil.of(1993, Month.FEBRUARY),
            DateUtil.of(1994, Month.FEBRUARY));
    private static int count = 0;

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.setContactInfo(ContactsType.MOBIL, mobil.get(count));
        resume.setContactInfo(ContactsType.E_MAIL, mail.get(count));
        resume.setSectionInfo(SectionType.OBJECTIVE, new LineSections(objective.get(count)));
        resume.setSectionInfo(SectionType.PERSONAL, new LineSections(personal.get(count)));
        resume.setSectionInfo(SectionType.ACHIEVEMENT,
                new ListSections(Arrays.asList(achievement.get(count))));
        resume.setSectionInfo(SectionType.QUALIFICATION,
                new ListSections(Arrays.asList(qualification.get(count))));
        resume.setSectionInfo(SectionType.EXPERIENCE,
                new PlaceSections(Arrays.asList(
                        new Place(experienceName.get(count), null,
                                Arrays.asList(
                                        new PlaceDescription(
                                                startDate.get(count),
                                                endDate.get(count),
                                                experienceTitle.get(count),
                                                null))))));
        resume.setSectionInfo(SectionType.EDUCATION,
                new PlaceSections(Arrays.asList(
                        new Place(educationName.get(count), null,
                                Arrays.asList(
                                        new PlaceDescription(
                                                startDate.get(count),
                                                endDate.get(count),
                                                educationTitle.get(count),
                                                null))))));
        count++;
        if (count > 4) {
            count = 0;
        }
        return resume;
    }
}
