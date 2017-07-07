package com.manojkhannakm.nlptoolkit.language.english;

import com.manojkhannakm.nlptoolkit.NlpToolkit;
import com.manojkhannakm.nlptoolkit.toolkit.Charset;
import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;
import com.manojkhannakm.nlptoolkit.toolkit.cleaner.Cleaner;

/**
 * @author Manoj Khanna
 */

public class EnglishToolkit extends Toolkit {

    private static final String[] CHARS = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    private Charset charset;
    private EnglishCleaner englishCleaner;

    public EnglishToolkit(NlpToolkit nlpToolkit) {
        super(nlpToolkit);
    }

    @Override
    public Charset getCharset() {
        if (charset == null) {
            charset = new Charset(CHARS);
        }

        return charset;
    }

    @Override
    public Cleaner getCleaner() {
        if (englishCleaner == null) {
            englishCleaner = new EnglishCleaner(this);
        }

        return englishCleaner;
    }

}
