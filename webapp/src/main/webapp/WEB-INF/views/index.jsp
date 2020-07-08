<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "security" uri = "http://www.springframework.org/security/tags" %>

<%@ page isELIgnored="false"%>

<html>
<body>
<security:authorize access="isAuthenticated()">
	                <a href=<spring:url value="logout"/>><spring:message code="logout"></spring:message></a>
	                <c:set var="authenticated" value="true"/>
	            </security:authorize>
	<form method="POST" action="LogInController">
		<button type="submit" class="btn btn-default">Go To Dashboard</button>
	</form>
</body>
</html>
