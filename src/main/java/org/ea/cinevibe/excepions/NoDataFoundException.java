package org.ea.cinevibe.excepions;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String elementName) {
        super(elementName + " cannot find!!!");
    }
}
