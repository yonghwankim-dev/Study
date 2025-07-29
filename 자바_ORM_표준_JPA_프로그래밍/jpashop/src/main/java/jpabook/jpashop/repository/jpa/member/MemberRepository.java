package jpabook.jpashop.repository.jpa.member;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jpabook.jpashop.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
	// 쿼리 메소드 기능 방법1: 메소드 이름으로 쿼리 생성
	Member findByName(String name);

	// 쿼리 메소드 기능 방법2: JPA NamedQuery, Member 엔티티에 Named Query 정의
	List<Member> findByName_v2(@Param("name") String name);

	// 쿼리 메소드 기능 방법3-1: JPQL 쿼리 정의
	@Query("select m from Member m where m.name = ?1")
	Member findByName_v3_1(String name);

	// 쿼리 메소드 기능 방법3-2: 네이티브 쿼리 정의
	@Query(value = "select * from member where name = ?1", nativeQuery = true)
	Member findByName_v3_2(String name);

	// 파라미터 바인딩
	@Query("select m from Member m where m.name = :name")
	Member findByName_v4(@Param("name") String name);

	// count 쿼리 사용
	Page<Member> findByName(String name, Pageable pageable);

	// count 쿼리 사용 안함
	// List<Member> findByName(String name, Pageable pageable);

	List<Member> findByName(String name, Sort sort);
}
