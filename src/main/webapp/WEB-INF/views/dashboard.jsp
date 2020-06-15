<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
        	<c:if test="${fn:length(success)>0}">
	        	<div class="alert alert-danger">
	        		<c:out value="${success}"/>
	        	</div>
        	</c:if>	
            <h1 id="homeTitle">
                <c:out value="${pcCount}"/> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="SearchComputerServlet" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="AddComputerServlet">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="DeleteComputerServlet" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                        	<a href="OrderByServlet?order=name" id="orderBy">
                            Computer name
                            </a>
                        </th>
                        <th>
                        	<a href="OrderByServlet?order=introduced" id="orderBy">
                            Introduced date
                        	</a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                        	<a href="OrderByServlet?order=discontinued" id="orderBy">
                            Discontinued date
                            </a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                        	<a href="OrderByServlet?order=company" id="orderBy">
                            Company
                            </a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                <c:forEach var="computer" items="${pcList}">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                        </td>
                        <td>
                            <a href="EditComputerServlet?computerId=${computer.id}&numPage=${numPage}&nbrPage=${nbrPage}">
                            	<c:out value="${computer.name}"/>
                            </a>
                        </td>
                        <td>
                        	<c:out value="${computer.introductDate}"></c:out>
                        </td>
                        <td>
                        	<c:out value="${computer.discontinueDate}"></c:out>
                        </td>
                        <td>
                        	<c:out value="${computer.company.name}"></c:out>
                        </td>

                    </tr>
                  </c:forEach>
                    
                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
            <li>
            	<c:if test="${numPage>nbrPage}">
            		<form action="NotFoundServlet" id="page" method="POST"></form>
            		<script>document.getElementById("page").submit()</script>
            	</c:if>
            	<c:if test="${numPage!=1}">
	            	<a href="ListComputerServlet?currentPage=${numPage-1}" aria-label="Previous">
	                      <span aria-hidden="true">&laquo;</span>
	            	</a>
            	</c:if>
            </li>
            <c:choose>
            <c:when test="${nbrPage<=5 }">
            	<c:forEach var="i" begin="1" end="${nbrPage}">
            		<li><a href="ListComputerServlet?currentPage=${i}">
            			<c:out value="${i}"/>
            		</a></li>
            	</c:forEach>
            </c:when>
            <c:otherwise>
            	<c:choose>
            		<c:when test="${nbrPage - numPage >=5}">
            			<c:if test="${numPage !=1 }">
            				<li><a href="ListComputerServlet?currentPage=1">
            					<c:out value="1"></c:out>
            				</a></li>
            			</c:if>
            			<c:forEach var="i" begin="${numPage}" end="${numPage+4}">
            				<li><a href="ListComputerServlet?currentPage=${i}">
            					<c:out value="${i}"></c:out>
            				</a></li>
            			</c:forEach>
            			<li><a href="ListComputerServlet?currentPage=${nbrPage}">
            					<c:out value="${nbrPage}"></c:out>
            				</a></li>
            		</c:when>
            		<c:otherwise>
            			<li><a href="ListComputerServlet?currentPage=1">
            					<c:out value="1"></c:out>
            			</a></li>
            			<c:forEach var="i" begin="${nbrPage-4}" end="${nbrPage}">
            				<li><a href="ListComputerServlet?currentPage=${i}">
            					<c:out value="${i}"></c:out>
            				</a></li>
            			</c:forEach>
            		</c:otherwise>
            	</c:choose>
            </c:otherwise>
            </c:choose>
            <li>
            	<c:if test="${numPage!=nbrPage}">
	                <a href="ListComputerServlet?currentPage=${numPage+1}" aria-label="Next">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
                </c:if>
            </li>
        	</ul>
        

        <div class="btn-group btn-group-sm pull-right" role="group" >
            <form method="post" action="ListComputerServlet?nbrElement=20">
            <button type="submit" class="btn btn-default" formaction="ListComputerServlet?nbrElement=10">10</button>
            <button type="submit" class="btn btn-default" formaction="ListComputerServlet?nbrElement=50">50</button>
            <button type="submit" class="btn btn-default" formaction="ListComputerServlet?nbrElement=100">100</button>
        </form>
        </div>
		</div>
    </footer>
<script src="./js/jquery.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/dashboard.js"></script>

</body>
</html>