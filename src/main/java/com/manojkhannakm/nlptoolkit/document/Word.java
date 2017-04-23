package com.manojkhannakm.nlptoolkit.document;

/**
 * @author Manoj Khanna
 */

public class Word {

    private final String string;

    private String tag;

    public Word(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
