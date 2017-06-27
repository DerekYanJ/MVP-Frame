package com.yqy.mvp_frame.frame.utils;

import java.util.Arrays;

/*****************************************************************************
 *
 * Copyright 2010-2011 Sony Corporation
 *
 * The information contained here-in is the property of Sony corporation and
 * is not to be disclosed or used without the prior written permission of
 * Sony corporation. This copyright extends to all media in which this
 * information may be preserved including magnetic storage, computer
 * print-out or visual display.
 *
 * Contains proprietary information, copyright and database rights Sony.
 * Decompilation prohibited save as permitted by law. No using, disclosing,
 * reproducing, accessing or modifying without Sony prior written consent.
 *
 *****************************************************************************
 *
 *   File Name   : Base64.java
 *   Created     : 2010/08/05 By wangyuanchen
 *   Description : description
 *
 ****************************************************************************/
//import android.util.Log;

public class Base64 {
    // private static final String LOG_TAG = "Base64";
    private static final char[] CA =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    private static final int BUFFER_SIZE = 256;

    private static final int[] IA = new int[BUFFER_SIZE];
    static {
        Arrays.fill(IA, -1);
        for (int i = 0; i < CA.length; i++) {
            IA[CA[i]] = i;
        }
        IA['='] = 0;
    }


      /*Decodes a BASE64 encoded string that is known to be resonably well
      formatted. The method is about twice as fast as {@link #decode(String)}.
      The preconditions are:<br>
      + The array must have a line length of 76 chars OR no line separators at
      all (one line).<br>
      + Line separator must be "\r\n", as specified in RFC 2045 + The array
      must not contain illegal characters within the encoded string<br>
      + The array CAN have illegal characters at the beginning and end, those
      will be dealt with appropriately.<br>

      @param s The source string. Length 0 will return an empty array.
                 <code>null</code> will throw an exception.
      @return The decoded array of bytes. May be of length 0.*/

    public static final byte[] decodeFast(String s) {
        // Check special case
        int sLen = s.length();
        if (sLen == 0) {
            return new byte[0];
        }

        int sIx = 0, eIx = sLen - 1; // Start and end index after trimming.

        // Trim illegal chars from start
        while (sIx < eIx && IA[s.charAt(sIx) & 0xff] < 0) {
            sIx++;
        }

        // Trim illegal chars from end
        while (eIx > 0 && IA[s.charAt(eIx) & 0xff] < 0) {
            eIx--;
        }

        // get the padding count (=) (0, 1 or 2)
        int pad = s.charAt(eIx) == '=' ? (s.charAt(eIx - 1) == '=' ? 2 : 1) : 0; // Count
        // '='
        // at
        // end.
        int cCnt = eIx - sIx + 1; // Content count including possible separators
        int sepCnt = sLen > 76 ? (s.charAt(76) == '\r' ? cCnt / 78 : 0) << 1 : 0;

        int len = ((cCnt - sepCnt) * 6 >> 3) - pad; // The number of decoded
        // bytes
        byte[] dArr = new byte[len]; // Preallocate byte[] of exact length

        // Decode all but the last 0 - 2 bytes.
        int d = 0;
        for (int cc = 0, eLen = (len / 3) * 3; d < eLen;) {
            // Assemble three bytes into an int from four "valid" characters.
            int i = IA[s.charAt(sIx++)] << 18 | IA[s.charAt(sIx++)] << 12
                    | IA[s.charAt(sIx++)] << 6 | IA[s.charAt(sIx++)];

            // Add the bytes
            dArr[d++] = (byte)(i >> 16);
            dArr[d++] = (byte)(i >> 8);
            dArr[d++] = (byte)i;

            // If line separator, jump over it.
            if (sepCnt > 0 && ++cc == 19) {
                sIx += 2;
                cc = 0;
            }
        }

        if (d < len) {
            // Decode last 1-3 bytes (incl '=') into 1-3 bytes
            int i = 0;
            for (int j = 0; sIx <= eIx - pad; j++) {
                i |= IA[s.charAt(sIx++)] << (18 - j * 6);
            }

            for (int r = 16; d < len; r -= 8) {
                dArr[d++] = (byte)(i >> r);
            }
        }

        return dArr;
    }

    public static void main(String[] args) {
        String a = encode("1234567890".getBytes());
        System.out.println("a = " + a);
        System.out.println("b = " + decodeFast(a));

    }
    public static String encode(byte[] data) {
        int start = 0;
        int len = data.length;
        StringBuffer buf = new StringBuffer(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;

        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 0x0ff) << 8)
                    | (((int) data[i + 2]) & 0x0ff);

            buf.append(CA[(d >> 18) & 63]);
            buf.append(CA[(d >> 12) & 63]);
            buf.append(CA[(d >> 6) & 63]);
            buf.append(CA[d & 63]);

            i += 3;

            if (n++ >= 14) {
                n = 0;
                buf.append(" ");
            }
        }

        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 255) << 8);

            buf.append(CA[(d >> 18) & 63]);
            buf.append(CA[(d >> 12) & 63]);
            buf.append(CA[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(CA[(d >> 18) & 63]);
            buf.append(CA[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }

}
