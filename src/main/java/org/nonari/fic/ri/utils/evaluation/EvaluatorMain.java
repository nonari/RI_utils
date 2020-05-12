package org.nonari.fic.ri.utils.evaluation;

import org.nonari.fic.ri.utils.codes.Encoding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvaluatorMain {

    public static void main(String[] args) throws IOException {

        String mode = null;
        String relev = null;
        String found = null;
        String foundWeight = null;
        int at = 0;

        for (int i = 0; i < args.length; i++) {
            mode = args[i];
            i++;
            found = args[i];
            if ("dg".equals(mode)) {
                i++;
                foundWeight = args[i];
            }
            i++;
            relev = args[i];
            if (!"dg".equals(mode)) {
                i++;
                at = Integer.parseInt(args[i]);
            }
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
        } else if ("dgp".equals(mode)) {
            final List<Float> foundLR = parseListF(found);
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
