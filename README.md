# Java Swing Weather Application ☀️🌤️☔️

## Overview
Welcome to the Java Swing Weather Application! This application provides weather updates and forecasts, right at your fingertips. With a sleek and intuitive user interface built with Java Swing, you can easily search for weather conditions in any location around the world 🌎.

![изображение](https://github.com/C0s9Ar/WeatherApp/assets/94627679/0e7b34d3-d03e-4c86-8e61-4dafd9621828)

## Features
- Search by city name 🔍.
- Display of weather conditions including temperature 🌡️, humidity 💧, and wind 💨.
- Easy-to-use graphical user interface 👌.

## Prerequisites
Before running the application, ensure you have the following installed:
- Java Runtime Environment (JRE) 8 or above ☕️.

## Running the Application
To run the application, follow these steps:
1. **Navigate to your directory:**
```
cd your_directory_name
```
2. **Clone the repository to your local machine:**
```
git clone https://github.com/C0s9Ar/WeatherApp.git
```
3. **Navigate to the new project directory:**
```
cd your_directory_name/WeatherApp
```
4. **Build the Project with Maven:**
```
mvn clean install
```
## Running the Application
To run the application, you have two options after building it:
1. **Using Maven**:
This method is useful for development environments where you might have Maven integrate with other tools or plugins.
 ```
mvn exec:java -Dexec.mainClass="org.example.Main"
 ```

2. **Using the Standalone JAR**:
This method is great for running the application directly from a JAR file, which can be convenient for distribution or quick tests.
 ```
java -jar target/WeatherApp-1.0-jar-with-dependencies.jar
 ```

Choose the method that best fits your setup or preferences.

## Contribution
Contributions are welcome and greatly appreciated! Whether it's fixing a bug, adding a new feature, or improving the documentation, every contribution makes a difference. 
Feel free to fork the project and submit a pull request 🚀.

## License
Distributed under the MIT License. See `LICENSE` for more information.
