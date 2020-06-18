package com.excilys.cdb.persistence;

public enum AllComputerQuery {

	INSERT_COMPUTER("INSERT INTO computer "
			+ "(name,introduced,discontinued, company_id) VALUES (?,?,?,?)") ,
	SELECT_ALL_COMPUTER(
			"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " ORDER BY computer.id LIMIT ? OFFSET ?" ) ,
	SELECT_COMPUTER(
			"SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name"
			+ " FROM computer LEFT JOIN company ON company_id(company.id"
			+ " WHERE computer.id=?") ,
	UPDATE_COMPUTER(
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?") ,
	DELETE_COMPUTER("DELETE FROM computer WHERE id=?") ,
	SEARCH_COMPUTER(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.name LIKE ? ORDER BY computer.id LIMIT ? OFFSET ?") ,
	GET_NBR_COMPUTER(
			"SELECT COUNT(*) FROM computer "
			+ " WHERE computer.name LIKE ? ") ,
	ORDER_BY_COMPUTER_NAME_ASC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " ORDER BY computer.name LIMIT ? OFFSET ?") ,
	ORDER_BY_COMPUTER_NAME_DESC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " ORDER BY computer.name DESC LIMIT ? OFFSET ?") ,
	ORDER_BY_COMPANY_NAME_ASC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " ORDER BY company.name LIMIT ? OFFSET ?") ,
	ORDER_BY_COMPANY_NAME_DESC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " ORDER BY company.name DESC LIMIT ? OFFSET ?") ,
	ORDER_BY_INTRODUCED_ASC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " ORDER BY computer.introduced LIMIT ? OFFSET ?") ,
	ORDER_BY_INTRODUCED_DESC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " ORDER BY computer.introduced DESC LIMIT ? OFFSET ?") ,
	ORDER_BY_DISCONTINUED_ASC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " ORDER BY computer.discontinued LIMIT ? OFFSET ?") ,
	ORDER_BY_DISCONTINUED_DESC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " ORDER BY computer.discontinued DESC LIMIT ? OFFSET ?") ,
	ORDER_BY_COMPUTER_NAME_WITH_SEARCH_ASC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.name LIKE ? ORDER BY computer.name LIMIT ? OFFSET ?") ,
	ORDER_BY_COMPUTER_NAME_WITH_SEARCH_DESC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.name LIKE ? ORDER BY computer.name DESC LIMIT ? OFFSET ?") ,
	ORDER_BY_INTRODUCED_WITH_SEARCH_ASC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.name LIKE ? ORDER BY computer.introduced LIMIT ? OFFSET ?") ,
	ORDER_BY_INTRODUCED_WITH_SEARCH_DESC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.name LIKE ? ORDER BY computer.introduced DESC LIMIT ? OFFSET ?") ,
	ORDER_BY_DISCONTINUED_WITH_SEARCH_ASC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.name LIKE ? ORDER BY computer.discontinued LIMIT ? OFFSET ?") ,
	ORDER_BY_DISCONTINUED_WITH_SEARCH_DESC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.name LIKE ? ORDER BY computer.discontinued DESC LIMIT ? OFFSET ?") ,
	ORDER_BY_COMPANY_NAME_WITH_SEARCH_ASC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.name LIKE ? ORDER BY company.name LIMIT ? OFFSET ?") ,
	ORDER_BY_COMPANY_NAME_WITH_SEARCH_DESC(
			"SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name"
			+ " FROM computer LEFT JOIN company ON company_id=company.id"
			+ " WHERE computer.name LIKE ? ORDER BY company.name DESC LIMIT ? OFFSET ?");

	private String query;
	
	private AllComputerQuery(String query) {
		this.query=query;
	}
	
	public String getQuery() {
		return this.query;
	}
	
}
