<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>마이페이지</title>

  <!-- ✅ 외부 CSS 제거, 내부 style 사용 -->
  <style>
    body {
      background-color: #f5f6f7;
      font-family: 'Malgun Gothic', sans-serif;
    }
    table {
      margin: 30px auto;
    }
    input {
      margin: 5px 0;
      padding: 8px;
      width: 300px;
    }
  </style>

  <!-- ✅ 외부 JS 연결 -->
  <script src="${pageContext.request.contextPath}/dbQuiz.js"></script>
</head>
<body>
<div align="center">
  <font color="red">${msg}</font>
  <h1>회원 수정</h1>
  <form action="${pageContext.request.contextPath}/member/updateProc" method="post" id="f">

    <input type="text" name="id" id="id" value="${sessionScope.id}" readonly> (*필수 항목)<br>
    <input type="password" name="pw" placeholder="비밀번호" id="pw"><br>
    <input type="password" name="confirm" placeholder="비밀번호 확인" id="confirm" onchange="pwCheck()">
    <label id="label"></label><br>
    <input type="text" name="userName" id="userName" placeholder="이름" value="${user.userName}"><br>
    <input type="text" name="postcode" placeholder="우편번호" value="${user.postcode}"><br>
    <input type="text" name="address" placeholder="주소" value="${user.address}"><br>
    <input type="text" name="detailAddress" placeholder="상세 주소" value="${user.detailAddress}"><br>
    <input type="text" name="mobile" placeholder="전화번호" value="${user.mobile}"><br>

    <input type="button" value="회원수정" onclick="allCheck()">
    <input type="button" value="취소" onclick="location.href='${pageContext.request.contextPath}/template'"><br>
  </form>
</div>

<!-- ✅ 내부 JS 함수 보조 (pwCheck만 내부에 있어도 OK) -->
<script>
  function pwCheck() {
    const pw = document.getElementById("pw").value;
    const confirm = document.getElementById("confirm").value;
    const label = document.getElementById("label");

    if (pw !== confirm) {
      label.innerText = "❌ 비밀번호 불일치";
      label.style.color = "red";
    } else {
      label.innerText = "✅ 비밀번호 일치";
      label.style.color = "green";
    }
  }
</script>
</body>
</html>
