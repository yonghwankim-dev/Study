package org.example.ch12_imple.step02;

import java.util.*;
import java.io.*;
class Main{
    public static void main(String[] args) throws Exception{
        solution2("K1KA5CB7");
        solution2("AJKDLSI412K4JSJ9D");
    }

    private static void solution1(String words) {
        String result1 = words.replaceAll("[^A-Z]", "");
        char[] chars = result1.toCharArray();
        Arrays.sort(chars);
        result1 = new String(chars);

        System.out.println(result1);

        String result2 = words.replaceAll("[^0-9]", "");
        System.out.println(result2);
        int sum = Arrays.stream(result2.split(""))
            .mapToInt(Integer::valueOf)
            .sum();
        System.out.println(result1 + sum);
    }

    private static void solution2(String words){
        String answer = null;
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for(char ch : words.toCharArray()){
            if (Character.isAlphabetic(ch)){
                sb.append(ch);
            }else{
                sum += Integer.parseInt(String.valueOf(ch));
            }
        }
        char[] chars = sb.toString().toCharArray();
        Arrays.sort(chars);
        String result = new String(chars);
        
        if(sum != 0){
            answer = result + sum;
        }
        System.out.println(answer);
    }
}
