package com.ch15.class06.step05;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Builder
public class Product {
	@Id
	private String id;
	private Integer price;
}

@Service
class ProductService{

	@Autowired
	private EntityManagerFactory emf;

	public void registerBatchProduct(){
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		for (int i = 0; i < 100000; i++){
			Product product = Product.builder()
				.id("item" + i)
				.price(10000)
				.build();
			em.persist(product);

			// 100건마다 플러시와 영속성 컨텍스트 초기화
			if (i % 100 == 0){
				em.flush();
				em.clear();
			}
		}
		tx.commit();
		em.close();
	}
}


