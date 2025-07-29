package PROM.PROM_67256;

public class Solution {

    enum KeyPad{
        ONE(0,0,   "1"),
        TWO(0,1,   "2"),
        THREE(0,2, "3"),
        FOUR(1,0,  "4"),
        FIVE(1,1,  "5"),
        SIX(1,2,   "6"),
        SEVEN(2,0, "7"),
        EIGHT(2,1, "8"),
        NINE(2,2,  "9"),
        STAR(3,0,  "*"),
        ZERO(3,1,  "0"),
        HASH(3,2,  "#");

        private final int y;
        private final int x;
        private final String num;

        KeyPad(int y, int x, String num) {
            this.y = y;
            this.x = x;
            this.num = num;
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }

        public String getNum() {
            return num;
        }

        public static KeyPad convertIntToKeypad(int number){

            for(KeyPad k : KeyPad.values()){
                if(k.num.equals(String.valueOf(number))){
                    return k;
                }
            }
            return null;
        }
    }

    enum Direction{
        LEFT("L"), RIGHT("R");

        private final String value;

        Direction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    static class Hand{
        private int y;
        private int x;

        public Hand(KeyPad keyPad){
            this.y = keyPad.getY();
            this.x = keyPad.getX();
        }

        // 입력받은 y, x로 값을 변경
        private void changePosition(int y, int x){
            this.y = y;
            this.x = x;
        }

        // 현재 엄지손을 키패드의 숫자에 위치한 곳으로 이동
        public void move(KeyPad dest){
            changePosition(dest.getY(), dest.getX());
        }

        // 두 엄지손중 거리가 가까운 쪽을 판단,
        // 양수인 경우 왼손이 더 근접하고 음수인 경우 오른손이 더 급접함
        public static int comparePosition(Hand hand1, Hand hand2, KeyPad dest){
            return hand2.getDistance(dest) - hand1.getDistance(dest);
        }

        // 현재 엄지손의 위치와 입력받은 숫자 키패드간의 거리를 계산
        private int getDistance(KeyPad dest){
            return Math.abs(this.y - dest.getY()) + Math.abs(this.x - dest.getX());
        }
    }

    // 누르고자 하는 번호가 왼쪽 방향인지 확인
    public static boolean isLeftDirection(int number){
        return number == 1 || number == 4 || number == 7;
    }

    // 누르고자 하는 번호가 오른쪽 방향인지 확인
    public static boolean isRightDirection(int number){
        return number == 3 || number == 6 || number == 9;
    }

    public String solution(int[] numbers, String hand) {
        String answer = "";
        Hand leftHand = new Hand(KeyPad.STAR);
        Hand rightHand = new Hand(KeyPad.HASH);
        hand = hand.toUpperCase();

        for(int number : numbers){
            KeyPad dest = KeyPad.convertIntToKeypad(number);

            // 누르고자 하는 번호가 왼쪽 열인 경우
            if(isLeftDirection(number)){
                answer += Direction.LEFT.value;
                leftHand.move(dest);
                continue;
            }
            // 누르고자 하는 번호가 오른쪽 열인 경우
            else if(isRightDirection(number)){
                answer += Direction.RIGHT.value;
                rightHand.move(dest);
                continue;
            }

            // 누르고자 하는 번호가 가운데 열이고 왼쪽이 더 가까운 경우
            if(Hand.comparePosition(leftHand, rightHand, dest) > 0){
                answer += Direction.LEFT.value;
                leftHand.move(dest);
                continue;
            }
            // 누르고자 하는 번호가 가운데 열이고 오른쪽이 더 가까운 경우
            else if(Hand.comparePosition(leftHand, rightHand, dest) < 0){
                answer += Direction.RIGHT.value;
                rightHand.move(dest);
                continue;
            }

            // 누르고자 하는 번호가 가운데 열이고 왼손엄지와 오른손엄지간의 번호와의 거리가 동일한 경우
            if(Direction.valueOf(hand) == Direction.LEFT){
                leftHand.move(dest);
            }else{
                rightHand.move(dest);
            }
            answer += Direction.valueOf(hand).value;
        }

        return answer;
    }
}
