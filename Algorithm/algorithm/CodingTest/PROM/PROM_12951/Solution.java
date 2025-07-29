package PROM.PROM_12951;

class Solution {

    public static String solution(String s) {
        String answer = "";
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < s.length(); i++){
            char target = s.charAt(i);
            if(firstChracter(i) && isLower(target)){
                sb.append(toUpperCase(target));
            }else if(s.charAt(i-1) == ' ' && isLower(target)){
                sb.append(toUpperCase(target));
            }else{
                sb.append(target);
            }
        }

        answer = sb.toString();
        return answer;
    }

    private static boolean isLower(char ch){
        return Character.isLowerCase(ch);
    }

    private static boolean firstChracter(int i){
        return i == 0;
    }

    private static String toUpperCase(char ch){
        return String.valueOf(ch).toUpperCase();
    }

    public static void main(String[] args){
        System.out.println(new Solution().solution("3people unFollowed me"));
        System.out.println(new Solution().solution("for the last week"));
        System.out.println(new Solution().solution("for the  last week"));

    }
}
