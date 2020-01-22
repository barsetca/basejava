<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.PlaceSection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <% String action = request.getParameter("action");
            if (action.equals("add")) {%>
        <a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/back-button.png" width="150" height="45"></a>
        <% } else {%>
        <a href="resume"><img src="img/back-button.png" width="150" height="45"></a>
        <% } %> <br/>
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" required name="fullName" size=50 value="${resume.fullName}"
                       placeholder="Обязательное поле"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Описание:</h3>
        <table>

            <c:forEach var="type" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(type)}"/>
                <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSections"/>

                <tr>
                    <td><b>${type.title}</b></td>
                </tr>

                <c:choose>
                    <c:when test="${(type.name().equals('OBJECTIVE'))}">

                        <tr>
                            <td><input type="text" required name="${type.name()}" size=75
                                       value="${resume.getSection(type)}"
                                       placeholder="Обязательное поле"></td>
                        </tr>

                    </c:when>
                    <c:when test="${(type.name().equals('PERSONAL'))}">

                        <tr>
                            <td><textarea name='${type}' cols=80 rows=5><%=section%></textarea></td>
                        <tr>
                    </c:when>


                    <c:when test="${(type.name().equals('ACHIEVEMENT') || type.name().equals('QUALIFICATION'))}">
                        <jsp:useBean id="type" type="com.urise.webapp.model.SectionType"/>
                        <%
                            if (resume.getSection(type).toString().equals("null")) {
                                resume.setSection(type, new ListSection(""));
                            }
                        %>
                        <tr>
                            <td><textarea name='${type}' cols=80
                                          rows=5><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                            </td>
                        </tr>
                    </c:when>

                    <c:when test="${(type.name().equals('EXPERIENCE')|| type.name().equals('EDUCATION'))}">


                        <c:forEach var="place" items="<%=((PlaceSection) section).getPlaces()%>">
                            <tr>
                                <td>Название учреждения</td>
                            </tr>

                            <tr>
                                <td><textarea name="${type.name()}" rows="1" cols="80">${place.link.name}</textarea>
                                </td>
                            </tr>
                            <tr>
                                <td>Сайт (url) учреждения</td>
                            </tr>
                            <tr>
                                <td><textarea name="${type.name()}" rows="1" cols="80">${place.link.url}</textarea></td>
                            </tr>
                            <c:forEach var="descriptonList" items="${place.listDescriptions}">
                                <jsp:useBean id="descriptonList" type="com.urise.webapp.model.Place.PlaceDescription"/>
                                <tr>
                                    <td>Название должности/специальности</td>
                                </tr>
                                <tr>
                                    <td><textarea name="${type.name()}" rows="1"
                                                  cols="80">${descriptonList.title}</textarea></td>
                                </tr>
                                <tr>
                                    <td>Дата начала</td>
                                </tr>
                                <tr>
                                    <td>
                                        <input type="text" name="${type.name()}" size=10
                                               value="<%=DateUtil.localDateToString(descriptonList.getStartDate())%>"
                                               placeholder="dd/MM/yyyy">
                                    </td>
                                </tr>
                                <tr>
                                    <td>Дата завершения</td>
                                </tr>
                                <tr>
                                    <td>
                                        <input type="text" name="${type.name()}" size=10
                                               value="<%=DateUtil.localDateToString(descriptonList.getEndDate())%>"
                                               placeholder="dd/MM/yyyy">
                                    </td>
                                </tr>
                                <tr>
                                    <td>Описание</td>
                                </tr>
                                <tr>
                                    <td><textarea name="${type.name()}" rows="5"
                                                  cols="80">${descriptonList.description}</textarea></td>
                                </tr>
                            </c:forEach>
                        </c:forEach>

                    </c:when>

                </c:choose>

            </c:forEach>
        </table>

        <hr>
        <button type="submit">Сохранить</button>
        <% if (action.equals("add")) {%>
        <button><a href="resume?uuid=${resume.uuid}&action=delete">Отменить</a></button>
        <br/>
        <% } else {%>
        <button onclick="window.history.back()">Отменить</button>
        <button><a href="resume?uuid=${resume.uuid}&action=addEducation">Добавить учебное заведение</a></button>
        <button><a href="resume?uuid=${resume.uuid}&action=addExperience">Добавить место работы</a></button>
        <br/><br/>
        <font color="blue"><i>Для удаления информации о месте работы/учёбы достаточно удалить его название и
            Сохранить</i></font>
        <% } %>
    </form>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>