package org.nonari.fic.ri.utils.likelihood;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class TfIdfSimNorm {

    private final Collection coll;

    TfIdfSimNorm(final Collection coll) {
        this.coll = coll;
    }

    public List<Integer> tfIdf(final String query) {
        final String[] qparts = query.split(" ");
        final List<Float> vec1 = new ArrayList<>();
        final Set<String> qwords = new HashSet<>(Arrays.asList(qparts));
        printHead("Query");
        for (final String qword : qwords) {
            final float tf = tf(qword, Arrays.asList(qparts));
            final float idf = idf(qword);
            final float tfIdf = tf * idf;

            vec1.add(tfIdf);
        }
        final float normQ = VectorUtils.magnitude(vec1, 0);
        for (final String qword : qwords) {
            final float tf = tf(qword, Arrays.asList(qparts));
            final float idf = idf(qword);
            final float tfIdf = tf * idf;

            tabulate(qword, tf, idf, tfIdf, tfIdf/normQ);
        }

        for (final Map.Entry<Integer, List<String>> docById : this.coll.getDocsById().entrySet()) {
            System.out.println();
            printHead("Doc: " + docById.getKey());
            final List<Float> vec2 = new ArrayList<>();
            final List<Float> vec3 = new ArrayList<>();

            for (final String word : docById.getValue()) {
                final float tfIdf = tfidf(word, docById.getKey());
                vec3.add(tfIdf);
            }

            final float normD = VectorUtils.magnitude(vec3, 0);
            for (final String word : docById.getValue()) {
                final float tfIdf = tfidfT(word, docById.getKey());
                final int tf = this.coll.tf(word, docById.getKey());
                tabulate(word, tf, idf(word), tfIdf, tfIdf/normD);
            }

            for (final String qword : qwords) {
                final float tfIdf = tfidf(qword, docById.getKey());
                if (docById.getValue().contains(qword)) {
                    vec2.add(tfIdf);
                } else {
                    vec2.add(0F);
                }
            }

            printMagnitude(vec3);
            final float similarity = VectorUtils.similarity(vec1, vec2, vec3);
        }

        return null;
    }

    private float tfidfT(final String word, final int docId) {
        final int tf = this.coll.tf(word, docId);
        final float tfIdf = tf * idf(word);
        return tfIdf;
    }

    private float tfidf(final String word, final int docId) {
        final int tf = this.coll.tf(word, docId);

        return tf * idf(word);
    }

    private float idf(final String word) {
        return log2((this.coll.getDocsById().size() + 0F) / (this.coll.df(word) + 0F));
    }

    private static float tf(final String word, final List<String> words) {
        int count = 0;
        for (final String w : words) {
            if (w.equals(word)) {
                count++;
            }
        }

        return (float) count;
    }

    private void printHead(final String title) {
        System.out.println(title);
        System.out.println("    Word    |   tf   |  idf log  |  tf idf  |  norm  ");
        System.out.println("-----------------------------------------------------");
    }

    private void tabulate(final String word, final float tf, final float idf, final float tfIfd, final float norm) {
        final String smoothTf = String.format("%.0f", tf);
        final String smoothIdf = String.format("%.2f", idf);
        final String smoothTfIdf = String.format("%.2f", tfIfd);
        final String smoothNorm = String.format("%.2f", norm);

        final String wordPadded = StringUtils.rightPad(word, 12);
        final String tfPadded = StringUtils.rightPad(smoothTf, 8);
        final String idfPadded = StringUtils.rightPad(smoothIdf, 11);
        final String tfIdfPadded = StringUtils.rightPad(smoothTfIdf, 10);
        final String normPadded = StringUtils.rightPad(smoothNorm, 10);

        System.out.println(wordPadded + "|" + tfPadded + "|" + idfPadded + "|" + tfIdfPadded + "|" + normPadded);
    }

    private static float log2(final float n) {
        return (float)(Math.log10(n) / Math.log10(10));
    }

    private void printMagnitude(final List<Float> vec) {
        String sum = "";
        for (final float t : vec) {
            sum += t + "^2 + ";
        }

        System.out.println(sum + "=" + VectorUtils.magnitude(vec ,0));
    }

}
