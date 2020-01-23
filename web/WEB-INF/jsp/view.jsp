<%@ page import="com.urise.webapp.util.HtmlUtil" %>
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
    <a href="resume"><img src="img/back-button.png" width="150" height="45"></a>
    <h2>${resume.fullName}&nbsp; <a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit2.con" width="30"
                                                                                       height="30"></a>
    </h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contactsMap}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>

            <%=HtmlUtil.toHtmlContacts(contactEntry.getKey(), contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sectionsMap}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSections>"/>

        <c:if test="${((sectionEntry.value.toString().equals('null')) || (sectionEntry.value.toString().equals('')))}">
                <%=HtmlUtil.toHtmlSection(sectionEntry.getKey(), sectionEntry.getValue())%>
        </c:if>
        <c:if test="${(!(sectionEntry.value.toString().equals('null')) || !(sectionEntry.value.toString().equals('')))}">
    <h3><%=sectionEntry.getKey().getTitle()%>
    </h3>
    <ul><%=HtmlUtil.toHtmlSection(sectionEntry.getKey(), sectionEntry.getValue())%>
    </ul>
    </c:if>
    </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
