<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이미지 업로드</title>
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
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            text-align: center;
            animation: fadeIn 1s ease-out;
        }

        h2 {
            color: #222;
            margin-bottom: 20px;
        }

        input[type="file"] {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
            margin-bottom: 20px;
            width: 80%;
        }

        button {
            padding: 12px 30px;
            font-size: 16px;
            color: white;
            background-color: #19ce60;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.3s ease;
            animation: bounceIn 1s ease;
        }

        button:hover {
            background-color: #17b550;
            transform: scale(1.05);
        }

        .result-box {
            margin-top: 30px;
            text-align: left;
            animation: fadeSlideIn 1s ease;
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
            animation: fadeSlideIn 1s ease;
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
            transition: color 0.3s ease;
        }

        .back-btn:hover {
            text-decoration: underline;
            color: #17b550;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes bounceIn {
            0%   { transform: scale(0.9); }
            50%  { transform: scale(1.1); }
            100% { transform: scale(1); }
        }

        @keyframes fadeSlideIn {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<div class="container">
    <h2><spring:message code="image.upload.title" /></h2>
    <form action="/project1/image/upload" method="post" enctype="multipart/form-data">
    <label for="fileInput" style="
        display: inline-block;
        padding: 10px 20px;
        background-color: #f1f1f1;
        border-radius: 6px;
        border: 1px solid #ccc;
        cursor: pointer;
        margin-bottom: 20px;
    ">
        <spring:message code="image.upload.file.label" />
    </label>
    <input type="file" id="fileInput" name="imageFile" accept="image/*" style="display: none;" required><br>

    <button type="submit">
        <spring:message code="image.upload.button" />
    </button>
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

    <a href="/project1/template" class="back-btn">◀ <spring:message code="common.back" /></a>
</div>

</body>
</html>
