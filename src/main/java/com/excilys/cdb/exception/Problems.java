package com.excilys.cdb.exception;

public class Problems {

	public enum dataProblems{
		NOT_A_DATE("Not a date"), 
		NOT_AN_ID("Not an ID"),
		INVALID_DATES("Intro Date must be before disc Date"),
		OUT_OF_BOUND_DATES("This date is either too low or too high"),
		NAME_IS_NULL("Name can't be null"),
		ID_NOT_FOUND("ID not found");
		
		private String message;
		
		private dataProblems(String message) {
			this.message=message;
		}
		
		@Override
		public String toString() {
			return this.message;
		}
		
	};
	
	private dataProblems dp;
	private String origin;
	
	private Problems(String origin, dataProblems dp) {
		
		this.dp=dp;
		this.origin=origin;
	
	}
	
	public dataProblems getDP() {
		return this.dp;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public static Problems createNotADateProblem(String origin) {
	
		return new Problems(origin, dataProblems.NOT_A_DATE);
	
	}
	
	public static Problems createNotAnIDProblem(String origin) {
		
		return new Problems(origin, dataProblems.NOT_AN_ID);
	
	}
	
	public static Problems createInvalidDatesProblem(String origin) {
		
		return new Problems(origin, dataProblems.INVALID_DATES);
		
	}
	
	public static Problems createNameIsNullProblem(String origin) {
		
		return new Problems(origin, dataProblems.NAME_IS_NULL);
		
	}
	
	public static Problems createIDNotFoundProblem(String origin) {
		
		return new Problems(origin, dataProblems.ID_NOT_FOUND);
		
	}
	
	public static Problems createDateOutOfBoundsProblem(String origin) {
		
		return new Problems(origin, dataProblems.OUT_OF_BOUND_DATES);
		
	}
	
	@Override
	public String toString() {
		return "Origin: "+this.origin
				+" Problem: "+this.dp.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Problems))
			return false;
		Problems other = (Problems) obj;
		if (dp != other.dp)
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		return true;
	}
	
	
}
