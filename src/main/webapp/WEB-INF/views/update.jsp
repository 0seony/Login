<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/insert.css">
<title>회원정보 수정</title>
</head>
<body>
	<section class="wrap">
		<form action="update_action">
		<b>회원정보 수정</b>
			<label><input type="hidden" name="no"
				value="${no}" />
			</label><br> <label> 아이디 : <input type="text" name="user_id"
				value="${id}" />
			</label><br> <label> 이름 : <input type="text" name="user_name"
				value="${name}" />
			</label><br> <label> 생년월일 : <input type="date" name="birthday"
				value="${birthday}" />
			</label><br> <label> 주소 : <input type="text" name="address"
				value="${address}" />
			</label><br> <input type="submit" value="수정" />
		</form>
	</section>
</body>
</html>