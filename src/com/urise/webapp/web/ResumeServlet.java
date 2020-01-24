package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.util.ServletUtil.addSectionsDoGet;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        String action = request.getParameter("action");

        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Resume resume;
        if (isCreate || action.equals("add")) {
            resume = new Resume(fullName);
            storage.save(resume);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

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
            String[] values = request.getParameterValues(stringType);
            if (value != null && value.trim().length() != 0) {
                switch (stringType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        LineSection lineSection = new LineSection(value);
                        resume.setSection(sectionType, lineSection);
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        String[] lines = value.split(" *\n");
                        List<String> list = Arrays.asList(lines);
//                        Stream.of(lines).forEach(v -> list.add(v.trim()));
                        ListSection listSection = new ListSection(list);
                        resume.setSection(sectionType, listSection);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":

                        List<Place> placeList = new ArrayList<>();
                        for (int i = 0; i < values.length; i = i + 6) {
                            String placeName = values[i];
                            if (placeName == null || placeName.equals("")) {
                                continue;
                            }
                            String urlPlace = values[i + 1];
                            LocalDate startDate = DateUtil.stringToLocalDate(values[i + 3]);
                            LocalDate endDate = DateUtil.stringToLocalDate(values[i + 4]);
                            String position = values[i + 2];
                            String descriptionPosition = values[i + 5];
                            Place place = new Place(placeName, urlPlace,
                                    new Place.PlaceDescription(startDate, endDate, position, descriptionPosition));
                            placeList.add(place);
                        }
                        PlaceSection placeSection = new PlaceSection(placeList);
                        resume.setSection(sectionType, placeSection);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionType);
                }

            } else {
                resume.getSectionsMap().remove(sectionType);
            }
        }
        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
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
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "add":
                resume = Resume.EMPTY;
                break;
            case "addEducation":
            case "addExperience":
            case "edit":
                resume = storage.get(uuid);
                addSectionsDoGet(resume, action);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
