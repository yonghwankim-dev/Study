package org.example.ch14_sort.step02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;


// antenna
public class Main{
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] houses = Arrays.stream(br.readLine().split(" "))
            .mapToInt(Integer::valueOf)
            .toArray();
        int answer = solution(houses);
        System.out.println(answer);
    }

    private static int solution(int[] houses){
        Arrays.sort(houses);
        int mid = (houses.length - 1) / 2;
        return houses[mid];
    }
}
