package org.example.ch8_dp;

public class Fibo1 {
	public int fibo(int x){
		if (x == 1 || x == 2){
			return 1;
		}
		return fibo(x - 1) + fibo(x - 2);
	}

	public static void main(String[] args) {
		Fibo1 fibo1 = new Fibo1();
		System.out.println(fibo1.fibo(4));
	}
}
