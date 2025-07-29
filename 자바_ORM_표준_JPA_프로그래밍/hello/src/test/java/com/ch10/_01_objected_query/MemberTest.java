package com.ch10._01_objected_query;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hello.jpabook_ch10_objected_query._01_objected_query.QMember;
import com.querydsl.jpa.impl.JPAQuery;

class MemberTest {

	private static final Logger logger = LoggerFactory.getLogger(MemberTest.class);

	private static EntityManagerFactory emf;
	private EntityManager em;

	private Long memberId;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_objected_query");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		// 팀생성
		Team team = new Team(null, "팀1", new ArrayList<>());
		Team team2 = new Team(null, "팀2", new ArrayList<>());
		// 제품 생성
		Product product = new Product(null, "JPA책", 10L);
		em.persist(product);
		em.persist(team);
		em.persist(team2);
		// 회원 생성
		for (int i = 0; i < 50; i++) {
			Member member = new Member(null, "kim" + i, 20 + i, team, new ArrayList<>());
			Address address = new Address("JINJU", "street1", "12345");
			Orders orders = new Orders(null, member, product, 20000, address);
			member.addOrders(orders);
			team.addMember(member);
			em.persist(member);
			em.persist(orders);

			memberId = member.getId();
		}

		// Item 생성
		Book book = new Book(null, "JAVA", 10000, 100, new ArrayList<>(), "남궁성", "12345678");
		Album album = new Album(null, "뉴진스 1집", 20000, 200, new ArrayList<>(), "뉴진스", "etc");
		Movie movie = new Movie(null, "기생충", 30000, 300, new ArrayList<>(), "봉준호", "송강호");
		em.persist(book);
		em.persist(album);
		em.persist(movie);
		em.flush();
		transaction.commit();
	}

	@AfterEach
	public void afterEach() {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.createQuery("delete from Orders").executeUpdate();
		em.createQuery("delete from Product").executeUpdate();
		em.createQuery("delete from Member").executeUpdate();
		em.createQuery("delete from Team").executeUpdate();
		em.createQuery("delete from Item").executeUpdate();
		transaction.commit();
	}

	@Test
	@DisplayName("회원 엔티티를 대상으로 JPQL 사용")
	public void test() {
		// given
		String jpql = "select m from Member as m where m.username = 'kim0'";
		// when
		Member member = em.createQuery(jpql, Member.class).getSingleResult();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(member.getUsername()).isEqualTo("kim0");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("criteria 쿼리 사용")
	public void test2() {
		// Criteria 사용 준비
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> query = cb.createQuery(Member.class);
		// 루트 클래스(조회를 시작할 클래스)
		Root<Member> m = query.from(Member.class);
		// 쿼리 생성
		CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim0"));
		Member member = em.createQuery(cq).getSingleResult();
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(member.getUsername()).isEqualTo("kim0");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("QueryDSL 코드 사용")
	public void test3() {
		// 준비
		JPAQuery<Member> query = new JPAQuery(em);
		QMember member = QMember.member;
		// 쿼리, 결과조회
		Member findMember = query.from(member)
			.where(member.username.eq("kim0"))
			.fetch()
			.stream()
			.findAny()
			.orElseThrow();
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(findMember.getUsername()).isEqualTo("kim0");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("native sql 사용")
	public void test4() {
		// given
		String sql = "SELECT member_id, name FROM member WHERE name = 'kim0'";
		// when
		Member findMember = (Member)em.createNativeQuery(sql, Member.class)
			.getResultList()
			.stream()
			.findAny()
			.orElseThrow();
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(findMember.getUsername()).isEqualTo("kim0");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("TypeQuery 사용")
	public void test5() {
		// given
		TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
		// when
		List<Member> resultList = query.getResultList();
		// then
		resultList.forEach(System.out::println);
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(resultList.size()).isEqualTo(50L);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("Query 사용")
	public void test6() {
		// given
		Query query = em.createQuery("SELECT m FROM Member m");
		// when
		List resultList = query.getResultList();
		// then
		resultList.forEach(o -> logger.info("member : {}", o));
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(resultList.size()).isEqualTo(50L);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("이름 기준 파라미터 사용(권장)")
	public void test7() {
		// given
		String usernameParam = "kim0";
		// when
		TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m WHERE m.username = :username", Member.class);
		// then
		query.setParameter("username", usernameParam);
		List<Member> resultList = query.getResultList();
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(resultList.size()).isEqualTo(1L);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("위치 기준 파라미터 사용")
	public void test8() {
		// given
		String usernameParam = "kim0";
		// when
		TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m WHERE m.username = ?1", Member.class);
		// then
		query.setParameter(1, usernameParam);
		List<Member> resultList = query.getResultList();
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(resultList.size()).isEqualTo(1L);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("여러 프로젝션")
	public void test9() {
		// given
		Query query = em.createQuery("SELECT m.username, m.age FROM Member m");
		// when
		List resultList = query.getResultList();
		// then
		ListIterator iterator = resultList.listIterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[])iterator.next();
			String username = (String)row[0];
			Integer age = (Integer)row[1];
			System.out.println("username : " + username + ", age : " + age);
		}
	}

	@Test
	@DisplayName("여러 프로젝션 Object[]로 조회")
	public void test10() {
		// given
		String sql = "SELECT m.username, m.age FROM Member m";
		// when
		List<Object[]> resultList = em.createQuery(sql).getResultList();
		// then
		for (Object[] row : resultList) {
			System.out.println("username : " + row[0] + ", age : " + row[1]);
		}
	}

	@Test
	@DisplayName("여러 프로젝션 엔티티 타입 조회")
	public void test11() {
		// given
		List<Object[]> resultList =
			em.createQuery("SELECT o.member, o.product, o.orderAmount FROM Orders o").getResultList();
		// when
		Object[] row = resultList.get(0);
		Member member = (Member)row[0];
		Product product = (Product)row[1];
		int orderAmount = (int)row[2];

		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(resultList.size()).isEqualTo(50);
			softAssertions.assertThat(member.getUsername()).isEqualTo("kim0");
			softAssertions.assertThat(product.getName()).isEqualTo("JPA책");
			softAssertions.assertThat(orderAmount).isEqualTo(20000);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("new 명령어 사용전")
	public void test12() {
		List<Object[]> resultList = em.createQuery("SELECT m.username, m.age FROM Member m").getResultList();

		// 객체 변환작업
		List<UserDTO> userDTOs = new ArrayList<>();
		for (Object[] row : resultList) {
			userDTOs.add(new UserDTO((String)row[0], (Integer)row[1]));
		}
		UserDTO userDTO = userDTOs.get(0);

		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(userDTO.getUsername()).isEqualTo("kim0");
			softAssertions.assertThat(userDTO.getAge()).isEqualTo(20);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("new 명령어 사용후")
	public void test13() {
		// give
		TypedQuery<UserDTO> query = em.createQuery(
			"SELECT new com.hello.jpabook_ch10_objected_query._01_objected_query.UserDTO(m.username, m.age) FROM Member m",
			UserDTO.class);
		// when
		List<UserDTO> userDTOs = query.getResultList();
		// then
		UserDTO userDTO = userDTOs.get(0);

		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(userDTO.getUsername()).isEqualTo("kim0");
			softAssertions.assertThat(userDTO.getAge()).isEqualTo(20);
			softAssertions.assertAll();
		});
	}

	/**
	 * setFirstResult(int startPosition) : 조회 시작 위치(0부터 시작한다)
	 * setMaxResults(int maxResult) : 조회할 데이터 수
	 */
	@Test
	@DisplayName("페이징 사용")
	public void test14() {
		// given
		TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m ORDER BY m.username DESC", Member.class);
		// when
		query.setFirstResult(10); // 11번째 부터 조회
		query.setMaxResults(20); // 총 20건의 데이터 조회, 11~30번 데이터 조회
		List<Member> resultList = query.getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(resultList.size()).isEqualTo(20);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("집합 함수(COUNT, MAX, MIN, AVG, SUM)")
	public void test15() {
		// given

		// when
		Query query = em.createQuery("SELECT COUNT(m), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age) FROM Member m");
		List<Object[]> resultList = query.getResultList();
		// then
		for (Object[] result : resultList) {
			long count = (long)result[0];
			long sum = (long)result[1];
			double avg = (double)result[2];
			int max = (int)result[3];
			int min = (int)result[4];

			SoftAssertions.assertSoftly(softAssertions -> {
				softAssertions.assertThat(count).isEqualTo(50L);
				softAssertions.assertThat(sum).isEqualTo(2225L);
				softAssertions.assertThat(avg).isCloseTo(44.5, Percentage.withPercentage(0.9));
				softAssertions.assertThat(max).isEqualTo(69);
				softAssertions.assertThat(min).isEqualTo(20);
				softAssertions.assertAll();
			});
		}
	}

	@Test
	@DisplayName("group by")
	public void test16() {
		// given

		// when
		Query query = em.createQuery("SELECT COUNT(m), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age) "
			+ "FROM Member m LEFT JOIN m.team t "
			+ "GROUP BY t.name");
		List<Object[]> resultList = query.getResultList();
		// then
		Object[] row = resultList.get(0);
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(row[0]).isEqualTo(50L);
			softAssertions.assertThat(row[1]).isEqualTo(2225L);
			softAssertions.assertThat(row[2]).isEqualTo(44.5);
			softAssertions.assertThat(row[3]).isEqualTo(69);
			softAssertions.assertThat(row[4]).isEqualTo(20);
			softAssertions.assertAll();
		});

	}

	// having : group by로 그룹화된 통계 데이터를 기준으로 필터링합니다.
	@Test
	@DisplayName("having")
	public void test17() {
		// given

		// when
		Query query = em.createQuery("SELECT COUNT(m), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age) "
			+ "FROM Member m LEFT JOIN m.team t "
			+ "GROUP BY t.name "
			+ "HAVING AVG(m.age) >= 10");
		List<Object[]> resultList = query.getResultList();
		// then
		Object[] row = resultList.get(0);
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(row[0]).isEqualTo(50L);
			softAssertions.assertThat(row[1]).isEqualTo(2225L);
			softAssertions.assertThat(row[2]).isEqualTo(44.5);
			softAssertions.assertThat(row[3]).isEqualTo(69);
			softAssertions.assertThat(row[4]).isEqualTo(20);
			softAssertions.assertAll();
		});
	}

	// order by : 특정 필드를 기준으로 데이터를 정렬합니다.
	@Test
	@DisplayName("order by")
	public void test18() {
		// given

		// when
		Query query = em.createQuery("SELECT m.username, m.age FROM Member m ORDER BY m.age DESC, m.username ASC");
		List<Object[]> resultList = query.getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(resultList.size()).isEqualTo(50L);
			softAssertions.assertThat(resultList.get(0)[0]).isEqualTo("kim49");
			softAssertions.assertThat(resultList.get(1)[0]).isEqualTo("kim48");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("내부 조인")
	public void test19() {
		// given
		String teamName = "팀1";
		String jpql = "SELECT m FROM Member m INNER JOIN m.team t WHERE t.name = :teamName";
		// when
		List<Member> members = em.createQuery(jpql, Member.class)
			.setParameter("teamName", teamName)
			.getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(members.size()).isEqualTo(50L);
			softAssertions.assertThat(members.get(0).getTeam().getName()).isEqualTo("팀1");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("외부 조인")
	public void test20() {
		// given
		String teamName = "팀1";
		String jpql = "SELECT m FROM Member m LEFT JOIN m.team t WHERE t.name = :teamName";
		// when
		List<Member> members = em.createQuery(jpql, Member.class)
			.setParameter("teamName", teamName)
			.getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(members.size()).isEqualTo(50L);
			softAssertions.assertThat(members.get(0).getTeam().getName()).isEqualTo("팀1");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("컬렉션 값 연관 필드(members)로 외부 조인")
	public void test21() {
		// given

		// when
		List<Object[]> rows = em.createQuery("SELECT t, m FROM Team t LEFT JOIN t.members m").getResultList();
		// then
		rows.forEach(row -> {
			Team team = (Team)row[0];
			Member member = (Member)row[1];
			logger.info("team : {}", team);
			logger.info("member : {}", member);
		});
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(rows.size()).isEqualTo(51);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("세타 조인")
	public void test22() {
		// given

		// when
		Long count = em.createQuery("SELECT COUNT(m) FROM Member m, Team t WHERE m.team.name = t.name", Long.class)
			.getSingleResult();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(count).isEqualTo(50L);
		});
	}

	/**
	 * 조인할때 ON절 사용시 조인 대상을 필터링하고 조인할 수 있습니다.
	 * 내부 조인의 ON절은 WHERE절을 사용할때와 결과가 같으므로 내부조인시 ON절은 사용하지 않습니다.
	 * ON절은 보통 외부 조인에서만 사용합니다.
	 */
	@Test
	@DisplayName("JOIN ON 절")
	public void test23() {
		// given
		String jpql = "SELECT m, t FROM Member m LEFT JOIN m.team t ON t.name = '팀1'";
		// when
		List<Object[]> resultList = em.createQuery(jpql).getResultList();
		// then
		resultList.forEach(row -> {
			Member member = (Member)row[0];
			Team team = (Team)row[1];
			logger.info("member : {}", member);
			logger.info("team : {}", team);
		});
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(resultList.size()).isEqualTo(50L);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("fetch join 사용")
	public void test24() {
		// given
		String jpql = "SELECT m FROM Member m JOIN FETCH m.team";
		// when
		List<Member> members = em.createQuery(jpql, Member.class).getResultList();
		// then
		// 패치 조인으로 회원과 팀을 함께 조회해서 지연 로딩 발생 안함
		members.forEach(
			member -> logger.info("username = {}, teamName = {}", member.getUsername(), member.getTeam().getName()));

	}

	@Test
	@DisplayName("컬렉션 패치 조인 JPQL")
	public void test25() {
		// given
		String jpql = "SELECT t FROM Team t join fetch t.members WHERE t.name = '팀1'";
		// when
		List<Team> teams = em.createQuery(jpql, Team.class).getResultList();
		// then
		List<Member> members = teams.stream().findAny().orElseThrow().getMembers();
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(teams.size()).isEqualTo(50L);
			softAssertions.assertThat(members.size()).isEqualTo(50L);
			softAssertions.assertThat(teams.stream().allMatch(team -> team.getName().equals("팀1"))).isTrue();
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("페치 조인과 distinct")
	public void test26() {
		// given
		String jpql = "SELECT distinct t FROM Team t JOIN FETCH t.members WHERE t.name = '팀1'";
		// when
		List<Team> teams = em.createQuery(jpql, Team.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(teams.size()).isEqualTo(1L);
			softAssertions.assertAll();
		});
	}

	/**
	 * 패치 조인을 사용하지 않은 조인은 연관관계인 엔티티를 조회하지 않는다. (지연 로딩)
	 */
	@Test
	@DisplayName("내부 조인 JPQL")
	public void test27() {
		// given
		String jpql = "SELECT t FROM Team t WHERE t.name = '팀1'";
		// when
		List<Team> teams = em.createQuery(jpql, Team.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(teams.size()).isEqualTo(1);
		});
	}

	/**
	 * 페치 조인하여 연관관계 엔티티인 회원도 같이 조회하여 가져옵니다.
	 */
	@Test
	@DisplayName("컬렉션 페치 조인 JPQL")
	public void test28() {
		// given

		// when
		Team team = em.createQuery("SELECT t FROM Team t JOIN FETCH t.members WHERE t.name = '팀1'", Team.class)
			.getSingleResult();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(team.getName()).isEqualTo("팀1");
			softAssertions.assertThat(team.getMembers().size()).isEqualTo(50L);
		});
	}

	@Test
	@DisplayName("Order JPQL")
	public void test29() {
		// given

		// when
		Team team = em.createQuery(
			"SELECT o.member.team FROM Orders o WHERE o.product.name = 'JPA책' and o.address.city = 'JINJU'",
			Team.class).getSingleResult();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(team.getName()).isEqualTo("팀1");
		});
	}

	@Test
	@DisplayName("나이가 평균보다 많은 회원을 탐색하는 서브쿼리")
	public void test30() {
		// given

		// when
		TypedQuery<Member> query = em.createQuery(
			"SELECT m FROM Member m WHERE m.age > (SELECT AVG(m2.age) FROM Member m2)", Member.class);
		List<Member> members = query.getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(members.size()).isEqualTo(25);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("한건이라도 주문한 고객을 탐색하는 서브쿼리")
	public void test31() {
		// given

		// when
		TypedQuery<Member> query = em.createQuery(
			"SELECT m FROM Member m WHERE (SELECT COUNT(o) FROM Orders o WHERE m = o.member) > 0", Member.class);
		List<Member> members = query.getResultList();

		// 컬렉션 값 연관 필드의 size 기능 사용
		List<Member> members2 = em.createQuery("SELECT m FROM Member m WHERE m.orders.size > 0", Member.class)
			.getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(members.size()).isEqualTo(50);
			softAssertions.assertThat(members2.size()).isEqualTo(50);
			softAssertions.assertAll();
		});
	}

	// [NOT] EXISTS (서브쿼리)
	@Test
	@DisplayName("서브 쿼리 함수 EXISTS")
	public void test32() {
		// given
		String jpql = "SELECT m FROM Member m WHERE EXISTS (SELECT t FROM m.team t WHERE t.name = '팀1')";
		String jpql2 = "SELECT m FROM Member m WHERE NOT EXISTS (SELECT t FROM m.team t WHERE t.name = '팀1')";
		// when
		List<Member> members = em.createQuery(jpql, Member.class).getResultList();
		List<Member> members2 = em.createQuery(jpql2, Member.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(members.size()).isEqualTo(50L);
			softAssertions.assertThat(members2.size()).isEqualTo(0);
			softAssertions.assertAll();
		});
	}

	// {ALL | ANY | SOME} (서브쿼리)
	// ALL: 조건을 모두 만족하면 참
	// ANY | SOME : 조건중 하나라도 만족하면 참
	@Test
	@DisplayName("서브 쿼리 함수 ALL, ANY, SOME")
	public void test33() {
		// given
		// 전체 상품 각각의 재고보다 주문량이 많은 주문들
		String jpql = "SELECT o FROM Orders o WHERE o.orderAmount > ALL (SELECT p.stockAmount FROM Product p)";
		// 어떤 팀이든 팀에 소속된 회원
		String jpql2 = "SELECT m FROM Member m WHERE m.team = ANY (SELECT t FROM Team t)";

		// when
		List<Orders> orders = em.createQuery(jpql, Orders.class).getResultList();
		List<Member> members = em.createQuery(jpql2, Member.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(orders.size()).isEqualTo(50);
			softAssertions.assertThat(members.size()).isEqualTo(50);
			softAssertions.assertAll();
		});
	}

	// [NOT] IN (서브쿼리) : 서크쿼리 결과 중 하나라도 같은 것이 있으면 참
	@Test
	@DisplayName("서브 쿼리 함수 IN")
	public void test34() {
		// given
		String jpql = "SELECT t FROM Team t WHERE t IN (SELECT t2 FROM Team t2 JOIN t2.members m2 WHERE m2.age >= 20)";
		// when
		// 20세 이상을 보유한 팀
		List<Team> teams = em.createQuery(jpql, Team.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(teams.size()).isEqualTo(1L);
			softAssertions.assertAll();
		});
	}

	// 문자표현식 [NOT] LIKE 패턴값 [ESCAPE 이스케이프문자]
	@Test
	@DisplayName("like식")
	public void test35() {
		// given
		String jpql = "SELECT m FROM Member m WHERE m.username like '%원%'"; // 좋은회원, 회원, 원
		String jpql2 = "SELECT m FROM Member m WHERE m.username like '회원%'"; // 회원1, 회원ABC
		String jpql3 = "SELECT m FROM Member m WHERE m.username like '%회원'"; // 좋은 회원, A회원
		String jpql4 = "SELECT m FROM Member m WHERE m.username like '회원_'"; // 회원A, 회원1
		String jpql5 = "SELECT m FROM Member m WHERE m.username like '__3'"; // 회원3
		String jpql6 = "SELECT m FROM Member m WHERE m.username like '회원\\%' ESCAPE '\\'"; // 회원%
		// when
		List<Member> members = em.createQuery(jpql, Member.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(members.size()).isEqualTo(0);
		});
	}

	// {컬렉션 값 연관 경로} IS [NOT] EMPTY
	@Test
	@DisplayName("빈 컬렉션 비교 식")
	public void test36() {
		// given
		String jpql = "SELECT m FROM Member m WHERE m.orders IS NOT EMPTY";
		// when
		List<Member> members = em.createQuery(jpql, Member.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(members.size()).isEqualTo(50);
			softAssertions.assertAll();
		});
	}

	// CURRENT_DATE: 현재 날짜
	// CURRENT_TIME: 현재 시간
	// CURRENT_TIMESTAMPE : 현재 날짜 시간
	@Test
	@DisplayName("날짜 함수")
	public void test37() {
		// given
		String jpql = "SELECT CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP FROM Team t";
		String jpql2 = "SELECT year(CURRENT_TIMESTAMP), MONTH(CURRENT_TIMESTAMP), DAY(CURRENT_TIMESTAMP) FROM Member m";
		// when
		Query query = em.createQuery(jpql);
		List<Object[]> resultList = query.getResultList();

		Query query2 = em.createQuery(jpql2);
		List<Object[]> resultList2 = query2.getResultList();
		// then
		Object[] row = resultList.get(0);
		System.out.println(row[0]); // 2023-07-16
		System.out.println(row[1]); // 15:56:30
		System.out.println(row[2]); // 2023-07-16 15:56:30.444085

		Object[] row2 = resultList2.get(0);
		System.out.println(row2[0]); // 2023
		System.out.println(row2[1]); // 7
		System.out.println(row2[2]); // 16
	}

	@Test
	@DisplayName("기본 CASE")
	public void test38() {
		// given
		String jpql = "SELECT CASE WHEN m.age <= 10 THEN '학생요금' WHEN m.age >= 60 THEN '경로요금' ELSE '일반요금' END FROM Member m";
		// when
		List<String> rows = em.createQuery(jpql).getResultList();
		// then
		rows.forEach(System.out::println);
	}

	@Test
	@DisplayName("심플 CASE")
	public void test39() {
		// given
		String jpql = "SELECT CASE t.name "
			+ "WHEN '팀1' THEN '인센티브110%' "
			+ "WHEN '팀2' THEN '인센티브120%' "
			+ "END "
			+ "FROM Team t";
		// when
		List<String> rows = em.createQuery(jpql).getResultList();
		// then
		rows.forEach(logger::info);
	}

	// COALESCE (<스칼라식> {, <스칼라식>}+)
	// 스칼라식을 차례대로 조회해서 null이 아니면 반환한다.
	@Test
	@DisplayName("COALESCE")
	public void test40() {
		// given

		// when
		List<String> members = em.createQuery("SELECT COALESCE(m.username, '이름 없는 회원') FROM Member m", String.class)
			.getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(members.stream().noneMatch(username -> username.equals("이름 없는 회원")))
				.isTrue();
		});
	}

	// NULLIF (<스칼라식>, <스칼라식>)
	// NULLIF : 두 값이 같으면 NULL반환, 다르면 첫번째 값을 반환
	@Test
	@DisplayName("NULLIF")
	public void test41() {
		// given
		String jpql = "SELECT NULLIF(m.username, '관리자') FROM Member m";
		// when
		List<String> rows = em.createQuery(jpql, String.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(rows.stream().noneMatch(row -> row == null)).isTrue();
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("다형성 쿼리")
	public void test42() {
		// given
		String jpql = "SELECT i FROM Item i";
		// when
		List<Item> items = em.createQuery(jpql, Item.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(items.size()).isEqualTo(3);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("다형성 쿼리 TYPE")
	public void test43() {
		// given
		String jpql = "SELECT i FROM Item i WHERE TYPE(i) IN (Book, Movie)";
		// when
		List<Item> items = em.createQuery(jpql, Item.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(items.size()).isEqualTo(2);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("다형성 쿼리 TREAT (JPA 2.1)")
	public void test44() {
		// given
		String jpql = "SELECT i FROM Item i WHERE TREAT(i as Book).author = '남궁성'";
		// when
		List<Item> items = em.createQuery(jpql, Item.class).getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(items.size()).isEqualTo(1);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("엔티티를 파라미터로 직접 받는 코드")
	public void test45() {
		// given
		Member member = new Member(memberId, null, 0, null, null);
		String jpql = "SELECT m FROM Member m WHERE m = :member";
		// when
		Member findMember = em.createQuery(jpql, Member.class)
			.setParameter("member", member)
			.getSingleResult();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(findMember.getId()).isEqualTo(memberId);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("외래 키 대신에 엔티티를 직접 사용하는 코드")
	public void test46() {
		// given
		Team team = em.createQuery("SELECT t FROM Team t WHERE t.name = '팀1'", Team.class).getSingleResult();
		String jpql = "SELECT m FROM Member m WHERE m.team = :team";
		// when
		List<Member> members = em.createQuery(jpql, Member.class)
			.setParameter("team", team)
			.getResultList();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(members.size()).isEqualTo(50);
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("@NamedQuery 용")
	public void test47() {
		// given

		// when
		Member member = em.createNamedQuery("Member.findByUsername", Member.class)
			.setParameter("username", "kim0")
			.getSingleResult();

		Long count = em.createNamedQuery("Member.count", Long.class).getSingleResult();
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(member.getUsername()).isEqualTo("kim0");
			softAssertions.assertThat(count).isEqualTo(50L);
			softAssertions.assertAll();
		});
	}
}
