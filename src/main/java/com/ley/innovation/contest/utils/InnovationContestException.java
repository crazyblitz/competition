package com.ley.innovation.contest.utils;

/**
 * innovation contest exception
 **/
public class InnovationContestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * error code
     **/
    private String errorCode;

    public InnovationContestException(String message) {
        this("-1", message);
    }

    public InnovationContestException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
