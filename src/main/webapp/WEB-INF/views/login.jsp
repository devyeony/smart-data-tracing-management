<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>SDTM</title>

<!-- Custom fonts for this template-->
<link
	href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.5.1.min.js"></script>
<script>
$(function(){
	/* 로그인, 회원가입 메뉴 컨트롤 */
	$('#goToRegister').click(function(){
		$('#login').attr('style', 'display:none');
		$('#register').attr('style', 'display:block');
	});
	$('#goToLogin').click(function(){
		$('#login').attr('style', 'display:block');
		$('#register').attr('style', 'display:none');
	});
})
</script>
</head>

<body class="bg-gradient-primary">
<!-- contextPath -->
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

	<div class="container">

		<!-- Outer Row -->
		<div class="row justify-content-center">
			<div class="col-xl-10 col-lg-12 col-md-9">
				<div class="card o-hidden border-0 shadow-lg my-5">
					<div class="card-body p-0">
						<!-- Nested Row within Card Body -->
						<div class="row">
							<div class="col-lg-6 d-none d-lg-block" style="position: relative;">
								<image 
									src="${pageContext.request.contextPath}/resource/img/NAMU_Logo_PNG.png"
									width="300" 
									style="display: block;
  											margin-left: auto;
  											margin-right: auto; 
  											position: absolute;
											top: 50%;
											left: 50%;
											transform: translate(-50%, -50%);">
							  </image>
							</div><!-- col-lg-6 d-none d-lg-block -->
							<div class="col-lg-6">
								<div class="p-5">
								
									<!-- 로그인 -->
									<article id="login">
										<%@ include file="/WEB-INF/views/login/include-login.jsp"%>
										<hr>
										<div class="text-center">
											<a id="goToRegister" class="small" href="javascript:void(0);">사용자 등록</a>
										</div><!-- text-center -->
									</article>
									
									<!-- 회원가입 -->
									<article id="register" style="display:none;">
										<%@ include file="/WEB-INF/views/login/include-register.jsp"%>
										<hr>
										<div class="text-center">
											<a id="goToLogin" class="small" href="javascript:void(0);">이미 계정이 있으신가요? 로그인으로 돌아가기</a>
										</div><!-- text-center -->
									</article>
									
								</div><!-- p-5 -->
							</div><!-- col-lg-6 -->
						</div><!-- row -->
					</div><!-- card-body p-0 -->
				</div><!-- card o-hidden border-0 shadow-lg my-5 -->
			</div><!-- col-xl-10 col-lg-12 col-md-9 -->
		</div><!-- row justify-content-center -->
		
	</div><!-- container -->

	<!-- Bootstrap core JavaScript-->

	<script
		src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script
		src="${pageContext.request.contextPath}/resource/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script
		src="${pageContext.request.contextPath}/resource/js/sb-admin-2.min.js"></script>
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

</body>

</html>