package PROM.PROM_120869;

import java.util.Arrays;

class Solution {
    public int solution(String[] spell, String[] dic) {
        return Arrays.stream(dic)
                     .anyMatch(alienWord->isContainsSpellInAlienWord(spell, alienWord)) ? 1 : 2;
    }

    private boolean isContainsSpellInAlienWord(String[] spell, String alienWord) {
        return Arrays.stream(spell)
                     .allMatch(alphabet->isExistAlphabetInAlienWord(alienWord, alphabet));
    }

    private boolean isExistAlphabetInAlienWord(String alienWord, String alphabet){
        return alienWord.contains(alphabet);
    }
}
