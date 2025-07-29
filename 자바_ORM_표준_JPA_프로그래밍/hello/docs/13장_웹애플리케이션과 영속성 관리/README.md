# 웹 애플리케이션과 영속성 관리
## 목차

## 트랜잭션 범위의 영속성 컨텍스트
J2SE(Java 2 Standard Edition) 환경에서 JPA를 사용하면 개발자가 직접 엔티티 매지너를 생성하고 트랜잭션을 관리해야 합니다.
하지만 스프링이나 J2EE(Java 2 Platform, Enterprise Edition) 환경에서 JPA를 사용하면 컨테이너가 제공하는 전략을 따라야 합니다.

