package mhashim6.sadb.exceptions;

import java.io.IOException;

/**
 * @author mhashim6
 */
@SuppressWarnings("serial")
public class FileFailException extends IOException {
    private String fileName;

    public FileFailException(String fileName) {
        this.fileName = fileName;
    }

    public String string() {
        return "could not cerate : " + fileName + " file";

    }

}
