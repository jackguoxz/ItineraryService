package com.example.developer.docker_microservices.service.Algorithm.Dijkstra;


import java.util.*;

import com.example.developer.docker_microservices.service.dtos.ItineraryDto;

public class Dijkstra {

    private static List<Vertex> nodes;
    private static List<Edge> edges;

    public  static void main(String []args)
    {
        buildEdges(11);
        List<String > list=convert(102);
        System.out.println(list);

    }
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
            int sourceLocNo=list.get(i).getOriginalCityId();
            int destLocNo=list.get(i).getDestinationCityId();
            //Vertex source=new Vertex(sourceLocNo+"",sourceLocNo+"");
            //Vertex dest=new Vertex(destLocNo+"",destLocNo+"");
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
            //String departureTime =list.get(i).getDepartureTimeName();
            //String arrivalTime=list.get(i).getArrivalTimeName();
            //int flightTime=Integer.parseInt(arrivalTime)-Integer.parseInt(departureTime);
            int sourceLocNo=list.get(i).getOriginalCityId();
            int destLocNo=list.get(i).getDestinationCityId();
            //Vertex source=new Vertex(sourceLocNo+"",sourceLocNo+"");
            //Vertex dest=new Vertex(destLocNo+"",destLocNo+"");
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
        addLane("101", 101, 102, 85);
        addLane("101", 101, 103, 217);
        addLane("101", 102, 103, 10);

        /*
        addLane("2", 2, 6, 186);
        addLane("2", 2, 7, 103);
        addLane("3", 3, 7, 183);
        addLane("5", 5, 8, 250);
        addLane("8", 8, 9, 84);
        addLane("7", 7, 9, 167);
        addLane("4", 4, 9, 502);
        addLane("9", 9, 10, 40);
        addLane("1", 1, 10, 600);
        */

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

       // System.out.println(destinationCityId.toArray());
       // dijkstra.execute(nodes.get(start));
        //int id=Integer.parseInt(edges.get(i).getDestination().getName());
        //System.out.println(nodes.get());
        //path = dijkstra.getPath(nodes.get(2));
        //System.out.println(path);
        Vertex source=new Vertex(start+"",start+"");
        for(int id: destinationCityId)
        {
            //System.out.println();
            Vertex dest=new Vertex(id+"",id+"");
            //dijkstra.execute(nodes.get(start));
            dijkstra.execute(source);
            //int id=Integer.parseInt(edges.get(i).getDestination().getName());
           // System.out.println(nodes.get(id));
            //path = dijkstra.getPath(nodes.get(id));
            path = dijkstra.getPath(dest);
            try {
                if(path!=null) {
                    //System.out.println(path);
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
        Vertex v1=new Vertex(sourceLocNo+"",sourceLocNo+"");
        Vertex v2=new Vertex(destLocNo+"",destLocNo+"");
       // v.setName(sourceLocNo+"");
        //Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        Edge lane = new Edge(laneId,v1, v2, duration );
        edges.add(lane);
    }
}