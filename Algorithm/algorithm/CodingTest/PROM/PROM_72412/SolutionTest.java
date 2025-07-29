package PROM.PROM_72412;


import org.junit.Test;

import java.util.Arrays;



class SolutionTest {

	@Test
	void test1() {
		int info_n = 50000;
		int query_n = 100000;
		
		String[] info = new String[info_n];
		String[] query = new String[query_n];
		
		for(int i = 0; i < info_n; i++)
		{
			info[i] = "java backend junior pizza 150";
		}
		
		for(int i = 0; i < query_n; i++)
		{
			query[i] = "java and backend and junior and pizza 100";
		}
		
		System.out.println(new Solution().solution(info, query));
		
	}

	@Test
	void test2() {
		String[] info = {"java backend junior pizza 150"
				   , "python frontend senior chicken 210"
				   ,"python frontend senior chicken 150"
				   ,"cpp backend senior pizza 260"
				   ,"java backend junior chicken 80"
				   ,"python backend senior chicken 50"
				   };
	
		String[] query = {"java and backend and junior and pizza 100"
			        , "python and frontend and senior and chicken 200"
			        , "cpp and - and senior and pizza 250"
			        , "- and backend and senior and - 150"
			        , "- and - and - and chicken 100"
			        , "- and - and - and - 150"
			        };
	
		System.out.println(Arrays.toString(new Solution2().solution(info, query)));

	}

}
