package com.excilys.cdb.exception;

public class ComputerIsNullException extends Exception {

	private static final long serialVersionUID = 1L;

	public ComputerIsNullException() {
	}

	public ComputerIsNullException(String message) {
		super(message);
	}

	public ComputerIsNullException(Throwable cause) {
		super(cause);
	}

	public ComputerIsNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComputerIsNullException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
