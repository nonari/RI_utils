package org.nonari.fic.ri.utils.evaluation;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {

    private final List<Integer> relev;
    private final List<Integer> result;

    public Evaluator(final List<Integer> result, final List<Integer> relev) {
        this.relev = relev;
        this.result = result;
    }

    public float precision(final int at) {
        return (float) relevantDocsFound(at) / at;
    }

    public float recall(final int at) {
        return (float) relevantDocsFound(at) / this.relev.size();
    }

    public void recallStandard() {
        final List<Float> resultAt = new ArrayList<>();
        int rFound = 0;
        for (final int doc : this.result) {
            if (this.relev.contains(doc)) {
                rFound++;
            }
            resultAt.add(((float)rFound / (float) this.relev.size()));
        }

        int point = 0;
        for (final float r : resultAt) {
             if ((point / 10) >= r) {

             } else {
                 while ((point / 10)<=r) {
                     System.out.println("At " + (point / 10F) + ": " + r);

                     point++;
                 }
             }
        }
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

}
