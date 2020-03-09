package com.excilys.cdb.model;

import java.util.*;

import com.excilys.cdb.persistence.CompanieDAO;
import com.excilys.cdb.persistence.ComputerDAO;;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ComputerDAO cdao=new ComputerDAO();
		Computer c= new Computer.ComputerBuilder("iPhone 4S", 574).setIdEntreprise(12).build();
		ComputerDAO.updateComputer(c);
		ComputerDAO.showDetailComputerByID(574);
	}

}
