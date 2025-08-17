package com.myshop.order.infrastructure.domain;

import com.myshop.order.domain.CancelPolicy;
import com.myshop.order.domain.Canceller;
import com.myshop.order.domain.Order;

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
