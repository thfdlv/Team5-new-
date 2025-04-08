<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><spring:message code="image.upload.title" /></title>
    <style>
        body {
            font-family: 'Malgun Gothic', sans-serif;
            background: #f0f2f5;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 60px auto;
            padding: 30px;
            background: white;
            border-radius: 15px;
            box-shadow: 0 8px 24px rgba(0,0,0,0.1);
            text-align: center;
        }

        h2 {
            color: #222;
            margin-bottom: 20px;
        }

        input[type="file"] {
            display: none;
        }

        .file-label {
            display: inline-block;
            padding: 12px 20px;
            background: #f1f1f1;
            border: 1px solid #ccc;
            border-radius: 6px;
            cursor: pointer;
            margin-bottom: 20px;
            font-weight: bold;
        }

        .button-group {
            display: flex;
            justify-content: center;
            gap: 12px;
            margin-bottom: 20px;
        }

        .action-btn {
            padding: 12px 24px;
            font-size: 16px;
            font-weight: bold;
            color: white;
            background-color: #19ce60;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.3s;
        }

        .action-btn:hover {
            background-color: #17b550;
        }

        .result-box {
            text-align: left;
            margin-top: 30px;
        }

        .result-box h3 {
            margin-bottom: 10px;
        }

        .result-box ul {
            list-style: none;
            padding: 0;
        }

        .result-box li::before {
            content: "🔍 ";
        }

        .preview {
            margin-top: 30px;
        }

        .preview img {
            max-width: 100%;
            border-radius: 10px;
            border: 1px solid #ccc;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .back-btn {
            display: inline-block;
            margin-top: 30px;
            text-decoration: none;
            color: #19ce60;
            font-weight: bold;
        }

        .back-btn:hover {
            text-decoration: underline;
            color: #17b550;
        }
    </style>
</head>
<body>

<div class="container">
    <h2><spring:message code="image.upload.title" /></h2>

    <form id="imageForm" method="post" enctype="multipart/form-data">
        <input type="file" id="imageFile" name="imageFile" accept="image/*" required>
        <label for="imageFile" class="file-label">
            <spring:message code="image.upload.file.label" />
        </label>
        <br>

        <div class="button-group">
            <button type="submit" class="action-btn" onclick="submitForm('/project1/image/labels')">
                <spring:message code="image.label.button" />
            </button>
            <button type="submit" class="action-btn" onclick="submitForm('/project1/image/faces')">
                <spring:message code="image.face.button" />
            </button>
        </div>
    </form>

    <c:if test="${not empty labels}">
        <div class="result-box">
            <h3><spring:message code="image.analysis.result" /></h3>
            <ul>
                <c:forEach var="label" items="${labels}">
                    <li>${label}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <c:if test="${not empty faces}">
        <div class="result-box">
            <h3><spring:message code="image.face.result" /></h3>
            <ul>
                <c:forEach var="face" items="${faces}">
                    <li>${face}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

   

    <a href="/project1/template" class="back-btn">◀ <spring:message code="common.back" /></a>
</div>

<script>
    function submitForm(action) {
        const form = document.getElementById("imageForm");
        const fileInput = document.getElementById("imageFile");

        if (!fileInput.value) {
            alert("파일을 선택해주세요.");
            event.preventDefault();
            return false;
        }

        form.action = action;
    }
</script>

</body>
</html>
