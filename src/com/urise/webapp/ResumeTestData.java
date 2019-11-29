package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
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
//        list.add("\nНалаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
//                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
//                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
//                "интеграция CIFS/SMB java сервера");
//        list.add("\nРеализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, " +
//                "GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
//        list.add("\nСоздание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
//                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии " +
//                "через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга " +
//                "системы по JMX (Jython/ Django).");
//        list.add("\nРеализация протоколов по приему платежей всех основных платежных системы России" +
//                " (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        return list;
    }

    public static List<String> fillListQualification() {
        List<String> list = new ArrayList<>();
        list.add("\nJEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
//        list.add("\nVersion control: Subversion, Git, Mercury, ClearCase, Perforce");
//        list.add("\nDB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, " +
//                "MySQL, SQLite, MS SQL, HSQLDB");
//        list.add("\nLanguages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy," +
//                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
//        list.add("\nJava Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
//                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), " +
//                "Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, " +
//                "Eclipse SWT, JUnit, Selenium (htmlelements).");
//        list.add("\nJavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
//        list.add("\nScala: SBT, Play2, Specs2, Anorm, Spray, Akka");
//        list.add("\nТехнологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, " +
//                "MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
//        list.add("\nИнструменты: Maven + plugin development, Gradle, настройка Ngnix,");
//        list.add("\nадминистрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, " +
//                "OpenCmis, Bonita, pgBouncer.");
//        list.add("\nОтличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, " +
//                "UML, функционального программирования");
//        list.add("\nРодной русский, английский \"upper intermediate\"");

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
                        "" ), new PlaceDescription(LocalDate.of(1987, 9, 1),
                        LocalDate.of(1993, 7, 1), "\nИнженер (программист Fortran, C)",
                        "" ))));
        return list;
    }
}
