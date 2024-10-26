

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Weather Info</title>
    <style>
        /* Card Style */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f0f8ff;
            font-family: Arial, sans-serif;
        }

        .card {
            width: 400px;
            padding: 30px;
            background-color: white;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            border-radius: 12px;
            text-align: center;
            background-color: #ffe2b0;
            color: #333;
        }

        .scrollable-dropdown select {
            width: 100%;
            height: 35px; /* Show only one option */
            overflow-y: scroll;
            margin: 10px 0;
            font-size: 16px;
            padding: 5px;
        }

        .btn {
            width: 100%;
            background-color: #2596be;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
        }

        .emoji {
            font-size: 50px;
            margin: 20px 0;
        }

        .temp-convert-btn {
            background-color: #d7bf64;
            margin-top: 15px;
        }

       
    </style>
    <script>
       

        function convertTemperature() {
            const tempElement = document.getElementById("temperature");
            const tempValue = parseFloat(tempElement.getAttribute("data-temp"));
            const currentUnit = tempElement.getAttribute("data-unit");
            let newTemp, newUnit;

            if (currentUnit === "C") {
                // Convert Celsius to Kelvin
                newTemp = tempValue + 273.15;
                newUnit = "K";
            } else {
                // Convert Kelvin to Celsius
                newTemp = tempValue - 273.15;
                newUnit = "C";
            }

            tempElement.innerHTML = newTemp.toFixed(2) + " °" + newUnit;
            tempElement.setAttribute("data-temp", newTemp);
            tempElement.setAttribute("data-unit", newUnit);
        }
function fetchWeather(city) {
    const apiKey = '4f3b05df9505cf36873cf27e224ecbb7';
    const url = `http://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&appid=${apiKey}`;
    
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (!data.main) {
                console.error('Weather data is not available:', data);
                return;
            }

            const temp = data.main.temp; // Current temperature
            const feelsLike = data.main.feels_like; // Feels like temperature
            const weatherCondition = data.weather[0].main; // Weather condition
            const maxTemp = data.main.temp_max; // Maximum temperature
            const minTemp = data.main.temp_min; // Minimum temperature
            
            // Update the UI with weather data
            document.getElementById('temperature').innerText = `Temperature: ${temp.toFixed(2)} °C`;
            document.getElementById('feelsLike').innerText = `Feels Like: ${feelsLike.toFixed(2)} °C`;
            document.getElementById('weatherCondition').innerText = `Condition: ${weatherCondition}`;
            document.getElementById('maxTemperature').innerText = `Max Temperature: ${maxTemp.toFixed(2)} °C`;
            document.getElementById('minTemperature').innerText = `Min Temperature: ${minTemp.toFixed(2)} °C`;

            // Set the average temperature
            const averageTemp = (temp + maxTemp + minTemp) / 3;
            document.getElementById('averageTemperature').innerText = `Average Temperature: ${averageTemp.toFixed(2)} °C`;
        })
        .catch(error => {
            console.error('Error fetching weather:', error);
        });
}

       
    </script>
</head>
<body>
    <div class="card">
        <h2>Select a City</h2>
        <div class="scrollable-dropdown">
            <form action="weather" method="get">
                <select name="city" id="citySelect" size="1">
                    <option value="Delhi" ${city == 'Delhi' ? 'selected' : ''}>Delhi</option>
                    <option value="Mumbai" ${city == 'Mumbai' ? 'selected' : ''}>Mumbai</option>
                    <option value="Chennai" ${city == 'Chennai' ? 'selected' : ''}>Chennai</option>
                    <option value="Bangalore" ${city == 'Bangalore' ? 'selected' : ''}>Bangalore</option>
                    <option value="Kolkata" ${city == 'Kolkata' ? 'selected' : ''}>Kolkata</option>
                    <option value="Hyderabad" ${city == 'Hyderabad' ? 'selected' : ''}>Hyderabad</option>
                </select>
                <br>
                <button type="submit" class="btn">Submit</button>
                <br>
                <button type="button" class="btn temp-convert-btn" onclick="convertTemperature()">Convert Temperature</button>
               
            </form>
                <form action="weather" method="get">
    <input type="hidden" name="action" value="summary">
    <input type="hidden" name="city" value="${city}">
    <button type="submit" class="btn">Show Summary</button>
</form>
        </div>

        <div class="weather-info">
            <c:if test="${not empty weatherData}">
                <h3>Weather for ${city}</h3>
                <p>Condition: ${weatherData.weatherCondition}</p>

                <c:choose>
                    <c:when test="${weatherData.weatherCondition == 'Clear'}">
                        <div class="emoji">☀️</div>
                    </c:when>
                    <c:when test="${weatherData.weatherCondition == 'Rain'}">
                        <div class="emoji">🌧️</div>
                    </c:when>
                    <c:when test="${weatherData.weatherCondition == 'Clouds'}">
                        <div class="emoji">☁️</div>
                    </c:when>
                    <c:otherwise>
                        <div class="emoji">🌤️</div>
                    </c:otherwise>
               </c:choose>

                <p>Temperature: <span id="temperature" data-temp="${weatherData.temp}" data-unit="C">${weatherData.temp} °C</span></p>
                <p>Feels Like: ${weatherData.feelsLike} °C</p>
                
                <p>Updated: ${weatherData.formattedDate}</p>
                <c:if test="${not empty dailySummary}">
    <h3>Daily Weather Summary for ${city}</h3>
    <p>Average Temperature: ${dailySummary.avgTemp} °C</p>
    <p>Maximum Temperature: ${dailySummary.maxTemp} °C</p>
    <p>Minimum Temperature: ${dailySummary.minTemp} °C</p>
    <p>Dominant Weather Condition: ${dailySummary.dominantCondition}</p>
</c:if>
        </c:if>
                
                
                
                
           

            <c:if test="${not empty error}">
                <p style="color: red;">${error}</p>
            </c:if>
        </div>

<!--        <div class="forecast" id="forecast">
             7-Day Forecast will be displayed here 
        </div>-->
    </div>
</body>
</html>