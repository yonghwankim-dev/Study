package BOJ.BOJ_24060;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static int[] arr, tmp;
    private static int count = 0;
    private static int answer = -1;
    private static int k;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] info =  br.readLine().split(" ");
        int n = Integer.parseInt(info[0]);
        int k = Integer.parseInt(info[1]);
        int[] arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int answer = Main.solution(n, k, arr);
        System.out.println(answer);
    }

    public static int solution(int n, int k, int[] arr){
        Main.arr = arr;
        Main.tmp = new int[n];
        Main.k   = k;
        mergeSort(Main.arr, 0, n - 1);
        return answer;
    }

    private static void mergeSort(int[] arr, int p, int r){
        if(count > k){
            return;
        }
        if(p < r){
            int q = (p + r) / 2;
            mergeSort(arr, p, q);
            mergeSort(arr, q + 1, r);
            merge(arr, p, q, r);
        }
    }

    private static void merge(int[] arr, int p, int q, int r) {
        int i = p;
        int j = q + 1;
        int t = 0;

        while(i <= q && j <= r){

            if(arr[i] <= arr[j]){
                tmp[t] = arr[i];
                i++;
            }else{
                tmp[t] = arr[j];
                j++;
            }
            t++;
        }

        while(i <= q){
            tmp[t++] = arr[i++];

        }
        while(j <= r){
            tmp[t++] = arr[j++];
        }
        i = p; t = 0;
        while(i <= r){
            count++;
            if(count == k){
                answer = tmp[t];
                break;
            }
            arr[i++] = tmp[t++];
        }
    }
}
