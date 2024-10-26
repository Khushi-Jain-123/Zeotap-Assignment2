/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Neha Meena
 */
public class WeatherSimulator {
     private Random random = new Random();
    private static final String[] WEATHER_CONDITIONS = {"Clear", "Rain", "Clouds", "Storm"};
    
    public List<WeatherData> simulateDailyData(int numberOfUpdates) {
        List<WeatherData> dailyData = new ArrayList<>();
        long baseTimestamp = System.currentTimeMillis() / 1000;
        
        for (int i = 0; i < numberOfUpdates; i++) {
            double temp = 20 + random.nextGaussian() * 5; // Mean of 20°C with standard deviation of 5°C
            double feelsLike = temp + random.nextGaussian() * 2;
            String condition = WEATHER_CONDITIONS[random.nextInt(WEATHER_CONDITIONS.length)];
            long timestamp = baseTimestamp + (i * 3600); // Hourly updates
            
            dailyData.add(new WeatherData(condition, temp, feelsLike, timestamp));
        }
        
        return dailyData;
    }
}

