package org.nonari.aiseo.application.page_rank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


    public static void main(String[] args) {
        PageRankCalculator prCal =  new PageRankCalculator();
        Graph g = new Graph();
        float teleport = 0;
        int iterations = 5;

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
                g.addEdge((Integer)c.getArgs().get(0), (Integer)c.getArgs().get(1));
            }

            if (c.getComm().equals("iterations")){
                iterations = (Integer)c.getArgs().get(0);
            }

            if (c.getComm().equals("teleport")){
                teleport = (Integer)c.getArgs().get(0);
            }

            if (c.getComm().equals("calc")){
                prCal.calc(g, teleport, iterations);
            }

        }
    }
}
