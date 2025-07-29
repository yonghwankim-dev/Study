package com.ch15.class06.step07;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;

import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
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
		Session session = em.unwrap(Session.class);
		tx.begin();
		ScrollableResults scroll = session.createQuery("select p from Product p")
			.setCacheMode(CacheMode.IGNORE) // 2차 캐시 기능을 끈다
			.scroll(ScrollMode.FORWARD_ONLY);

		int count = 0;
		while(scroll.next()){
			Product p = (Product)scroll.get(0);
			p.setPrice(p.getPrice() + 100);

			count++;
			if (count % 100 == 0){
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		em.close();
	}
}


