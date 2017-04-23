package com.manojkhannakm.nlptoolkit.toolkit.tokenizer;

import com.manojkhannakm.nlptoolkit.document.Document;
import com.manojkhannakm.nlptoolkit.document.Sentence;
import com.manojkhannakm.nlptoolkit.document.Paragraph;
import com.manojkhannakm.nlptoolkit.document.Word;
import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Manoj Khanna
 */

public class SimpleTokenizer extends Tokenizer {

    public SimpleTokenizer(Toolkit toolkit) {
        super(toolkit);
    }

    @Override
    public void tokenize() {    //TODO: Clear
        Document document = toolkit.getDocument();

        ArrayList<Paragraph> paragraphList = new ArrayList<>();
        document.setParagraphList(paragraphList);

        for (String paragraphString : document.getCleanedCharsAsString().split("[\n\r]+")) {
            Paragraph paragraph = new Paragraph();
            paragraphList.add(paragraph);

            for (String sentenceString : paragraphString.split("(?<=[\\.\\?!][ \n\r])")) {
                Sentence sentence = new Sentence();
                paragraph.getSentenceList().add(sentence);

                for (String wordString : sentenceString.split(" ")) {
                    sentence.getWordList().add(new Word(wordString));
                }
            }
        }
    }

    public static class Options extends com.manojkhannakm.nlptoolkit.Options {

        public static final boolean DEFAULT_TOKENIZE_URLS = true;
        public static final boolean DEFAULT_TOKENIZE_EMAILS = true;

        private static final String TOKENIZE_URLS_OPTION = "tokenize_urls";
        private static final String TOKENIZE_EMAILS_OPTION = "tokenize_emails";

        private boolean tokenizeUrls, tokenizeEmails;

        public Options() {
            tokenizeUrls = DEFAULT_TOKENIZE_URLS;
            tokenizeEmails = DEFAULT_TOKENIZE_EMAILS;
        }

        public Options(Options options) {
            super(options);

            tokenizeUrls = options.tokenizeUrls;
            tokenizeEmails = options.tokenizeEmails;
        }

        public Options(String fileName) throws IOException {
            super(fileName);
        }

        @Override
        protected void readFromMap(Map map) {
            tokenizeUrls = map.getBoolean(TOKENIZE_URLS_OPTION, DEFAULT_TOKENIZE_URLS);
            tokenizeEmails = map.getBoolean(TOKENIZE_EMAILS_OPTION, DEFAULT_TOKENIZE_EMAILS);
        }

        @Override
        protected void writeToMap(Map map) {
            map.setBoolean(TOKENIZE_URLS_OPTION, tokenizeUrls);
            map.setBoolean(TOKENIZE_EMAILS_OPTION, tokenizeEmails);
        }

        public boolean isTokenizeUrls() {
            return tokenizeUrls;
        }

        public void setTokenizeUrls(boolean tokenizeUrls) {
            this.tokenizeUrls = tokenizeUrls;
        }

        public boolean isTokenizeEmails() {
            return tokenizeEmails;
        }

        public void setTokenizeEmails(boolean tokenizeEmails) {
            this.tokenizeEmails = tokenizeEmails;
        }

    }

}
