package PROM.PROM_120886;

class Solution {
    public int solution(String before, String after) {
        String[] beforeSplit = before.split("");

        for(String s : beforeSplit){
            after = after.indexOf(s) != -1 ? after.replaceFirst(s, "") : after;
        }
        return after.length() == 0 ? 1 : 0;
    }
}
