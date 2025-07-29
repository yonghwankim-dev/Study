package PROM.PROM_120864;

import java.util.Arrays;

class Solution {
    public int solution(String my_string) {
        String[] numbers = my_string.replaceAll("[^0-9]", " ").split(" ");

        int answer = 0;
        System.out.println(Arrays.toString(numbers));
        for(int i = 0; i < numbers.length; i++){
            if(numbers[i].equals("")){
                continue;
            }
            answer += Integer.parseInt(numbers[i].trim());
        }
        return answer;
    }
}
