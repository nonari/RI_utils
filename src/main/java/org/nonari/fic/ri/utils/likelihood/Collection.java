package org.nonari.fic.ri.utils.likelihood;

import java.util.*;

public class Collection {

    private final Map<Integer, List<String>> docs = new HashMap<>();
    private final Map<String, Map<Integer, Integer>> tfs = new HashMap<>();
    private final Map<String, Integer> dfs = new HashMap<>();
    private int i = 0;

    public Collection() {

    }

    public void add(final String doc) {
        final String[] words = doc.split(" ");

        docs.put(i, Arrays.asList(words));

        final HashSet<String> vocabulary = new HashSet<>(Arrays.asList(words));
        for (final String word : vocabulary) {
            int count = 0;
            for (final String w : words) {
                if (w.equals(word)) {
                    count++;
                }
            }
            putTf(word, count);
            updateDf(word);
        }

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

        for (final Map.Entry<Integer, List<String>> docById : this.docs.entrySet()) {
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
            final float similarity = similarity(vec1, vec2, rem);
            System.out.println("Sim for doc: " + docById.getKey() + " is " + similarity);
        }

        return null;
    }

    private float idf(final String word) {
        return (float) Math.log((this.docs.size() + 1F) / (this.dfs.get(word) + 1F));
    }

    private float tf(final String word, final List<String> words) {
        int count = 0;
        for (final String w : words) {
            if (w.equals(word)) {
                count++;
            }
        }

        return (float) count;
    }

    private float magnitude(final List<Float> vec, final float rem) {
        float sum = rem;
        for (final float t : vec) {
            sum += t*t;
        }

        return (float) Math.sqrt(sum);
    }

    private float similarity(final List<Float> qvec, final List<Float> dvec, final float rem) {
        final float d = magnitude(dvec, rem) * magnitude(qvec, 0);

        return dotProduct(qvec, dvec) / d;
    }

    private float dotProduct(final List<Float> vec1, final List<Float> vec2) {
        float sum = 0;
        for (int i = 0; i < vec1.size(); i++) {
            sum += vec1.get(0) * vec2.get(0);
        }

        return sum;
    }

    private float tfidf(final String word, final int docId) {
        final Integer tf = this.tfs.get(word).get(docId);
        System.out.println("D " + word + " tf:" + tf + " idf:" + idf(word));
        return tf * idf(word);
    }

    private void putTf(final String word, final int count) {
        final Map<Integer, Integer> tfByDoc = new HashMap<>();
        tfByDoc.put(i, count);
        this.tfs.put(word, tfByDoc);
    }

    private void updateDf(final String word) {
        final int count;
        if (this.dfs.containsKey(word)) {
            count = this.dfs.get(word) + 1;
        } else {
            count = 1;
        }

        this.dfs.put(word, count);
    }

}
