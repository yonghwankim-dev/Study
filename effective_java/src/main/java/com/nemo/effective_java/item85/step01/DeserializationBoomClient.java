package com.nemo.effective_java.item85.step01;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.SerializationUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DeserializationBoomClient {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		byte[] result = boom();
		Set<?> set = deserialize(result);
		System.out.println(set);
	}

	private static byte[] boom() {
		Set<Object> root = new HashSet<>();
		Set<Object> s1 = root;
		Set<Object> s2 = new HashSet<>();

		for (int i = 0; i < 100; i++) {
			Set<Object> t1 = new HashSet<>();
			Set<Object> t2 = new HashSet<>();

			t1.add("foo");
			s1.add(t1);
			s1.add(t2);
			s2.add(t1);
			s2.add(t2);
			s1 = t1;
			s2 = t2;
		}
		return serialize(root);
	}

	private static byte[] serialize(Set<Object> root) {
		try {
			return SerializationUtils.serialize(root);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static Set<?> deserialize(byte[] bytes) {
		try {
			return objectMapper.readValue(bytes, Set.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
