package PROM.PROM_72412;

import java.util.Arrays;

// timeover
public class Solution {
	
	static class Condition{
		String lang;
		String position;
		String career;
		String soul;
		int score;
		
		public Condition(String lang, String position, String career, String soul, int score) {
			this.lang = lang;
			this.position = position;
			this.career = career;
			this.soul = soul;
			this.score = score;
		}
		
		public boolean isSatisfied(Condition candidate)
		{
			if((lang.equals(candidate.lang) || lang.equals("-"))
			&& (position.equals(candidate.position) || position.equals("-"))
			&& (career.equals(candidate.career) || career.equals("-"))
			&& (soul.equals(candidate.soul) || soul.equals("-"))
			&& score <= candidate.score)
			{
				return true;
			}
			return false;
		}
		
		public static Condition inputCondition(String line)
		{
			String[] c = line.split("and");
			String lang = c[0].trim();
			String position = c[1].trim();
			String career = c[2].trim();
	    	String[] soulAndscore = c[3].trim().split(" ");
	    	String soul = soulAndscore[0];
	    	int score = Integer.parseInt(soulAndscore[1]);
	    	
	    	Condition condition = new Condition(lang, position, career, soul, score);
	    	
			return condition;
		}
		
		public static Condition inputCandidate(String line)
		{
			String[] c = line.split(" ");
			String lang = c[0].trim();
			String position = c[1].trim();
			String career = c[2].trim();
			String soul = c[3].trim();
	    	int score = Integer.parseInt(c[4]);
	    	
	    	Condition condition = new Condition(lang, position, career, soul, score);
	    	
			return condition;
		}

		@Override
		public String toString() {
			return "Condition [lang=" + lang + ", position=" + position + ", career=" + career + ", soul=" + soul
					+ ", score=" + score + "]";
		}
		
	}
	
    public int[] solution(String[] info, String[] query) {
    	
    	int[] answer = new int[query.length];
    	int i = 0;
    	
    	for(String q : query)
    	{
    		Condition condition = Condition.inputCondition(q); 
    		int cnt = 0;
    		for(String in : info)
    		{
        		Condition candidate = Condition.inputCandidate(in);
        		
        		if(condition.isSatisfied(candidate))
        		{
        			cnt++;
        		}
    		}
    		answer[i++] = cnt; 
    	}
    	
        
        return answer;
    }
    
 	public static void main(String[] args)
	{
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
		
		System.out.println(Arrays.toString(
											new Solution()
											.solution(info, query)
										  ));
	}
}
