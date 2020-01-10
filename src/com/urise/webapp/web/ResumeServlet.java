package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    // private Storage storage = Config.get().getStorage();
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       //storage =  new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres","password");
      storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        // Аналог - response.setHeader("Content-Type", "text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        response.getWriter().write(
                "<html>" +
                        "<body>" +
                        "<h3 align='center'>Список резюме</h3>" +
                        "<table border='1' cellpadding='10' align='center' " +
                        "<tr>" +
                        "<th bgcolor='#add8e6'>Number</th>" +
                        "<th bgcolor='#add8e6'>UUID</th>" +
                        "<th bgcolor='#add8e6'>FullName</th>" +
                        "</tr>");
        if (uuid == null) {
            for (int i = 0; i < storage.getAllSorted().size(); i++) {
                response.getWriter().write(
                        "<tr>" +
                                "<td align='center'>" + (i + 1) + "</td>" +
                                "<td align='center'>" + storage.getAllSorted().get(i).getUuid() + "</td>" +
                                "<td align='center'>" + storage.getAllSorted().get(i).getFullName() + "</td>" +
                                "</tr>"
                );
            }
        } else response.getWriter().write(
                "<tr>" +
                        "<td align='center'>" + 1 + "</td>" +
                        "<td align='center'>" + uuid + "</td>" +
                        "<td align='center'>" + storage.get(uuid).getFullName() + "</td>" +
                        "</tr>");
        response.getWriter().write(
                "</table>" +
                        "</body>" +
                        "</html>");
    }
}
