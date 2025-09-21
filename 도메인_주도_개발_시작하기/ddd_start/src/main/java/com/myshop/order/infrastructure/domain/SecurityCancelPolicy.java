package com.myshop.order.infrastructure.domain;

import org.springframework.stereotype.Component;

import com.myshop.order.domain.model.CancelPolicy;
import com.myshop.order.domain.model.Canceller;
import com.myshop.order.domain.model.Order;

@Component
public class SecurityCancelPolicy implements CancelPolicy {
	@Override
	public boolean hasCancellationPermission(Order order, Canceller canceller) {
		return isCancellerOrderer(order, canceller) || isCurrentUserAdminRole(canceller);
	}

	private boolean isCancellerOrderer(Order order, Canceller canceller) {
		return order.getOrderer().getMemberId().getId().equals(canceller.getMemberId());
	}

	/**
	 * todo: add SpringSecurity
	 * @param canceller
	 * @return
	 */
	private boolean isCurrentUserAdminRole(Canceller canceller) {
		return false;
	}
}
