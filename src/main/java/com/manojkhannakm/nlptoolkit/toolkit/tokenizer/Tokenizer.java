package com.manojkhannakm.nlptoolkit.toolkit.tokenizer;

import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;

/**
 * @author Manoj Khanna
 */

public abstract class Tokenizer {

    protected final Toolkit toolkit;

    public Tokenizer(Toolkit toolkit) {
        this.toolkit = toolkit;
    }

    public abstract void tokenize();

}
