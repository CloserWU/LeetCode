package com.wushuai.string;

import org.junit.Test;

import java.util.Arrays;

/**
 * <p>Compress</p>
 * <p>443</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-08 11:09
 */
public class Compress {
    @Test
    public void test1() {
        System.out.println(compress(new char[]{'a', 'a', 'b', 'b', 'b', 'c', 'd', 'd'}));
        System.out.println(compress(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}));
        System.out.println(compress(new char[]{'a', 'b', 'c', 'c', 'c', 'c', 'g', 'h'}));
        System.out.println(compress(new char[]{'a', 'a', 'a', 'c', 'b', 'b', 'g', 'h'}));
        System.out.println(compress(new char[]{'a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b'}));
    }

    /**
     * 同一数组既有读也有写时，设置write和read指针，write不要参与任何判断和修改，只进行arr[write++] 的操作
     * 用另外的指针来进行原数组的判断。
     * 这样代码逻辑条理清晰
     * @param chars
     * @return
     */
    public int compress(char[] chars) {
        int write = 0, anchor = 0;
        for (int read = 1; read <= chars.length; read++) {
            if (read == chars.length || chars[anchor] != chars[read]) {
                if (read - anchor == 1) {
                    chars[write++] = chars[anchor];
                } else {
                    chars[write++] = chars[anchor];
                    for (char c : ("" + (read - anchor)).toCharArray()) {
                        chars[write++] = c;
                    }
                }
                anchor = read;
            }
        }
        System.out.println(Arrays.toString(chars));
        return write;
    }
}

