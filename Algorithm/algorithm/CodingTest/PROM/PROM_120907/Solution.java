package PROM.PROM_120907;

import java.util.Stack;

class Solution {
    public String[] solution(String[] quiz) {
        int n = quiz.length;
        String[] answer = new String[n];
        int i = 0;

        for(String q : quiz){
            String[] expAndAnswer = getExpAndAnswer(q);
            String infixExp = getExp(expAndAnswer);
            int expAnswer = getExpAnswer(expAndAnswer);
            String postfixExp = infixToPostfix(infixExp.split(" "));
            int calc = calc(postfixExp);
            answer[i++] = matchCalcAnswer(calc, expAnswer);
        }
        return answer;
    }



    public String[] getExpAndAnswer(String text){
        return text.split("=");
    }

    public String getExp(String[] expAndAnswer){
        return expAndAnswer[0].trim();
    }

    public int getExpAnswer(String[] expAndAnswer){
        return Integer.parseInt(expAndAnswer[1].trim());
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

    private String matchCalcAnswer(int calc, int expAnswer) {
        return calc == expAnswer ? "O" : "X";
    }
}
