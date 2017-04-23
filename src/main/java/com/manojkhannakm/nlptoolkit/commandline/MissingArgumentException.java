package com.manojkhannakm.nlptoolkit.commandline;

/**
 * @author Manoj Khanna
 */

public class MissingArgumentException extends RuntimeException {

    public MissingArgumentException(String message) {
        super(message);
    }

}
