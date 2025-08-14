package com.myshop.order.domain;

import com.myshop.member.domain.MemberId;

public record Orderer(MemberId memberId, String name, String email) {

}
