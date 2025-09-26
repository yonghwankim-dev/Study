package com.myshop.order.command.domain.model;

public interface Specification<T> {
	boolean isSatisfiedBy(T agg);
}
