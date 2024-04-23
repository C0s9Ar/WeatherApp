package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherApp {

    public static JSONObject getWeather(String cityName) {
        JSONArray cityData = getCityData(cityName);
        if (cityData == null)
            return null;
        JSONObject city = (JSONObject) cityData.get(0);
        String country = city.get("country").toString();
        double latitude = (Double) city.get("latitude");
        double longitude = (Double) city.get("longitude");
        String urlStr = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relativehumidity_2m,weathercode,windspeed_10m&timezone=Europe%2FMoscow";
        try{
            HttpURLConnection connection = fetchApiResponse(urlStr);
            if (connection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return null;
            }
            StringBuilder resultJSON = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine())
                resultJSON.append(scanner.nextLine());
            scanner.close();
            connection.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject resultJSONObject = (JSONObject) parser.parse(String.valueOf(resultJSON));
            JSONObject hourly = (JSONObject) resultJSONObject.get("hourly");

            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (Double) temperatureData.get(index);

            JSONArray weathercode = (JSONArray) hourly.get("weathercode");
            String weatherCondition = convertWeatherCode((long) weathercode.get(index));

            JSONArray relativeHumidity = (JSONArray) hourly.get("relativehumidity_2m");
            long humidity = (Long) relativeHumidity.get(index);

            JSONArray windspeedData = (JSONArray) hourly.get("windspeed_10m");
            double windspeed = (Double) windspeedData.get(index);

            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("windspeed", windspeed);
            weatherData.put("country", country);

            return weatherData;
        }
        catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private static JSONArray getCityData(String city) {
        city = city.replace(" ", "+");

        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                city + "&count=10&language=en&format=json";

        try{
            HttpURLConnection connection = fetchApiResponse(urlString);
            if (connection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }
            StringBuilder resultJSON = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext())
                resultJSON.append(scanner.nextLine());
            scanner.close();
            connection.disconnect();
            JSONParser parser = new JSONParser();
            JSONObject resultJSONObject = (JSONObject) parser.parse(String.valueOf(resultJSON));
            JSONArray cityData = (JSONArray) resultJSONObject.get("results");
            return cityData;
        }
        catch(Exception e) { e.printStackTrace(); }
        return null;
    }
    private static HttpURLConnection fetchApiResponse(String urlString){
        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return connection;
        }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    private  static  int findIndexOfCurrentTime(JSONArray timeList){
        String currentTime = getCurrentTime();
        for (int i = 0; i < timeList.size(); i++){
            String time = (String) timeList.get(i);
            if (time.equals(currentTime))
                return i;
        }
        return 0;
    }
    private  static  String getCurrentTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        String fromatedDateTime = currentDateTime.format(formatter);
        return fromatedDateTime;
    }
    private static String convertWeatherCode(long weathercode){
        String weatherCondition = "";
        if (weathercode == 0L)
            weatherCondition = "Clear";
        else if (weathercode > 0L && weathercode <= 3L)
            weatherCondition = "Cloudy";
        else if ((weathercode >= 51L && weathercode <= 67L ) || (weathercode >= 80L && weathercode <= 99L))
            weatherCondition = "Rainy";
        else if (weathercode >= 71L && weathercode <= 77L)
            weatherCondition = "Snow";
        return weatherCondition;
    }
}
