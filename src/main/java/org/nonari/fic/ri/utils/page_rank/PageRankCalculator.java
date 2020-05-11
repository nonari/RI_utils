package org.nonari.fic.ri.utils.page_rank;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PageRankCalculator {

    final private Map<Integer, Float> prByNode = new HashMap<>();
    final Map<Integer, Float> tempPrByNode = new HashMap<>();

    public PageRankCalculator() {
    }

    private float calcNodePR(final Graph graph, int node, float teleport){
        final List<Integer> incoming = graph.incoming(node);
        float sum = 0;
        for (final int page : incoming) {
            final float ratio = this.prByNode.get(page) / graph.outgoing(page).size();
            sum += ratio;
        }
        final float currPR = (teleport / graph.nodes().size()) + ((1 - teleport) * sum);

        return currPR;
    }

    private void initPrByNode(final Graph graph) {
        final float prIni = 1F / graph.nodes().size();
        for (final Integer node : graph.nodes()) {
            this.prByNode.put(node, prIni);
        }
    }

    private void adjust(final Graph graph) {
        for (final int node : graph.nodes()) {
            if (graph.outgoing(node).size() == 0) {
                final Float nodePR = this.prByNode.get(node);
                final float residual = nodePR / (graph.nodes().size() - 1);
                for (final int other : this.prByNode.keySet()) {
                    if (other != node) {
                        final float otherPR = this.tempPrByNode.get(other) + residual;
                        this.tempPrByNode.put(other, otherPR);
                    }
                }
            }
        }
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
                System.out.println("Node " + node + " : " + currPR);
            }
            adjust(graph);
            this.prByNode.putAll(this.tempPrByNode);
        }
    }

}
