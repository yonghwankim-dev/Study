package PROM.PROM_120834;

class Solution {
    public String solution(int age) {
        StringBuilder sb = new StringBuilder();
        String str_age = String.valueOf(age);
        String[] split = str_age.split("");

        for(String s : split){
            sb.append(toAge(Integer.parseInt(s)));
        }
        return sb.toString();
    }

    private char toAge(int num){
        return (char) (num + 97);
    }
}
