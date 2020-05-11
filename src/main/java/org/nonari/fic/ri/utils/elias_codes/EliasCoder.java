package org.nonari.fic.ri.utils.elias_codes;

import org.apache.commons.lang3.StringUtils;


public class EliasCoder {

    public static String encodeGamma(final int n) {
        final int k = log2(n);
        final int rem = n % (int)(Math.pow(2, k));

        final String unary = StringUtils.leftPad("1", k + 1, "0");

        return unary + Integer.toBinaryString(rem);
    }

    public static int decodeGamma(final String n) {
        final int k = n.indexOf('1');
        final int rem = Integer.parseInt(n.substring(k + 1), 2);

        return (int)(Math.pow(2, k) + rem);
    }

    public static String encodeDelta(final int n) {
        final int k = log2(n);
        final String gamma = encodeGamma(k + 1);

        return gamma + Integer.toBinaryString(n).substring(1);
    }

    public static int decodeDelta(final String n) {
        final int k = n.indexOf('1');
        final int gammaLimit = k * 2 + 1;

        final String gamma = n.substring(0, gammaLimit);
        final int l = decodeGamma(gamma) - 1;

        final int rem = Integer.parseInt("1" + n.substring(gammaLimit + 1), 2);

        return (int)(Math.pow(2,l) + rem);
    }

    public static String encodeOmega(final int n) {
        String s = "0";

        int k = n;
        while (k > 1) {
            final String rep = Integer.toBinaryString(k);
            s = rep + s;
            k = rep.length();
            if (k == 2) {
                break;
            }
        }

        return s;
    }

    public static int decodeOmega(String n) {
        int k = 1;
        int curr = 0;
        String d = n.substring(0, 1);
        while (d.equals("1")) {
            final int temp = Integer.parseInt(d + n.substring(curr + 1, curr + k + 1), 2);
            curr += k;
            k = temp;
            d = n.substring(curr, curr + 1);
        }

        return k;
    }

    private static int log2(final int n) {
        return (int)(Math.log10(n) / Math.log10(2));
    }

}
