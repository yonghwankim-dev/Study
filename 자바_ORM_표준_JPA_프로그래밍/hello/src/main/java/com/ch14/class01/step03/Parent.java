package com.ch14.class01.step03;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@ToString(exclude = {"set"})
public class Parent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn
	private Set<SetChild> set = new HashSet<>();
}

@Slf4j
@Controller
class ParentController{
	@Autowired
	private ParentService parentService;

	public Parent save(Parent parent){
		return parentService.save(parent);
	}

	public boolean addSetChild(Long parentId, SetChild setChild){
		return parentService.addSetChild(parentId, setChild);
	}

	public boolean containsSetChild(Long parentId, SetChild setChild){
		return parentService.containsSetChild(parentId, setChild);
	}

	public boolean removeSetChild(Long parentId, SetChild setChild){
		return parentService.removeSetChild(parentId, setChild);
	}
}

@Slf4j
@Service
class ParentService{
	@Autowired
	private ParentRepository parentRepository;

	@Transactional
	public Parent save(Parent parent){
		return parentRepository.save(parent);
	}

	@Transactional
	public Parent merge(Parent parent) {
		return parentRepository.merge(parent);
	}

	@Transactional(readOnly = true)
	public Parent findOne(Long id) {
		return parentRepository.findOne(id);
	}

	@Transactional
	public boolean addSetChild(Long parentId, SetChild setChild) {
		Parent parent = parentRepository.findOne(parentId);
		return parent.getSet().add(setChild);
	}

	@Transactional
	public boolean containsSetChild(Long parentId, SetChild setChild) {
		Parent parent = parentRepository.findOne(parentId);
		return parent.getSet().contains(setChild);
	}

	@Transactional
	public boolean removeSetChild(Long parentId, SetChild setChild) {
		Parent parent = parentRepository.findOne(parentId);
		return parent.getSet().remove(setChild);
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

	public Parent merge(Parent parent) {
		em.merge(parent);
		return parent;
	}

	public Parent findOne(Long id) {
		return em.find(Parent.class, id);
	}
}
