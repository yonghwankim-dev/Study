package com.ch15.class06.step06;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

	public void updateBatchProduct(){
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		int pageSize = 100;
		for (int i = 0; i < 10; i++){
			List<Product> resultList = em.createQuery("select p from Product p", Product.class)
				.setFirstResult(i * pageSize)
				.setMaxResults(pageSize)
				.getResultList();

			// 비즈니스 로직 실행
			for (Product product : resultList){
				product.setPrice(product.getPrice() + 100);
			}

			em.flush();
			em.clear();
		}

		tx.commit();
		em.close();
	}
}


