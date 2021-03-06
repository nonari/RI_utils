package org.nonari.fic.ri.utils.page_rank;


import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class PageRankCalculator {

    private final Map<Integer, Float> prByNode = new HashMap<>();
    private final Map<Integer, Float> tempPrByNode = new HashMap<>();

    public PageRankCalculator() {
    }

    public void calc(Graph graph, float teleport, float iterations) {
        this.prByNode.clear();
        initPrByNode(graph);

        final Set<Integer> nodes = graph.nodes();


        for (int i = 1; i <= iterations; i++) {
            System.out.println("Iteration " + i);

            for (final int node : nodes) {
                final float currPR = calcNodePR(graph, node, teleport);
                this.tempPrByNode.put(node, currPR);
            }
            sinksAdjust(graph, teleport);
            this.prByNode.putAll(this.tempPrByNode);
            tabulateResults(graph);
            System.out.println("----------------------------------------");
        }
    }

    private void tabulateResults(final Graph graph) {
        String d = "";
        for (final int node: graph.nodes()) {
            d += StringUtils.rightPad("Node " + node, 7) + "|";
        }
        System.out.println(d);
        d = "";
        for (final int node: graph.nodes()) {
            final String smooth = String.format("%.3f", this.prByNode.get(node));
            d += StringUtils.rightPad(smooth, 7) + "|";
        }
        System.out.println(d);
    }

    private void initPrByNode(final Graph graph) {
        final float prIni = 1F / graph.nodes().size();
        for (final Integer node : graph.nodes()) {
            this.prByNode.put(node, prIni);
        }
    }

    private float calcNodePR(final Graph graph, int node, float teleport){
        System.out.println("FOR node: " + node);
        final List<Integer> incoming = graph.incoming(node);
        float sum = 0;
        for (final int page : incoming) {
            final float ratio = this.prByNode.get(page) / graph.outgoing(page).size();
            System.out.println(page + " -> " + this.prByNode.get(page) + "/" + graph.outgoing(page).size() + " = " + ratio);
            sum += ratio;
        }

        return (teleport / graph.nodes().size()) + ((1 - teleport) * sum);
    }

    private void sinksAdjust(final Graph graph, final float teleport) {
        for (final int node : graph.nodes()) {
            if (graph.outgoing(node).size() == 0) {
                final Float nodePR = this.prByNode.get(node);
                final float residual = (1 - teleport) * (nodePR / (graph.nodes().size()));
                System.out.println("Residual = " + residual);
                for (final int other : this.prByNode.keySet()) {
                    final float otherPR = this.tempPrByNode.get(other) + residual;
                    this.tempPrByNode.put(other, otherPR);
                }
            }
        }
    }

    public void power(final Graph graph, final float teleport, final int iterations) {
        final Set<Integer> nodes = graph.nodes();
        final int size = nodes.size();
        final float[][] h = new float[size][size];
        final Float[] pr = new Float[size];

        for (final int node : nodes) {
            final List<Integer> outgoing = graph.outgoing(node);
            if (outgoing.size() == 0) {
                for (int i = 0; i < size; i++) {
                    h[node - 1][i] = 1F / size;
                }
            }
            final float fraction = 1F / outgoing.size();
            for (final int to : outgoing) {
                h[node - 1][to - 1] = fraction;
            }
            pr[node - 1] = 1F / size;
        }


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                h[i][j] *= 1 - teleport;
                h[i][j] += teleport / graph.nodes().size();
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(h[i][j] + ",");
            }
            System.out.println();
        }

        final Float[] prTemp = new Float[size];
        for (int i = 0; i < iterations; i++) {
            for (int row = 0; row < size; row++) {
                float sum = 0;
                for (int col = 0; col < size; col++) {
                    sum += h[col][row] * pr[col];
                }
                prTemp[row] = sum;
            }
            System.arraycopy(prTemp, 0, pr, 0, size);

            System.out.println(Arrays.asList(pr));
        }

    }

}
