package org.example.ch14_sort.step01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

// korea, eng, math
public class Main{
    static class Student{
        String name;
        int[] courses;
        
        public Student(String name, int[] courses){
            this.name = name;
            this.courses = courses;
        }

        public String getName(){
            return this.name;
        }
    }
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        List<Student> stds = new ArrayList();
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            String name = st.nextToken();
            int kor = Integer.parseInt(st.nextToken());
            int eng = Integer.parseInt(st.nextToken());
            int math = Integer.parseInt(st.nextToken());
            stds.add(new Student(name, new int[]{kor, eng, math}));
        }
        
        List<String> result = solution(stds);
        result.forEach(System.out::println);
    }
    
    private static List<String> solution(List<Student> students){
        Comparator<Student> reverseKor = Comparator.comparingInt((Student s)->s.courses[0]).reversed();
        Comparator<Student> engComp = Comparator.comparingInt((Student s)->s.courses[1]);
        Comparator<Student> reverseMath = Comparator.comparingInt((Student s)->s.courses[2]).reversed();
        return students.stream()
            .sorted(reverseKor
                .thenComparing(engComp)
                .thenComparing(reverseMath)
                .thenComparing(Student::getName))
            .map(Student::getName)
            .collect(Collectors.toList());
    }
}
