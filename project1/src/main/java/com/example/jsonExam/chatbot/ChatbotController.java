package com.example.jsonExam.chatbot;

import com.amazonaws.services.lambda.*;
import com.amazonaws.services.lambda.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Controller
public class ChatbotController {

    private static final String FUNCTION_NAME = "team5-lambda";

    @PostMapping("/chatbot")
    @ResponseBody
    public String handleChat(@RequestParam("question") String question) {
        AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                .withRegion("ap-northeast-2") // 사용 중인 리전에 맞게 설정
                .build();

        String payload = "{ \"question\": \"" + question + "\" }";

        InvokeRequest request = new InvokeRequest()
                .withFunctionName(FUNCTION_NAME)
                .withPayload(payload);

        try {
            InvokeResult result = awsLambda.invoke(request);
            String response = new String(result.getPayload().array(), StandardCharsets.UTF_8);

            // 쌍따옴표 제거
            if (response.startsWith("\"") && response.endsWith("\"")) {
                response = response.substring(1, response.length() - 1);
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Lambda 호출 중 오류가 발생했습니다.";
        }
    } // 👉 이 중괄호가 빠졌던 부분!

    @GetMapping("/chatbot")
    public String showChatPage() {
        return "chatbot"; // /jsp/chatbot.jsp로 매핑됨
    }
}
