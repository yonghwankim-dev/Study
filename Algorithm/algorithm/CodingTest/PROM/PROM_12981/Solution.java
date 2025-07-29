package PROM.PROM_12981;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

	private static List<String> submitWords;  // 게임에서 말한 단어 리스트

    public static int[] solution(int n, String[] words) {
		submitWords = new ArrayList<>();
		int[] answer = new int[2];

    	// key : 사람번호, value : 한 사람이 말한 단어 리스트
    	Map<Integer, List<String>> personMap = IntStream.rangeClosed(1, n)
												  .boxed()
												  .collect(Collectors.toMap(i -> i, i -> new ArrayList<>(), (a, b) -> b));

    	for(int i = 0; i < words.length; i++) {
			String word = words[i];
    		int p_turn = (i % n) + 1;	// 게임에 참가한 사람번호(차례)
    		int turn = (i / n) + 1;		// 게임이 진행된 차례
    		List<String> personWords = personMap.get(p_turn); // p_turn번 사람의 단어 리스트 참조
			personWords.add(word);

    		if(submitWords.isEmpty()) {
    			submitWords.add(word);
    		}else if(isDuplicatedWord(word) || isNotEqualFirstCharacter(getLastWord(submitWords), word)){
				answer[0] = p_turn;	// 가장 먼저 탈락하는 사람의 번호
				answer[1] = turn;	// 가장 먼저 탈락하는 사람이 몇번째 차례인지 저장
				break;
    		}
			submitWords.add(word);
    	}
        return answer;
    }

	private static boolean isDuplicatedWord(String word) {
		return submitWords.contains(word);
	}

	private static String getLastWord(List<String> words){
		return words.get(words.size()-1);
	}

	private static boolean isNotEqualFirstCharacter(String lastWord, String word) {
		return getLastCharacter(lastWord) != getFirstCharacter(word);
	}

	private static char getLastCharacter(String text){
		return text.charAt(text.length() - 1);
	}

	private static char getFirstCharacter(String text){
		return text.charAt(0);
	}
}
