package org.nonari.fic.ri.utils.likelihood;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dirichlet {
    private final Collection coll;

    Dirichlet(final Collection coll) {
        this.coll = coll;
    }

    public void calc(final String query, final float mu, final int docId) {
        final String[] qparts = query.split(" ");
        final Set<String> qwords = new HashSet<>(Arrays.asList(qparts));
        final int collLen = this.coll.wordsInColl();
        final List<String> doc = this.coll.getDocsById().get(docId);
        final int docLen = doc.size();

        String op = "";
        float res = 1;
        for (final String qword : qwords) {
            final int tf = this.coll.tf(qword, docId);
            final int tfInColl = this.coll.tfInColl(qword);
            final float first = (float) tf + (mu * ((float) tfInColl / docLen));
            final float second = mu + docLen;
            res += log2(first / second);
            op += "log2((" + tf + " + " + mu + "*" + "(" + tfInColl + " / " + collLen + ")) / (" + collLen + "+" + mu + ")) +" ;
        }

        System.out.println(op + "=" + res);
    }

    private static float log2(final float n) {
        return (float)(Math.log10(n) / Math.log10(2));
    }

}
