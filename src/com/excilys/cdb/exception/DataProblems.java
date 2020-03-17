package com.excilys.cdb.exception;

public class DataProblems extends Problems{

	public enum dataProblem{INVALID_DATES,NAME_IS_NULL,ID_NOT_FOUND}
	
	private final dataProblem dProb;
	
	private DataProblems(String origin, dataProblem dProb) {
		
		this.origin=origin;
		this.dProb=dProb;
		
	}

	public String getOrigin() {
		return origin;
	}

	public dataProblem getdProb() {
		return dProb;
	}
	
	public DataProblems createInvalidDatesProblem(String origin) {
		
		return new DataProblems(origin, dataProblem.INVALID_DATES);
		
	}
	
	public DataProblems createNameIsNullProblem(String origin) {
		
		return new DataProblems(origin, dataProblem.NAME_IS_NULL);
		
	}
	
	public DataProblems createIDNotFoundProblem(String origin) {
		
		return new DataProblems(origin, dataProblem.ID_NOT_FOUND);
		
	}
	
}
