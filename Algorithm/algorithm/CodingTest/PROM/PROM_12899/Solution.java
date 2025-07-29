package PROM.PROM_12899;

/**
 * 124 ������ ����
 * 124 ���󿡼��� 1, 2, 4 ���ڸ� ����Ѵ�.
 * �� 3�����̴�. ������ ���� 3������ 0,1,2�� ���������
 * 124 ���󿡼��� 1, 2, 4�� ����Ѵ�.
 * ������ n�� 3�� ������ �������� 1�� �ø� �����ؾ� ������
 * 124 ���󿡼��� -1�� �ؾ� �Ѵ�.
 * 
 * nums�� ������ 4, 1, 2�� ������
 * 124���󿡼� 1 -> 1, 2 -> 2 �̴�.
 * 1%3 = 1 -> 1��° �ڸ�
 * 2%3 = 2 -> 2��° �ڸ�
 * ���� ���� �ε��� ��ȣ�� ���߾� �迭�Ͽ���.
 * 
 * @author qkdlf
 *
 */

public class Solution{
	
    public static String solution(int n) {
        String answer = "";
        
        String[] nums = {"4","1","2"};
        
        while(n!=0)
        {
        	answer = nums[n%3] + answer;
        	n = n/3 - (n%3==0 ? 1 : 0);
        }
        return answer;
    }
    
	public static void main(String args[])
	{
		System.out.println(solution(6));
	}
}
