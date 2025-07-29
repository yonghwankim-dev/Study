## 목차

## 개요

### JPQL 특징

- JPQL은 객체지향 쿼리 언어입니다. 따라서 테이블을 대상으로 쿼리하는 것이 아니라 엔티티 객체를 대상으로 쿼리합니다.
- JPQL은 SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않습니다.
- JPQL은 결국 SQL로 변환합니다.

## 2.1 기본 문법과 쿼리 API

JPQL도 SQL과 비슷하게 SELECT, UPDATE, DELETE 문을 사용할 수 있습니다.

INSERT문은 EntityManager.persist() 메소드를 사용하면 되므로 INSERT문은 없습니다.

### SELECT문

```
SELECT m FROM Member m WHERE m.username = 'Hello'
```

- 대소문자 구분
    - 엔티티와 속성은 대소문자를 구분합니다.
    - 예를 들어 Member, username은 대소문자를 구분합니다.
    - 반면, SELECT, FROM, AS같은 JPQL 키워드는 대소문자를 구분하지 않습니다.
- 엔티티 이름
    - JPQL에서 사용하는 Member는 클래스명이 아니라 **엔티티**명입니다.
    - 엔티티명은 @Entity(name = "Member")와 같이 지정할 수 있습니다.
    - 엔티티 명을 지정하지 않으면 클래스명을 기본값으로 사용합니다.
- 별칭은 필수
    - JPQL은 별칭을 필수로 사용해야 합니다.
    - SELECT username FROM Member m // 잘못된 문법, username -> m.username
    - AS는 생략할 수 있습니다.

### TypeQuery, Query

- TypeQuery : 반환할 타입을 명확하게 지정하는 경우 반환되는 객체
- Query : 반환할 타입을 명확하게 지정할 수 없는 경우 반환되는 객체

TypeQuery 사용

```
TypedQuery <Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
List <Member> resultList = query.getResultList();
```

- 반환할 타입으로 Member 클래스 타입을 지정하였기 때문에 TypeQuery 객체를 반환하였습니다.

Query 사용

```
Query query = em.createQuery("SELECT m FROM Member m");
List resultList = query.getResultList();
```

- 반환할 타입으로 아무것도 정하지 않았기 때문에 Query 객체가 반환되었습니다.

## 2.2 파라미터 바인딩

- 이름 기준 파라미터
- 위치 기준 파라미터

### 이름 기준 파라미터

- 파라미터를 이름으로 구분하는 방법입니다.
- 이름 기준 파라미터는 앞에 콜론(:)을 사용합니다.

```
String usernameParam = "kim0";
TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m WHERE m.username = :username", Member.class);
query.setParameter("username", usernameParam);
List<Member> resultList = query.getResultList();
```

- JPQL에서 이름 기준 파라미터를 정의할때 파라미터 이름앞에 콜론(:)을 정의합니다.
    - 예를 들어 :username과 같은 형태가 있습니다.
- query.setParameter 메소드를 통해서 이름 기준 파라미터에 값을 전달합니다.

### 위치 기준 파라미터

- 물음표(?) 다음에 위치 값을 1부터 주어 파라미터를 전달합니다.

```
String usernameParam = "kim0";
TypedQuery < Member > query = em.createQuery("SELECT m FROM Member m WHERE m.username = ?1", Member.class);
query.setParameter(1, usernameParam);
```

- 1번 파라미터에 usernameParam 값을 전달합니다.

## 2.3 프로젝션

- 프로젝션(Projection) : SELECT 절에 조회할 대상을 지정하는 것
- 프로젝션 대상 : 엔티티, 임베디드 타입, 스칼라 타입
    - 스칼라 타입 : 숫자, 문자 등 기본 데이터 타입

### 엔티티 프로젝션

```
SELECT m FROM Member m // 회원
SELECT m.team FROM Member m // 팀
```

- 둘다 엔티티를 대상으로 프로젝션 대상으로 사용하였습니다.
- **조회한 엔티티는 영속성 컨텍스트에서 관리**됩니다.

### 임베디드 타입 프로젝션

- 임베디드 타입은 조회의 시작점이 될 수 없습니다.
- 엔티티 타입의 객체를 통해서 참조되어야 합니다.
- 임베디드 타입은 엔티티 타입이 아닌 값 타입이기 때문에 영속성 컨텍스트에서 관리하지 않습니다.

```
String query = "SELECT o.address FROM Order o";
List<Address> addresses = em.createQuery(query, Address.class).getResultList();
```

### 스칼라 타입 프로젝션

