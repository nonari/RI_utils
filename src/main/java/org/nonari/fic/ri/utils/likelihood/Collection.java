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

        this.docs.put(this.i, Arrays.asList(words));

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

        i++;
    }

    private void putTf(final String word, final int count) {
        if (!this.dfs.containsKey(word)) {
            final Map<Integer, Integer> tfByDoc = new HashMap<>();
            this.tfs.put(word, tfByDoc);
        }
        this.tfs.get(word).put(this.i, count);
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
            final Map<Integer, Integer> tfByDoc = this.tfs.get(word);
            if (tfByDoc.containsKey(doc)) {
                return tfByDoc.get(doc);
            }
        }

        return 0;
    }

    public int tfInColl(final String word) {
        int count = 0;
        for (final int docId : this.docs.keySet()) {
            count += tf(word, docId);
        }

        return count;
    }

    public int wordsInColl() {
        int count = 0;
        for (final List<String> doc : this.docs.values()) {
            count += doc.size();
        }

        return count;
    }

    public int df(final String word) {
        return this.dfs.getOrDefault(word, 0);
    }

    public Map<Integer, List<String>> getDocsById() {
        return Collections.unmodifiableMap(this.docs);
    }

}
