Swagger - http://localhost:8080/weather-application/swagger-ui/index.html#/

API Curl - To get Weather Prediction for a city for next 3 (Or pass number of day) :

curl --location --request GET 'http://localhost:8080/weather-application/weatherforcast/v1/city/london?numOfDays=3'