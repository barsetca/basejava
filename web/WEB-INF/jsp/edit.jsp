<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
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
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Описание:</h3>
        <kbd>
            <i> <font color="blue">Формат ввода нескольких атрибутов одного параметра</font></i> -
            <font color="red"> через запятую с пробелом</font>.<br/>
            <i><font color="blue">Например, в разделе "Достижения" можно написать:</font></i>
        </kbd>
        <kbd>Покорил Эверест
            <mark>,</mark>
            Пересёк Евразию на воздушном шаре</kbd>
        <br/><br/>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=130 value="${resume.getSection(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <% String action = request.getParameter("action");
            if (action.equals("edit")) {%>
        <button onclick="window.history.back()">Отменить</button>
        <% } else {%>
        <button><a href="resume?uuid=${resume.uuid}&action=delete">Отменить</a></button>
        <% } %>
    </form>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>