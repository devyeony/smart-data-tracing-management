<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SDTM</title>

    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css" rel="stylesheet">
    
    <link href="${pageContext.request.contextPath}/resource/css/paging.css" rel="stylesheet">
    
    <script src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
    <script>
    $(function(){
    	$('#selectAndHandle').empty();
        $('#selectAndHandle').append(
    		'<br/>' +
    		'<div style="float:left;">' +
    		  	'<button class="button" onclick="javascript:deleteRule()">일괄삭제</button>' +
    		'</div>'
    	);
    });
    </script>
</head>

<body id="page-top">
<!-- requestMapping -->
<input type="hidden" id="requestUrl" value="/rule/getRuleList">

<input id="ruleList_top_level_id" type="hidden" value="${top_level_id}">
<input id="ruleList_middle_level_id" type="hidden" value="${middle_level_id}">
<input id="ruleList_bottom_level_id" type="hidden" value="${bottom_level_id}">

<!-- 등록 후 성공메세지 -->
<input id="ruleRegSuccessMsg" type="hidden" value="${ruleRegSuccessMsg}">

<!-- 삭제 후 메세지 -->
<input id="ruleDelSuccessMsg" type="hidden" value="${ruleDelSuccessMsg}">
<input id="ruleDelErrorMsg" type="hidden" value="${ruleDelErrorMsg}">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- 사이드바 include-->
    <%@ include file="/WEB-INF/views/include/sidebar.jsp"%>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <!-- 이 부분만 바꿔주면 됩니다 -->
        <div id="content">
            <!-- 툴바 include -->
            <%@ include file="/WEB-INF/views/include/toolbar.jsp"%>
            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-2 text-gray-800">
                    	<b>룰 목록</b>
                    	<span id="searchResultArea" style="font-size:18px;"></span>
                    </h1>
                    <span style="font-size:18px; float:right;">
                    	<input type="radio" name="rule_type" id="all" value="all" checked onchange="startPagingHandling();">
                    	<label for="all">전체</label>
                    	<input type="radio" name="rule_type" id="sql" value="sql" style="margin-left: 5px;" onchange="startPagingHandling();">
                    	<label for="sql">SQL</label>
                    	<input type="radio" name="rule_type" id="method" value="method" style="margin-left: 5px;" onchange="startPagingHandling();">
                    	<label for="method">메서드</label>
                    </span>
                </div>
                <br/>

                <!-- Page Body -->
                <div class="card shadow mb-4">
                    <div class="card-body"><br/>
                    	<div class="d-sm-inline-block form-inline ml-md-3 my-2 my-md-0 mw-100">
								<%@ include file="/WEB-INF/views/rule/include/topLevel.jsp"%>
								<%@ include file="/WEB-INF/views/rule/include/middleLevel.jsp"%>
								<%@ include file="/WEB-INF/views/rule/include/bottomLevel.jsp"%>
							</div>
							<script src="${pageContext.request.contextPath}/resource/js/rule/ruleCategory.js"></script>
							<script src="${pageContext.request.contextPath}/resource/js/rule/ruleList.js"></script>
                    	<%@ include file="/WEB-INF/views/paging/ajax_template.jsp"%>
                    </div>
                </div>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- End of Main Content -->

        <!-- footer include-->
        <%@ include file="/WEB-INF/views/include/footer.jsp"%>

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Bootstrap core JavaScript-->
<script src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="${pageContext.request.contextPath}/resource/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="${pageContext.request.contextPath}/resource/js/sb-admin-2.min.js"></script>

</body>

</html>
