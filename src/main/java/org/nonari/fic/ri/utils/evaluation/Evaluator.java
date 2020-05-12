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

    public int precision(final int at) {

        return 0;
    }

    public int recall(final int at) {
        return 0;
    }

    public int recallStandard() {
        return 0;
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

        return this.result.size() - cutCopy.size();
    }

}
