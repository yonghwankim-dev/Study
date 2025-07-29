package PROM.PROM_12953;

public class Solution {
	// LCM(X, Y) = K * A * B
	// K=X, Y의 공약수, A=X / K, B=Y / K
	// X = A * K
	// Y = B * K
	// X * Y = (K * A) * (K * B) = K * K * A * B
	// X * Y = K * LCM(X, Y)
	// (X * Y) / K = LCM(X, Y)
	// 최소공배수 = 두수의 곱 / 최대 공약수
	
	// 두수의 최대공약수를 구함
	public static int gcd(int a, int b) {
		int A = a >= b ? a : b;
		int B = a < b ? a : b;
		
		while(A % B != 0) {
			int R = A % B;
			A = B;
			B = R;
		}
		return B;
	}
	public static int lcm(int X, int Y, int gcd) {
		return (X * Y) / gcd; 
	}
	
    public int solution(int[] arr) { 
     
        int answer = lcm(arr[0], 1, 1);
        
        for(int i = 1; i < arr.length; i++) {
        	answer = lcm(answer, arr[i], gcd(answer, arr[i]));
        }
        
        return answer;
    }
    
    public static void main(String[] args) {
    	System.out.println(new Solution().solution(new int[] {2, 6, 8, 14}));
    }
}
