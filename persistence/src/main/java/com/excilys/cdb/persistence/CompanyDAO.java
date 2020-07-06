package com.excilys.cdb.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.QCompany;
import com.excilys.cdb.model.QComputer;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CompanyDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public List<Company> listCompany(){
		
		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		QCompany company = QCompany.company;
		
		return query.from(company).fetch();
		
	}
	
	@Transactional
	public Optional<Company> showDetailCompany(int id){
		
		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		QCompany company = QCompany.company;
		
		Company comp = query.from(company)
				.where(company.id.eq(id))
				.fetchOne();
		
		return Optional.ofNullable(comp);
		
	}
	
	@Transactional
	public void deleteCompany(int id) {
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QCompany company = QCompany.company;
		QComputer computer = QComputer.computer;
		
		queryFactory.delete(computer)
				.where(computer.company.id.eq(id)).execute();
		
		queryFactory.delete(company)
				.where(company.id.eq(id)).execute();
		
	}
	
}
