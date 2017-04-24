package com.manojkhannakm.nlptoolkit.toolkit;

import java.util.HashSet;

/**
 * @author Manoj Khanna
 */

public class Charset {

    private final String[] chars;
    private final HashSet<String> charSet;

    public Charset(String[] chars) {
        this.chars = chars;

        charSet = new HashSet<>(chars.length);
        for (String c : chars) {
            charSet.add(c);
        }
    }

    public boolean contains(String c) {
        return charSet.contains(c);
    }

    public String[] getChars() {
        return chars;
    }

}
