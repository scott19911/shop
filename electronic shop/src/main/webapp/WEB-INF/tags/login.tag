<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<p>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link rel="stylesheet" href="/css/avatar.css">
    <link rel="stylesheet" href="/css/loginInLine.css">

<div class="log">
    <c:if test="${specificUser.getUserId() > 0}">
        <img src="/drawAvatar" class="avatar__image1">
        <a href="/orderInf" style="padding-left: 10px; font-size: 25px; padding-top: 15px" ><fmt:message key="hi" /> ${specificUser.getFirstName()}</a>
        <a href="/LogOff" style="padding-left: 10px; font-size: 25px; padding-top: 15px" ><fmt:message key="logOff" /></a>
    </c:if>
    <c:if test="${specificUser == null || specificUser.getUserId() == 0}">
        <form action="/login" class="form-inline" method="post">
            <input type="text" name="EMAIL" value="" placeholder="<fmt:message key="email" />">
            <p id="errorMassage" style="color:red"> ${requestScope.get("com.example.electronicshop.login.error").get("EMAIL")}
            <input type="password" name="password" value="" placeholder="<fmt:message key="pas" />">
            <p id="errorMassage1" style="color:red"> ${requestScope.get("com.example.electronicshop.login.error").get("password")}
            <button type="submit"><fmt:message key="sign.in" /></button>
        </form>
    </c:if>
</div>
<p id="errorMassage0" style="color:red;font-size: xx-large"> ${sessionScope.get("errMessage")}
</p>