package com.myshop.member.query.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.myshop.member.query.dto.MemberData;

public interface MemberDataDao extends Repository<MemberData, String> {
	MemberData findById(String id);

	List<MemberData> findByNameLike(String name, Pageable pageable);

	Page<MemberData> findByBlocked(boolean blocked, Pageable pageable);

	Page<MemberData> findAll(Specification<MemberData> spec, Pageable pageable);

	void save(MemberData memberData);

	void deleteAll();
}
