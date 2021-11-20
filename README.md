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
 
