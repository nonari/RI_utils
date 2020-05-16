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

    public int tf(final String word, final int doc) {
        if (this.tfs.containsKey(word)) {
            return this.tfs.get(word).get(doc);
        } else {
            return 0;
        }
    }

    public int df(final String word) {
        return this.dfs.getOrDefault(word, 0);
    }

    public Map<Integer, List<String>> getDocsById() {
        return Collections.unmodifiableMap(this.docs);
    }

}
