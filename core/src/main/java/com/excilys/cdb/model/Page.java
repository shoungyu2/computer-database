package com.excilys.cdb.model;

public class Page {

	private static int nbrElements=20;
	private final int offset;
	private final int numPage;
	private static long nbrPages;
	
	public Page(int numPage) {
		this.numPage=numPage;
		this.offset=Page.nbrElements*(this.numPage-1);
	}
	
	public static int getNbrElements() {
		return nbrElements;
	}
	
	public static void setNbrElements(int nbrElements) {
		Page.nbrElements = nbrElements;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int getNumPage() {
		return numPage;
	}

	public static long getNbrPages() {
		return nbrPages;
	}
	
	public static void setNbrPages(long nbrPages2) {
		Page.nbrPages = nbrPages2;
	}
	
	

	
	
	
	
}
