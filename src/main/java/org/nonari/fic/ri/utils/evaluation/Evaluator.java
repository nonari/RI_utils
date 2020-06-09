package org.nonari.fic.ri.utils.evaluation;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Evaluator {

    private final List<Integer> relev;
    private final List<Integer> result;

    public Evaluator(final List<Integer> result, final List<Integer> relev) {
        this.relev = relev;
        this.result = result;
    }

    public float fScore(final int at, final float beta) {
        final float precision = precision(at);
        final float recall = recall(at);
        final float bCube = beta * beta;
        System.out.println("((" + 1 + " + " + beta + "^2) " + "*" + recall + "*" + precision + ") /"
        + " (" + beta + "^2 * " + precision + "+" + recall + ")");
        return (1 + bCube) * recall * precision / (bCube * precision + recall);
    }

    public float precision(final int at) {
        return (float) relevantDocsFound(at) / at;
    }

    public float recall(final int at) {
        return (float) relevantDocsFound(at) / this.relev.size();
    }

    public float rPrecision() {
        return precision(this.relev.size());
    }

    public void recallStandard() {
        final List<Float> precisionAt = new ArrayList<>();
        final List<Float> recallAt = new ArrayList<>();
        int rFound = 0;
        int count = 0;
        for (final int doc : this.result) {
            if (this.relev.contains(doc)) {
                rFound++;
            }
            count++;
            precisionAt.add((float)rFound / count);
            recallAt.add((float)rFound / this.relev.size());
        }
        tabulate(precisionAt, recallAt);

        for (int point = 0; point <= 10; point++) {
            final int fPos = first(recallAt, point / 10F);
            final float p = max(precisionAt, fPos);
            System.out.println("At " + point/10F + ":" + p);
        }
    }

    private void tabulate(final List<Float> precisionAt, List<Float> recallAt) {
        for (final float n : recallAt) {
            System.out.print(StringUtils.rightPad(Float.toString(round(n)), 6));
        }
        System.out.println();
        for (final float n : precisionAt) {
            System.out.print(StringUtils.rightPad(Float.toString(round(n)), 6));
        }
        System.out.println();
    }

    public float ap(final int at) {
        int relevant = 0;
        double precisionAccum = 0;
        int count = 0;

        for (final int doc : this.result) {
            if (count >= at) {
                break;
            }
            count++;
            if (this.relev.contains(doc)) {
                relevant++;
                final double point = relevant / (double) count;
                precisionAccum += point;
                System.out.println(relevant + " / " + count + " = " + point);
            }
        }
        final double res = precisionAccum / this.relev.size();
        System.out.println(precisionAccum + " / " + this.relev.size() + " = " + res);

        return (float)res;
    }

    public float dcg(final List<Float> scores, final int at) {
        float sum = 0;
        int i = 1;
        for (final float score : scores) {
            if (i > at) {
                break;
            }
            if (i == 1) {
                sum = score;
                i++;
                continue;
            }
            final float term = score / log2(i);
            sum += term;
            System.out.println(score + " / " + log2(i) + " = " + term);
            i++;
        }

        return sum;
    }

    public float ndcg(final List<Float> scores, final int at) {
        final float dcg = dcg(scores, at);
        System.out.println("=" + dcg);
        scores.sort(Comparator.reverseOrder());
        final float dcgIdeal = dcg(scores, at);
        System.out.println("=" + dcgIdeal);

        return dcg / dcgIdeal;
    }

    private int first(final List<Float> list, final float val) {
        int pos = 0;
        for (final float v : list) {
            if (val <= v) {
                break;
            }
            pos++;
        }

        return pos;
    }

    private float max(final List<Float> list, final int from) {
        return list.stream()
                .skip(from)
                .max(Float::compareTo)
                .get();
    }

    private int relevantDocsFound(final int at) {
        final List<Integer> cut;
        if (at < this.result.size()) {
            cut = this.result.subList(0, at);
        } else {
            cut = this.result;
        }
        final ArrayList<Integer> cutCopy = new ArrayList<>(cut);
        cutCopy.removeAll(this.relev);

        return at - cutCopy.size();
    }

    private static float log2(final int n) {
        return (float)(Math.log10(n) / Math.log10(2));
    }

    private static float round(final float n) {
        return ((int) (n * 100)) / 100F;
    }

}
