package PROM.PROM_42890;

import java.util.*;

class Solution {
    private static List<String> list = new ArrayList<>();

    public int solution(String[][] relation) {
        int n = relation[0].length;
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = i;
        }

        // 1. 컬럼들에 대한 조합 생성
        for(int i = 1; i <= n; i++){
            boolean[] chosen = new boolean[n];
            combination(arr, n, i, 0, chosen);
        }

        // 2. 유일성을 만족하는 후보키 조합 탐색
        List<String> unique = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(isUnique(list.get(i), relation)){
                unique.add(list.get(i));
            }
        }

        // 3. 최소성을 만족하는 후보키 조합 탐색
        List<String> minimum = new ArrayList<>(unique);
        for(String start : unique){
            for(String target : unique){
                deleteNotMinimum(start, target, minimum);
            }
        }

        return minimum.size();
    }


    public boolean isUnique(String columns, String[][] relation) {
        Set<String> set = new HashSet<>();
        for(String[] record : relation){
            String value = "";
            for(char column : columns.toCharArray()){
                value += record[Integer.parseInt(String.valueOf(column))];
            }
            set.add(value);
        }
        if(set.size() == relation.length){
            return true;
        }
        return false;
    }

    public void deleteNotMinimum(String start, String target, List<String> minimum){
        if(start.equals(target)){
            return;
        }

        int cnt = 0;
        for(char c : start.toCharArray()){
            if(target.contains(String.valueOf(c))){
                cnt++;
            }
        }

        if(cnt == start.length()){
            minimum.remove(target);
        }
    }


    public void combination(int[] arr, int n, int r, int i, boolean[] chosen) {
        if(i == r){
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < chosen.length; j++){
                if(chosen[j]){
                    sb.append(arr[j]);
                }
            }
            if(sb.length() > 0 && !list.contains(sb.toString())) {
                list.add(sb.toString());
            }
            return;
        }

        chosen[i] = true;
        combination(arr, n, r, i+1, chosen);
        chosen[i] = false;
        combination(arr, n, r, i+1, chosen);
    }

    public static void main(String[] args){

        String[][] relation = {{"100","ryan","music","2"},
                                {"200","apeach","math","2"},
                                {"300","tube","computer","3"},
                                {"400","con","computer","4"},
                                {"500","muzi","music","3"},
                                {"600","apeach","music","2"}};

        Solution s = new Solution();
        int answer = s.solution(relation);
        System.out.println(answer);
    }
}
