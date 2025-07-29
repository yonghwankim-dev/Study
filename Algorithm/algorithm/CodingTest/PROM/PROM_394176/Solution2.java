package PROM.PROM_394176;

import java.util.Arrays;

class Solution2 {
    public String convert(int n ,int k){
        int temp = n;
        StringBuilder sb = new StringBuilder();
        while(temp / k != 0){
            sb.insert(0,temp % k);
            temp = temp / k;
        }
        sb.insert(0, temp % k);

        return sb.toString();
    }

    public void condition1(String s){
        String[] result = s.split("0\\d0");
        System.out.println(Arrays.toString(result));
    }

    public void condition2(String s){
        String[] result = s.split("\\   d0");
        System.out.println(Arrays.toString(result));
    }

    public void condition3(String s){
        String[] result = s.split("0\\d");
        System.out.println(Arrays.toString(result));
    }

    public void condition4(String s){
        String[] result = s.split("\\d");
        System.out.println(Arrays.toString(result));
    }


    public boolean isPrime(int n){
        for(int i=2; i*i <= n; i++){
            if(n % i == 0){
                return false;
            }
        }
        return true;
    }


    public int solution(int n, int k) {
        int answer = 0;
        String s = convert(n, k);
        condition1(s);
        condition2(s);
        condition3(s);
        condition4(s);
        return answer;
    }

    public static void main(String[] args){
        int n = 437674;
        int k = 3;

        new Solution2().solution(n, k);
    }
}
