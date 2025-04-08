package com.example.jsonExam.Fortune;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FortuneController {

    @GetMapping("/fortune")
    public String showFortunePage() {
        return "fortune"; // /jsp/fortune.jsp
    }

    // ✅ 폼 방식 요청 처리
    @GetMapping("/getFortune")
    public String getFortune(@RequestParam String date, Model model) throws JsonProcessingException {
        Map<String, String> fortune = fetchFortuneFromLambda(date);
        model.addAttribute("fortune", fortune.get("fortune"));
        model.addAttribute("date", fortune.get("date"));
        return "fortune";
    }

    // ✅ JS fetch 방식 응답
    @ResponseBody
    @GetMapping("/api/fortune")
    public Map<String, String> getFortuneJson(@RequestParam String date) throws JsonProcessingException {
        return fetchFortuneFromLambda(date);
    }

    // ✅ Lambda 호출 로직 (공통)
    private Map<String, String> fetchFortuneFromLambda(String date) throws JsonProcessingException {
        String url = "https://2aobsvngh5.execute-api.ap-northeast-2.amazonaws.com/prod/fortune?date=" + date;
        RestTemplate restTemplate = new RestTemplate();
        String rawResponse = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode body = mapper.readTree(rawResponse).get("body");
        JsonNode inner = mapper.readTree(body.asText());

        Map<String, String> result = new HashMap<>();
        result.put("date", inner.get("date").asText());
        result.put("fortune", inner.get("fortune").asText());
        return result;

    }
    
    @ResponseBody
    @GetMapping("/test")
    public String test() {
        return "✅ FortuneController OK!";
    }
}
