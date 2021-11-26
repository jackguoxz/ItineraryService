package com.example.developer.docker_microservices.service.Algorithm.Dijkstra;


import java.util.*;

import com.example.developer.docker_microservices.service.dtos.ItineraryDto;

public class Dijkstra {

    private static List<Vertex> nodes;
    private static List<Edge> edges;

    public static List<Edge> buildEdges(int number,List<ItineraryDto> list)
    {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        for (int i = 0; i < number; i++) {
            Vertex location = new Vertex("" + i, "" + i);
            nodes.add(location);
        }

        for(int i=0;i<list.size();i++)
        {
            String departureTime =list.get(i).getDepartureTimeName();
            String arrivalTime=list.get(i).getArrivalTimeName();
            int flightTime=Integer.parseInt(arrivalTime)-Integer.parseInt(departureTime);
            addLane("Edge_"+i,list.get(i).getOriginalCityId(),list.get(i).getDestinationCityId(),flightTime);
        }

        return  edges;
    }

    public static List<String> convert(int start) {
        /*
        itineraryDAO.save(new Itinerary(0,1,"10", "13"));
        itineraryDAO.save(new Itinerary(1,0,"10", "18"));
        itineraryDAO.save(new Itinerary(2,0,"10", "15"));
        itineraryDAO.save(new Itinerary(0,3,"10", "17"));
        itineraryDAO.save(new Itinerary(3,0,"10", "12"));
        itineraryDAO.save(new Itinerary(1,2,"10", "12"));
        itineraryDAO.save(new Itinerary(2,3,"10", "11"));
        itineraryDAO.save(new Itinerary(1,5,"10", "11"));
        */
        // Lets check from location Loc_1 to Loc_10

        List<String> result= new ArrayList<>();
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        LinkedList<Vertex> path = new LinkedList<>();
        Set<Integer> destinationCityId=new HashSet<>();

        for(int i=0;i<edges.size();i++)
        {
            int id=Integer.parseInt(edges.get(i).getDestination().getName());
            destinationCityId.add(id);
        }

        for(int id: destinationCityId)
        {
            dijkstra.execute(nodes.get(start));
            //int id=Integer.parseInt(edges.get(i).getDestination().getName());
            path = dijkstra.getPath(nodes.get(id));
            try {
                if(path!=null) {
                    result.add(path.toString());

                }
            }catch (Exception e)
            {
                System.out.println(e.toString());
            }

        }
        return  result;

    }

    private static void addLane(String laneId, int sourceLocNo, int destLocNo,
                         int duration) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);
    }
}