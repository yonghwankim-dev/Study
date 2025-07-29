package PROM.PROM_85002;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solution{
	static class Player{
		int num;
		String record;
		double win_rate;
		int win_cnt_heaviy;	// 자기보다 무거운 복서를 이긴 횟수
		int weight;
		
		public Player(int num, String record, double win_rate, int win_cnt_heaviy, int weight) {
			this.num = num;
			this.record = record;
			this.win_rate = win_rate;
			this.win_cnt_heaviy = win_cnt_heaviy;
			this.weight = weight;
		}

		@Override
		public String toString() {
			return "Player [num=" + num + ", record=" + record + ", win_rate=" + win_rate + ", win_cnt_heaviy="
					+ win_cnt_heaviy + ", weight=" + weight + "]";
		}
		
		
	}
	
	public static double get_winRate(String record)
	{
		int win = 0;
		String record_ = record.replace("N", "");
		
		for(int i=0;i<record_.length();i++)
		{
			if(record_.charAt(i)=='W')
			{
				win++;
			}
		}
		
		// 설명 역량 부족
		return (record_.length()==0 ? 0.0 : win*1000.0/record_.length());
		
	}
	
	public static int get_winCntHeaviy(int num, int[] weights, String record)
	{
		int win_count = 0;
		for(int i=0;i<record.length();i++)
		{
			if(record.charAt(i)=='W')
			{
				if(weights[num]<weights[i])
				{
					win_count++;
				}
			}
		}
		return win_count;
	}
	
    public static int[] solution(int[] weights, String[] head2head) {
        int[] answer = {};
        List<Integer> answer_list = new ArrayList<Integer>();
        
        
        List<Player> players = new ArrayList<>();
        
        for(int i=0;i<weights.length;i++)
        {
        	players.add(new Player(i+1, head2head[i], get_winRate(head2head[i]), get_winCntHeaviy(i, weights, head2head[i]), weights[i]));
        }
        
        
        
        Comparator<Player> compara_winRate_desc = new Comparator<Player>() {
			
			@Override
			public int compare(Player p1, Player p2) {
				return Double.compare(p2.win_rate,p1.win_rate);
			}
		};
		Comparator<Player> compara_winCntHeaviy_desc = new Comparator<Player>() {
			
			@Override
			public int compare(Player p1, Player p2) {
				return Integer.compare(p2.win_cnt_heaviy,p1.win_cnt_heaviy);
			}
		};
		
		Comparator<Player> compara_weight_desc = new Comparator<Player>() {
			
			@Override
			public int compare(Player p1, Player p2) {
				return Integer.compare(p2.weight,p1.weight);
			}
		};
		Comparator<Player> compara_num_asc = new Comparator<Player>() {
			
			@Override
			public int compare(Player p1, Player p2) {
				return Integer.compare(p1.num, p2.num);
			}
		};
		
		Collections.sort(players, 
				compara_winRate_desc
				.thenComparing(compara_winCntHeaviy_desc)
				.thenComparing(compara_weight_desc)
				.thenComparing(compara_num_asc));
		
        players.forEach(p -> answer_list.add(p.num));   
        answer = answer_list.stream().mapToInt(i->i).toArray();
        return answer;
    }
    
	public static void main(String args[])
	{
		int[] weights = {50,82,75,120};
		String[] head2head = {"NLWL","WNLL","LWNW","WWLN"};
		System.out.println(Arrays.toString(solution(weights, head2head)));
	}
}
