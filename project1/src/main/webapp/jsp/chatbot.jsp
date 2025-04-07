<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FAQ 챗봇</title>
    <style>
        body {
            font-family: 'Malgun Gothic', sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }

        .chat-container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 60px 20px 20px 20px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .chat-box {
            height: 400px;
            overflow-y: auto;
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 10px;
            background-color: #fafafa;
        }

        .chat-message {
            margin: 5px 0;
        }

        .user {
            text-align: right;
            color: #007bff;
        }

        .bot {
            text-align: left;
            color: #28a745;
        }

        input[type="text"] {
            width: 80%;
            padding: 10px;
            font-size: 16px;
        }

        button {
            padding: 10px;
            font-size: 16px;
            cursor: pointer;
        }

        .faq-buttons {
            margin-bottom: 10px;
        }

        .faq-buttons button {
            margin: 3px;
            background-color: #e0f8e0;
            border: 1px solid #28a745;
            border-radius: 5px;
            color: #28a745;
        }

        .faq-buttons button:hover {
            background-color: #d2f0d2;
        }

        .back-btn {
            position: fixed;
            top: 15px;
            left: 20px;
            padding: 10px 20px;
            background-color: #19ce60;
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
            cursor: pointer;
            z-index: 9999;
        }

        .back-btn:hover {
            background-color: #17a551;
        }
    </style>
</head>
<body>

<spring:message code="faq.signup" var="faqSignup" />
<spring:message code="faq.password" var="faqPassword" />
<spring:message code="faq.withdraw" var="faqWithdraw" />
<spring:message code="faq.post" var="faqPost" />
<spring:message code="faq.inquiry" var="faqInquiry" />
<spring:message code="alert.login.required" var="msgLoginRequired" />

<div class="chat-container">
    <a href="/project1/template" class="back-btn">
        <spring:message code="common.back" />
    </a>

    <h2><spring:message code="chatbot.title" /></h2>

    <div class="faq-buttons">
        <p style="margin-bottom: 5px;">
            <spring:message code="chatbot.subtitle" /> 👇
        </p>
        <button onclick="sendFAQ('${faqSignup}')">${faqSignup}</button>
        <button onclick="sendFAQ('${faqPassword}')">${faqPassword}</button>
        <button onclick="sendFAQ('${faqWithdraw}')">${faqWithdraw}</button>
        <button onclick="sendFAQ('${faqPost}')">${faqPost}</button>
        <button onclick="sendFAQ('${faqInquiry}')">${faqInquiry}</button>
    </div>

    <div id="chat-box" class="chat-box"></div>

    <input type="text" id="userInput" placeholder="<spring:message code='chatbot.placeholder'/>" />
    <button onclick="sendMessage()">
        <spring:message code="chatbot.send" />
    </button>
</div>

<script>
    const IS_LOGGED_IN = '${sessionScope.id}' !== '';
    const loginMessage = '<spring:message code="alert.login.required"/>'; // 🔥 이렇게 고정

    function checkLoginAndView(postId) {
        if (!IS_LOGGED_IN) {
            if (confirm(loginMessage)) {
                window.location.href = "/project1/login";
            }
        } else {
            window.location.href = "/project1/post/view?id=" + postId;
        }
    }

    function sendMessage() {
        const input = document.getElementById("userInput");
        const message = input.value.trim();
        if (!message) return;

        appendMessage("user", message);
        input.value = "";

        fetch("/project1/chatbot", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "question=" + encodeURIComponent(message)
        })
        .then(response => response.text())
        .then(answer => {
            if (answer.startsWith('"') && answer.endsWith('"')) {
                answer = answer.slice(1, -1);
            }
            appendMessage("bot", answer);
        })
        .catch(err => {
            console.error(err);
            appendMessage("bot", "❌ Lambda 호출 중 오류가 발생했습니다.");
        });
    }

    function appendMessage(sender, message) {
        const chatBox = document.getElementById("chat-box");
        const div = document.createElement("div");
        div.className = "chat-message " + sender;
        div.textContent = message;
        chatBox.appendChild(div);
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    document.getElementById("userInput").addEventListener("keyup", function(event) {
        if (event.key === "Enter") {
            sendMessage();
        }
    });

    function sendFAQ(question) {
        document.getElementById("userInput").value = question;
        sendMessage();
    }
</script>
</body>
</html>
