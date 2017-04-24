package com.manojkhannakm.nlptoolkit.document;

import java.util.ArrayList;

/**
 * @author Manoj Khanna
 */

public class Document {

    private final char[] chars;

    private char[] cleanedChars;
    private ArrayList<Paragraph> paragraphList;

    public Document(String input) {
        chars = input.toCharArray();
    }

    public boolean isCleaned() {
        return cleanedChars != null;
    }

    public boolean isTokenized() {
        return paragraphList != null;
    }

    public boolean isTagged() {
        return false;
    }

    public char[] getChars() {
        return chars;
    }

    public String getTextAsString() {
        return new String(chars);
    }

    public char[] getCleanedChars() {
        return cleanedChars;
    }

    public void setCleanedChars(char[] cleanedChars) {
        this.cleanedChars = cleanedChars;
    }

    public String getCleanedCharsAsString() {
        return new String(cleanedChars);
    }

    public ArrayList<Paragraph> getParagraphList() {
        return paragraphList;
    }

    public void setParagraphList(ArrayList<Paragraph> paragraphList) {
        this.paragraphList = paragraphList;
    }

}
