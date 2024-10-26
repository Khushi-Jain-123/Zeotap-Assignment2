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
//public class WeatherServlet {
    
//}
import java.io.BufferedReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final String API_KEY = "4f3b05df9505cf36873cf27e224ecbb7"; // Replace with your OpenWeatherMap API key
private WeatherAlert alertSystem;
    private WeatherSimulator simulator;
    private List<WeatherData> simulatedData;
    @Override
    public void init() throws ServletException {
       alertSystem = new WeatherAlert(35.0, 5.0); // Max 35°C, Min 5°C
        alertSystem.addAlertCondition("Storm");
        
        // Add a simple console alert listener
        alertSystem.addListener(event -> {
            System.out.println("[" + new Date(event.getTimestamp()) + "] " + 
                             event.getType() + ": " + event.getMessage());
        });

        simulator = new WeatherSimulator();
        
        // Schedule periodic weather simulation and monitoring
        scheduler.scheduleAtFixedRate(() -> {
            simulatedData = simulator.simulateDailyData(24); // 24 updates per day
            for (WeatherData data : simulatedData) {
                alertSystem.checkThresholds(data);
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public void destroy() {
        scheduler.shutdown();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String city = request.getParameter("city");
    String action = request.getParameter("action");
        if (city == null || city.isEmpty()) {
            request.setAttribute("error", "City not specified");
            request.getRequestDispatcher("weather.jsp").forward(request, response);
            return;
        }

        // Fetch the weather data for the city
        WeatherData weatherData = fetchWeatherData(city);
 if ("simulate".equals(action)) {
            // Use simulated data
            weatherData = simulatedData != null && !simulatedData.isEmpty() 
                         ? simulatedData.get(simulatedData.size() - 1) 
                         : fetchWeatherData(city);
        } else {
            // Fetch real data
            weatherData = fetchWeatherData(city);
        }

        if (weatherData != null) {
            if ("summary".equals(action)) {
                // Create daily weather summary using simulated data
                List<WeatherData> dailyData = simulatedData != null 
                                            ? simulatedData 
                                            : Collections.singletonList(weatherData);
                DailyWeatherSummary dailySummary = new DailyWeatherSummary(dailyData);
                request.setAttribute("dailySummary", dailySummary);
            }
            
            request.setAttribute("city", city);
            request.setAttribute("weatherData", weatherData);
            request.getRequestDispatcher("weather.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Failed to retrieve weather data");
            request.getRequestDispatcher("weather.jsp").forward(request, response);
        }
    }

//        if (weatherData != null) {
//              if ("summary".equals(action)) {
//            // Create daily weather summary
//            List<WeatherData> dailyData = new ArrayList<>();
//            dailyData.add(weatherData);  // Just using the current weather for a single day's summary
//            DailyWeatherSummary dailySummary = new DailyWeatherSummary(dailyData);
// request.setAttribute("dailySummary", dailySummary);
//              }
//            // Set attributes for the JSP
//            request.setAttribute("city", city);
//            request.setAttribute("weatherData", weatherData);
//           
//            request.getRequestDispatcher("weather.jsp").forward(request, response);
//        } else {
//            request.setAttribute("error", "Failed to retrieve weather data");
//            request.getRequestDispatcher("weather.jsp").forward(request, response);
//        }
//    }

    // Method to fetch weather data from OpenWeatherMap API
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("updateThresholds".equals(action)) {
            // Read the request body
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            // Parse JSON
            JSONObject json = new JSONObject(sb.toString());
           DecimalFormat df = new DecimalFormat("#.##");
double maxTemp = Double.parseDouble(df.format(json.getDouble("maxTemp")));
double minTemp = Double.parseDouble(df.format(json.getDouble("minTemp")));
            
            // Update alert system thresholds
            alertSystem = new WeatherAlert(maxTemp, minTemp);
            alertSystem.addAlertCondition("Storm"); // Re-add default conditions
            
            // Send success response
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\"}");
        }
    }
    private WeatherData fetchWeatherData(String city) {
        try {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q="
                                + city + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject main = jsonResponse.getJSONObject("main");

                String weatherMain = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("main");
                double temp = main.getDouble("temp");
                double feelsLike = main.getDouble("feels_like");
                long timestamp = jsonResponse.getLong("dt");

                // Return the weather data object
                return new WeatherData(weatherMain, temp, feelsLike, timestamp);
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

