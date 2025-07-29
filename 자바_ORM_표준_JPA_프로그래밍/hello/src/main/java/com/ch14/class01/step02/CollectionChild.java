package com.ch14.class01.step02;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CollectionChild {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Parent parent;

	@Builder
	public CollectionChild(Long id, Parent parent) {
		this.id = id;
		this.parent = parent;
	}
}

@Service
class CollectionChildService{
	@Autowired
	private CollectionChildRepository repository;

	@Transactional
	public CollectionChild save(CollectionChild child){
		return repository.save(child);
	}
}

@Repository
class CollectionChildRepository{
	@PersistenceContext
	private EntityManager em;

	public CollectionChild save(CollectionChild child){
		em.persist(child);
		return child;
	}
}
