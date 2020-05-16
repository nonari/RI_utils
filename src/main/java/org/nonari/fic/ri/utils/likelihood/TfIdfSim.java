package org.nonari.fic.ri.utils.likelihood;

import java.util.*;

public class TfIdfSim {

    private final Collection coll;

    TfIdfSim(final Collection coll) {
        this.coll = coll;
    }

    public List<Integer> tfIdf(final String query) {
        final String[] qparts = query.split(" ");
        final List<Float> vec1 = new ArrayList<>();
        final Set<String> qwords = new HashSet<>(Arrays.asList(qparts));
        for (final String qword : qwords) {
            final float tf = tf(qword, Arrays.asList(qparts));
            final float idf = idf(qword);
            System.out.println("Q " + qword + " tf:" + tf + " idf:" + idf);
            vec1.add(tf * idf);
        }

        for (final Map.Entry<Integer, List<String>> docById : this.coll.getDocsById().entrySet()) {
            System.out.println("FOR DOC: " + docById.getKey());
            final List<Float> vec2 = new ArrayList<>();
            float rem = 0;
            for (final String qword : qwords) {
                if (docById.getValue().contains(qword)) {
                    vec2.add(tfidf(qword, docById.getKey()));
                } {
                    vec2.add(0F);
                    final float tfidf = tfidf(qword, docById.getKey());
                    rem += tfidf * tfidf;
                }
            }
            final float similarity = VectorUtils.similarity(vec1, vec2, rem);
            System.out.println("Sim for doc: " + docById.getKey() + " is " + similarity);
        }

        return null;
    }

    private float tfidf(final String word, final int docId) {
        final int tf = this.coll.tf(word, docId);
        System.out.println("D " + word + " tf:" + tf + " idf:" + idf(word));
        return tf * idf(word);
    }

    private float idf(final String word) {
        return (float) Math.log((this.coll.getDocsById().size() + 1F) / (this.coll.df(word) + 1F));
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

}
