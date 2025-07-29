package PROM.PROM_120875;

class Solution {
    private static class Point{
        int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Line{
        double inclination;
        double yIntercept;

        public Line(Point p1, Point p2){
            this.inclination = getInclination(p1, p2);
            this.yIntercept = getYIntercept(p1, inclination);
        }

        private double getInclination(Point p1, Point p2){
            return (double) (p2.y - p1.y) / (double) (p2.x - p1.x);
        }

        private double getYIntercept(Point p1, double inclination){
            return p1.y  - inclination * p1.x;
        }

        private boolean isParallel(Line line){
            return this.inclination == line.inclination &&
                    (this.yIntercept != line.yIntercept || this.yIntercept == line.yIntercept);
        }
    }

    public int solution(int[][] dots) {
        int answer = 0;
        Point p1 = new Point(dots[0][0], dots[0][1]);
        Point p2 = new Point(dots[1][0], dots[1][1]);
        Point p3 = new Point(dots[2][0], dots[2][1]);
        Point p4 = new Point(dots[3][0], dots[3][1]);

        Line line1 = new Line(p1, p2);
        Line line2 = new Line(p3, p4);

        Line line3 = new Line(p1, p3);
        Line line4 = new Line(p2, p4);

        Line line5 = new Line(p1, p4);
        Line line6 = new Line(p2, p3);

        answer = line1.isParallel(line2) ||
                 line3.isParallel(line4) ||
                 line5.isParallel(line6) ? 1 : 0;
        return answer;
    }
}
