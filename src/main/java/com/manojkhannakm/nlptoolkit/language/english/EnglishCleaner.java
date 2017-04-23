package com.manojkhannakm.nlptoolkit.language.english;

import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;
import com.manojkhannakm.nlptoolkit.toolkit.cleaner.SimpleCleaner;

import java.io.IOException;

/**
 * @author Manoj Khanna
 */

public class EnglishCleaner extends SimpleCleaner {

    public EnglishCleaner(Toolkit toolkit) {
        super(toolkit);
    }

    public static class Options extends SimpleCleaner.Options {

        public static final boolean DEFAULT_AMERICANIZE = true;

        private static final String AMERICANIZE_OPTION = "americanize";

        private boolean americanize;

        public Options() {
            americanize = DEFAULT_AMERICANIZE;
        }

        public Options(Options options) {
            super(options);

            americanize = options.americanize;
        }

        public Options(String fileName) throws IOException {
            super(fileName);
        }

        @Override
        protected void readFromMap(com.manojkhannakm.nlptoolkit.Options.Map map) {
            super.readFromMap(map);

            americanize = map.getBoolean(AMERICANIZE_OPTION, DEFAULT_AMERICANIZE);
        }

        @Override
        protected void writeToMap(com.manojkhannakm.nlptoolkit.Options.Map map) {
            super.writeToMap(map);

            map.setBoolean(AMERICANIZE_OPTION, americanize);
        }

        public boolean isAmericanize() {
            return americanize;
        }

        public void setAmericanize(boolean americanize) {
            this.americanize = americanize;
        }

    }

}
