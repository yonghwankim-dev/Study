package com.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jpabook.member.Member;

public class EntityTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	public void registerEntity() {
		EntityTransaction transaction = em.getTransaction();

		Member member1 = Member.builder().id("member1").username("회원1").age(20).build();
		Member member2 = Member.builder().id("member2").username("회원2").age(20).build();

		// 엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
		transaction.begin();
		em.persist(member1);
		em.persist(member2);
		// 여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.

		// 커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
		//        transaction.commit();
		Member findMember = em.find(Member.class, member1.getId());
		Assertions.assertThat(findMember).isEqualTo(member1);
		transaction.rollback();
	}

	@Test
	public void updateEntity() {
		EntityTransaction transaction = em.getTransaction();
		Member member1 = Member.builder().id("member1").username("회원1").age(20).build();
		transaction.begin();
		em.persist(member1);

		Member findMember = em.find(Member.class, "member1");
		findMember.setUsername("홍길동");
		findMember.setAge(10);

		Member updatedMember = em.find(Member.class, "member1");
		Assertions.assertThat(updatedMember.getUsername()).isEqualTo("홍길동");
		Assertions.assertThat(updatedMember.getAge()).isEqualTo(10);

		transaction.rollback();
	}

	@Test
	public void deleteEntity() {
		EntityTransaction transaction = em.getTransaction();
		Member member1 = Member.builder().id("member1").username("회원1").age(20).build();
		transaction.begin();
		em.persist(member1);

		Member findMember = em.find(Member.class, "member1");
		em.remove(findMember);

		Member actual = em.find(Member.class, "member1");
		Assertions.assertThat(actual).isNull();
		transaction.rollback();
	}

	@Test
	public void executeJPQLQuery() {
		em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Member member1 = Member.builder().id("member1").username("회원1").age(20).build();
		transaction.begin();
		em.persist(member1);

		TypedQuery<Member> query = em.createQuery("select m from Member m order by m.id",
			Member.class);
		List<Member> members = query.getResultList();

		Assertions.assertThat(members.size()).isEqualTo(1);
		transaction.rollback();
	}

	// detach를 호출하여 특정 엔티티를 준영속 상태로 변경하면 영속성 컨텍스트가 엔티티를 관리하지 않는다.
	// 쓰기 지연 SQL 저장소의 INSERT SQL도 제거되어 데이터베이스에 저장되지도 않는다
	@Test
	public void detachEntity() {
		EntityTransaction transaction = em.getTransaction();
		Member member1 = Member.builder().id("member1").username("회원1").age(20).build();
		transaction.begin();
		em.persist(member1);

		em.detach(member1);

		TypedQuery<Member> query = em.createQuery("select m from Member m order by m.id",
			Member.class);
		List<Member> members = query.getResultList();

		Assertions.assertThat(members.size()).isEqualTo(0);

		transaction.rollback();
	}

	// em.clear() 호출시 영속성 컨텍스트에 있는 엔티티는 초기화되어 준영속 상태가 됩니다.
	@Test
	public void clearEntityManager() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Member member1 = Member.builder().id("member1").username("회원1").age(20).build();

		transaction.begin();
		em.persist(member1);

		em.clear();

		// 준영속 상태
		member1.setUsername("홍길동");

		transaction.rollback();
	}

	@Test
	public void closePersistenceContext() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Member member1 = Member.builder().id("member1").username("회원1").age(20).build();

		transaction.begin();
		em.persist(member1);

		em.close();

		transaction.rollback();
	}

	@Test
	public void mergeMember() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Member member1 = Member.builder().id("member1").username("회원1").age(20).build();

		transaction.begin();
		em.persist(member1);
		transaction.commit();
		em.close();

		// 준영속 상태에서 변경
		member1.setUsername("홍길동");

		em = emf.createEntityManager();
		transaction = em.getTransaction();
		transaction.begin();
		Member merge = em.merge(member1);
		transaction.commit();

		Assertions.assertThat(member1.getUsername()).isEqualTo("홍길동");
		Assertions.assertThat(merge.getUsername()).isEqualTo("홍길동");

		Assertions.assertThat(em.contains(member1)).isFalse();
		Assertions.assertThat(em.contains(merge)).isTrue();

		Assertions.assertThat(member1 == merge).isFalse();

		transaction = em.getTransaction();
		transaction.begin();
		em.remove(merge);
		transaction.commit();
	}

}

