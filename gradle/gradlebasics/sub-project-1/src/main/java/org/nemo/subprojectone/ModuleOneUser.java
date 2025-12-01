package org.nemo.subprojectone;

import java.util.Map;

import org.nemo.common.CommonEntity;

import com.google.common.collect.ImmutableMap;

public class ModuleOneUser extends CommonEntity {

	private final Map<String, String> map = new ImmutableMap.Builder<String, String>()
		.put("key1", "value1")
		.put("key2", "value2")
		.build();

	public ModuleOneUser(String id) {
		super(id);
	}

	public static void main(String[] args) {
		System.out.println("Hello from Module One User!");
	}
}
