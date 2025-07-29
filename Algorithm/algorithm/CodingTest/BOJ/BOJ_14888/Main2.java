package BOJ.BOJ_14888;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
	static boolean[] choosen = new boolean[5];
	static int N = 5;
	public static void backtracking(List<String> v, List<String> vc, int pos)
	{
		if(vc.size()==N)
		{
			System.out.println();
			for(String i : vc)
			{
				System.out.print(i + " ");
			}
			return;
		}
		
		for(int i=0; i<v.size(); i++)
		{
			if(choosen[i])
			{
				continue;
			}
			
			choosen[i] = true;
			vc.add(v.get(i));
			
			backtracking(v, vc, pos+1);
			
			choosen[i] = false;
			vc.remove(vc.size()-1);
		}
	}
	public static void main(String[] args) {
		List<String> v = new ArrayList<String>();
		v.add("+");
		v.add("+");
		v.add("-");
		v.add("*");
		v.add("/");
		
		List<String> vc = new ArrayList<String>();
		backtracking(v, vc, 0);
	}

}
