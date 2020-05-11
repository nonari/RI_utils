package org.nonari.aiseo.application.page_rank;

import java.util.*;


public class Graph {
    private Map<Integer, List<Integer>> outgoingByNode = new HashMap<>();
    private Map<Integer, List<Integer>> incomingByNode = new HashMap<>();

    public void addEdge (int src, int dest) {
        putInListMap(this.outgoingByNode, src, dest);
        putInListMap(this.incomingByNode, dest, src);
    }

    private static void putInListMap(final Map<Integer, List<Integer>> map, final int key, final int val) {
        final List<Integer> vals;
        if (!map.containsKey(key)) {
            vals = new ArrayList<>();
            map.put(key, vals);
        } else {
            vals = map.get(key);
        }
        vals.add(val);
    }

    public List<Integer> incoming(final int node) {
        if (this.incomingByNode.containsKey(node)) {
            return this.incomingByNode.get(node);
        }

        return new ArrayList<>();
    }

    public List<Integer> outgoing(final int node) {
        if (this.outgoingByNode.containsKey(node)) {
            return this.outgoingByNode.get(node);
        }

        return new ArrayList<>();
    }

    public Set<Integer> nodes() {
        final HashSet<Integer> all = new HashSet<>(this.outgoingByNode.keySet());
        all.addAll(this.incomingByNode.keySet());

        return all;
    }

    public Boolean nodeExists(int node) {
        return outgoingByNode.containsKey(node);
    }

}
