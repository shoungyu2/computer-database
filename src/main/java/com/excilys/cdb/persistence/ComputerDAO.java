package com.excilys.cdb.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.QCompany;
import com.excilys.cdb.model.QComputer;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@EnableTransactionManagement
@Repository
public class ComputerDAO {

	@PersistenceContext
	private EntityManager entityManager;

	private JPAQuery<Computer> setOrderInQuery(JPAQuery<Computer> query, String filter, String order) {
		
		QComputer computer = QComputer.computer;
		
		if(filter==null) {
			filter="";
		}
		
		switch(filter) {
			
		case "name":
			if(order != null && order.equals("desc")) {
				return query.orderBy(computer.name.desc());
			}
			else {
				return query.orderBy(computer.name.asc());
			}
			
		case "introduced":
			if(order != null && order.equals("desc")) {
				return query.orderBy(computer.introductDate.desc());
			}
			else {
				return query.orderBy(computer.introductDate.asc());
			}
			
		case "discontinued":
			if(order != null && order.equals("desc")) {
				return query.orderBy(computer.discontinueDate.desc());
			}
			else {
				return query.orderBy(computer.discontinueDate.asc());
			}
			
		case "company_id":
			if(order != null && order.equals("desc")) {
				return query.orderBy(computer.company.name.desc());
			}
			else {
				return query.orderBy(computer.company.name.asc());
			}
			
		default:
			return query.orderBy(computer.id.asc());
		}
						
	}
	
	@Transactional
	public Optional<Computer> getComputerFromId(int id){
		
		JPAQuery<Computer> query= new JPAQuery<Computer>(entityManager);
		QComputer computer= QComputer.computer;
		QCompany company=QCompany.company;
		Computer comp= query.from(computer)
				.leftJoin(computer.company,company)
				.where(computer.id.eq(id))
				.fetchOne();
		return Optional.ofNullable(comp);
	}
	
	@Transactional
	public boolean createComputer(Computer c) {
		
		this.entityManager.persist(c);
		return true;
		
	}
	
	@Transactional
	public boolean updateComputer(Computer c) {
		
		JPAQueryFactory queryFactory= new JPAQueryFactory(entityManager);
		QComputer computer= QComputer.computer;
		long success = queryFactory.update(computer).where(computer.id.eq(c.getId()))
		.set(computer.name, c.getName())
		.set(computer.introductDate, c.getIntroductDate())
		.set(computer.discontinueDate, c.getDiscontinueDate())
		.set(computer.company, c.getCompany())
		.execute();
		
		return success==1;
		
	}
	
	@Transactional
	public boolean deleteComputer(int id) {
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputer computer = QComputer.computer;
		long success = queryFactory.delete(computer).where(computer.id.eq(id)).execute();
		
		return success==1;
		
	}
	
	@Transactional
	public long getNbrComputer(String search) {
		
		JPAQuery<Void> query = new JPAQuery<>(entityManager);
		QComputer computer = QComputer.computer;
		
		if(search==null) {
			search="";
		}
		
		return query.from(computer)
				.where(computer.name.like("%"+search+"%"))
				.fetchCount();
				
	}
	
	@Transactional
	public List<Computer> getComputers(String search, String filter, String order, Page page){
		
		List<Computer> listComp= new ArrayList<Computer>();
		
		JPAQuery<Computer> query =  new JPAQuery<Computer>(entityManager);
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		query = query.from(computer)
				.leftJoin(computer.company, company)
				.where(computer.name.contains(search))
				.limit(Page.getNbrElements())
				.offset(page.getOffset());
		
		query = setOrderInQuery(query, filter, order);
		
		listComp = query.fetch();
		
		return listComp;
		
	}
			
}
