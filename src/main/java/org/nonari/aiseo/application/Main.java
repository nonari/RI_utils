package org.nonari.aiseo.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


    public static void main(String[] args) {
        Graph g = new Graph();
        String line;
        BufferedReader br = new BufferedReader(new
                InputStreamReader(System.in));
        while(true){

            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            Command c = Parser.getCommand(line);
            if (c == null) {
                System.out.println("Bad syntax");
                continue;
            }
            if (c.getComm().equals("add")){
                if ((Integer)c.getArgs().get(0) > 255){
                    System.out.println("Node number cant be greater than 255");
                    continue;
                }
                if ((Integer)c.getArgs().get(1) > 255){
                    System.out.println("Node number cant be greater than 255");
                    continue;
                }
                g.addEdge((Integer)c.getArgs().get(0), (Integer)c.getArgs().get(1), (Integer)c.getArgs().get(2));
            }
            if (c.getComm().equals("path")){
                if (!g.nodeExists((Integer)c.getArgs().get(0))){
                    System.out.println("Non-existent source node");
                    continue;
                }
                if (!g.nodeExists((Integer)c.getArgs().get(0))){
                    System.out.println("Non-existent destination node");
                    continue;
                }
                Integer distance;
                try {
                    distance = Dijkstra.distance(g, (Integer)c.getArgs().get(0), (Integer)c.getArgs().get(1));
                }catch (Exception e){
                    System.out.println("No path between nodes");
                    continue;
                }
                System.out.print(line + " " + distance + "\n");
            }

        }
    }
}
