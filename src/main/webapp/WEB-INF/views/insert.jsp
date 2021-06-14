<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/insert.css">
    <title>입력</title>
</head>
<body>
   <section class="wrap">
        <form action="insert_action" method="post">
            <label> 아이디 : 
                <input type="text" name="user_id" placeholder="아이디" />
            </label><br>
            <label> 패스워드 : 
                <input type="password" name="user_pwd" placeholder="비밀번호" />
            </label><br>
            <label> 이름 : 
                <input type="text" name="user_name" placeholder="이름" />
            </label><br>
            <label> 생년월일 : 
                <input type="date" name="birthday" placeholder="생년월일" />
            </label><br>
            <label> 주소 : 
                <input type="text" name="address" placeholder="주소" />
            </label><br>
            <input type="submit" value="입력 완료" />
        </form>
    </section>
</body>
</html>