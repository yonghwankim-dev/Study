package PROM.PROM_60057;

public class Solution2 {
	public int solution(String s)
	{
		int answer = 0; // 최소 문자열 압축 길이
		
		// i : 문자열 압축 단위
		for(int i = 1; i <= s.length() + 1; i++)
		{
			int result = getSplitedLength(s, i, 1).length();
			answer = i == 1 ? result : (answer > result ? result : answer);
		}
		return answer;
	}

	/**
	 * 
	 * @param s 압축하좌 하는 문자열
	 * @param n 문자열 압축 단위
	 * @param repeat 문자열의 반복횟수
	 * @return 압축한 문자열의 길이
	 */
	public String getSplitedLength(String s, int n, int repeat) {
		if(s.length()<n)
		{
			return s;
		}
		
		String result = "";
		String preString = s.substring(0,n); // s[0]~s[n-1] 까지의 문자열
		String postString = s.substring(n,s.length()); // 그외의 문자열
		
		// 불일치 -> 현재까지 [반복횟수 + 반복문자] 조합
		if(!postString.startsWith(preString))
		{
			if(repeat==1)
			{
				// 반복횟수가 1인경우 문자열(preString)을 앞에 붙이고 뒤에 문자열을 압축 수행
				return result += preString + getSplitedLength(postString, n, 1);
			}
			return result += Integer.toString(repeat) + preString + getSplitedLength(postString, n, 1);
		}
		// 일치 -> 반복횟수 증가, 그외의 문자열부터 다시 문자열 압축 시작
		return result += getSplitedLength(postString, n, repeat+1);
	}
	
	
}
