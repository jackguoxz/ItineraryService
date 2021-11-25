package com.example.developer.docker_microservices.service.services;

// Java program to find the shortest
// path between any two nodes using
// Floyd Warshall Algorithm.
import java.util.*;

class GFG{

    //static final int MAXN = 100;

    // Infinite value for array
    static int INF = (int) 1e7;
    public int [][]dis;
    public int [][]Next;

    // Initializing the distance and
// Next array

    public GFG(int V)
    {
        dis = new int[V][V];
        Next = new int[V][V];
    }
    public void initialise(int V,
                           int [][] graph)
    {

        for(int i = 0; i < V; i++)
        {
            for(int j = 0; j < V; j++)
            {
                dis[i][j] = graph[i][j];

                // No edge between node
                // i and j
                if (graph[i][j] == INF)
                    Next[i][j] = -1;
                else
                    Next[i][j] = j;
            }
        }
    }

    // Function construct the shortest
// path between u and v
    public Vector<Integer> constructPath(int u,
                                         int v)
    {

        // If there's no path between
        // node u and v, simply return
        // an empty array
        if (Next[u][v] == -1)
            return null;

        // Storing the path in a vector
        Vector<Integer> path = new Vector<Integer>();
        path.add(u);

        while (u != v)
        {
            u = Next[u][v];
            path.add(u);
        }
        return path;
    }

    // Standard Floyd Warshall Algorithm
// with little modification Now if we find
// that dis[i][j] > dis[i][k] + dis[k][j]
// then we modify next[i][j] = next[i][k]
    public void floydWarshall(int V)
    {
        for(int k = 0; k < V; k++)
        {
            for(int i = 0; i < V; i++)
            {
                for(int j = 0; j < V; j++)
                {

                    // We cannot travel through
                    // edge that doesn't exist
                    if (dis[i][k] == INF ||
                            dis[k][j] == INF)
                        continue;

                    if (dis[i][j] > dis[i][k] +
                            dis[k][j])
                    {
                        dis[i][j] = dis[i][k] +
                                dis[k][j];
                        Next[i][j] = Next[i][k];
                    }
                }
            }
        }
    }

    // Print the shortest path
    public void printPath(Vector<Integer> path)
    {
        int n = path.size();
        for(int i = 0; i < n - 1; i++)
            System.out.print(path.get(i) + " -> ");
        System.out.print(path.get(n - 1) + "\n");
    }

    // Driver code
    public  void main(String[] args)
    {
        int V = 4;
        int [][] graph = { { 0, 3, INF, 7 },
                { 8, 0, 2, INF },
                { 5, INF, 0, 1 },
                { 2, INF, INF, 0 } };

        // Function to initialise the
        // distance and Next array
        initialise(V, graph);

        // Calling Floyd Warshall Algorithm,
        // this will update the shortest
        // distance as well as Next array
        floydWarshall(V);
        Vector<Integer> path;

        // Path from node 1 to 3
        System.out.print("Shortest path from 1 to 3: ");
        path = constructPath(1, 3);
        printPath(path);

        // Path from node 0 to 2
        System.out.print("Shortest path from 0 to 2: ");
        path = constructPath(0, 2);
        printPath(path);

        // Path from node 3 to 2
        System.out.print("Shortest path from 3 to 2: ");
        path = constructPath(3, 2);
        printPath(path);
    }
}

// This code is contributed by Amit Katiyar
