package org.nonari.fic.ri.utils.evaluation;

import org.nonari.fic.ri.utils.codes.Encoding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvaluatorMain {

    public static void main(String[] args) throws IOException {

        String mode = null;
        String relev = "1";
        String found = "1";
        String foundWeights = null;
        float beta = 1;
        int at = 0;

        for (int i = 0; i < args.length; i++) {
            mode = args[i];
            if (!"dcg".equals(mode) && !"ndcg".equals(mode)) {
                i++;
                found = args[i];
            }
            if ("dcg".equals(mode) || "ndcg".equals(mode)) {
                i++;
                foundWeights = args[i];
            }
            if (!"dcg".equals(mode) && !"ndcg".equals(mode)) {
                i++;
                relev = args[i];
            }
            if ("f".equals(mode)) {
                i++;
                beta = Float.parseFloat(args[i]);
            }
            i++;
            at = Integer.parseInt(args[i]);
        }

        final List<Integer> relevant = parseList(relev);
        final List<Integer> foundL = parseList(found);

        final Evaluator evaluator = new Evaluator(foundL, relevant);
        if ("p".equals(mode)) {
            System.out.println(evaluator.precision(at));
        } else if ("r".equals(mode)) {
            System.out.println(evaluator.recall(at));
        } else if ("rs".equals(mode)) {
            evaluator.recallStandard();
        } else if ("ap".equals(mode)) {
            System.out.println(evaluator.ap(at));
        } else if ("f".equals(mode)) {
            System.out.println(evaluator.fScore(at, beta));
        } else if ("dcg".equals(mode)) {
            final List<Float> foundW = parseListF(foundWeights);
            System.out.println(evaluator.dcg(foundW, at));
        } else if ("ndcg".equals(mode)) {
            final List<Float> foundW = parseListF(foundWeights);
            System.out.println(evaluator.ndcg(foundW, at));
        } else {
            System.out.println("Incorrect type " + mode);
        }

    }

    private static List<Integer> parseList(final String str) {
        final List<Integer> list = new ArrayList<>();
        final String[] parts = str.split(";");
        for (final String part : parts) {
            list.add(Integer.parseInt(part));
        }

        return list;
    }

    private static List<Float> parseListF(final String str) {
        final List<Float> list = new ArrayList<>();
        final String[] parts = str.split(";");
        for (final String part : parts) {
            list.add(Float.parseFloat(part));
        }

        return list;
    }

}
