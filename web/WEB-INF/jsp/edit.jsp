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
        <input type="hidden" name="action" value="<%=request.getParameter("action")%>">

        <a href="resume"><img src="img/back-button.png" width="150" height="45"></a>
        <button type="submit" type="button"><img src="img/save.png" width="50" height="50"></button>

        <h2>Имя:</h2>
        <dl>
            <input type="text" required name="fullName" size=50 value="${resume.fullName}"
                   placeholder="Обязательное поле">
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSections"/>
            <h3>${type.title}</h3>


            <c:choose>
                <c:when test="${(type.name().equals('OBJECTIVE'))}">
                    <input type="text" name="${type.name()}" size=79 value="${resume.getSection(type)}">
                </c:when>

                <c:when test="${(type.name().equals('PERSONAL'))}">
                    <textarea name="${type}" cols=80 rows=5><%=section%></textarea>
                </c:when>

                <c:when test="${(type.name().equals('ACHIEVEMENT') || type.name().equals('QUALIFICATION'))}">
                    <jsp:useBean id="type" type="com.urise.webapp.model.SectionType"/>

                    <textarea name="${type}" cols=80
                              rows=5><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                </c:when>

                <c:when test="${(type.name().equals('EXPERIENCE')|| type.name().equals('EDUCATION'))}">
                    <c:forEach var="place" items="<%=((PlaceSection) section).getPlaces()%>">
                        <dl>
                            <dt>Название учреждения</dt>
                            <dd><input type="text" name="${type.name()}" size=100 value="${place.link.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт (url) учреждения</dt>
                            <dd><input type="text" name="${type.name()}" size=100 value="${place.link.url}"></dd>
                        </dl>

                        <div style="margin-left: 30px">
                            <c:forEach var="descriptonList" items="${place.listDescriptions}">
                                <jsp:useBean id="descriptonList" type="com.urise.webapp.model.Place.PlaceDescription"/>
                                <dl>
                                    <dt>Должность/специальность</dt>
                                    <dd>
                                        <input type="text" name="${type.name()}" size=79
                                               value="${descriptonList.title}">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Начальная дата</dt>
                                    <dd><input type="text" name="${type.name()}" size=10
                                               value="<%=DateUtil.localDateToString(descriptonList.getStartDate())%>"
                                               placeholder="dd/MM/yyyy"></dd>
                                </dl>

                                <dl>
                                    <dt>Дата завершения:</dt>
                                    <dd><input type="text" name="${type.name()}" size=10
                                               value="<%=DateUtil.localDateToString(descriptonList.getEndDate())%>"
                                               placeholder="dd/MM/yyyy"></dd>
                                </dl>

                                <dl>
                                    <dt>Описание</dt>
                                    <dd><textarea name="${type.name()}" rows="5"
                                                  cols="80">${descriptonList.description}</textarea></dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>

                </c:when>

            </c:choose>

        </c:forEach>
        <hr>

        <button type="submit" type="button">Сохранить</button>
        <button><a href="resume">Отменить</a></button>
        <br/><br/>
        <font color="blue"><i>Дополнительные учреждения работы/учёбы можно добавить после сохранения документа<br/></i></font>
        <font color="blue"><i>Для удаления информации о месте работы/учёбы достаточно удалить его название и
            Сохранить</i></font>

    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>