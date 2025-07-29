package PROM.PROM_68935;

import java.util.ArrayList;
import java.util.List;

/**
 * step1 n�� 3���� ������ n�� 3������ ����
 * step2 n�� 3������ ����
 * step3 ������ 3������ 10������ ��ȯ
 * @author qkdlf
 *
 */
public class Solution {
	public static int solution(int n)
	{
		//step1 3���� ���ϱ� �� n�� 3���� ���� ����
		int n_ = n;
		List<Integer> list = new ArrayList<>();
		
		while(n_>0)
		{
			list.add(n_%3);
			n_ /= 3;
		}
		
		int answer = 0;
		for(int i=list.size()-1, k=0;i>=0;i--,k++)
		{
			answer += (list.get(i)*Math.pow(3, k));
		}
	
		return answer;
	}
	
	// �ٸ� ��� Ǯ��
    public static int solution2(int n) {
        String a = "";

        while(n > 0){
            a = (n % 3) + a;
            n /= 3;
        }
        a = new StringBuilder(a).reverse().toString();

        return Integer.parseInt(a,3);
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		solution(45);
		
	}

}

/* �ٸ� ��� Ǯ��
class Solution {

}
*/
