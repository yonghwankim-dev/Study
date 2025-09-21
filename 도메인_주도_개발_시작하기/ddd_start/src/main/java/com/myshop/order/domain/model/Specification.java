package com.myshop.order.domain.model;

public interface Specification<T> {
	boolean isSatisfiedBy(T agg);
}
