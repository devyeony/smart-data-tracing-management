var contextPath = '';

$(function(){
	/* Context Path */
	contextPath = $('#contextPath').val();
	
	/* 회원의 이메일로 임시 비밀번호를 발송 */
	$('#sendPwdBtn').click(function(){
		
		$.ajax({
			url : contextPath + "/sendPwdToEmail",
			type : "POST",
			data : {
				member_id : $('#send_pwd_id').val(),
				email : $('#send_pwd_email').val()
			},
			
			success : function(result) {
				if(result == 'idNotExist'){
					alert("존재하지 않는 아이디입니다.");
				}else if(result == 'emailNotSame'){
					alert("이메일을 잘못 입력하셨습니다.");
				}else if(result == 'success'){
					alert("임시 비밀번호를 이메일로 발송했습니다. 새로운 비밀번호로 로그인하세요.");
					sessionStorage.removeItem("member"); 
				}else{
					alert("임시 비밀번호 이메일 발송에 실패하였습니다.");
				}
			},
			error : function(request, status, error) {
				alert("에러가 발생하였습니다.");
				//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error + "서버에러");
			}
			
		});//ajax
		
	}); //click
});
