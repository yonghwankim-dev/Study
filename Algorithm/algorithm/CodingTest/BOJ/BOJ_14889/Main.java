package BOJ.BOJ_14889;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
	public static int min_val = Integer.MAX_VALUE;
	
	// 팀이 정해진 상태에서 팀원간의 조합 생성
	public static int backtracking2(int[][] board, boolean[] chosen, List<Integer> team, List<Integer> vc)
	{
		int sum = 0;
		if(vc.size() == 2)
		{
			return board[vc.get(0)][vc.get(1)];
		}
		
		for(int i=0; i<team.size(); i++)
		{
			if(chosen[i])
			{
				continue;
			}
			
			chosen[i] = true;
			vc.add(team.get(i));
			
			sum += backtracking2(board, chosen, team, vc);
			
			chosen[i] = false;
			vc.remove(vc.size()-1);	
		}
		return sum;
	}
	
	public static int getTeamTotalSynergy(int[][] board, List<Integer> team)
	{
		boolean[] chosen = new boolean[team.size()];
		List<Integer> vc = new ArrayList<Integer>();
		int teamTotalSynergy = 0;
		
		teamTotalSynergy = backtracking2(board, chosen, team, vc);
		
		return teamTotalSynergy;
	}
	
	// N명에 대한 팀 조합 생성
	public static void backtracking(int n, int[][] board, boolean[] chosen, List<Integer> person)
	{
		if(person.size() == n/2)
		{
			// 팀 생성
			List<Integer> startTeam = new ArrayList<Integer>();
			List<Integer> linkTeam = new ArrayList<Integer>();
			
			for(int i=1; i<=n; i++)
			{
				if(chosen[i])
				{
					startTeam.add(i);
				}
				else
				{
					linkTeam.add(i);
				}
			}
			
			// 팀간의 시너지 합 계산
			int startTeamTotalSynergy = getTeamTotalSynergy(board, startTeam); // start team
			int linkTeamTotalSynergy = getTeamTotalSynergy(board, linkTeam);   // link team
			
			// 최솟값 갱신
			min_val = Math.min(min_val, Math.abs(startTeamTotalSynergy-linkTeamTotalSynergy ));
			
			return;
		}
		for(int i=1; i<=n; i++)
		{
			if(chosen[i] || (person.size() >= 1 && i < person.get(person.size()-1)))
			{
				continue;
			}
			
			chosen[i] = true;	// 팀선택
			person.add(i);
			
			backtracking(n, board, chosen, person);
			
			chosen[i] = false;	// 팀선택 안함
			person.remove(person.size()-1);
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());	// 사람수
		int[][] board = new int[n+1][n+1];			// 사람간의 시너지 표
		boolean[] chosen = new boolean[n+1];		// 사람의 선택여부 배열
		List<Integer> person = new ArrayList<>();	// 선택된 사람 리스트
		
		String[] line;
		
		for(int i=1; i<=n; i++)
		{
			line = br.readLine().split(" ");
			for(int j=1; j<=n; j++)
			{
				board[i][j] = Integer.parseInt(line[j-1]);
			}
		}
		backtracking(n, board, chosen, person);
		System.out.println(min_val);
		
	}	
}
