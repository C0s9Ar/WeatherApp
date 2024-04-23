package org.example;

import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class WeatherAppGUI extends JFrame {
    private JSONObject weatherData;
    final String font = "Cascadia Code";
    final private String[] placeholderCities = {
            "Moscow", "London", "Paris", "New York",
            "Saint Petersburg", "Berlin", "Frankfurt",
            "Rome", "Kiev", "Stockholm", "Warsaw"
    };
    public WeatherAppGUI() {
        super("Weather App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setIconImage(loadImage("src/assets/icon.png").getImage());
        getContentPane().setBackground(Color.lightGray);
        setGuiComponents();

    }
    private void setGuiComponents() {

        PlaceholderTextField searchField = new PlaceholderTextField();
        searchField.setBounds(15, 15, 351, 45);
        searchField.setFont(new Font(font, Font.PLAIN, 24));
        Random rand = new Random();
        String placeholderCity = placeholderCities[rand.nextInt(placeholderCities.length)];
        searchField.setPlaceholder(placeholderCity);
        add(searchField);

        JLabel cityNameText = new JLabel("Input the city name");
        cityNameText.setFont(new Font(font, Font.BOLD, 30));
        cityNameText.setBounds(0, 65, 450, 80);
        cityNameText.setHorizontalAlignment(SwingConstants.CENTER);
        add(cityNameText);

        JLabel countryNameText = new JLabel("");
        countryNameText.setFont(new Font(font, Font.BOLD, 15));
        countryNameText.setBounds(0, 90, 450, 80);
        countryNameText.setHorizontalAlignment(SwingConstants.CENTER);
        add(countryNameText);

        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        JLabel temperatureText = new JLabel("10°C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font(font, Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        JLabel weatherConditionDescription = new JLabel("Cloudy");
        weatherConditionDescription.setBounds(0, 405, 450, 36);
        weatherConditionDescription.setFont(new Font(font, Font.PLAIN, 32));
        weatherConditionDescription.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDescription);

        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font(font, Font.PLAIN, 16));
        add(humidityText);

        JLabel windspeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);

        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font(font, Font.PLAIN, 16));
        add(windspeedText);

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));
        searchButton.setBackground(Color.lightGray);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 14, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchField.getText().strip();

                if (userInput.replace("\\s", "").length() <= 0) return;

                weatherData = WeatherApp.getWeather(userInput);

                if (weatherData == null) {
                    cityNameText.setText("Not found");
                    countryNameText.setText("");
                    return;
                }
                cityNameText.setText(userInput.toUpperCase());
                countryNameText.setText((String)weatherData.get("country"));
                String weatherCondition = (String) weatherData.get("weather_condition");
                switch (weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }

                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + "°C");

                weatherConditionDescription.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
        }
        });
        add(searchButton);
    }

    private ImageIcon loadImage(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            return new ImageIcon(image);
        }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }
}
