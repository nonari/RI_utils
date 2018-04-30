package org.nonari.aiseo.application;

import java.util.*;


public class Graph {
    //Save each node in a map, maintaining for each a map of neighbours and distances
    private Map<Integer, Map<Integer, Integer>> adjacencyMap = new HashMap<>();

    //Adds the path between two nodes plus nodes if not already exists
    public void addEdge (Integer srcNode, Integer destNode, Integer weight) throws RuntimeException{
        if (srcNode == null || destNode == null || weight == null){
            throw new NullPointerException();
        }
        if (weight < 0) {
            throw new RuntimeException("Weight must be greater than 0");
        }
        if (srcNode < 0 || srcNode > 255 || destNode < 0 || destNode > 255){
            throw new RuntimeException("Node Id numbers must be between 0 and 255");
        }

        if (adjacencyMap.containsKey(srcNode)) {
            adjacencyMap.get(srcNode).put(destNode, weight);
        }else{
            Map<Integer, Integer> map = new HashMap<>();
            map.put(destNode, weight);
            adjacencyMap.put(srcNode, map);
        }
        if (!adjacencyMap.containsKey(destNode)) {
            adjacencyMap.put(destNode, new HashMap<>());
        }
    }

    //Returns the distance between two nodes
    public Integer Distance(Integer srcNode, Integer destNode){
        if (srcNode == null || destNode == null) {
            throw new NullPointerException();
        }
        return adjacencyMap.get(srcNode).get(destNode);
    }

    //Returns neighbours as a list of Vertexes
    public List<Vertex> getNeighbours(Integer node){
        if (node == null) {
            throw new NullPointerException();
        }
        if (!adjacencyMap.containsKey(node)){
            throw new RuntimeException("Not existing node: " + node);
        }
        List <Vertex> list = new ArrayList<>();
        Map<Integer,Integer> neighbours = adjacencyMap.get(node);
        for (Map.Entry<Integer, Integer> e : neighbours.entrySet() ){
            Vertex v = new Vertex(e.getKey(), e.getValue());
            list.add(v);
        }
        return list;
    }

    public List<Integer> getNodes(){
        return new ArrayList<>(adjacencyMap.keySet());
    }

    public Boolean nodeExists (Integer node){
        if (node == null) {
            throw new NullPointerException();
        }
        return adjacencyMap.containsKey(node);

    }

}
