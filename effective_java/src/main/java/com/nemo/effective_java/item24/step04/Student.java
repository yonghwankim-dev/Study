package com.nemo.effective_java.item24.step04;

public class Student {
    private String name;
    private int studentId;

    public Student(String name, int studentId) {
        this.name = name;
        this.studentId = studentId;
    }

    public void takeExam(int score) {
        class ExamResult {
            private int score;

            public ExamResult(int score) {
                this.score = score;
            }

            public boolean isPass() {
                return score >= 60;
            }

            public void printResult() {
                System.out.println("Student Name: " + name);
                System.out.println("Student ID: " + studentId);
                System.out.println("Test Score: " + score);
                System.out.println("Pass : " + (isPass() ? "pass" : "noPass"));
            }
        }

        ExamResult result = new ExamResult(score);
        result.printResult();
    }

    public static void main(String[] args) {
        Student student = new Student("Alice", 101);
        student.takeExam(85);
        student.takeExam(45);
    }
}
