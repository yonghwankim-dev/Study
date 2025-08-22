package com.myshop.order.domain;

public interface Specification<T> {
	boolean isSatisfiedBy(T agg);
}
