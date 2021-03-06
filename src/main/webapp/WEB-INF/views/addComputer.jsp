<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">

</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<c:if test="${fn:length(success)>0}">
				<form id="bravo" action="ListComputerServlet" method="POST">
					<input type="hidden" name="success" value="${success}" />
				</form>
				<script>
					document.getElementById("bravo").submit();
				</script>
			</c:if>
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<c:if test="${fn:length(errors)>0}">
				<div class="alert alert-danger">
					<c:out value="${errors}" />
				</div>
			</c:if>
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="AddComputerServlet" id="formAddComputer"
						method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									placeholder="Computer name" name="computerName" required>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									placeholder="Introduced date" name="introduced"
									min="1970-01-01">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									placeholder="Discontinued date" name="discontinued"
									min="1970-01-01">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="-1">None</option>
									<c:forEach var="company" items="${listCompany}">
										<option value="${company.id}">
											<c:out value="${company.name}" />
										</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="ListComputerServlet" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/frontValidation.js"></script>
</body>
</html>