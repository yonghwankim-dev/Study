package com.ch14.class03.step02;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuckListener {

	@PrePersist
	// 특정 타입이 확실하면 특정 타입을 받을 수 있다
	public void perPersist(Object obj){
		log.info("Duck.prePersist obj={}", obj);
	}

	@PostPersist
	public void postPersist(Object obj){
		log.info("Duck.postPersist obj={}", obj);
	}

	@PostLoad
	public void postLoad(Object o){
		log.info("Duck.postLoad");
	}

	@PostRemove
	public void preRemove(Object o){
		log.info("Duck.preRemove");
	}

	@PreUpdate
	public void preUpdate(Object o){
		log.info("Duck.preUpdate");
	}

	@PostUpdate
	public void postUpdate(Object o){
		log.info("Duck.postUpdate");
	}
}
