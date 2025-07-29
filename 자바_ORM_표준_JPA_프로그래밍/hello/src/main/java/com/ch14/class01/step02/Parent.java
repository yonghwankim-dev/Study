package com.ch14.class01.step02;

import java.beans.beancontext.BeanContextServicesSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = {"collection", "list"})
public class Parent {
	@Id
	@GeneratedValue
	private Long id;

	@OneToMany
	@JoinColumn
	private Collection<CollectionChild> collection = new ArrayList<>();

	@OneToMany
	@JoinColumn
	private List<ListChild> list = new ArrayList<>();
}

@Service
class ParentService{
	@Autowired
	private ParentRepository parentRepository;

	@Transactional
	public Parent save(Parent parent){
		return parentRepository.save(parent);
	}
}

@Repository
class ParentRepository{
	@PersistenceContext
	private EntityManager em;

	public Parent save(Parent parent){
		em.persist(parent);
		return parent;
	}
}
