package com.example.developer.docker_microservices.service.Algorithm.Dijkstra;


import java.util.*;

import com.example.developer.docker_microservices.service.dtos.ItineraryDto;

public class Dijkstra {
    private static List<Vertex> nodes;
    private static List<Edge> edges;

    public static List<Edge> buildEdgesByTime(List<ItineraryDto> list)
    {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        for (int i = 0; i < list.size(); i++) {
            Vertex location = new Vertex("" + i, "" + i);
            nodes.add(location);
        }

        for(int i=0;i<list.size();i++)
        {
            String departureTime =list.get(i).getDepartureTimeName();
            String arrivalTime=list.get(i).getArrivalTimeName();
            int flightTime=Integer.parseInt(arrivalTime)-Integer.parseInt(departureTime);
            String sourceLocNo=list.get(i).getOriginalCityId();
            String destLocNo=list.get(i).getDestinationCityId();
            addLane("Edge_"+i,sourceLocNo,destLocNo,flightTime);
        }
        return  edges;
    }

    public static List<Edge> buildEdgesByConnection(List<ItineraryDto> list)
    {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        for (int i = 0; i < list.size(); i++) {
            Vertex location = new Vertex("" + i, "" + i);
            nodes.add(location);
        }

        for(int i=0;i<list.size();i++)
        {
            String sourceLocNo=list.get(i).getOriginalCityId();
            String destLocNo=list.get(i).getDestinationCityId();
            addLane("Edge_"+i,sourceLocNo,destLocNo,1);
        }
        return  edges;
    }


    public static List<Edge> buildEdges(int size)
    {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        for (int i = 0; i < size; i++) {
            Vertex location = new Vertex("" + i, "" + i);
            nodes.add(location);
        }
        return  edges;
    }
    public static List<String> convert(String start) {
        List<String> result= new ArrayList<>();
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        LinkedList<Vertex> path = new LinkedList<>();
        Set<String> destinationCityId=new HashSet<>();
        for(int i=0;i<edges.size();i++)
        {
            String id=edges.get(i).getDestination().getName();
            destinationCityId.add(id);
        }

        Vertex source=new Vertex(start,start);
        for(String id: destinationCityId)
        {
            Vertex dest=new Vertex(id,id);
            dijkstra.execute(source);
            path = dijkstra.getPath(dest);
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

    private static void addLane(String laneId, String sourceLocNo, String destLocNo,
                         int duration) {
        Vertex v1=new Vertex(sourceLocNo,sourceLocNo);
        Vertex v2=new Vertex(destLocNo,destLocNo);
        Edge lane = new Edge(laneId,v1, v2, duration );
        edges.add(lane);
    }
}