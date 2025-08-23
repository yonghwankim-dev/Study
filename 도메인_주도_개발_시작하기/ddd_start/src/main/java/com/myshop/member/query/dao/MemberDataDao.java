package com.myshop.member.query.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.myshop.member.query.dto.MemberData;

public interface MemberDataDao extends Repository<MemberData, String> {
	List<MemberData> findByNameLike(String name, Pageable pageable);

	void save(MemberData memberData);

	MemberData findById(String id);

	void deleteAll();
}
