package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ResumeServlet extends HttpServlet {

    private Storage storage = Config.get().getStorage();


/* If need a new logic during loading Servlet use init()
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            storage = Config.get().getStorage();
    }
    */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (fullName.equals("")) {
            fullName = "<b>Введите имя!!!</b>";
        }
        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && value.trim().length() != 0) {
                resume.setContact(contactType, value);
            } else {
                resume.getContactsMap().remove(contactType);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            String stringType = sectionType.name();
            String value = request.getParameter(stringType);
            if (value != null && value.trim().length() != 0) {
                switch (stringType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        LineSection lineSection = new LineSection(value);
                        resume.setSection(sectionType, lineSection);
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        String[] lines = value.split(",");
                        List<String> list = new ArrayList<>();
                        Stream.of(lines).forEach(v -> list.add(v.trim()));
                        ListSection listSection = new ListSection(list);
                        resume.setSection(sectionType, listSection);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionType);
                }

            } else {
                resume.getSectionsMap().remove(sectionType);
            }
        }
        storage.update(resume);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        Resume resume;

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                resume = new Resume("");
                storage.save(resume);
                // ResumeTestData.createEmptyResume(resume);
                break;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" :
                        "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
/*
edit.jsp
<c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSections"/>
            <dl>
                <dt>${type.title}</dt>
                <c:choose>
                    <c:when test="${(type.name().equals('OBJECTIVE') || type.name().equals('PERSONAL')) }">
                        <dd><input type="text" name="${type.name()}" size=130 value="${section}"></dd>
                    </c:when>
                    <c:when test="${(type.name().equals('ACHIEVEMENT') || type.name().equals('QUALIFICATION')) }">
                        <dd><input type="text" name="${type.name()}" size=130 value="
                        <%=String.join(", " , ((ListSection) section).getItems().)%>"></dd>
                    </c:when>
                    <c:otherwise>
                        <dd><input type="text" name="${type.name()}" size=130 value="${resume.getSection(type)}"></dd>
                    </c:otherwise>
                </c:choose>
            </dl>
        </c:forEach>
 */