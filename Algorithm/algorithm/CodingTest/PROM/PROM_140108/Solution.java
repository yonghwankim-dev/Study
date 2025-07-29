package PROM.PROM_140108;

public class Solution {
    public int solution(String s) {
        int answer = 0;
        String[] strings = s.split("");
        String first = "";
        String target = "";
        int firstCnt = 0;
        int otherCnt = 0;

        for(int i = 0; i < strings.length; i++){
            target = strings[i];

            if(first.equals("")){
                first = target;
                firstCnt++;
                continue;
            }

            if(first.equals(target)){
                firstCnt++;
            }
            if(!first.equals(target)){
                otherCnt++;
            }

            if(firstCnt == otherCnt){
                first = "";
                firstCnt = 0;
                otherCnt = 0;
                answer++;
            }
        }

        if(!first.equals("")){
            answer++;
        }


        return answer;
    }
}
