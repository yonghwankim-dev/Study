package PROM.PROM_17687;

class Solution {
    private static StringBuilder answer;

    /**
     *
     * @param n n����
     * @param t �̸� ���� ������ ����
     * @param m �ο���
     * @param p Ʃ���� ����
     * @return
     */
    public static String solution(int n, int t, int m, int p) {
        int num = 0;
        int turn = 0;
        answer = new StringBuilder();

        while(isLessThanT(t)){
            String text = convertDecimalToNRadix(num, n);

            for(char val : text.toCharArray()){
                if(isTubeTurn(turn + 1, p)){
                    writeResult(val);
                }
                turn = getNextTurn(turn, m);
            }
            num++;
        }

        return answer.substring(0, t);
    }

    private static boolean isLessThanT(int t){
        return answer.length() < t;
    }

    private static String convertDecimalToNRadix(int num, int radix){
        return toUppercase(Integer.toString(num, radix));
    }

    private static String toUppercase(String text){
        return text.toUpperCase();
    }

    private static boolean isTubeTurn(int turn, int p){
        return turn == p;
    }

    private static void writeResult(char val){
        answer.append(val);
    }

    private static int getNextTurn(int turn, int m){
        return (turn + 1) % m;
    }
}
