package PROM.PROM_120876;

class Solution {
    static class Line{
        int start;
        int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean contains(Line line){
            return line.start >= start && line.start <= end && line.end >= start && line.end <= end;
        }

        public int length(){
            return Math.abs(end - start);
        }

        @Override
        public String toString() {
            return String.format("[%d, %d]", start, end);
        }
    }

    public int solution(int[][] lines) {
        int answer = 0;

        for(int i = 0; i < lines.length; i++){
            Line line1 = new Line(lines[i][0], lines[i][1]);

            for(int j = i + 1; j < lines.length; j++){
                Line line2 = new Line(lines[j][0], lines[j][1]);

                Line commonLine = new Line(Math.max(line1.start, line2.start),
                                           Math.min(line1.end, line2.end));

                System.out.println("line1 : " + line1);
                System.out.println("line2 : " + line2);
                System.out.println("commonLine : " + commonLine);
                answer += line1.contains(commonLine) && line2.contains(commonLine) ? commonLine.length() : 0;
            }
        }
        return answer;
    }
}
