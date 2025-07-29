package PROM.PROM_120844;

class Solution {
    public int[] solution(int[] numbers, String direction) {
        if(direction.equals("left")){
            return moveLeftArray(numbers);
        }
        return moveRightArray(numbers);
    }

    private int[] moveLeftArray(int[] numbers){
        int n = numbers.length;
        int[] result = new int[n];
        for(int i = 0; i < n; i++){
            result[i] = numbers[(i + 1) % n];
        }
        return result;
    }

    private int[] moveRightArray(int[] numbers){
        int n = numbers.length;
        int[] result = new int[n];
        for(int i = 0; i < n; i++){
            result[(i + 1) % n] = numbers[i];
        }
        return result;
    }
}
