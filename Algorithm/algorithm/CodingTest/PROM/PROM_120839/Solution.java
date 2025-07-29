package PROM.PROM_120839;

class Solution {
    public String solution(String rsp) {
        StringBuilder sb = new StringBuilder();
        String scissors = "2";
        String rock = "0";
        String cloth = "5";
        String[] rspArray = rsp.split("");

        for(String r : rspArray){
            if(r.equals(scissors)){
                sb.append(rock);
            }else if(r.equals(rock)){
                sb.append(cloth);
            }else if(r.equals(cloth)){
                sb.append(scissors);
            }
        }

        return sb.toString();
    }
}
