package PROM.PROM_60057;



class Solution {
	private static String s;

	public static int solution(String s) {
		int encapsulateLen;
		int min = s.length();
		int limit = s.length() / 2;
		Solution.s = s;

		for(int sepUnit = 1; sepUnit <= limit; sepUnit++)
		{
			encapsulateLen = encapsulate(sepUnit);
			min = Math.min(min, encapsulateLen);
		}

		return min;
	}

	// 압축한 문자열의 길이를 반환
	public static int encapsulate(int sepUnit)
	{
		int start = 0;
		int repeat = 1;
		int next_start, next_end;
		StringBuilder sb = new StringBuilder();
		String sub;

		while(start < s.length())
    	{
    		if(start + sepUnit >= s.length()) 	      // start + sepUnit(압축단위)가 마지막 문자까지 탐색했는지 검사
    		{
    			sub = s.substring(start);
				sub = isMoreThan2(repeat) ? repeat+sub : sub ;
    			sb.append(sub);
    			break;
    		}
			sub = s.substring(start, start + sepUnit); // s[start] ~ s[start + sepUnit-1]까지 추출하여 저장

    		next_start = start + sepUnit;
    		next_end = next_start + sepUnit;
    		
    		// 현재 문자열의 다음 부분 문자열을 추출
    		String next_sub = getNextSubString(start, next_start, next_end);

    		if(sub.equals(next_sub)){
    			repeat++;
    		}else{
				String result = repeat >= 2 ? repeat+sub : sub;
				repeat = 1;
				sb.append(result);
			}
//			repeat = sub.equals(next_sub) ? repeat + 1 : 1;
    		start += sepUnit; // 다음 문자열 세트로 이동
    	}
		return sb.length();
	}

	private static String getNextSubString(int start, int next_start, int next_end){
		return isInside(next_end) ? getSubString(next_start, next_end) : getSubString(start);
	}

	private static boolean isMoreThan2(int repeat){
		return repeat >= 2;
	}

	private static boolean isInside(int idx){
		return idx <= s.length();
	}

	private static String getSubString(int start){
		return getSubString(start, s.length());
	}

	private static String getSubString(int start ,int end){
		return s.substring(start, end);
	}
}
