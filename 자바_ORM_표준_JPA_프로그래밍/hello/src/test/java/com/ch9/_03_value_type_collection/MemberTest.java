package com.ch9._03_value_type_collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_value_type_collection");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("값 타입 컬렉션 사용")
	public void test() {
		// given
		// 임베디드 값 타입
		Address address = new Address("서울", "강서", "456-456");
		Member member = new Member(null, "김용환", address, new HashSet<>(), new ArrayList<>());
		// when
		// 기본값 타입 컬렉션
		member.getFavoriteFoods().add("짬뽕");
		member.getFavoriteFoods().add("짜장");
		member.getFavoriteFoods().add("탕수육");
		// 임베디드 값 타입 컬렉션
		member.getAddressHistory().add(new Address("서울", "강남", "123-123"));
		member.getAddressHistory().add(new Address("서울", "강북", "000-000"));

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(member);
		em.flush();
		em.clear();

		/**
		 *     select
		 *         member0_.id as id1_2_0_,
		 *         member0_.city as city2_2_0_,
		 *         member0_.street as street3_2_0_,
		 *         member0_.zipcode as zipcode4_2_0_,
		 *         member0_.name as name5_2_0_
		 *     from
		 *         Member member0_
		 *     where
		 *         member0_.id=?
		 */
		Member findMember = em.find(Member.class, member.getId());

		Address homeAddress = findMember.getHomeAddress();

		Set<String> favoriteFoods = findMember.getFavoriteFoods();

		/**
		 *     select
		 *         favoritefo0_.MEMBER_ID as member_i1_1_0_,
		 *         favoritefo0_.FOOD_NAME as food_nam2_1_0_
		 *     from
		 *         FAVORITE_FOODS favoritefo0_
		 *     where
		 *         favoritefo0_.MEMBER_ID=?
		 */
		for (String favoriteFood : favoriteFoods) {
			System.out.println("favoriteFood : " + favoriteFood);
		}

		List<Address> addressHistory = findMember.getAddressHistory();

		/**
		 *     select
		 *         addresshis0_.MEMBER_ID as member_i1_0_0_,
		 *         addresshis0_.city as city2_0_0_,
		 *         addresshis0_.street as street3_0_0_,
		 *         addresshis0_.zipcode as zipcode4_0_0_
		 *     from
		 *         ADDRESS addresshis0_
		 *     where
		 *         addresshis0_.MEMBER_ID=?
		 */
		System.out.println(addressHistory.get(0));

		// 1. 임베디드 값 타입 수정
		member.setHomeAddress(new Address("새로운도시", "신도시1", "123456"));

		// 2. 기본값 타입 컬렉션 수정
		favoriteFoods = member.getFavoriteFoods();
		favoriteFoods.remove("탕수육");
		favoriteFoods.add("치킨");

		// 3. 임베디드 값 타입 컬렉션 수정
		addressHistory = member.getAddressHistory();
		addressHistory.remove(new Address("서울", "강남", "123-123"));
		addressHistory.add(new Address("새로운도시", "신도시1", "123-456"));
		em.merge(member);
		em.flush();
		transaction.rollback();
	}

}
