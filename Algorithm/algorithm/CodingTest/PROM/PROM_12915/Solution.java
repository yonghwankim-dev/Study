package PROM.PROM_12915;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solution {

	public static String[] solution(String[] strings, int n) {
        String[] answer = {};
        List<String> list = Arrays.asList(strings);
        
        Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				if(o1.charAt(n)==o2.charAt(n))
				{
					return o1.compareTo(o2);
				}
				else
				{
					return o1.charAt(n)-o2.charAt(n);
				}
				
			}
		});
        
        answer = list.toArray(new String[0]);
        
        return answer;
    }
	public String[] solution2(String[] strings, int n) {
	      Arrays.sort(strings, new Comparator<String>(){
	          @Override
	          public int compare(String s1, String s2){
	              if(s1.charAt(n) > s2.charAt(n)) return 1;
	              else if(s1.charAt(n) == s2.charAt(n)) return s1.compareTo(s2);
	              else if(s1.charAt(n) < s2.charAt(n)) return -1;
	              else return 0;
	          }
	      });
	      return strings;
	  }
	public static void main(String[] args)
	{
		String[] strings = {"abce","abcd","cdx"};
		System.out.println(Arrays.toString(solution(strings, 1)));
	}
}
