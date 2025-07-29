package PROM.PROM_150366;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
	static class Point{
		int r;
		int c;

		public Point(int r, int c){
			this.r = r;
			this.c = c;
		}
	}

	Point[][] merge;
	String[][] contents;

	public String[] solution(String[] commands) {
		List<String> answer = new ArrayList<>();
		this.merge = new Point[51][51];
		this.contents = new String[51][51];
		int r1, c1, r2, c2;
		String content;
		for(String command : commands){
			String[] info = command.split(" ");
			String op = info[0];

			if(op.equals("UPDATE") && info.length == 4){
				r1 = Integer.parseInt(info[1]);
				c1 = Integer.parseInt(info[2]);
				content = info[3];
				update(r1, c1, content);
			}else if(op.equals("UPDATE") && info.length == 3){
				String value1 = info[1];
				String value2 = info[2];
				update(value1, value2);
			}else if(op.equals("MERGE")){
				r1 = Integer.parseInt(info[1]);
				c1 = Integer.parseInt(info[2]);
				r2 = Integer.parseInt(info[3]);
				c2 = Integer.parseInt(info[4]);
				merge(r1, c1, r2, c2);
			}else if(op.equals("UNMERGE")){
				r1 = Integer.parseInt(info[1]);
				c1 = Integer.parseInt(info[2]);
				unmerge(r1, c1);
			}else if(op.equals("PRINT")){
				r1 = Integer.parseInt(info[1]);
				c1 = Integer.parseInt(info[2]);
				if(contents[r1][c1] == null){
					answer.add("EMPTY");
					continue;
				}
				answer.add(contents[r1][c1]);
			}
		}
		return answer.toArray(String[]::new);
	}

	private void update(int r, int c, String content){
		Point point = merge[r][c];
		if (point == null){
			point = new Point(r, c);
			merge[r][c] = point;
		}
		contents[point.r][point.c] = content;
	}

	private void update(String value1, String value2){
		for(int i = 1; i < contents.length; i++){
			for(int j = 1; j < contents[i].length; j++){
				if(contents[i][j] != null && contents[i][j].equals(value1)){
					contents[i][j] = value2;
				}
			}
		}
	}

	private void merge(int r1, int c1, int r2, int c2){
		Point point1 = merge[r1][c1];
		Point point2 = merge[r2][c2];
		if(contents[point1.r][point1.c] != null && contents[point2.r][point2.c] != null){
			contents[point2.r][point2.c] = contents[point1.r][point1.c];
		}else if(contents[point1.r][point1.c] != null && contents[point2.r][point2.c] == null){
			contents[point2.r][point2.c] = contents[point1.r][point1.c];
		}else if(contents[point1.r][point1.c] == null && contents[point2.r][point2.c] != null){
			contents[point1.r][point1.c] = contents[point2.r][point2.c];
		}
		merge[r1][c1] = new Point(r1, c1);
		merge[r2][c2] = new Point(r1, c1);
	}

	private void unmerge(int r, int c){
		Point point = merge[r][c];
		String content = contents[point.r][point.c];

		for(int i = 1; i < merge.length; i++){
			for(int j = 1; j < merge[i].length; j++){
				Point target = merge[i][j];
				if(target != null && target.r == point.r && target.c == point.c){
					merge[i][j] = null;
					contents[i][j] = null;
				}
			}
		}
		contents[r][c] = content;
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
		String[] commands = {"UPDATE 1 1 a", "UPDATE 1 2 b", "UPDATE 2 1 c", "UPDATE 2 2 d", "MERGE 1 1 1 2", "MERGE 2 2 2 1", "MERGE 2 1 1 1", "PRINT 1 1", "UNMERGE 2 2", "PRINT 1 1"};
		String[] answer = solution.solution(commands);
		System.out.println(Arrays.toString(answer));
	}
}
