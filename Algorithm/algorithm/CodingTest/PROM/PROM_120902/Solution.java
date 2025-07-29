package PROM.PROM_120902;

import java.util.Stack;

class Solution {
    public int solution(String my_string) {
        int answer = 0;
        String postfix = infixToPostfix(my_string.split(" "));
        answer = calc(postfix);
        return answer;
    }


    public String infixToPostfix(String[] exp){
        Stack<String> op = new Stack<>();
        String result = "";

        for(String e : exp){
            if(e.equals("+") || e.equals("-")){
                while(!op.isEmpty()){
                    result += (op.pop() + " ");
                }
                op.push(e);
                continue;
            }
            result += (e + " ");
        }

        while(!op.isEmpty()){
            result += (op.pop() + " ");
        }
        result = result.trim();
        return result;
    }

    public int calc(String postfix) {
        Stack<Integer> operand = new Stack<>();
        int op1, op2;

        for(String s : postfix.split(" ")){
            if(s.equals("+")){
                op1 = operand.pop();
                op2 = operand.pop();
                operand.push(op1 + op2);
            }else if(s.equals("-")){
                op1 = operand.pop();
                op2 = operand.pop();
                operand.push(op2 - op1);
            }else{
                operand.push(Integer.parseInt(s));
            }
        }
        return operand.pop();
    }
}
