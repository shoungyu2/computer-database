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
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="ListComputerController">
				Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<c:if test="${fn:length(success)>0}">
				<div class="alert alert-success">
					<c:out value="${success}" />
				</div>
			</c:if>
			<h1 id="homeTitle">
				<c:out value="${pcCount}" />
				Computers found
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="ListComputerController" method="GET"
						class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="AddComputerController">Add Computer</a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="DeleteComputerController" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bfiltered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name <a
							href="ListComputerController?filter=name&search=${search}&order=asc"
							id="filterBy" style="font-size: xx-large"> &uarr; </a> <a
							href="ListComputerController?filter=name&search=${search}&order=desc"
							id="filterBy" style="font-size: xx-large"> &darr; </a>
						</th>
						<th>Introduced date <a
							href="ListComputerController?filter=introduced&search=${search}&order=asc"
							id="filterBy" style="font-size: xx-large"> &uarr; </a> <a
							href="ListComputerController?filter=introduced&search=${search}&order=desc"
							id="filterBy" style="font-size: xx-large"> &darr; </a>
						</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date <a
							href="ListComputerController?filter=discontinued&search=${search}&order=asc"
							id="filterBy" style="font-size: xx-large"> &uarr; </a> <a
							href="ListComputerController?filter=discontinued&search=${search}&order=desc"
							id="filterBy" style="font-size: xx-large"> &darr; </a>
						</th>
						<!-- Table header for Company -->
						<th>Company <a
							href="ListComputerController?filter=company_id&search=${search}&order=asc"
							id="filterBy" style="font-size: xx-large"> &uarr; </a> <a
							href="ListComputerController?filter=company_id&search=${search}&order=desc"
							id="filterBy" style="font-size: xx-large"> &darr; </a>
						</th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${pcList}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a
								href="EditComputerController?computerId=${computer.id}&numPage=${numPage}&nbrPage=${nbrPage}">
									<c:out value="${computer.name}" />
							</a></td>
							<td><c:out value="${computer.introductDate}"></c:out></td>
							<td><c:out value="${computer.discontinueDate}"></c:out></td>
							<td><c:out value="${computer.company.name}"></c:out></td>

						</tr>
					</c:forEach>


				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><c:if test="${numPage!=1}">
						<a
							href="ListComputerController?currentPage=${numPage-1}&search=${search}&filter=${filter}&order=${order}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a>
					</c:if></li>
				<c:choose>
					<c:when test="${nbrPage<=5}">
						<c:forEach var="i" begin="1" end="${nbrPage}">
							<li><a
								href="ListComputerController?currentPage=${i}&search=${search}&filter=${filter}&order=${order}">
									<c:out value="${i}" />
							</a></li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${numPage>=3}">
								<c:if test="${numPage>=4}">
									<li>
										<a href="ListComputerController?currentPage=1&search=${search}&filter=${filter}&order=${order}">
											<c:out value="1"/>
										</a>
									</li>
								</c:if>
								<c:if test="${nbrPage-numPage>=3}">
									<c:forEach var="i" begin="${numPage-2}" end="${numPage+2}">
										<li>
											<a href="ListComputerController?currentPage=${i}&search=${search}&filter=${filter}&order=${order}">
												<c:out value="${i}"/>
											</a>
										</li>
									</c:forEach>
									<li>
										<a href="ListComputerController?currentPage=${nbrPage}&search=${search}&filter=${filter}&order=${order}">
											<c:out value="${nbrPage}"/>
										</a>
									</li>
								</c:if>
								<c:if test="${nbrPage-numPage<=2}">
									<c:forEach var="i" begin="${nbrPage-4}" end="${nbrPage}">
										<li>
											<a href="ListComputerController?currentPage=${i}&search=${search}&filter=${filter}&order=${order}">
												<c:out value="${i}"/>
											</a>
										</li>
									</c:forEach>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:forEach var="i" begin="1" end="5">
									<li>
										<a href="ListComputerController?currentPage=${i}&search=${search}&filter=${filter}&order=${order}">
											<c:out value="${i}"/>
										</a>
									</li>
								</c:forEach>
								<li>
									<a href="ListComputerController?currentPage=${nbrPage}&search=${search}&filter=${filter}&order=${order}">
										<c:out value="${nbrPage}"/>
									</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<li><c:if test="${numPage!=nbrPage}">
						<a
							href="ListComputerController?currentPage=${numPage+1}&search=${search}&filter=${filter}&order=${order}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a>
					</c:if></li>
			</ul>


			<div class="btn-group btn-group-sm pull-right" role="group">

				<button class="btn btn-default"
					onclick="window.location.href='?nbrElement=10&search=${search}&filter=${filter}&order=${order}';">10</button>
				<button class="btn btn-default"
					onclick="window.location.href='?nbrElement=50&search=${search}&filter=${filter}&order=${order}';">50</button>
				<button class="btn btn-default"
					onclick="window.location.href='?nbrElement=100&search=${search}&filter=${filter}&order=${order}';">100</button>
			</div>
		</div>
	</footer>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/dashboard.js"></script>

</body>
</html>