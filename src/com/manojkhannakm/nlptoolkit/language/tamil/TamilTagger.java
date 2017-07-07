package com.manojkhannakm.nlptoolkit.language.tamil;

import com.manojkhannakm.nlptoolkit.document.Paragraph;
import com.manojkhannakm.nlptoolkit.document.Sentence;
import com.manojkhannakm.nlptoolkit.document.Word;
import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;
import com.manojkhannakm.nlptoolkit.toolkit.tagger.SimpleTagger;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.util.Properties;

/**
 * @author Manoj Khanna
 */

public class TamilTagger extends SimpleTagger {

    public TamilTagger(Toolkit toolkit) {
        super(toolkit);
    }

    @Override
    public void tag() { //TODO: Clear
        Properties properties = new Properties();
        properties.setProperty("verbose", "false");

        MaxentTagger maxentTagger = new MaxentTagger("model/tamil/tagger/tamil.tagger", properties);
        for (Paragraph paragraph : toolkit.getDocument().getParagraphList()) {
            for (Sentence sentence : paragraph.getSentenceList()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Word word : sentence.getWordList()) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(" ");
                    }

                    stringBuilder.append(word.getString());
                }

                String[] tokens = maxentTagger.tagTokenizedString(stringBuilder.toString()).split(" ");
                for (int i = 0; i < tokens.length; i++) {
                    sentence.getWordList().get(i).setTag(tokens[i].split("_")[1]);
                }
            }
        }
    }

}
