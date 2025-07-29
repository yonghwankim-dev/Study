package PROM.PROM_77885;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public static long[] solution(long[] numbers) {
        List<Long> answer = new ArrayList<>();

        for(long number : numbers){
            long target = findNextNumber(number);
            answer.add(target);
        }
        return answer.stream().mapToLong(Long::longValue).toArray();
    }

    private static long findNextNumber(long number) {
        String bit = Long.toBinaryString(number);
        String[] bits;

        if(isExistZero(bit)){
            bits = bit.split("");
            return findNextDecimalNumber(bits);
        }
        bit = "0"+bit;
        bits = bit.split("");
        return findNextDecimalNumber(bits);
    }

    private static boolean isExistZero(String bit){
        return bit.replaceAll("1","").length() > 0;
    }

    private static long findNextDecimalNumber(String[] bits){
        long result = 0;
        for(int i = bits.length - 1; i >= 0; i--){
            if(isZeroBit(bits[i]) && i == bits.length - 1){ // ù�ڸ��� 0�� ���
                bits[i] = "1";
                result = getDecimal(bits);
                break;
            }else if(bits[i].equals("0")){ // ù�ڸ��� 0�� �ƴϰ� 0�� �����ϴ� ���
                bits[i] = "1";
                bits[i+1] = "0";
                result = getDecimal(bits);
                break;
            }
        }
        return result;
    }

    private static boolean isZeroBit(String bit){
        return bit.equals("0");
    }

    private static long getDecimal(String[] bits){
        return Long.parseLong(String.join("",bits),2);
    }
}
