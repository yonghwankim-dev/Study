package jpabook.jpashop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "jpabook.jpashop.repository.jpa")
public class JpaConfig {
}