- SELECT 절에 조회할 대상으로 스칼라 타입으로 조회합니다.
- 스칼라 타입은 숫자, 문자, 날짜와 같은 기본 데이터 타입들입니다.

```
Query query = em.createQuery("SELECT m.usernameFROM Member m");
List resultList = query.getResultList();
```

- 쿼리에서 m.username, m.age는 String타입입니다.

### 여러 값 조회

- 엔티티를 대상으로 조회할수도 있지만 꼭 필요한 데이터들만 선택해서 조회할 수 있습니다.
- 프로젝션에 여러 값을 선택하면 TypeQuery를 사용할 수 없고, 대신에 Query를 사용해야 합니다.

```
Query query = em.createQuery("SELECT m.username, m.age FROM Member m");
List resultList = query.getResultList();
```

### NEW 명령어

특정 필드만 프로젝션 조회하는 경우 반환타입으로 TypeQuery를 사용할 수 없습니다.

```
Query query = em.createQuery("SELECT m.username, m.age FROM Member m");
List resultList = query.getResultList();
```

JPQL에서 new 명령어를 사용하여 TypeQuery 타입으로 반환할 수 있습니다.

```
TypedQuery<UserDTO> query = em.createQuery("SELECT new com.hello.jpabook_ch10_objected_query._01_objected_query.UserDTO(m.username, m.age) FROM Member m",UserDTO.class);
List<UserDTO> userDTOs = query.getResultList();
```

- SELECT 다음에 new 명령어를 사용하면 반환받을 클래스를 지정할 수 있는데 이 클래스의 생성자에 JPQL 조회 결과를 넘겨 줄 수 있습니다.
- TypeQuery를 응답받기 때문에 객체 변환 작업을 줄일 수 있습니다.

## 2.4 페이징 API

JPA가 추상화한 페이징 API

- setFirstResult(int startPosition): 조회 시작 위치(0부터 시작)
- setMaxResults(int maxResult): 조회할 데이터 수

```
TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m ORDER BY m.username DESC", Member.class);
query.setFirstResult(10); // 11번째 부터 조회
query.setMaxResults(20); // 총 20건의 데이터 조회, 11~30번 데이터 조회
List<Member> resultList = query.getResultList();
```

## 2.5 집합과 정렬

집합은 집합함수와 함께 통계 정보를 구할 때 사용합니다.

```
SELECT
  COUNT(m),   // 회원수
  SUM(m.age), // 나이합
  AVG(m.age), // 평균 나이
  MAX(m.age), // 최대 나이
  MIN(m.age)  // 최소 나이
 FROM Member m
```

### 집합 함수

- COUNT : 결과 수를 구합니다. 반환타입: Long
- MAX, MIN : 최대, 최소 값을 구합니다. 문자, 숫자, 날짜 등에 사용합니다.
- AVG: 평균값을 구합니다. 숫자타입만 사용할 수 있습니다. 반환 타입: Double
- SUM: 합을 구합니다. 숫자타입만 사용할 수 있습니다.

### GROUP BY, HAVING

- GROUP BY : 특정 그룹끼리 묶어줍니다.
- HAVING : 그룹화한 데이터를 기준으로 필터링합니다.

GROUP BY 사용

```
Query query = em.createQuery("SELECT COUNT(m), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age) "
			+ "FROM Member m LEFT JOIN m.team t "
			+ "GROUP BY t.name");
List<Object[]> resultList = query.getResultList();
```

HAVING 사용

```
Query query = em.createQuery("SELECT COUNT(m), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age) "
			+ "FROM Member m LEFT JOIN m.team t "
			+ "GROUP BY t.name "
			+ "HAVING AVG(m.age) >= 10");
List<Object[]> resultList = query.getResultList();
```

### 정렬(ORDER BY)

- ORDER BY : 데이터를 정렬할때 사용합니다.

```
Query query = em.createQuery("SELECT m.username, m.age FROM Member m ORDER BY m.age DESC, m.username ASC");
List<Object[]> resultList = query.getResultList();
```

- 나이를 기준으로 내림차순, 유저이름을 기준으로 오름차순으로 정렬합니다.

## 2.6 JPQL 조인

### 내부 조인

- 내부 조인은 INNER JOIN을 사용합니다. INNER는 생략할 수 있습니다.

```
String teamName = "팀1";
String jpql = "SELECT m FROM Member m INNER JOIN m.team t WHERE t.name = :teamName";
List<Member> members = em.createQuery(jpql, Member.class)
    .setParameter("teamName", teamName)
    .getResultList();
```

