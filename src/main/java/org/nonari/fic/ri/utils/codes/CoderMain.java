package org.nonari.fic.ri.utils.codes;

import java.io.IOException;

public class CoderMain {

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
                System.out.println(Encoding.encodeGamma(Integer.parseInt(code)));
            } else if ("delta".equals(type)) {
                System.out.println(Encoding.encodeDelta(Integer.parseInt(code)));
            } else if ("omega".equals(type)) {
                System.out.println(Encoding.encodeOmega(Integer.parseInt(code)));
            } else if ("vbyte".equals(type)) {
                System.out.println(Encoding.encodeVByte(Integer.parseInt(code)));
            } else {
                System.out.println("Incorrect type " + type);
            }
        } else if ("decode".equals(mode)) {
            if ("gamma".equals(type)) {
                System.out.println(Encoding.decodeGamma(code));
            } else if ("delta".equals(type)) {
                System.out.println(Encoding.decodeDelta(code));
            } else if ("omega".equals(type)) {
                System.out.println(Encoding.decodeOmega(code));
            } else if ("vbyte".equals(type)) {
                System.out.println(Encoding.decodeVByte(code));
            } else {
                System.out.println("Incorrect type " + type);
            }
        } else {
            System.out.println("Incorrect mode " + mode);
        }


    }

}
