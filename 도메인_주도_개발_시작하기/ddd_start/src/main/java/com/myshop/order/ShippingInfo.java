package com.myshop.order;

public class ShippingInfo {
	private String receiverName;
	private String receiverPhoneNumber;
	private String shippingAddress1;
	private String shippingAddress2;
	private String shippingZipCode;

	public ShippingInfo(String receiverName, String receiverPhoneNumber, String shippingAddress1,
		String shippingAddress2,
		String shippingZipCode) {
		this.receiverName = receiverName;
		this.receiverPhoneNumber = receiverPhoneNumber;
		this.shippingAddress1 = shippingAddress1;
		this.shippingAddress2 = shippingAddress2;
		this.shippingZipCode = shippingZipCode;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public String getReceiverPhoneNumber() {
		return receiverPhoneNumber;
	}

	public String getShippingAddress1() {
		return shippingAddress1;
	}

	public String getShippingAddress2() {
		return shippingAddress2;
	}

	public String getShippingZipCode() {
		return shippingZipCode;
	}
}
