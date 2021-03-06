package com.excilys.cdb.exception;

public class NameIsNullException extends Exception {

	private static final long serialVersionUID = 1L;

	public NameIsNullException() {
	}

	public NameIsNullException(String message) {
		super(message);
	}

	public NameIsNullException(Throwable cause) {
		super(cause);
	}

	public NameIsNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public NameIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
