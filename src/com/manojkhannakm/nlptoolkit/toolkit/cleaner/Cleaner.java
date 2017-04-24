package com.manojkhannakm.nlptoolkit.toolkit.cleaner;

import com.manojkhannakm.nlptoolkit.toolkit.Toolkit;

/**
 * @author Manoj Khanna
 */

public abstract class Cleaner {

    protected final Toolkit toolkit;

    public Cleaner(Toolkit toolkit) {
        this.toolkit = toolkit;
    }

    public abstract void clean();

}
