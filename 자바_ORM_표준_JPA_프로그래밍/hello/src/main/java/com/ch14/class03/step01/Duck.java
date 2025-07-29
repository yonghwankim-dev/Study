package com.ch14.class03.step01;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Duck {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	public Duck(String name) {
		this.name = name;
	}

	@PrePersist
	public void perPersist(){
		log.info("Duck.prePersist id={}", id);
	}

	@PostPersist
	public void postPersist(){
		log.info("Duck.postPersist id={}", id);
	}

	@PostLoad
	public void postLoad(){
		log.info("Duck.postLoad");
	}

	@PostRemove
	public void preRemove(){
		log.info("Duck.preRemove");
	}

	@PreUpdate
	public void preUpdate(){
		log.info("Duck.preUpdate");
	}

	@PostUpdate
	public void postUpdate(){
		log.info("Duck.postUpdate");
	}
}
