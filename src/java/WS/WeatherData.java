/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Neha Meena
 */
//public class WeatherData {
//    
//}
public class WeatherData {
 
    public String weatherCondition;
    public double temp;
    public double feelsLike;
    public long timestamp;
 private String formattedDate;
    public WeatherData( String weatherCondition, double temp, double feelsLike, long timestamp) {
       
        this.weatherCondition = weatherCondition;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.timestamp = timestamp;
        this.formattedDate = formatDate(timestamp);
    }
     public String getFormattedDate() {
        return formattedDate;
    }
     private String formatDate(long timestamp) {
        Date date = new Date(timestamp * 1000); // Convert UNIX timestamp to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // Format as needed
        return sdf.format(date);
    }
     public String getWeatherCondition() { return weatherCondition; }
    public double getTemp() { return temp; }
    public double getFeelsLike() { return feelsLike; }
    public long getTimestamp() { return timestamp; }
}
