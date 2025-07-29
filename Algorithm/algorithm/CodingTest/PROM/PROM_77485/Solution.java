package PROM.PROM_77485;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    static class Point{
        int y, x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static class Vertex{
        Point leftUp;
        Point rightUp;
        Point rightDown;
        Point leftDown;

        public Vertex(Point leftUp, Point rightUp, Point rightDown, Point leftDown) {
            this.leftUp = leftUp;
            this.rightUp = rightUp;
            this.rightDown = rightDown;
            this.leftDown = leftDown;
        }
    }

    private static int[][] matrix;
    private static Point start, end;
    private static Vertex vertex;

    public static int[] solution(int rows, int columns, int[][] queries) {
        List<Integer> answer = new ArrayList<>();

        // ��� ����
        matrix = createMatrix(rows, columns);

        for(int[] query : queries){
            // ������ ����
            start = new Point(query[0],query[1]);
            end = new Point(query[2],query[3]);

            // ������ ����
            vertex = getVertex();

            // ������ ���� ���ڵ� ����
            List<Integer> list = parseMatrix();

            // ����Ʈ�� ��ĭ�� �̵�
            move(list);

            // ����Ʈ�� �ּڰ� ����
            int min = getMinVal(list);

            // �̵��� ����Ʈ�� ��Ŀ� �ٽ� �ݿ�
            updateMatrix(list);

            // answer�� �߰�
            answer.add(min);
        }


        return toIntArray(answer);
    }

    private static int[][] createMatrix(int rows, int columns){
        int[][] result = new int[rows + 1][columns + 1];

        int cnt = 1;
        for(int i = 1; i <= rows; i++){
            for(int j = 1; j <= columns; j++){
                result[i][j] = cnt;
                cnt++;
            }
        }
        return result;
    }

    private static Vertex getVertex(){
        Point leftUp    = new Point(start.y, start.x);
        Point rightUp   = new Point(start.y, end.x);
        Point rightDown = new Point(end.y, end.x);
        Point leftDown  = new Point(end.y, start.x);
        return new Vertex(leftUp, rightUp, rightDown, leftDown);
    }

    private static List<Integer> parseMatrix() {
        int i = start.y;
        int j = start.x;
        List<Integer> list = new ArrayList<>();

        // ���������� �̵�
        Point rightUp = vertex.rightUp;
        while(j < rightUp.x){
            list.add(matrix[i][j]);
            j++;
        }
        // �Ʒ��� �̵�
        Point rightDown = vertex.rightDown;
        while(i < rightDown.y){
            list.add(matrix[i][j]);
            i++;
        }

        // �������� �̵�
        Point leftDown = vertex.leftDown;
        while(j > leftDown.x){
            list.add(matrix[i][j]);
            j--;
        }

        // ���� �̵�
        Point leftUp = vertex.leftUp;
        while(i > leftUp.y){
            list.add(matrix[i][j]);
            i--;
        }

        return list;
    }

    private static void move(List<Integer> list){
        Collections.rotate(list, 1);
    }

    private static int getMinVal(List<Integer> list){
        return Collections.min(list);
    }

    private static void updateMatrix(List<Integer> list){
        int i = start.y;
        int j = start.x;
        int idx = 0;
        // ���������� �̵�
        Point rightUp = vertex.rightUp;
        while(j < rightUp.x){
            matrix[i][j] = list.get(idx);
            j++;
            idx++;
        }
        // �Ʒ��� �̵�
        Point rightDown = vertex.rightDown;
        while(i < rightDown.y){
            matrix[i][j] = list.get(idx);
            i++;
            idx++;
        }

        // �������� �̵�
        Point leftDown = vertex.leftDown;
        while(j > leftDown.x){
            matrix[i][j] = list.get(idx);
            j--;
            idx++;
        }

        // ���� �̵�
        Point leftUp = vertex.leftUp;
        while(i > leftUp.y){
            matrix[i][j] = list.get(idx);
            i--;
            idx++;
        }
    }

    private static int[] toIntArray(List<Integer> list){
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