- 회원과 팀을 내부 조인해서 "팀1"에 소속된 회원을 조회하는 JPQL입니다.
- JPQL에서 조인시 엔티티 객체의 연관 필드(m.team)를 사용하는 것을 볼 수 있습니다.
- 연관 필드 : 다른 엔티티와 연관관계를 가지기 위해 사용하는 필드

### 외부 조인

- 외부 조인은 어느 한쪽 테이블이 조인하는 다른 테이블의 값이 없는대로 출력하는 조인입니다.
- 예를 들어 LEFT [OUTER] JOIN은 왼쪽 테이블이 오른쪽 테이블의 외래키가 없어도 출력됩니다. (단, 조건이 없는 한)

```
String teamName = "팀1";
String jpql = "SELECT m FROM Member m LEFT JOIN m.team t WHERE t.name = :teamName";
List<Member> members = em.createQuery(jpql, Member.class)
    .setParameter("teamName", teamName)
    .getResultList();
```

- 회원과 팀 엔티티 객체간에 외부 조인하여 "팀1"인 회원을 조인합니다.

### 컬렉션 조인

컬렉션 조인은 일대다 관계나 다대다 관계처럼 컬렉션을 사용하는 곳에 조인하는 것을 말합니다.

- [회원 -> 팀]으로의 조인은 다대일 조인이면서 **단일 값 연관 필드(m.team)** 를 사용합니다.
- [팀 -> 회원]으로의 조인은 일대다 조인이면서 **컬렉션 값 연관 필드(m.members)** 를 사용합니다.

```
List<Object[]> rows = em.createQuery("SELECT t, m FROM Team t LEFT JOIN t.members m").getResultList();
```

- t LEFT JOIN t.members는 팀과 팀이 보유한 회원 목록을 **컬렉션 값 연관 필드**로 외부 조인했습니다.

### 세타 조인

- WHERE 절을 사용해서 세타 조인을 할 수 있습니다.
- 세타 조인은 내부 조인만 지원합니다.

```
Long count = em.createQuery("SELECT COUNT(m) FROM Member m, Team t WHERE m.team.name = t.name", Long.class)
			.getSingleResult();
```

### JOIN ON절

- JPA 2.1부터 조인할때 ON절을 지원합니다.
- **ON 절을 사용하면 조인 대상을 필터링하고 조인할 수 있습니다.**
- 보통 ON절은 외부조인에서만 사용합니다. 왜냐하면 내부 조인의 ON 절은 WHERE절을 사용한 결과와 동일하기 때문입니다.

```
String jpql = "SELECT m, t FROM Member m LEFT JOIN m.team t ON t.name = '팀1'";
List<Object[]> resultList = em.createQuery(jpql).getResultList();
```

- 조인 시점에 팀이 '팀1'인 대상으로 필터링합니다.

### 페치 조인

- 연관된 엔티티나 컬렉션을 한번에 같이 조회하는 기능입니다.

```
페치 조인 ::= [ LEFT [OUTER] INNER ] JOIN FETCH 조인 경로
```

#### 엔티티 페치 조인

- 엔티티 조회시 해당 엔티티와 관계 있는 다른 엔티티와 조인하여 함께 조회합니다.

```
String jpql = "SELECT m FROM Member m JOIN FETCH m.team";
```

- 회원(m)과 팀(m.team)을 함께 조회합니다.
- 회원 조회할때 페치 조인을 사용해서 팀도 함께 조회했으므로 **연관된 팀 엔티티는 프록시가 아닌 실제 엔티티**입니다.
- 이는 지연 로딩을 사용하지 않는다는 의미입니다.

#### 컬렉션 페치 조인

다음은 팀(t)을 조회하면서 페치 조인을 사용해서 연관된 회원 컬렉션을 함께 조회하는 페치 조인입니다.

```
String jpql = "SELECT t FROM Team t join fetch t.members WHERE t.name = '팀1'";
```

#### 페치 조인과 DISTINCT

- JPQL에 DISTINCT 명령어를 사용하여 중복을 제거합니다.

```
String jpql = "SELECT distinct t FROM Team t JOIN FETCH t.members WHERE t.name = '팀1'";
```

#### 페치 조인과 일반 조인의 차이

