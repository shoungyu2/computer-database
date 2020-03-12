package com.excilys.cdb.exception;

import java.util.List;

public class InvalidEntryException extends Exception {

	private static final long serialVersionUID = 1L;
	private static List<Exception> listExp;
	
	
	public InvalidEntryException() {
	}

	public InvalidEntryException(List<Exception> le) {
		listExp=le;
	}
	
	public InvalidEntryException(String message) {
		super(message);
	}

	public InvalidEntryException(Throwable cause) {
		super(cause);
	}

	public InvalidEntryException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEntryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public List<Exception> getListExp(){
		return listExp;
	}

}
