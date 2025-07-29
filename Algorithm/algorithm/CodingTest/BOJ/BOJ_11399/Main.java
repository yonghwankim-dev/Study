package BOJ.BOJ_11399;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    static class Person{
        int id;
        int time;

        public Person(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] orders = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int answer = Main.solution(orders);
        System.out.println(answer);
    }

    public static int solution(int[] orders){
        Person[] persons = new Person[orders.length];
        for(int i = 0; i < orders.length; i++){
            persons[i] = new Person(i+1, orders[i]);
        }

        Comparator<Person> ascByTime = Comparator.comparingInt(o -> o.time);
        Arrays.sort(persons, ascByTime);

        int[] reduce = new int[persons.length];
        reduce[0] = persons[0].time;
        for(int i = 1; i < persons.length; i++){
            reduce[i] = reduce[i-1] + persons[i].time;
        }

        return Arrays.stream(reduce).sum();
    }
}
