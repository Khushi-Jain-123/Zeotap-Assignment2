# Weather-Fetcher-App
## Overview
The *Weather Fetcher App* is a Java EE project designed to fetch and display real-time weather data from the OpenWeatherMap API. This project provides continuous weather updates, weather summaries, and alert notifications for major cities in India. It also includes a simulated weather feature and customizable alert thresholds.

## Steps to configure this web-application on your system ##
### Prerequisites ###
 *Step 1.* Before you begin, ensure you have the following installed:
- *NetBeans IDE (Java EE version)*
- *Apache Tomcat Server (v9+)*

*Step 2.* Download the project from GitHub by cloning the repository or downloading it as a zip file.

*Step 3.* Open NetBeans and navigate to the folder where you cloned or downloaded the project. Select the project to open it.

*Step 4.* Start the Tomcat server, then run the project in NetBeans. Access the application in your browser at:
http://localhost:8080/WeatherMonitoring/weather

## Features ##
1.*Real-time Weather Data*: Retrieves up-to-date weather information including temperature, perceived temperature, and dominant weather condition.<br>
2.*Weather Simulation*: Simulates daily weather data with options to retrieve a daily summary.<br>
3.*Custom Alerts*: Set threshold values to trigger alerts for extreme weather conditions (e.g., Storm, temperature limits).<br>
4.*Daily Summary*: Provides the average, maximum, and minimum temperatures for a selected city. <br>
5.*Configurable Update Interval*: The app calls the OpenWeatherMap API at a specified interval to keep data current.<br>

## Techologies Used
### 1. Front-end Technologies
- *HTML*
- *CSS*
- *Bootstrap*
- *jQuery*

### 2. Back-end Technologies
- *Servlet*
- *JSP (JavaServer Pages)*
- *JDBC (Java Database Connectivity)*

### 4. Web  ###
- *Apache Tomcat*
