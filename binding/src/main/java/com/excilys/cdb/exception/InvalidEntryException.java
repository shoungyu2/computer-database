package com.excilys.cdb.exception;

import java.util.List;

public class InvalidEntryException extends Exception {

	private static final long serialVersionUID = 1L;
	private List<Problems> listProb;
	
	public List<Problems> getListProb(){
		return this.listProb;
	}
	
	public InvalidEntryException() {
		// TODO Auto-generated constructor stub
	}
	
	public InvalidEntryException(List<Problems> listProb) {
		this.listProb=listProb;
	}

	public InvalidEntryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidEntryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidEntryException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidEntryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
