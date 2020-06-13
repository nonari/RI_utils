package org.nonari.fic.ri.utils.likelihood;

import org.junit.jupiter.api.Test;
import org.nonari.fic.ri.utils.page_rank.Graph;
import org.nonari.fic.ri.utils.page_rank.PageRankCalculator;

public class PageRankTest {

    @Test
    public void algo() {
        final Graph graph = new Graph();
        graph.addEdge(1,2);
        final PageRankCalculator calculator = new PageRankCalculator();
        calculator.calc(graph, 0, 10);
    }

    @Test
    public void power() {
        final Graph graph = new Graph();
        graph.addEdge(1,2);
        final PageRankCalculator calculator = new PageRankCalculator();
        calculator.power(graph, 0, 5);
    }

}
