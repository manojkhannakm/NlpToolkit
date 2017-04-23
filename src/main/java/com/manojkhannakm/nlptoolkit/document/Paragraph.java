package com.manojkhannakm.nlptoolkit.document;

import java.util.ArrayList;

/**
 * @author Manoj Khanna
 */

public class Paragraph {

    private final ArrayList<Sentence> sentenceList = new ArrayList<>();

    public ArrayList<Sentence> getSentenceList() {
        return sentenceList;
    }

}