- 일반 조인 사용시 연관된 엔티티를 조회하지 않고 조회된 엔티티는 연관된 엔티티 객체를 프록시 래퍼 객체를 가리킵니다.
- 프록시 래퍼 객체를 통하여 조회할 시점에 실제 SQL 쿼리하여 조회합니다.
- 페치 조인 사용시 연관된 엔티티까지 한꺼번에 조회합니다. 즉, 지연 로딩이 없습니다.
- 정리하면 페치 조인과 일반 조인의 차이는 페치 조인은 지연 로딩을 사용하지 않고, 일반 조인은 지연 로딩을 사용할 수 있습니다.

## 2.8 경로 표현식
경로 표현식은 점(.)을 찍어서 객체를 탐색하는 것입니다.
```
select m.username
from Member m
    join m.team t
    join m.orders o
where t.name = '팀A';
```
- 회원 중 팀 이름이 '팀A'인 회원들의 이름을 탐색합니다.

**경로 표현식 종류**
- 상태 필드 경로 탐색
- 단일 값 연관 경로 탐색
- 컬렉션 값 연관 경로 탐색

**상태 필드 경로 탐색**
객체가 가지고 있는 멤버를 바로 탐색하는 방법입니다.
```
select m.username, m.age
from Member m
```
- 회원 객체가 가지고 있는 username과 age를 바로 탐색하여 출력합니다.

**단일 값 연관 경로 탐색**
단일 값 연관 경로 탐색이란 다쪽의 객체에서 일쪽의 객체로 객체 탐색하는 것입니다.

예를 들어 주문(Order) 객체에서 연관 경로를 탐색하여 회원(Member)을 탐색합니다.
```
select m.*
from Orders o
  inner join Member m on o.member_id = m.id
```
- o.member를 통해서 주문에서 회원으로 경로를 탐색하였습니다.

회원과 같이 일 쪽으로 경로 탐색을 하게 되면 SQL에서 내부 조인이 일어나는데 이것을 **묵시적 조인**이라 한다. 묵시적 조인은 경로 표현식에 의해서 묵시적으로 조인됩니다. 단, INNER JOIN만 할 수 있습니다.

**컬렉션 값 연관 경로 탐색**
일 쪽의 객체에서 다쪽의 컬렉션을 탐색하는 것이다. 하지만, 다쪽의 컬렉션의 상태 필드를 바로 참조할 수 는 없습니다.

```
select t.members from Team t; // 성공
select t.members.username from Team t; // 실패
```
- 일쪽인 Team 객체에서 members 컬렉션을 참조하여 출력하는 것은 가능합니다.
- 하지만 members 컬렉션의 username을 참조하는 것은 불가능합니다.

만약 컬렉션 값의 상태 필드를 경로 탐색하고 싶으면 다음 코드처럼 조인을 사용해서 새로운 별칭을 얻어야 합니다.
```
select m.username from Team t join t.members m;
```
- 별칭 m부터 다시 시작하여 경로 탐색을 할 수 있습니다.

일쪽의 객체에서 컬렉션 값을 참조시 컬렉션의 크기를 구하기 위해서 size를 호출하면 됩니다.
```
select t.members.size from Team t;
```
- size 사용시 COUNT 함수를 사용하는 SQL로 변환됩니다.

### 경로 탐색시 묵시적 조인시 주의사항
- 묵시적 조인은 항상 내부 조인입니다. 내부 조인으로 인하여 두 테이블간에 값이 있는 데이터만 출력합니다.
- 컬렉션에서 경로 탐색을 계속 하려면 JOIN 해서 별칭을 얻어서 해야 합니다.
  - `select m.username from Team t join t.members m;`
- 경로 탐색은 주로 SELECT, WHERE 절에 사용하지만 묵시적 조인으로 인해서 SQL의 FROM 절에 영향을 준다.


## 2.9 서브 쿼리
JPQL도 SQL처럼 서브 쿼리를 지원합니다. 
단, 서브쿼리를 where, having 절에서만 사용할 수 있고 select, from 절에서는 사용할 수 없습니다.

예시1) 나이가 평균보다 많은 회원을 탐색합니다.
```
select m
from Member m
where m.age > (select avg(m2.age) from Member m2)
```

예시2) 최소 한건이라도 주문한 고객을 탐색합니다.
```
select m
from Member m
where (select count(o) from Order o where m = o.member) > 0

or

select m
from Member m
where m.orders.size > 0
```

### 서브 쿼리 함수
- [NOT] EXISTS (subquery)
- {ALL | ANY | SOME} (subquery)
- [NOT] IN (subquery)

#### EXISTS
EXISTS 함수는 서브 쿼리에 결과가 존재하면 참입니다. NOT을 붙이면 결과가 반대가 됩니다.

