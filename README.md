Swagger - http://localhost:8080/weather-application/swagger-ui/index.html#/

API Curl - To get Weather Prediction for a city for next 3 (Or pass number of day) :

curl --location --request GET 'http://localhost:8080/weather-application/weatherforcast/v1/city/london?numOfDays=3'

Doekrized Application
  - Install Dockerhub and refer for docker command :
       https://spring.io/guides/topicals/spring-boot-docker/ 

Spring Security
  - Username/Password authentication for Admin
  - Json token authentication for clients - Register client and provide Token for access

Resiliency :
  - Cache city wise response for resiliency

CI 
  - CI pipeline in Github

Design Principles
  - Builder
  - Template - HttpUtil