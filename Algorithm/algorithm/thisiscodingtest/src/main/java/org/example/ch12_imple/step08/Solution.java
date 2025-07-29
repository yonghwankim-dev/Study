package org.example.ch12_imple.step08;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public int solution(int n, int[] originalWeak, int[] dist) {
        int answer = Integer.MAX_VALUE;
        int[] weak = makeDoubleArray(n, originalWeak);
        List<int[]> friendPermutation = new ArrayList();
        perm(dist, 0, dist.length, dist.length, friendPermutation);
        for(int startWeak = 0; startWeak < originalWeak.length; startWeak++){
            for(int[] friends : friendPermutation){
                int count = 1;
                int position = weak[startWeak] + friends[count - 1];
                
                for(int idx = startWeak; idx < startWeak + originalWeak.length; idx++){
                    if(weak[idx] > position){
                        count++;
                        if(count > dist.length){
                            break;
                        }
                        position = weak[idx] + friends[count - 1];
                    }
                }
                answer = Math.min(answer, count);
            }            
        }
        if(answer > dist.length){
            return -1;
        }
        return answer;
    }
    
    private int[] makeDoubleArray(int n, int[] weak){
        int[] result = new int[weak.length * 2];
        int i = 0;
        for(; i < weak.length; i++){
            result[i] = weak[i];
        }
        for(; i < result.length; i++){
            result[i] = n + weak[i % weak.length];
        }
        return result;
    }
    
    private void perm(int[] dist, int depth, int n, int r, List<int[]> permList){
        if(depth == r){
            int[] copy = copyArray(dist);
            permList.add(copy);
            return;
        }
        
        for(int i = depth; i < n; i++){
            swap(dist, i, depth);
            perm(dist, depth + 1, n, r, permList);
            swap(dist, i, depth);
        }
    }
    
    private int[] copyArray(int[] dist){
        int[] result = new int[dist.length];
        for(int i = 0; i < dist.length; i++){
            result[i] = dist[i];
        }
        return result;
    }
    
    private void swap(int[] dist, int from, int to){
        int temp = dist[from];
        dist[from] = dist[to];
        dist[to] = temp;
    }
}
