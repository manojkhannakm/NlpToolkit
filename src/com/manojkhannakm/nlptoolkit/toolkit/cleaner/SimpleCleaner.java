package com.manojkhannakm.nlptoolkit.toolkit.cleaner;

import com.manojkhannakm.nlptoolkit.document.Document;
import com.manojkhannakm.nlptoolkit.util.StringUtils;
import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Manoj Khanna
 */

public class SimpleCleaner extends Cleaner {

    private static final char[] NUMBER_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] COMMON_SYMBOL_CHARS = new char[]{
            '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*',
            '+', ',', '-', '.', '/', ':', ';', '<', '=', '>',
            '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|',
            '}', '~'
    };
    private static final char[] UNCOMMON_SYMBOL_CHARS = new char[]{};

    public SimpleCleaner(Toolkit toolkit) {
        super(toolkit);
    }

    @Override
    public void clean() {
        Document document = toolkit.getDocument();
        char[] chars = document.getChars();

        Toolkit.Options toolkitOptions = toolkit.getOptions();
        if (!toolkitOptions.isCleanerEnabled()) {
            document.setCleanedChars(Arrays.copyOf(chars, chars.length));
            return;
        }

        Options options = toolkitOptions.getCleanerOptions();
        if (!options.isCleanBlankSpaces()
                && !options.isCleanLineBreaks()
                && !options.isCleanNumbers()
                && !options.isCleanCommonSymbols()
                && !options.isCleanUncommonSymbols()
                && !options.isCleanUrls()
                && !options.isCleanEmails()
                && !options.isCleanOtherLanguages()
                && !options.isReplaceUncommonSymbols()) {
            document.setCleanedChars(Arrays.copyOf(chars, chars.length));
            return;
        }

        char[] cleanedChars = new char[chars.length];
        int l = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (options.isReplaceUncommonSymbols()) {   //TODO: Finish

            }

            if (StringUtils.isDelimiter(c)) {
                if (StringUtils.isBlankSpace(c) && options.isCleanBlankSpaces()
                        || StringUtils.isLineBreak(c) && options.isCleanLineBreaks()) {
                    if (l == 0 || cleanedChars[l - 1] != ' ') {
                        cleanedChars[l++] = ' ';
                    }
                } else {
                    cleanedChars[l++] = c;
                }
            } else {
                int j = i;
                while (j + 1 < chars.length && !StringUtils.isDelimiter(chars[j + 1])) {
                    j++;
                }

                if (StringUtils.isUrl(chars, i, j)) {
                    if (options.isCleanUrls()) {
                        for (int k = i; k <= j; k++) {
                            cleanedChars[l++] = chars[k];
                        }
                    }
                } else if (StringUtils.isEmail(chars, i, j)) {
                    if (options.isCleanEmails()) {
                        for (int k = i; k <= j; k++) {
                            cleanedChars[l++] = chars[k];
                        }
                    }
                } else {
                    for (int k = i; k <= j; k++) {
                        boolean clean = false;
                        if (options.isCleanNumbers()) {
                            for (char numberChar : NUMBER_CHARS) {
                                if (numberChar == chars[k]) {
                                    clean = true;
                                    break;
                                }
                            }
                        }

                        if (options.isCleanCommonSymbols()) {
                            for (char commonSymbolChar : COMMON_SYMBOL_CHARS) {
                                if (commonSymbolChar == chars[k]) {
                                    clean = true;
                                    break;
                                }
                            }
                        }

                        if (options.isCleanUncommonSymbols()) {
                            for (char uncommonSymbolChar : UNCOMMON_SYMBOL_CHARS) {
                                if (uncommonSymbolChar == chars[k]) {
                                    clean = true;
                                    break;
                                }
                            }
                        }

                        if (options.isCleanOtherLanguages()) {  //TODO: Finish

                        }

                        if (!clean) {
                            cleanedChars[l++] = chars[k];
                        }
                    }
                }

                i = j;
            }
        }

        document.setCleanedChars(Arrays.copyOf(cleanedChars, l));
    }

    public static class Options extends com.manojkhannakm.nlptoolkit.Options {

        public static final boolean DEFAULT_CLEAN_BLANK_SPACES = true;
        public static final boolean DEFAULT_CLEAN_LINE_BREAKS = false;
        public static final boolean DEFAULT_CLEAN_NUMBERS = false;
        public static final boolean DEFAULT_CLEAN_COMMON_SYMBOLS = false;
        public static final boolean DEFAULT_CLEAN_UNCOMMON_SYMBOLS = true;
        public static final boolean DEFAULT_CLEAN_URLS = false;
        public static final boolean DEFAULT_CLEAN_EMAILS = false;
        public static final boolean DEFAULT_CLEAN_OTHER_LANGUAGES = false;
        public static final boolean DEFAULT_REPLACE_UNCOMMON_SYMBOLS = true;

        private static final String CLEAN_BLANK_SPACES_OPTION = "clean_blank_spaces";
        private static final String CLEAN_LINE_BREAKS_OPTION = "clean_line_breaks";
        private static final String CLEAN_NUMBERS_OPTION = "clean_numbers";
        private static final String CLEAN_COMMON_SYMBOLS_OPTION = "clean_common_symbols";
        private static final String CLEAN_UNCOMMON_SYMBOLS_OPTION = "clean_uncommon_symbols";
        private static final String CLEAN_URLS_OPTION = "clean_urls";
        private static final String CLEAN_EMAILS_OPTION = "clean_emails";
        private static final String CLEAN_OTHER_LANGUAGES_OPTION = "clean_other_languages";
        private static final String REPLACE_UNCOMMON_SYMBOLS_OPTION = "replace_uncommon_symbols";

        private boolean cleanBlankSpaces, cleanLineBreaks, cleanNumbers, cleanCommonSymbols, cleanUncommonSymbols, cleanUrls,
                cleanEmails, cleanOtherLanguages, replaceUncommonSymbols;

        public Options() {
            cleanBlankSpaces = DEFAULT_CLEAN_BLANK_SPACES;
            cleanLineBreaks = DEFAULT_CLEAN_LINE_BREAKS;
            cleanNumbers = DEFAULT_CLEAN_NUMBERS;
            cleanCommonSymbols = DEFAULT_CLEAN_COMMON_SYMBOLS;
            cleanUncommonSymbols = DEFAULT_CLEAN_UNCOMMON_SYMBOLS;
            cleanUrls = DEFAULT_CLEAN_URLS;
            cleanEmails = DEFAULT_CLEAN_EMAILS;
            cleanOtherLanguages = DEFAULT_CLEAN_OTHER_LANGUAGES;
            replaceUncommonSymbols = DEFAULT_REPLACE_UNCOMMON_SYMBOLS;
        }

        public Options(Options options) {
            super(options);

            cleanBlankSpaces = options.cleanBlankSpaces;
            cleanLineBreaks = options.cleanLineBreaks;
            cleanNumbers = options.cleanNumbers;
            cleanCommonSymbols = options.cleanCommonSymbols;
            cleanUncommonSymbols = options.cleanUncommonSymbols;
            cleanUrls = options.cleanUrls;
            cleanEmails = options.cleanEmails;
            cleanOtherLanguages = options.cleanOtherLanguages;
            replaceUncommonSymbols = options.replaceUncommonSymbols;
        }

        public Options(String fileName) throws IOException {
            super(fileName);
        }

        @Override
        protected void readFromMap(Map map) {
            cleanBlankSpaces = map.getBoolean(CLEAN_BLANK_SPACES_OPTION, DEFAULT_CLEAN_BLANK_SPACES);
            cleanLineBreaks = map.getBoolean(CLEAN_LINE_BREAKS_OPTION, DEFAULT_CLEAN_LINE_BREAKS);
            cleanNumbers = map.getBoolean(CLEAN_NUMBERS_OPTION, DEFAULT_CLEAN_NUMBERS);
            cleanCommonSymbols = map.getBoolean(CLEAN_COMMON_SYMBOLS_OPTION, DEFAULT_CLEAN_COMMON_SYMBOLS);
            cleanUncommonSymbols = map.getBoolean(CLEAN_UNCOMMON_SYMBOLS_OPTION, DEFAULT_CLEAN_UNCOMMON_SYMBOLS);
            cleanUrls = map.getBoolean(CLEAN_URLS_OPTION, DEFAULT_CLEAN_URLS);
            cleanEmails = map.getBoolean(CLEAN_EMAILS_OPTION, DEFAULT_CLEAN_EMAILS);
            cleanOtherLanguages = map.getBoolean(CLEAN_OTHER_LANGUAGES_OPTION, DEFAULT_CLEAN_OTHER_LANGUAGES);
            replaceUncommonSymbols = map.getBoolean(REPLACE_UNCOMMON_SYMBOLS_OPTION, DEFAULT_REPLACE_UNCOMMON_SYMBOLS);
        }

        @Override
        protected void writeToMap(Map map) {
            map.setBoolean(CLEAN_BLANK_SPACES_OPTION, cleanBlankSpaces);
            map.setBoolean(CLEAN_LINE_BREAKS_OPTION, cleanLineBreaks);
            map.setBoolean(CLEAN_NUMBERS_OPTION, cleanNumbers);
            map.setBoolean(CLEAN_COMMON_SYMBOLS_OPTION, cleanCommonSymbols);
            map.setBoolean(CLEAN_UNCOMMON_SYMBOLS_OPTION, cleanUncommonSymbols);
            map.setBoolean(CLEAN_URLS_OPTION, cleanUrls);
            map.setBoolean(CLEAN_EMAILS_OPTION, cleanEmails);
            map.setBoolean(CLEAN_OTHER_LANGUAGES_OPTION, cleanOtherLanguages);
            map.setBoolean(REPLACE_UNCOMMON_SYMBOLS_OPTION, replaceUncommonSymbols);
        }

        public boolean isCleanBlankSpaces() {
            return cleanBlankSpaces;
        }

        public void setCleanBlankSpaces(boolean cleanBlankSpaces) {
            this.cleanBlankSpaces = cleanBlankSpaces;
        }

        public boolean isCleanLineBreaks() {
            return cleanLineBreaks;
        }

        public void setCleanLineBreaks(boolean cleanLineBreaks) {
            this.cleanLineBreaks = cleanLineBreaks;
        }

        public boolean isCleanNumbers() {
            return cleanNumbers;
        }

        public void setCleanNumbers(boolean cleanNumbers) {
            this.cleanNumbers = cleanNumbers;
        }

        public boolean isCleanCommonSymbols() {
            return cleanCommonSymbols;
        }

        public void setCleanCommonSymbols(boolean cleanCommonSymbols) {
            this.cleanCommonSymbols = cleanCommonSymbols;
        }

        public boolean isCleanUncommonSymbols() {
            return cleanUncommonSymbols;
        }

        public void setCleanUncommonSymbols(boolean cleanUncommonSymbols) {
            this.cleanUncommonSymbols = cleanUncommonSymbols;
        }

        public boolean isCleanUrls() {
            return cleanUrls;
        }

        public void setCleanUrls(boolean cleanUrls) {
            this.cleanUrls = cleanUrls;
        }

        public boolean isCleanEmails() {
            return cleanEmails;
        }

        public void setCleanEmails(boolean cleanEmails) {
            this.cleanEmails = cleanEmails;
        }

        public boolean isCleanOtherLanguages() {
            return cleanOtherLanguages;
        }

        public void setCleanOtherLanguages(boolean cleanOtherLanguages) {
            this.cleanOtherLanguages = cleanOtherLanguages;
        }

        public boolean isReplaceUncommonSymbols() {
            return replaceUncommonSymbols;
        }

        public void setReplaceUncommonSymbols(boolean replaceUncommonSymbols) {
            this.replaceUncommonSymbols = replaceUncommonSymbols;
        }

    }

}
