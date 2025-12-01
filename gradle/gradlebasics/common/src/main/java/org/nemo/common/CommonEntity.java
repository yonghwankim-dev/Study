package org.nemo.common;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommonEntity {
	private final String id;
	private final Map<String, String> map = new ImmutableMap.Builder<String, String>()
		.put("key1", "value1")
		.put("key2", "value2")
		.build();
}
