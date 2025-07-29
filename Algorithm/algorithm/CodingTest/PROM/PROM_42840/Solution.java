package PROM.PROM_42840;

import java.util.*;

public class Solution {

    static class Person{
        int id;
        int[] check;
        int cnt;

        public Person(int id, int[] check) {
            this.id = id;
            this.check = check;
            this.cnt = 0;
        }

        public int getCnt() {
            return cnt;
        }
    }

    // 1번 : 1, 2, 3, 4, 5
    // 2번 : 2, 1, 2, 3, 2, 4, 2, 5
    // 3번 : 3, 3, 1, 1, 2, 2, 4, 4, 5, 5
    public static int[] solution(int[] answers) {
        List<Integer> answer = new ArrayList<>();
        int cnt;
        int max = 0;
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1, new int[]{1, 2, 3, 4, 5}));
        persons.add(new Person(2, new int[]{2, 1, 2, 3, 2, 4, 2, 5}));
        persons.add(new Person(3, new int[]{3, 3, 1, 1, 2, 2, 4, 4, 5, 5}));

        persons.stream().forEach((person)->checkAnswers(person, answers));
        max = findMaxVal(persons);
        pushMaxValPerson(persons, answer, max);
        Collections.sort(answer);

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }

    private static void checkAnswers(Person person, int[] answers){
        int n = person.check.length;
        int result = 0;
        for(int i = 0 ; i < answers.length; i++){
            if(person.check[i % n] == answers[i]){
                result++;
            }
        }
        person.cnt = result;
    }

    private static int findMaxVal(List<Person> persons){
        Comparator<Person> comparatorByCnt = Comparator.comparingInt(Person::getCnt);
        return persons.stream().max(comparatorByCnt).get().cnt;
    }

    private static void pushMaxValPerson(List<Person> persons, List<Integer> answer, int max){
        for(Person person : persons){
            if(person.cnt == max){
                answer.add(person.id);
            }
        }
    }
}
