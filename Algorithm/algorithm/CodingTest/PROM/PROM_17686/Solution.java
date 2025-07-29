package PROM.PROM_17686;

import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public static String[] solution(String[] files) {
		Comparator<String> alphabet = (s1, s2) -> parseHead(s1).compareToIgnoreCase(parseHead(s2));
		Comparator<String> numberOrdered = Comparator.comparingInt(Solution::parseNumber);
		Arrays.sort(files, alphabet.thenComparing(numberOrdered));
		return files;
    }

	private static String parseHead(String text){
		for(int i = 0; i < text.length(); i++){
			if(isDigit(text, i)){
				return text.substring(0, i);
			}
		}
		return text;
	}

	private static int parseNumber(String text){
		StringBuilder sb = new StringBuilder();
		int idx = findFirstNumberIndex(text);
		while(isInside(text, idx) &&
			  isDigit(text, idx)  &&
			  isNotMoreThan5(sb)){
			sb.append(text.charAt(idx));
			idx++;
		}
		return Integer.parseInt(sb.toString());
	}

	private static boolean isInside(String text, int idx){
		return idx < text.length();
	}

	private static boolean isDigit(String text, int idx){
		return Character.isDigit(text.charAt(idx));
	}

	private static boolean isNotMoreThan5(StringBuilder sb){
		return sb.length() <= 5;
	}

	// text���� ���ڰ� �����ϴ� ���� ó�� �ε����� Ž��
	private static int findFirstNumberIndex(String text){
		for(int i = 0; i < text.length(); i++){
			if(isDigit(text ,i)){
				return i;
			}
		}
		return 0;
	}
    
}
