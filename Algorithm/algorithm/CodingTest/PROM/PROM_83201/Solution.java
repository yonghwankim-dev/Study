package PROM.PROM_83201;

public class Solution{
	public static String getGrade(int score)
	{
		if(score>=90 && score<=100)
		{
			return "A";
		}
		else if(score>=80 && score<90)
		{
			return "B";
		}
		else if(score>=70 && score<80)
		{
			return "C";
		}
		else if(score>=50 && score<70)
		{
			return "D";
		}
		else
		{
			return "F";
		}
	}
	
	// i번째 열 학생의 평가받은 점수집합의 평균을 게산
	// flag : true=>(i,i)번째 (자기평가점수 제외) 점수를 평균 계산에서 제외
	public static int getAverage(int[][] scores, int i, boolean flag)
	{
		int sum =0;
		int n = scores.length;
		for(int j=0;j<scores.length;j++)
		{
			if(flag && j==i)
			{
				n--;
				continue;
			}
			else 
			{
				sum += scores[j][i];
			}
		}
		return sum / n;
	}

	// 해당 점수가 N번째 학생의 평가받은 점수집합에서 2개이상 존재하는지 재탐색
	// scores : 2차원 점수 배열
	// i : i번째 열
	public static boolean isDuplicateNumber(int[][] scores, int i)
	{
		int count = 0;
		for(int j=0;j<scores[i].length;j++)
		{
			if(scores[j][i]==scores[i][i])
			{
				count++;
			}
		}
		return (count>=2 ? true : false);
	}
	
	// scores 첫번째 인덱스 (열) : N번째 학생이 평가된 점수집합
	// scores 두번째 인덱스 (행) : N번째 학생이 평가한 점수집합
    public static String solution(int[][] scores) {
        String answer = "";
        
        for(int i=0;i<scores.length;i++)
        {
        	// 0 <= score <= 100 이므로 최고점=-1, 최저점=101 초기화
        	int maxVal = -1;
        	int minVal = 101;
        	
        	// N번째 학생이 평가받은 점수집합에서 최고점과 최저점을 구함
        	for(int j=0;j<scores[i].length;j++)
        	{
        		if(scores[j][i]>maxVal)
        		{
        			maxVal = scores[j][i];
        		}
        		if(scores[j][i]<minVal)
        		{
        			minVal = scores[j][i];
        		}
        		
        	}
        	
        	// i번째 학생이 자기를 평가한 점수가 최고점이거나 최저점인지 검사한다.
        	if(scores[i][i]==maxVal || scores[i][i]==minVal)
        	{
        		if(isDuplicateNumber(scores, i))	// 최고점 또는 최저점이나 중복됨
        		{
        			answer += getGrade(getAverage(scores, i,false));
        			
        		}
        		else								// 최고점 또는 최저점이자 유일함
        		{
        			answer += getGrade(getAverage(scores, i,true));
        			
        		}
        	}else // 최고점 또는 최저점이 아님
        	{
        		answer += getGrade(getAverage(scores, i,false));
        	}
        }
        return answer;
    }
}
