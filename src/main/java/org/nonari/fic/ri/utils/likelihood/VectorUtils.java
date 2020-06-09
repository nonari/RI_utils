package org.nonari.fic.ri.utils.likelihood;

import java.util.List;

public class VectorUtils {

    public static float similarity(final List<Float> qvec, final List<Float> dvec, final List<Float> dcomp) {
        final float d = magnitude(dcomp, 0) * magnitude(qvec, 0);
        final float dot = dotProduct(qvec, dvec) / d;
        System.out.print(d + "=" + dot);
        return dot;
    }

    public static float magnitude(final List<Float> vec, final float rem) {
        float sum = rem;
        for (final float t : vec) {
            sum += t*t;
        }

        return (float) Math.sqrt(sum);
    }

    private static float dotProduct(final List<Float> vec1, final List<Float> vec2) {
        float sum = 0;
        String op = "";
        for (int i = 0; i < vec1.size(); i++) {
            op += "(" + vec1.get(i) + "*" + vec2.get(i) + ")+";
            sum += vec1.get(i) * vec2.get(i);
        }
        System.out.print(op + "/");
        return sum;
    }

}
