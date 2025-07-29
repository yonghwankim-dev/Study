package PROM.PROM_120871;

class OtherSolution {
    public int solution(int n){
        int answer = 0;
        for(int i = 1; i <= n; i++){
            answer = get3xNationNumber(answer + 1);
        }
        return answer;
    }

    private int get3xNationNumber(int n){
        if(isDividedBy3(n) || isContain3(n)){
            return get3xNationNumber(n + 1);
        }
        return n;
    }

    private boolean isDividedBy3(int n){
        return n % 3 == 0;
    }

    private boolean isContain3(int n){
        return String.valueOf(n).contains("3");
    }
}
