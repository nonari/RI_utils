package org.nonari.fic.ri.utils.likelihood;

import java.util.List;

public class VectorUtils {

    public static float similarity(final List<Float> qvec, final List<Float> dvec, final float rem) {
        final float d = magnitude(dvec, rem) * magnitude(qvec, 0);

        return dotProduct(qvec, dvec) / d;
    }

    private static float magnitude(final List<Float> vec, final float rem) {
        float sum = rem;
        for (final float t : vec) {
            sum += t*t;
        }

        return (float) Math.sqrt(sum);
    }

    private static float dotProduct(final List<Float> vec1, final List<Float> vec2) {
        float sum = 0;
        for (int i = 0; i < vec1.size(); i++) {
            sum += vec1.get(0) * vec2.get(0);
        }

        return sum;
    }

}
