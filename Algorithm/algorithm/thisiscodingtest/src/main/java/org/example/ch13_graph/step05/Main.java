package org.example.ch13_graph.step05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

// Insert operator
public class Main{
    static boolean[] choosed;
    static int max = Integer.MIN_VALUE;
    static int min = Integer.MAX_VALUE;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] numbers = Arrays.stream(br.readLine().split(" "))
            .mapToInt(Integer::valueOf)
            .toArray();
        int[] op = Arrays.stream(br.readLine().split(" "))
            .mapToInt(Integer::valueOf)
            .toArray();
        int[] answer = solution(n, numbers, op);
        System.out.println(answer[0]);
        System.out.println(answer[1]);
    }
    
    private static int[] solution(int n, int[] numbers, int[] op){
        List<String> operator = convertOperator(op);
        List<String> opQueue = new ArrayList<>();
        choosed = new boolean[n-1];
        backtracking(numbers, operator, opQueue);
        return new int[]{max, min};
    }
    
    private static List<String> convertOperator(int[] op){
        List<String> result = new ArrayList();
        String[] symbols = {"+", "-", "*", "/"};
        for(int i = 0; i < op.length; i++){
            while(op[i] != 0){
                result.add(symbols[i]);
                op[i]--;
            }
        }
        return result;
    }
    
    private static void backtracking(int[] numbers, List<String> operator, List<String> curOperator){
        if(curOperator.size() == numbers.length - 1){
            int result = calNumbers(numbers, curOperator);
            max = Math.max(max, result);
            min = Math.min(min, result);
            return;
        }
        
        for(int i = 0; i < operator.size(); i++){
            if(!choosed[i]){
                choosed[i] = true;
                curOperator.add(operator.get(i));
                backtracking(numbers, operator, curOperator);
                curOperator.remove(curOperator.size() - 1);
                choosed[i] = false;
            }
        }
    }
    
    private static int calNumbers(int[] numbers, List<String> curOperator){
        int result = 0;
        Stack<Integer> stack = new Stack();
        int idx = 0;
        for(int i = 0; i < numbers.length || !stack.isEmpty();){
            if(stack.size() == 2){
                int a = stack.pop();
                int b = stack.pop();
                String op = curOperator.get(idx++);
                stack.push(calExp(b, a, op));
                
                if(idx == curOperator.size()){
                    return stack.pop();
                }
            }else{
                stack.push(numbers[i]);
                i++;
            }
        }

        return result;
    }
    
    private static int calExp(int a, int b, String op){
        if(op.equals("+")){
            return a + b;
        }else if(op.equals("-")){
            return a - b;
        }else if(op.equals("*")){
            return a * b;
        }else if (op.equals("/")){
            return a / b;
        }
        return 0;
    }
}
