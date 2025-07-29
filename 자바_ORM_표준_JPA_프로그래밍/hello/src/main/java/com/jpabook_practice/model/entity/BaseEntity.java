package com.jpabook_practice.model.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
	private Date createdDate; // 등록일
	private Date lastModifiedDate; // 수정일
}
