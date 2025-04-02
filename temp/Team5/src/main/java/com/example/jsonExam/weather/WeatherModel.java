package com.example.jsonExam.weather;

import java.util.List;

public class WeatherModel {

  public static class WeatherResponse {
    private String temp;
    private String moisture;
    private String windSpeed;
    private String date;

    public WeatherResponse(String temp, String moisture, String windSpeed, String date) {
      this.temp = temp;
      this.moisture = moisture;
      this.windSpeed = windSpeed;
      this.date = date;
    }

    public String getTemp() { return temp; }
    public String getMoisture() { return moisture; }
    public String getWindSpeed() { return windSpeed; }
    public String getDate() { return date; }
  }

  public static class WeatherSearchData {
    private Weather response;

    public WeatherSearchData() {}

    public Weather getResponse() { return response; }
    public void setResponse(Weather response) { this.response = response; }

    public List<WeatherItem> getWeatherItems() {
      return response != null &&
             response.getBody() != null &&
             response.getBody().getItems() != null
             ? response.getBody().getItems().getItem()
             : List.of(); // 빈 리스트 반환
    }
  }

  public static class Weather {
    private WeatherData body;

    public Weather() {}
    public WeatherData getBody() { return body; }
    public void setBody(WeatherData body) { this.body = body; }
  }

  public static class WeatherData {
    private String dataType;
    private WeatherArr items;

    public WeatherData() {}

    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    public WeatherArr getItems() { return items; }
    public void setItems(WeatherArr items) { this.items = items; }
  }

  public static class WeatherArr {
    private List<WeatherItem> item;

    public WeatherArr() {}

    public List<WeatherItem> getItem() { return item; }
    public void setItem(List<WeatherItem> item) { this.item = item; }
  }

  public static class WeatherItem {
    private String category;
    private String fcstValue;

    public WeatherItem() {}

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getFcstValue() { return fcstValue; }
    public void setFcstValue(String fcstValue) { this.fcstValue = fcstValue; }
  }
}
