package com.manojkhannakm.nlptoolkit.toolkit;

import com.manojkhannakm.nlptoolkit.document.Document;
import com.manojkhannakm.nlptoolkit.toolkit.cleaner.Cleaner;
import com.manojkhannakm.nlptoolkit.toolkit.cleaner.SimpleCleaner;
import com.manojkhannakm.nlptoolkit.toolkit.tagger.SimpleTagger;
import com.manojkhannakm.nlptoolkit.toolkit.tagger.Tagger;
import com.manojkhannakm.nlptoolkit.toolkit.tokenizer.SimpleTokenizer;
import com.manojkhannakm.nlptoolkit.toolkit.tokenizer.Tokenizer;
import com.manojkhannakm.nlptoolkit.NlpToolkit;

import java.io.IOException;

/**
 * @author Manoj Khanna
 */

public abstract class Toolkit {

    private final NlpToolkit nlpToolkit;

    private Cleaner cleaner;
    private Tokenizer tokenizer;
    private Tagger tagger;

    public Toolkit(NlpToolkit nlpToolkit) {
        this.nlpToolkit = nlpToolkit;
    }

    public abstract Charset getCharset();

    public Cleaner getCleaner() {
        if (cleaner == null) {
            cleaner = new SimpleCleaner(this);
        }

        return cleaner;
    }

    public Tokenizer getTokenizer() {
        if (tokenizer == null) {
            tokenizer = new SimpleTokenizer(this);
        }

        return tokenizer;
    }

    public Tagger getTagger() {
        if (tagger == null) {
            tagger = new SimpleTagger(this);
        }

        return tagger;
    }

    public Options getOptions() {
        return nlpToolkit.getOptions().getToolkitOptions();
    }

    public Document getDocument() {
        return nlpToolkit.getDocument();
    }

    public static class Options extends com.manojkhannakm.nlptoolkit.Options {

        public static final boolean DEFAULT_CLEANER_ENABLED = true;

        private static final String CLEANER_ENABLED_OPTION = "cleaner_enabled";

        private boolean cleanerEnabled;
        private SimpleCleaner.Options cleanerOptions;
        private SimpleTokenizer.Options tokenizerOptions;

        public Options() {
            cleanerEnabled = DEFAULT_CLEANER_ENABLED;
            cleanerOptions = new SimpleCleaner.Options();
            tokenizerOptions = new SimpleTokenizer.Options();
        }

        public Options(Options options) {
            super(options);

            cleanerEnabled = options.cleanerEnabled;
            cleanerOptions = new SimpleCleaner.Options(options.cleanerOptions);
            tokenizerOptions = new SimpleTokenizer.Options(options.tokenizerOptions);
        }

        public Options(String fileName) throws IOException {
            super(fileName);
        }

        @Override
        protected void readFromMap(Map map) {
            cleanerEnabled = map.getBoolean(CLEANER_ENABLED_OPTION, DEFAULT_CLEANER_ENABLED);

            cleanerOptions = new SimpleCleaner.Options();
            map.getOptions(cleanerOptions);

            tokenizerOptions = new SimpleTokenizer.Options();
            map.getOptions(tokenizerOptions);
        }

        @Override
        protected void writeToMap(Map map) {
            map.setBoolean(CLEANER_ENABLED_OPTION, cleanerEnabled);
            map.setOptions(cleanerOptions);
            map.setOptions(tokenizerOptions);
        }

        public boolean isCleanerEnabled() {
            return cleanerEnabled;
        }

        public void setCleanerEnabled(boolean cleanerEnabled) {
            this.cleanerEnabled = cleanerEnabled;
        }

        public SimpleCleaner.Options getCleanerOptions() {
            return cleanerOptions;
        }

        public void setCleanerOptions(SimpleCleaner.Options cleanerOptions) {
            if (cleanerOptions == null) {
                cleanerOptions = new SimpleCleaner.Options();
            }

            this.cleanerOptions = cleanerOptions;
        }

        public SimpleTokenizer.Options getTokenizerOptions() {
            return tokenizerOptions;
        }

        public void setTokenizerOptions(SimpleTokenizer.Options tokenizerOptions) {
            if (tokenizerOptions == null) {
                tokenizerOptions = new SimpleTokenizer.Options();
            }

            this.tokenizerOptions = tokenizerOptions;
        }

    }

}
