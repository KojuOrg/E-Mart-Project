<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Aroma Shop - Home</title>
	<link rel="icon" href="${pageContext.request.contextPath}/resources/img/Fevicon.png" type="image/png">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendors/bootstrap/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendors/fontawesome/css/all.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendors/themify-icons/themify-icons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendors/nice-select/nice-select.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendors/owl-carousel/owl.theme.default.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendors/owl-carousel/owl.carousel.min.css">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
  <!--================ Start Header Menu Area =================-->
	<%@ include file="includes/header.jsp" %>
	<!--================ End Header Menu Area =================-->

  
<c:choose>
	<c:when test="${page eq 'mainBody'}">
		<%@ include file="includes/mainBody.jsp" %>
	</c:when>
	<c:when test="${page eq 'contact'}">
		<%@ include file="includes/contact.jsp" %>
	</c:when>
	<c:when test="${page eq 'userLogin'}">
		<%@ include file="includes/userLogin.jsp" %>
	</c:when>
</c:choose>
  <!--================ Start footer Area  =================-->	
	<%@ include file="includes/footer.jsp" %>
	<!--================ End footer Area  =================-->



  <script src="${pageContext.request.contextPath}/resources/vendors/jquery/jquery-3.2.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/vendors/bootstrap/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/vendors/skrollr.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/vendors/owl-carousel/owl.carousel.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/vendors/nice-select/jquery.nice-select.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/vendors/jquery.ajaxchimp.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/vendors/mail-script.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>