package org.nonari.fic.ri.utils.likelihood;

import org.junit.jupiter.api.Test;
import org.nonari.fic.ri.utils.evaluation.Evaluator;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class EvaluatorTest {

    @Test
    public void ndcg() {
        final List<Integer> found = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        final List<Integer> relevant = Arrays.asList(2,3,5,6,7);
        final List<Float> weight = Arrays.asList(0F,2F,2F,0F,1F,2F,1F,0F,0F,0F);
        System.out.println(new Evaluator(found, relevant).ndcg(weight, 10));
    }

    @Test
    public void ap() {
        final List<Integer> found = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        final List<Integer> relevant = Arrays.asList(2,3,5,6,7);
        System.out.println(new Evaluator(found, relevant).fScore(10,1));
    }

    //1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30

    @Test
    public void st() {
        final List<Integer> found = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
        final List<Integer> relevant = Arrays.asList(2, 4, 5, 8, 14);
        new Evaluator(found, relevant).recallStandard();
    }

}
