package org.nonari.fic.ri.utils.likelihood;

import java.util.*;

public class JelinekMercer {

    private final Collection coll;

    JelinekMercer(final Collection coll) {
        this.coll = coll;
    }

    public void calc(final String query, final float lambda, final int docId) {
        final float lambdaComp = 1 - lambda;
        final String[] qparts = query.split(" ");
        final Set<String> qwords = new HashSet<>(Arrays.asList(qparts));
        final int collLen = this.coll.wordsInColl();
        final List<String> doc = this.coll.getDocsById().get(docId);
        final int docLen = doc.size();

        String op = "";
        float res = 1;
        for (final String qword : qwords) {
            final int tf = this.coll.tf(qword, docId);
            final float first = (float) tf / docLen;
            final int tfInColl = this.coll.tfInColl(qword);
            final float second = (float) tfInColl / collLen;
            res *= (lambdaComp * first) + (lambda * second);
            op += "(" + lambdaComp + " * " + tf + "/" + docLen + " + " + lambda + " * " + tfInColl + "/" + collLen + ") *" ;
        }

        System.out.println(op + "=" + res);
    }

}
