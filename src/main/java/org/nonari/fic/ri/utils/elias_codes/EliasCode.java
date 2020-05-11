package org.nonari.fic.ri.utils.elias_codes;

import java.io.IOException;

public class EliasCode {

    public static void main(String[] args) throws IOException {

        String mode = null;
        String type = null;
        String code = null;

        for (int i = 0; i < args.length; i++) {
            mode = args[i];
            i++;
            type = args[i];
            i++;
            code = args[i];
        }

        if ("encode".equals(mode)) {
            if ("gamma".equals(type)) {
                System.out.println(EliasCoder.encodeGamma(Integer.parseInt(code)));
            } else if ("delta".equals(type)) {
                System.out.println(EliasCoder.encodeDelta(Integer.parseInt(code)));
            } else if ("omega".equals(type)) {
                System.out.println(EliasCoder.encodeOmega(Integer.parseInt(code)));
            } else {
                System.out.println("Incorrect type " + type);
            }
        } else if ("decode".equals(mode)) {
            if ("gamma".equals(type)) {
                System.out.println(EliasCoder.decodeGamma(code));
            } else if ("delta".equals(type)) {
                System.out.println(EliasCoder.decodeDelta(code));
            } else if ("omega".equals(type)) {
                System.out.println(EliasCoder.decodeOmega(code));
            } else {
                System.out.println("Incorrect type " + type);
            }
        } else {
            System.out.println("Incorrect mode " + mode);
        }


    }

}
