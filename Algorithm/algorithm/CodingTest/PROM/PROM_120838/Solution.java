package PROM.PROM_120838;

import java.util.HashMap;
import java.util.Map;

class Solution {

    private static final String[] morseCodeArray = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.",
                                                    "---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
    public String solution(String letter) {
        StringBuilder sb = new StringBuilder();
        String[] letterArray = letter.split(" ");
        Map<String, String> morseCodeMap = createMorseCodeMap();

        for(String letterChar : letterArray){
            sb.append(morseCodeMap.get(letterChar));
        }

        return sb.toString();
    }

    private Map<String, String> createMorseCodeMap(){
        Map<String, String> morseCodeMap = new HashMap<>();
        char ch = 'a';
        for(String morseCode : morseCodeArray){
            morseCodeMap.put(morseCode, String.valueOf(ch));
            ch++;
        }
        return morseCodeMap;
    }
}
