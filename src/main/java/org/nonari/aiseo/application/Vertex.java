package org.nonari.aiseo.application;

// The path to the neighbour nodes
public class Vertex {
    private Integer dest;
    private Integer len;

    public Integer getDest() {
        return dest;
    }

    public Integer getLen() {
        return len;
    }

    public Vertex(Integer dest, Integer len){
        this.dest = dest;
        this.len = len;
    }
}
