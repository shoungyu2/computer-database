package com.excilys.cdb.model;

public class Page {

	private static int nbrElements=20;
	private final int offset;
	private final int numPage;
	private static int nbrPages;
	
	public Page(int numPage) {
		this.numPage=numPage;
		this.offset=Page.nbrElements*(this.numPage-1);
	}
	
	public int getNbrElements() {
		return nbrElements;
	}
	
	public void setNbrElements(int nbrElements) {
		Page.nbrElements = nbrElements;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int getNumPage() {
		return numPage;
	}

	public static int getNbrPages() {
		return nbrPages;
	}
	
	public static void setNbrPages(int nbrPages) {
		Page.nbrPages = nbrPages;
	}
	
	

	
	
	
	
}
