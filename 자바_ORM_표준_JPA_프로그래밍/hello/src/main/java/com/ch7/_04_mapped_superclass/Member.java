package com.ch7._04_mapped_superclass;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
// 부모로부터 물려받은 매핑 정보 재정의 (선택적)
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID")),
	@AttributeOverride(name = "name", column = @Column(name = "MEMBER_NAME"))
})
public class Member extends BaseEntity {
	// ID 상속
	// NAME 상속
	private String email;
}
