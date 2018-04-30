package org.nonari.aiseo.application;


import java.util.*;

// This class implements the Dijkstra algorithm, breaking the execution
// when the solution is reached
public class Dijkstra {
    private static Integer min(Integer[] array, List<Integer> Q){
        Integer pos = -1 ;
        Integer last = 99999999;
        for(int i = 0; i < array.length; i++){
            if (array[i] != null && array[i] < last && array[i] > -1) {
                if (!Q.contains(i)) {
                    continue;
                }
                pos = i;
                last = array[i];
            }
        }
        return pos;
    }

    public static Integer distance(Graph graph, Integer srcNode, Integer destNode) throws Exception {
        if (graph == null || srcNode == null || destNode == null) {
            throw new NullPointerException();
        }
        Integer[] dist = new Integer[256];
        Integer[] prev = new Integer[256];
        List<Integer> Q = new ArrayList<>();
        List<Integer> nodes = graph.getNodes();
        for (Integer node: nodes) {
            dist[node] = -1;
            prev[node] = -1;
            Q.add(node);
        }
        dist[srcNode] = 0;
        while(Q.size() > 0){
            Integer u = min(dist, Q);
            // If destination node reached, finish search
            if (u.equals(destNode)) {
                break;
            }
            Q.remove(u);
            List<Vertex> neighbours;
            try {
                neighbours = graph.getNeighbours(u);
            }catch(RuntimeException e){
                throw new Exception("No path found");
            }
            for (Vertex vertex: neighbours) {
                int alt = dist[u] + vertex.getLen();
                if (dist[vertex.getDest()] == -1 || alt < dist[vertex.getDest()]){
                    dist[vertex.getDest()] = alt;
                    prev[vertex.getDest()] = u;
                }
            }
        }
        //Find nodes path
        Stack<Integer> S = new Stack<>();
        Integer u = destNode;
        while (prev[u] > -1){
            S.push(u);
            u = prev[u];
        }
        S.push(u);
        //Distance calculation
        Integer s = S.pop();
        Integer dpath = 0;
        int ssize = S.size();
        for(int j = 0; j < ssize; j++) {
            Integer d = S.pop();
            dpath += graph.Distance(s, d);
            s = d;
        }


        return dpath;
    }
}
