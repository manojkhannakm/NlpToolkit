package com.manojkhannakm.nlptoolkit.test;

import com.manojkhannakm.nlptoolkit.document.Document;
import com.manojkhannakm.nlptoolkit.document.Sentence;
import com.manojkhannakm.nlptoolkit.toolkit.cleaner.SimpleCleaner;
import com.manojkhannakm.nlptoolkit.NlpToolkit;
import com.manojkhannakm.nlptoolkit.document.Paragraph;
import com.manojkhannakm.nlptoolkit.document.Word;
import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;

import java.io.IOException;

/**
 * @author Manoj Khanna
 */

public class Test {

    public static void main(String[] args) throws IOException {
        NlpToolkit.Options options = new NlpToolkit.Options();
        options.setLanguage(NlpToolkit.Options.Language.TAMIL);
        options.setInput("in.txt");

        Toolkit.Options toolkitOptions = new Toolkit.Options();
        options.setToolkitOptions(toolkitOptions);

        SimpleCleaner.Options cleanerOptions = new SimpleCleaner.Options();
        cleanerOptions.setCleanBlankSpaces(true);
        cleanerOptions.setCleanLineBreaks(true);
        toolkitOptions.setCleanerOptions(cleanerOptions);

        NlpToolkit nlpToolkit = new NlpToolkit(options);
        nlpToolkit.clean();
        nlpToolkit.tokenize();
        nlpToolkit.tag();

        Document document = nlpToolkit.getDocument();

        System.out.println("Cleaned text: " + document.getCleanedCharsAsString());

        System.out.println("");

        System.out.println("Tokens: ");
        for (Paragraph paragraph : document.getParagraphList()) {
            for (Sentence sentence : paragraph.getSentenceList()) {
                for (Word word : sentence.getWordList()) {
                    System.out.println(word.getString());
                }
            }
        }

        System.out.println("");

        System.out.println("Tagged text");
        for (Paragraph paragraph : document.getParagraphList()) {
            for (Sentence sentence : paragraph.getSentenceList()) {
                for (Word word : sentence.getWordList()) {
                    System.out.print(word.getString() + "_" + word.getTag() + " ");
                }

                System.out.println("");
            }
        }
    }

}
