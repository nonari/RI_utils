package org.nonari.fic.ri.utils.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        final List<Float> precisionAt = new ArrayList<>();
        final List<Float> recallAt = new ArrayList<>();
        int rFound = 0;
        int count = 0;
        for (final int doc : this.result) {
            if (this.relev.contains(doc)) {
                rFound++;
            }
            count++;
            precisionAt.add(rFound / (float)count);
            recallAt.add(rFound / (float)this.relev.size());
        }

        for (int point = 0; point <= 10; point++) {
            final int fPos = first(recallAt, point / 10F);
            final float p = max(precisionAt, fPos);
            System.out.println("At " + point/10F + ":" + p);
        }
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

}
