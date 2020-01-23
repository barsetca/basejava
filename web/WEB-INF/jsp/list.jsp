<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr bgcolor="#ffe4e1">
            <th>№ п/п</th>
            <th>Имя</th>
            <th>e-mail</th>
            <th>Удалить</th>
            <th>Изменить</th>
            <th>Добавить<br/>"Образование"</th>
            <th>Добавить<br/>"Опыт работы"</th>

        </tr>
        <c:set var="n" value="0"/>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>

            <tr>
                <td align="center"><b>${n=n+1}<b/></td>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=HtmlUtil.toHtmlContacts(ContactType.E_MAIL, resume.getContact(ContactType.E_MAIL))%>
                </td>
                <td align="center"><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"
                                                                                           width="25" height="25"></a>
                </td>
                <td align="center"><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit2.con" width="25"
                                                                                         height="25"></a></td>
                <td align="center"><a href="resume?uuid=${resume.uuid}&action=addEducation"><img src="img/education.png"
                                                                                                 width="30"
                                                                                                 height="30"></a></td>
                <td align="center"><a href="resume?uuid=${resume.uuid}&action=addExperience"><img src="img/workPNG.png"
                                                                                                  width="30"
                                                                                                  height="30"></a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <button><a href="resume?action=add"><img src="img/add-user-icon.png" width="55" height="50"></a></button>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
