package com.nemo.effective_java.item65.step01;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

public class ReflectionClient {
	public static void main(String[] args) {
		// convert Class instance using the className
		Class<? extends Set<String>> cl = null;
		try {
			// args[0] = "java.util.HashSet"
			cl = (Class<? extends Set<String>>)Class.forName(args[0]);
		} catch (ClassNotFoundException e) {
			fatalError("Class not found.");
		}

		// get constructor of the class
		Constructor<? extends Set<String>> cons = null;
		try {
			cons = cl.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			fatalError("Not Found The no parameterless constructor");
		}

		// create instance of the class
		Set<String> s = null;
		try {
			s = cons.newInstance();
		} catch (InstantiationException e) {
			fatalError("Failed to instantiate");
		} catch (IllegalAccessException e) {
			fatalError("can't create the instance");
		} catch (InvocationTargetException e) {
			fatalError("constructor threw " + e.getCause());
		} catch (ClassCastException e) {
			fatalError("This class is not implement the Set interface");
		}

		// use the Set
		s.addAll(Arrays.asList(args).subList(1, args.length));
		System.out.println(s); // [1, 2, 3]
	}

	private static void fatalError(String message) {
		System.err.println(message);
		System.exit(1);
	}
}
