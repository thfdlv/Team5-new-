<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>회원 탈퇴</title>
  <script src="${pageContext.request.contextPath}/dbQuiz.js"></script>
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
</head>
<body>

<table align="center">
  <tr>
    <td><font color="red">${msg}</font></td>
  </tr>
  <tr>
    <td>
      <form action="${pageContext.request.contextPath}/member/deleteProc" method="post" id="f">
        <input type="text" name="id" id="id" value="${sessionScope.id}" readonly><br>

        <input type="password" name="pw" id="pw" placeholder="비밀번호"><br>
        <input type="password" name="confirm" id="confirm" placeholder="비밀번호 확인"><br>

        <input type="button" value="회원 탈퇴" onclick="deleteCheck()">
        <input type="button" value="취소" onclick="location.href='${pageContext.request.contextPath}/template'">
      </form>
    </td>
  </tr>
</table>

<script>
function deleteCheck() {
  const pw = document.getElementById("pw").value;
  const confirm = document.getElementById("confirm").value;

  if (!pw || !confirm) {
    alert("비밀번호와 비밀번호 확인은 필수 항목입니다.");
    return;
  }

  if (pw !== confirm) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }

  console.log("✅ 탈퇴 조건 통과 → submit 실행");
  document.getElementById("f").submit();
}
</script>

</body>
</html>
