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
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>
	<c:if test="${fn:length(success)>0}">
		<form id="bravo" action="ListComputerController" method="POST">
			<input type="hidden" name="success" value="${success}" /> <input
				type="hidden" name="currentPage" value="${numPage}" /> 
		</form>
		<script>
	        	document.getElementById("bravo").submit();
	        </script>
	</c:if>
	<section id="main">
		<div class="container">
			<c:if test="${fn:length(errors)>0}">
				<div class="alert alert-danger">
					<c:out value="${errors}" />
				</div>
			</c:if>
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						<c:out value="id:${computerId}" />
					</div>
					<h1>Edit Computer</h1>

					<form action="EditComputerController" id="formEditComputer" method="POST">
						<input type="hidden" value="${numPage}" name="numPage" /> <input
							type="hidden" value="${nbrPage}" name="nbrPage" /> <input
							type="hidden" value="${computerId}" id="id" name="computerId" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									value="${computerName}" placeholder="Computer name"
									name="computerName" required>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									value="${introducedDate}" placeholder="Introduced date"
									name="introduced">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									value="${discontinuedDate}" placeholder="Discontinued date"
									name="discontinued">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<c:forEach var="company" items="${listCompany}">
										<c:choose>
											<c:when test="${company.id==companyId}">
												<option selected value="${company.id}">
													<c:out value="${company.name}" />
												</option>
											</c:when>
											<c:otherwise>
												<option value="${company.id}">
													<c:out value="${company.name}" />
												</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary" /> or
							<a href="ListComputerController" class="btn btn-default">Cancel</a>
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