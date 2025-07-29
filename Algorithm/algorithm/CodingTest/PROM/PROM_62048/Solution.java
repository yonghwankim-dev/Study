package PROM.PROM_62048;



public class Solution {
	public long gcd(long a, long b) {
		if(b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}
	
    public long solution(int w, int h) {
        long big = w >= h ? w : h;
        long small = w < h  ? w : h;
        
        long gcd = gcd(big, small);
        
        return (big * small) - (big + small - gcd);
    }
}
