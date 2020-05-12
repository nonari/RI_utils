package org.nonari.fic.ri.utils.codes;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class Encoding {

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

    public static String encodeVByte(final int n) {
        final String rep = Integer.toBinaryString(n);
        final int groups = rep.length() / 7;
        final int last = rep.length() % 7;

        String vb = "";

        if (groups == 0) {
            vb = "1";
        } else {
            vb = "0";
        }
        int curr = 0;
        if (last != 0) {
            vb = StringUtils.rightPad(vb, 7 - last + 1, "0");
            vb += rep.substring(curr, last);
            curr += last;
        }

        for (int i = 1; i <= groups; i++) {
            if (i == groups) {
                vb += "1";
            } else {
                vb += "0";
            }
            vb += rep.substring(curr, curr + 7);
            curr += 7;
        }

        return vb;
    }

    public static List<Integer> decodeVByte(final String n) {
        if ((n.length() % 8) != 0) {
            System.out.println("El formato es incorrecto, el numero de bits " + n.length() + " no es multiplo de ocho");
        }

        final int groups = n.length() / 8;

        final List<Integer> v = new ArrayList<>();
        String accum = "";
        int curr = 0;
        for (int i = 0; i < groups; i++) {
            final String part = n.substring(curr, curr + 8);
            if (part.substring(0,1).equals("1")) {
                v.add(decodeOneVByte(accum + part));
                accum = "";
            } else {
                accum += part;
            }
            curr += 8;
        }

        return v;
    }

    private static int decodeOneVByte(final String n) {
        final int groups = n.length() / 8;

        String accum = "";
        int curr = 1;
        for (int i = 0; i < groups; i++) {
            accum += n.substring(curr, curr + 7);
            curr += 8;
        }

        return Integer.parseInt(accum, 2);
    }

    private static int log2(final int n) {
        return (int)(Math.log10(n) / Math.log10(2));
    }

}
