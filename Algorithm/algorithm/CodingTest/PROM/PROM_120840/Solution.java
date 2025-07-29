package PROM.PROM_120840;

import java.math.BigInteger;

class Solution {
    public int solution(int balls, int share) {
        BigInteger n = BigInteger.valueOf(balls);
        BigInteger r = BigInteger.valueOf(share);
        BigInteger answer = factorial(n).divide((factorial(n.subtract(r)).multiply(factorial(r))));
        return answer.intValue();
    }

    public BigInteger factorial(BigInteger n){
        if(n.compareTo(BigInteger.ONE) <= 0){
            return BigInteger.ONE;
        }
        return n.multiply(factorial(n.subtract(BigInteger.ONE)));
    }


}
