package com.example.developer.docker_microservices.ui.Algorithm.Dijkstra;

import java.util.*;

import com.example.developer.docker_microservices.ui.dto.ItineraryDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dijkstra {
    private static List<Vertex> nodesByTime;
    private static List<Edge> edgesByTime;
    private static List<Vertex> nodesByConnection;
    private static List<Edge> edgesByConnection;
    private static Graph graphByTime;
    private static Graph graphByConnection;
    private static DijkstraAlgorithm dijkstraByTime;
    private static DijkstraAlgorithm dijkstraByConnection;


    public Dijkstra(List<ItineraryDto> itineraryDtoList) {

        List<ItineraryDto> itineraryDto=itineraryDtoList;
        edgesByTime=buildEdgesByTime(itineraryDto);
        edgesByConnection=buildEdgesByConnection(itineraryDto);
        graphByTime= new Graph(nodesByTime, edgesByTime);
        dijkstraByTime = new DijkstraAlgorithm(graphByTime);
        graphByConnection = new Graph(nodesByConnection, edgesByConnection);
        dijkstraByConnection = new DijkstraAlgorithm(graphByConnection);
    }


    public static List<Edge> buildEdgesByTime(List<ItineraryDto> list)
    {
        nodesByTime = new ArrayList<Vertex>();
        edgesByTime = new ArrayList<Edge>();
        for (int i = 0; i < list.size(); i++) {
            Vertex location = new Vertex("" + i, "" + i);
            nodesByTime.add(location);
        }

        for(int i=0;i<list.size();i++)
        {
            String departureTime =list.get(i).getDepartureTimeName();
            String arrivalTime=list.get(i).getArrivalTimeName();
            int flightTime=Integer.parseInt(arrivalTime)-Integer.parseInt(departureTime);
            String sourceLocNo=list.get(i).getOriginalCityId();
            String destLocNo=list.get(i).getDestinationCityId();
            addLaneByTime("Edge_"+i,sourceLocNo,destLocNo,flightTime);
        }
        return  edgesByTime;
    }

    public static List<Edge> buildEdgesByConnection(List<ItineraryDto> list)
    {
        nodesByConnection = new ArrayList<Vertex>();
        edgesByConnection = new ArrayList<Edge>();
        for (int i = 0; i < list.size(); i++) {
            Vertex location = new Vertex("" + i, "" + i);
            nodesByConnection.add(location);
        }
        for(int i=0;i<list.size();i++)
        {
            String sourceLocNo=list.get(i).getOriginalCityId();
            String destLocNo=list.get(i).getDestinationCityId();
            addLaneByConnection("Edge_"+i,sourceLocNo,destLocNo,1);
        }
        return  edgesByConnection;
    }

    public static List<String> convertByTime(String start) {
        List<String> result= new ArrayList<>();
        LinkedList<Vertex> path = new LinkedList<>();
        Set<String> destinationCityId=new HashSet<>();

        for(int i=0;i<edgesByTime.size();i++)
        {
            String id=edgesByTime.get(i).getDestination().getName();
            destinationCityId.add(id);
        }

        Vertex source=new Vertex(start,start);
        for(String id: destinationCityId)
        {
            Vertex dest=new Vertex(id,id);
            dijkstraByTime.execute(source);
            path = dijkstraByTime.getPath(dest);
            try {
                if(path!=null) {
                    result.add(path.toString());
                }
            }catch (Exception e)
            {
                log.error(e.toString());
            }

        }
        return  result;
    }

    public static List<String> convertByConnection(String start) {
        List<String> result= new ArrayList<>();
        LinkedList<Vertex> path = new LinkedList<>();
        Set<String> destinationCityId=new HashSet<>();
        for(int i=0;i<edgesByConnection.size();i++)
        {
            String id=edgesByConnection.get(i).getDestination().getName();
            destinationCityId.add(id);
        }
        Vertex source=new Vertex(start,start);
        for(String id: destinationCityId)
        {
            Vertex dest=new Vertex(id,id);
            dijkstraByConnection.execute(source);
            path = dijkstraByConnection.getPath(dest);
            try {
                if(path!=null) {
                    result.add(path.toString());
                }
            }catch (Exception e)
            {
                log.error(e.toString());
            }

        }
        return  result;
    }
    private static void addLaneByTime(String laneId, String sourceLocNo, String destLocNo,
                         int duration) {
        Vertex v1=new Vertex(sourceLocNo,sourceLocNo);
        Vertex v2=new Vertex(destLocNo,destLocNo);
        Edge lane = new Edge(laneId,v1, v2, duration );
        edgesByTime.add(lane);
    }

    private static void addLaneByConnection(String laneId, String sourceLocNo, String destLocNo,
                                      int duration) {
        Vertex v1=new Vertex(sourceLocNo,sourceLocNo);
        Vertex v2=new Vertex(destLocNo,destLocNo);
        Edge lane = new Edge(laneId,v1, v2, duration );
        edgesByConnection.add(lane);
    }
}