package recursion.recursion04_gcd;

public class Main {
	
	// p, q의 최대 공약수를 구함
	public static int gcd(int p, int q)
	{
		if(q==0)
		{
			return p;
		}
		return gcd(q, p%q);
	}

	public static void main(String[] args) {
		System.out.println(gcd(4,20)); // Expected Output : 4
		// 최소 공배수 : ( p * q ) / gcd(p,q) 
	}

}
