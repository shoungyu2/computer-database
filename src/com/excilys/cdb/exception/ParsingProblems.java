package com.excilys.cdb.exception;

public class ParsingProblems {

	public enum parsingProblems{NOT_A_DATE, NOT_AN_ID;}
	
	private final String origin;
	private final parsingProblems pProb;
	
	private ParsingProblems(String ori, parsingProblems pProb) {
		
		origin=ori;
		this.pProb=pProb;
		
	}

	public parsingProblems getpProb() {
		return pProb;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public ParsingProblems createNotADateProblem(String oringin) {
		return new ParsingProblems(origin, parsingProblems.NOT_A_DATE);
	}
	
	public ParsingProblems createNotAnIDProblem(String oringin) {
		return new ParsingProblems(origin, parsingProblems.NOT_AN_ID);
	}
	
}
