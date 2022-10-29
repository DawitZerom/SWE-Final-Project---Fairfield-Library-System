package edu.miu.cs.cs425.fairfieldlibrarywebapp.exception;

public class BookCopyNotAvailableException extends Exception {
    public BookCopyNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
