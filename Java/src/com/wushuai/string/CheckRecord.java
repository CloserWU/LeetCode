package com.wushuai.string;

import org.junit.Test;

/**
 * <p>CheckRecord</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-13 10:02
 */
public class CheckRecord {
    @Test
    public void test1() {
        System.out.println(checkRecord("PPALLP"));
        System.out.println(checkRecord("PPALLL"));
        System.out.println(checkRecord("PPALLPPPALLPPL"));
        System.out.println(checkRecord("PPALLPPPPLPPL"));
    }

    public boolean checkRecord(String s) {
        int index = 0, idx = 0;
        int count = 0;
        while ( (idx = s.indexOf("A", index)) != -1 ) {
            index += idx + 1;
            count++;
        }
        if (count > 1) {
            return false;
        }
        String[] split = s.replaceAll("[^L]", " ").trim().split(" ");
        for (String tmp: split) {
            if (tmp.length() > 2) {
                return false;
            }
        }
        return true;
    }
}

