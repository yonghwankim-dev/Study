package com.nemo.effective_java.item58.step03;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;

public class FaceClient {
	public static void main(String[] args) {
		Collection<Face> faces = EnumSet.allOf(Face.class);
		for (Iterator<Face> i = faces.iterator(); i.hasNext(); ) {
			for (Iterator<Face> j = faces.iterator(); j.hasNext(); ) {
				// logical error, expected result is 36 pair, but actual result is 6 pair
				// no compile error, no runtime error
				System.out.println(i.next() + " " + j.next());
			}
		}
	}
}
