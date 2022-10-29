package edu.miu.cs.cs425.fairfieldlibrarywebapp.exception;

public class MemberCannotCheckoutException extends Exception {
    public MemberCannotCheckoutException(String errorMessage) {
        super(errorMessage);
    }
}
