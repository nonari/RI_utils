package org.nonari.fic.ri.utils.codes;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class Encoding {

    public static String encodeGamma(final int n) {
        final int k = log2(n);
        final int rem = n % (int)(Math.pow(2, k));
        System.out.println("k=" + k + " r=" + rem);
        final String unary = StringUtils.leftPad("0", k + 1, "1");

        return unary + Integer.toBinaryString(rem);
    }

    public static List<Integer> decodeGamma(final String n) {
        final List<Integer> list = new ArrayList<>();
        int pos = 0;

        String np = n;
        while (pos < n.length()) {
            final int k = np.indexOf('0');
            if (k == 0) {
                list.add(1);
                pos++;
            } else {
                final int rem = Integer.parseInt(np.substring(k + 1, k + k + 1), 2);
                list.add((int) (Math.pow(2, k) + rem));
                System.out.println("2^" + k + " + " + rem);
                pos += k + k + 1;
            }
            np = n.substring(pos);
        }

        return list;
    }

    public static String encodeDelta(final int n) {
        final int k = log2(n);
        final String gamma = encodeGamma(k + 1);

        return gamma + Integer.toBinaryString(n).substring(1);
    }

    public static List<Integer> decodeDelta(final String n) {
        final List<Integer> list = new ArrayList<>();

        int pos = 0;
        String np = n;
        while (pos < n.length()) {
            final int k = np.indexOf('0');
            if (k == 0) {
                list.add(1);
                pos++;
            } else {
                final int gammaLimit = k * 2 + 1;

                final String gamma = np.substring(0, gammaLimit);
                final int l = decodeGamma(gamma).get(0) - 1;
                System.out.println("gamma - 1 = " + l);
                final int rem = Integer.parseInt("1" + np.substring(gammaLimit + 1, gammaLimit + l), 2);
                list.add((int) (Math.pow(2, l) + rem));
                pos += gammaLimit + l;
            }
            np = n.substring(pos);
        }

        return list;
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

    public static List<Integer> decodeOmega(String n) {
        final List<Integer> list = new ArrayList<>();
        int pos = 0;
        String p;
        while (pos < n.length()) {
            int k = 1;
            int curr = 1;
            p = n.substring(pos);
            String d = p.substring(0, 1);
            while (d.equals("1")) {
                if (k == 1) {
                    curr = 1;
                    k = 2;
                }
                final int temp = Integer.parseInt(d + n.substring(curr, curr + k - 1), 2);
                curr += k - 1;
                k = temp;
                d = p.substring(curr, curr + 1);
                curr++;
            }
            pos = curr + pos;
            list.add(k);
        }

        return list;
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
