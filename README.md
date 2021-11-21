1: Project introduction
=======================
We will build two projects in this tutorial: a business service (Itinerary-Service) and a UI service(Itinerary-Rest). The business service provides the persistent layer and business logic, and the UI service provides the user interface. Connecting them is possible with minimal configuration.

Open a terminal, navigate to the Itinerary-Service directory, and run the command below:
./mvnw spring-boot:run

The application will start on port 8088 (as defined in file Itinerary-Service/src/main/resources/application.properties).
 
The Itinerary-Rest is a single web page that lists the Shortest Itinerary by Connection and  Shortest Itinerary by Time.To get the information, it connects with the Itinerary-Service through a configuration in file Itinerary-Rest/src/main/resources/application.properties.
service.host=localhost:8088
 
With Itinerary-Service turned on, navigate to the Itinerary-Rest directory and start Itinerary-Rest by running the command below:
./mvnw spring-boot:run
 
 
Here are the examples:
curl http://localhost:8080/itinerarylist/1
["shortestItineraryByConnection","[1, 0]","[1, 0, 3]","[1, 2]","shortestItineraryByTime","[1, 2, 3, 0]","[1, 2, 3]","[1, 2]"]
 
curl http://localhost:8080/itinerarylist/2
["shortestItineraryByConnection","[2, 0, 1]","[2, 0]","[2, 3]","shortestItineraryByTime","[2, 3, 0, 1]","[2, 3, 0]","[2, 3]"]

curl http://localhost:8088/itinerary/getshortestitinerarybyconnection/user/password/2
["[2, 0, 1]","[2, 0]","[2, 3]"]

curl http://localhost:8088/itinerary/getshortestitinerarybytime/user/password/2
["[2, 3, 0, 1]","[2, 3, 0]","[2, 3]"]

2: The technology stack used in this project
============================================
Spring Boot is used to build a microservice. The reason to use Spring Boot is: Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".

Floyd Warshall Algorithm is used to compute the Shortest way(in time and in connections ) to travel from one city to another in a graph.

RestTemplate is used when the API in UI service(Itinerary-Rest) call API inbusiness service (Itinerary-Service).The RestTemplate is the central class within the Spring framework for executing synchronous HTTP requests on the client side.

3: About the Floyd Warshall Algorithm
============================================
You could refer to this picture for the graph demo in this project:
https://github.com/jackguoxz/ItineraryService/blob/main/graph.jpeg

        itineraryDAO.save(new Itinerary(0,1,"10", "13"));
        itineraryDAO.save(new Itinerary(1,0,"10", "18"));
        itineraryDAO.save(new Itinerary(2,0,"10", "15"));
        itineraryDAO.save(new Itinerary(0,3,"10", "17"));
        itineraryDAO.save(new Itinerary(3,0,"10", "12"));
        itineraryDAO.save(new Itinerary(1,2,"10", "12"));
        itineraryDAO.save(new Itinerary(2,3,"10", "11"));

4: To be enhanced in the future
============================================

- Basic authentication is used for the API security, we should consider use HTTPS or OAuth. 
- RestTemplate is used for executing HTTP request from client to server, the server's address need to be hard coded, it is essential to have a tool that helps the services to discover their counterparts. We should consider use Eureka from Netflix as it has outstanding Spring support.
- Circuit Breaker and Service Downgrade is not implemented yet, we should consider use Hystrix,  Hystrix is a library from Netflix,it isolates the points of access between the services, stops cascading failures across them and provides the fallback options.

    
 
