<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<title>New Idea</title>
</head>
<body>
<div style="    margin: 0px auto;
    width: 1200px;
    text-align: center;
    padding-top: 100px;">
	<h1 class="h3 mb-3 font-weight-normal">New Idea</h1>

	<p>
		<form:errors path="idea.*" />
	</p>
	<form:form method="POST" action="/ideas/new" modelAttribute="idea">
		<p>
			<form:label path="title">Title:</form:label>
			<form:input type="title" path="title"  class="form-control" />
		</p>
		<input type="submit" value="Create" class="btn btn-lg btn-primary "/>
	</form:form>
	</div>
</body>
</html>