package recursion.recursion13_twosum;

public class Main {

	// 2-SUM, recursion 버전
	// data[begin] ~ data[end] 사이에서 합이 k가 되는 쌍이 존재하는지 검사
	// data 배열은 오름차순 정렬되어 있다고 가정
	// 명시적으로 시작점(begin)과 종료점(end)을 설정하여 호출
	public static boolean twoSum(int[] data, int begin, int end, int k)
	{
		// 만약 중복 선택이 가능하다면 "="을 빼면 됨
		if(begin>=end)
		{
			return false;
		}
		else 
		{
			if(data[begin]+data[end]==k)
			{
				return true;
			}
			else if(data[begin]+data[end]<k)
			{
				return twoSum(data, begin+1, end, k);
			}
			else
			{
				return twoSum(data, begin, end-1, k);	
			}
		}	
	}
	
	public static void main(String[] args) {
		int[] data = {1,2,3,4,5};
		System.out.println(Main.twoSum(data, 0, data.length-1, 3));	// Expected Output : true
	}
}
