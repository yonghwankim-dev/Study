package PROM.PROM_42576;

import java.util.*;

class Solution {

    private static Map<String, Integer> countParticipant(String[] participant){
        Map<String, Integer> result = new HashMap<>();
        Arrays.stream(participant).forEach((player)->{
            int increaseCount = increasePlayerCount(result, player);
            result.put(player, increaseCount);
        });
        return result;
    }

    private static Map<String, Integer> minusParticipant(Map<String, Integer> map, String[] completion){
        Map<String, Integer> result = new HashMap<>(map);
        Arrays.stream(completion).forEach((player)-> {
            int decreaseCount = decreasePlayerCount(result, player);
            result.put(player, decreaseCount);
        });
        return result;
    }

    private static int increasePlayerCount(Map<String, Integer> map, String player){
        return map.getOrDefault(player, 0) + 1;
    }

    private static int decreasePlayerCount(Map<String, Integer> map, String player){
        return map.get(player) - 1;
    }

    private static String findOneNotCompletionPlayer(Map<String, Integer> map){
        return map.keySet()
                  .stream()
                  .filter((player)->isExistNonCompletionPlayer(map, player))
                  .findFirst()
                  .get();
    }

    private static boolean isExistNonCompletionPlayer(Map<String, Integer> map, String player){
        return map.get(player) != 0;
    }

	public static String solution(String[] participant, String[] completion) {
        String answer = "";
        Map<String, Integer> map = null;

        map = countParticipant(participant);
        map = minusParticipant(map, completion);
        answer = findOneNotCompletionPlayer(map);

        return answer;
    }
}
