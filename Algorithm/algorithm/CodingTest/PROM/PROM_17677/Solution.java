package PROM.PROM_17677;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class Solution {

	private static final String isApahabet = "^[a-zA-Z]*$";

	public static int solution(String str1, String str2) {
		int answer = 0;
		String s1 = toUppercase(str1);
		String s2 = toUppercase(str2);

		// 다중집합
		List<String> first  = getMultipleSet(s1);
		List<String> second = getMultipleSet(s2);

		// 교집합
		List<String> intersection = getIntersection(first, second);

		// 합집합
		List<String> union = getUnion(first, second);

		// 자카드 계산
		answer = getAnswer(getJacquard(intersection, union));
		return answer;
	}

	private static String toUppercase(String text){
		return text.toUpperCase();
	}

	private static List<String> getMultipleSet(String text){
		List<String> list = new ArrayList<>();
		String sub;
		for(int i = 0; i < text.length() - 1; i++){
			sub = text.substring(i, i + 2);
			if(Pattern.matches(isApahabet, sub)){
				list.add(sub);
			}
		}
		return list;
	}

	private static List<String> getIntersection(List<String> first, List<String> second){
		List<String> intersection = new ArrayList<>();
		List<String> first_copy = new ArrayList<>(first);

		second.stream().filter(first_copy::contains).forEach(s->{
			first_copy.remove(s);
			intersection.add(s);
		});

		return intersection;
	}

	private static List<String> getUnion(List<String> first, List<String> second){
		List<String> union      = new ArrayList<>(first);
		List<String> first_copy = new ArrayList<>(first);

		second.stream().forEach(s->{
			if(!first_copy.contains(s)){
				union.add(s);
			}else{
				first_copy.remove(s);
			}
		});
		return union;
	}

	private static double getJacquard(List<String> intersection, List<String> union){
		if(intersection.size() == 0 && union.size() == 0){
			return 1;
		}
		return (double)intersection.size() / (double)union.size();
	}
	private static int getAnswer(double val){
		return (int) Math.floor(val * 65536);
	}
}
