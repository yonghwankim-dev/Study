package com.nemo.effective_java.item77;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
	public static void main(String[] args) {
		CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			return 2;
		});
		int numColors = 4; // Default. Any map. This is enough
		try {
			numColors = f.get(1L, TimeUnit.SECONDS);
		} catch (TimeoutException | ExecutionException | InterruptedException ignored) {
			// use default value(It's good to minimize the number of colors, but it's not mandatory)
		}
		System.out.println(numColors);
	}
}
