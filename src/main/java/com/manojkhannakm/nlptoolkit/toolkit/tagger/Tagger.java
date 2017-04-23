package com.manojkhannakm.nlptoolkit.toolkit.tagger;

import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;

/**
 * @author Manoj Khanna
 */

public abstract class Tagger {

    protected final Toolkit toolkit;

    public Tagger(Toolkit toolkit) {
        this.toolkit = toolkit;
    }

    public abstract void tag();

}
