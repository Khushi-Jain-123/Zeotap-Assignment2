/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

import WS.AlertEvent.AlertListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Neha Meena
 */
public class WeatherAlert {
    private double maxTempThreshold;
    private double minTempThreshold;
    private Set<String> alertConditions;
    private List<AlertListener> listeners;

    public WeatherAlert(double maxTemp, double minTemp) {
        this.maxTempThreshold = maxTemp;
        this.minTempThreshold = minTemp;
        this.alertConditions = new HashSet<>();
        this.listeners = new ArrayList<>();
    }

    public void addAlertCondition(String condition) {
        alertConditions.add(condition);
    }

    public void addListener(AlertListener listener) {
        listeners.add(listener);
    }

    public void checkThresholds(WeatherData data) {
        if (data.getTemp() > maxTempThreshold) {
            notifyListeners(new AlertEvent("High Temperature Alert", 
                "Temperature " + data.getTemp() + "°C exceeds maximum threshold of " + maxTempThreshold + "°C"));
        }
        if (data.getTemp() < minTempThreshold) {
            notifyListeners(new AlertEvent("Low Temperature Alert", 
                "Temperature " + data.getTemp() + "°C is below minimum threshold of " + minTempThreshold + "°C"));
        }
        if (alertConditions.contains(data.getWeatherCondition())) {
            notifyListeners(new AlertEvent("Weather Condition Alert", 
                "Alert condition detected: " + data.getWeatherCondition()));
        }
    }

    private void notifyListeners(AlertEvent event) {
        for (AlertListener listener : listeners) {
            listener.onAlert(event);
        }
    }
}

