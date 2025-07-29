package com.ch15.class06.step08;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;

import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
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
		SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
		StatelessSession session = sessionFactory.openStatelessSession();
		Transaction tx = session.beginTransaction();
		ScrollableResults scroll = session.createQuery("select p from Product p")
			.scroll();

		while(scroll.next()){
			Product p = (Product)scroll.get(0);
			p.setPrice(p.getPrice() + 100);
			session.update(p); // 직접 update를 호출해야 한다
		}
		tx.commit();
		session.close();
	}
}