예시) 팀 A 소속인 회원을 탐색합니다.
```
select m
from Member m
where exists (select t from m.team t where t.name = '팀A')
```

예시) 팀 A 소속이 아닌 회원을 탐색합니다.
```
select m
from Member m
where not exists (select t from m.team t where t.name = '팀A')
```

#### {ALL | ANY | SOME}
- ALL : 모든 조건을 만족하면 결과가 참이 됩니다.
- ANY | SOME : 모든 조건중 하나라도 만족하면 결과가 참이 됩니다.

예시) 전체 상품 각각의 재고보다 주문량이 많은 주문들을 검색하세오
```
select o
from Order o
where o.orderAmount > all (select p.stockAmount from Product p)
```

예시) 어떤 팀이든 팀에 소속된 회원을 검색하세오.
```
select m
from Member m
where m.team = any (select t from Team t)
```

#### IN
서브쿼리의 결과 중 하나라도 같은 것이 있으면 참이 됩니다.

예시) 20세 이상을 보유한 팀을 검색하세요.
```
select t
from Team t
where t in (select t2 from Team t2 join t2.members m2 where m2.age >= 20)
```

## 2.10 조건식
### Between IN, Like, NULL 비교
#### Between
- A와 B 사이의 값이면 참입니다.

예시) 나이가 10~20인 회원을 찾아라
```
select m
from Member m
where m.age between 10 and 20
```

#### IN
- X와 같은 값이 예제에 하나라도 있으면 참입니다.

예시) 이름이 회원1이나 회원2인 회원을 찾아라
```
select m
from Member m
where m.username in ('회원1', '회원2')
```

#### Like
- 패턴에 맞는 데이터가 있다면 참입니다.
- %(퍼센트) : 아무 값들이 입력되어 된다. (값이 없어도됨)
- _(언더라인) : 한 글자는 아무 값이 입력되어도 되지만 값이 있어야 한다

예시) 중간에 원이라는 단어가 들어간 회원을 찾아라 (강원미, 강원, 원미)
```
select m
from Member m
where m.username like '%원%'
```

예시) 이름이 회원으로 시작하는 회원을 찾아라 (회원1, 회원ABC)
```
select m
from Member m
where m.username like '회원%'
```

예시) 이름이 회원으로 끝나는 회원을 찾아라 (좋은 회원, A회원)
```
select m
from Member m
where m.username like '%회원'
```

예시) 이름이 회원으로 시작하고 그 다음 한글자로 끝나는 회원을 찾아라 (회원A, 회원1)
```
select m
from Member m
where m.username like '회원_'
```

예시) 이름이 총 길이가 3이고 3으로 끝나는 회원을 찾아라 (회원3)
```
select m
from Member m
where m.username like '__3'
```

예시) 이름이 회원%인 회원을 찾아라 (회원%)
```
select m
from Member m
where m.username like '회원\%' ESCAPE '\'
```

#### NULL
입력값이 NULL인지 검사합니다. NULL 비교시 '=' 연산자가 아닌 IS NULL을 사용하여야 합니다.

예시) 회원의 이름이 null인 회원을 찾아라
```
select m
from Member m
where m.username is null
```

### 컬렉션 식
컬렉션 식은 컬렉션에만 사용할 수 있는 기능입니다.

#### 빈 컬렉션 비교 식 (EMPTY)
EMPTY는 컬렉션이 비어있으면 참입니다.

예시) 주문이 하나라도 있는 회원을 조회하라
```
select m
from Member m
where m.orders is not empty

// 실행된 SQL
select m.*
from Member m
where exists (
  select o.id
  from Orders o
  where m.id = o.member_id
)
```

#### 컬렉션의 멤버 식
엔티티나 값이 컬렉션에 포함되어 있으면 참입니다.

예시) 입력받은 memberParam이 컬렉션에 포함되어 있는 팀을 조회하시오.
```
select t
from Team t
where :memberParam member of t.members
```

### 스칼라 식
스칼라는 숫자, 문자, 날짜, case, 엔티티 타입 같은 가장 기본적인 타입들을 말한다.

#### 문자함수
- CONCAT
- SUBSTRING
- TRIM
- LOWER
- UPPER
- LENGTH
- LOCATE

#### 수학함수
- ABS
- SQRT
- MOD
- SIZE
- INDEX

#### 날짜함수
- CURRENT_DATE
- CURRENT_TIME
- CURRENT_TIMESTAMP

```
select current_date, current_time, current_timestamp from Team t
// 2013-08-19, 23:38:17, 2013-08-19 23:38:17
```







