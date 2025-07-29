package PROM.PROM_81302;

import java.util.*;

class Solution {

	static class Point{
		int y, x;

		public Point(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}

	static final String CANDIDATE   = "P";
	static final String EMPTY_TABLE = "O";
	static final String PARTITION   = "X";
	static final int[][] direction = {{-1,0}, {1,0}, {0,-1}, {0,1}}; // 상하좌우

	public String[][] inputArray(String[] place){
		String[][] wait_room = new String[5][5];

		for(int i = 0; i < wait_room.length; i++){
			wait_room[i] = place[i].split("");
		}
		return wait_room;
	}

	public boolean checkRange(String[][] wait_room, int ny, int nx, boolean[][] visited){
		if(ny < 0 || ny >= 5 || nx < 0 || nx >= 5 || visited[ny][nx]){
			return false;
		}
		return true;
	}

	// 한 응시자를 기준으로 거리두기가 되었는지 탐색
	public boolean bfs(String[][] wait_room, Point start, boolean[][] visited, int[][] distance){
		Queue<Point> queue = new LinkedList<>();

		queue.add(start);
		visited[start.y][start.x] = true;

		while(!queue.isEmpty()){
			Point p = queue.poll();

			for(int i = 0; i < 4; i++) {
				int ny = p.y + direction[i][0];
				int nx = p.x + direction[i][1];

				if (checkRange(wait_room, ny, nx, visited)) {
					if (wait_room[ny][nx].equals(EMPTY_TABLE)) {
						queue.add(new Point(ny, nx));
						visited[ny][nx] = true;
						distance[ny][nx] = distance[p.y][p.x] + 1;
					}

					if (wait_room[ny][nx].equals(CANDIDATE) &&
							distance[p.y][p.x] <= 1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	// wait_room에서 응시자의 위치를 리스트에 넣어서 반환
	public List<Point> getCandidatePosition(String[][] wait_room){
		List<Point> starts = new ArrayList<>();

		for(int i = 0; i < wait_room.length; i++){
			for(int j = 0; j <wait_room[i].length; j++){
				if(wait_room[i][j].equals(CANDIDATE)){
					starts.add(new Point(i,j));
				}
			}
		}
		return starts;
	}

	// 하나의 대기실을 확인하여 거리두기가 되었는지 검사
	public boolean checkWaitRoom(String[][] wait_room){
		List<Point> starts = getCandidatePosition(wait_room);

		for(Point start : starts){
			boolean[][] visited = new boolean[5][5];
			int[][] distance = new int[5][5];

			if(!bfs(wait_room, start, visited, distance)){
				return false;
			}
		}
		return true;
	}

	public void print(String[][] arr){
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}

	public int[] solution(String[][] places) {
		int[] answer = new int[places.length];
		int i = 0;

		for(String[] place : places){
			String[][] wait_room = inputArray(place);

			if(checkWaitRoom(wait_room)){
				answer[i] = 1;
			}else {
				answer[i] = 0;
			}
			i++;
		}
		return answer;
	}

	public static void main(String[] args){
		String[][] places = {{"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"},
							 {"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"},
							 {"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"},
							 {"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"},
							 {"PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"}};
//		String[][] places = {{"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"}};
		int[] answer = new Solution().solution(places);
		System.out.println(Arrays.toString(answer));
	}
}
