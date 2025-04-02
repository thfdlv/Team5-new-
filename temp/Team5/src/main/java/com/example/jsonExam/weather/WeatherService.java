package com.example.jsonExam.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    private static final String API_KEY = "83J%2BZlSbumSCc2rBcCipXddnGvjm7eTJIpu6ZFEJFY3BV2SqHP3VsxxsXK3m5jQhb8D%2FoXKVulq5Gv4xzLu72Q%3D%3D";
    private static final String PAGE_NO = "1";
    private static final String PAGE_SIZE = "30";

    public WeatherModel.WeatherResponse getUltraSrtNcst() throws URISyntaxException {
        String date = LocalDate.now().toString(); // 예: 2025-04-01
        String baseDate = date.replace("-", "");  // 예: 20250401
        String baseTime = getBaseTime();          // 자동 계산된 base_time

        URI uri = new URI(API_URL
                + "?serviceKey=" + API_KEY
                + "&numOfRows=" + PAGE_SIZE
                + "&pageNo=" + PAGE_NO
                + "&base_date=" + baseDate
                + "&base_time=" + baseTime
                + "&nx=37&ny=126"
                + "&dataType=JSON");

        WeatherModel.WeatherSearchData weather =
                restTemplate.getForObject(uri, WeatherModel.WeatherSearchData.class);

        if (weather == null) {
            throw new IllegalStateException("날씨 데이터 조회 실패");
        }

        List<WeatherModel.WeatherItem> weatherItems = weather.getWeatherItems();

        String temp = getWeatherInfo(weatherItems, "TMP");
        String moisture = getWeatherInfo(weatherItems, "REH");
        String windSpeed = getWeatherInfo(weatherItems, "WSD");

        return new WeatherModel.WeatherResponse(temp, moisture, windSpeed, date);
    }

    private String getWeatherInfo(List<WeatherModel.WeatherItem> weatherItems, String category) {
        return weatherItems.stream()
                .filter(it -> category.equals(it.getCategory()))
                .map(WeatherModel.WeatherItem::getFcstValue)
                .findFirst()
                .orElse("N/A");
    }

    /**
     * 기상청 API에 맞게 base_time을 계산 (이전 30분 단위 중 가장 가까운 값)
     */
    private String getBaseTime() {
        LocalDateTime now = LocalDateTime.now();

        // base_time 후보 (하루 8번 발표)
        int[] baseHours = {2, 5, 8, 11, 14, 17, 20, 23};

        int hour = now.getHour();
        int selected = 2; // 기본값

        for (int i = baseHours.length - 1; i >= 0; i--) {
            if (hour >= baseHours[i]) {
                selected = baseHours[i];
                break;
            }
        }

        return String.format("%02d30", selected); // 예: 0830
    }
}
