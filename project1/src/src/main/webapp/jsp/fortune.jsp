<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>오늘의 운세</title>
</head>
<body>
    <h2>🔮 오늘의 운세 보기</h2>

    <!-- ✅ ① form 방식 -->
    <form method="get" action="/getFortune">
        날짜: <input type="date" name="date" value="<%= java.time.LocalDate.now() %>" />
        <button type="submit">[폼] 운세 확인</button>
    </form>

    <!-- ✅ ② JS 방식 -->
    <div style="margin-top: 1em;">
        날짜: <input type="date" id="dateInput" value="<%= java.time.LocalDate.now() %>" />
        <button onclick="getFortune()">[JS] 운세 확인</button>
    </div>

    <hr>

    <!-- ✅ JSP에서 받은 결과 출력 -->
    <c:if test="${not empty fortune}">
        <h3>📅 ${date} 운세</h3>
        <p>${fortune}</p>
    </c:if>

    <!-- ✅ JS 결과 출력 영역 -->
    <div id="result" style="margin-top: 20px;"></div>

    <script>
        function getFortune() {
            const date = document.getElementById("dateInput").value;
            fetch("/api/fortune?date=" + date)
                .then(res => {
                    if (!res.ok) throw new Error("HTTP error " + res.status);
                    return res.json();
                })
                .then(data => {
                    document.getElementById("result").innerHTML =
                        `<h3>📅 ${data.date} 운세</h3><p>${data.fortune}</p>`;
                })
                .catch(err => {
                    document.getElementById("result").innerText = "❌ 오류 발생: " + err;
                });
        }
    </script>
</body>
</html>
