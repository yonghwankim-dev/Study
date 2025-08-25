package com.myshop.store.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.store.domain.Store;
import com.myshop.store.domain.StoreId;

public interface SpringDataJpaStoreRepository extends JpaRepository<Store, StoreId> {

}
