package com.excilys.cdb.exception;

public class DateInvalideException extends Exception {

	private static final long serialVersionUID = 1L;

	public DateInvalideException() {
	}

	public DateInvalideException(String message) {
		super(message);
	}

	public DateInvalideException(Throwable cause) {
		super(cause);
	}

	public DateInvalideException(String message, Throwable cause) {
		super(message, cause);
	}

	public DateInvalideException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
