/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

/**
 *
 * @author Neha Meena
 */
 

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DailyWeatherSummary {
   
    private double avgTemp;
    private double maxTemp;
    private double minTemp;
    private String dominantCondition;

    // Constructor
    public DailyWeatherSummary(List<WeatherData> dailyData) {
        calculateDailySummary(dailyData);
    }

    // This method calculates the daily summary
    private void calculateDailySummary(List<WeatherData> dailyData) {
        if (dailyData == null || dailyData.isEmpty()) {
            return;
        }

        double sum = 0;
        maxTemp = Double.MIN_VALUE;
        minTemp = Double.MAX_VALUE;
        Map<String, Integer> conditionFrequency = new HashMap<>();

        for (WeatherData data : dailyData) {
            double temp = data.getTemp();
            sum += temp;

            // Max and min temperature
            if (temp > maxTemp) maxTemp = temp;
            if (temp < minTemp) minTemp = temp;

            // Dominant condition (most frequent)
            String condition = data.getWeatherCondition();
            conditionFrequency.put(condition, conditionFrequency.getOrDefault(condition, 0) + 1);
        }

        // Average temperature
        avgTemp = sum / dailyData.size();

        // Find the dominant weather condition (most frequent)
        dominantCondition = conditionFrequency.entrySet()
                            .stream()
                            .max(Map.Entry.comparingByValue())
                            .get()
                            .getKey();
    }

    // Getters for the summary data
    public String getAvgTemp() {
         return String.format("%.2f", avgTemp);
    }

    public String getMaxTemp() {
         return String.format("%.2f", maxTemp);
    }

    public String getMinTemp() {
        return String.format("%.2f", minTemp);
    }

    public String getDominantCondition() {
        return dominantCondition;
    }
}


