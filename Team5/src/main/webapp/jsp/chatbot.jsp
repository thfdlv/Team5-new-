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
            padding: 20px;
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
    </style>
</head>
<body>
    <div class="chat-container">
        <h2>고객센터 FAQ 챗봇</h2>

        <!-- ✅ 초기 추천 질문 버튼 -->
        <div class="faq-buttons">
            <p style="margin-bottom: 5px;">무엇이 궁금하신가요? 클릭해보세요 👇</p>
            <button onclick="sendFAQ('회원가입')">회원가입</button>
            <button onclick="sendFAQ('비밀번호 변경')">비밀번호 변경</button>
            <button onclick="sendFAQ('탈퇴')">탈퇴</button>
            <button onclick="sendFAQ('게시글 작성')">게시글 작성</button>
            <button onclick="sendFAQ('문의')">문의</button>
        </div>

        <div id="chat-box" class="chat-box"></div>

        <input type="text" id="userInput" placeholder="질문을 입력하세요..." />
        <button onclick="sendMessage()">전송</button>
    </div>

    <script>
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

        // 엔터 입력 시 전송
        document.getElementById("userInput").addEventListener("keyup", function(event) {
            if (event.key === "Enter") {
                sendMessage();
            }
        });

        // ✅ 버튼 클릭 시 자동 입력 후 전송
        function sendFAQ(question) {
            document.getElementById("userInput").value = question;
            sendMessage();
        }
    </script>
</body>
</html>
