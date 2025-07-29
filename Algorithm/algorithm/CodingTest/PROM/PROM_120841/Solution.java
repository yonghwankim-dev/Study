package PROM.PROM_120841;

class Solution {
    public int solution(int[] dot) {
        int x = dot[0];
        int y = dot[1];

        if(isFirstQuadrant(x, y)){
            return 1;
        }
        if(isSecondQuadrant(x, y)){
            return 2;
        }
        if(isThirdQuadrant(x, y)){
            return 3;
        }
        return 4;
    }

    private boolean isFirstQuadrant(int x, int y){
        return x > 0 && y > 0;
    }

    private boolean isSecondQuadrant(int x, int y){
        return x < 0 && y > 0;
    }

    private boolean isThirdQuadrant(int x, int y){
        return x < 0 && y < 0;
    }
}
