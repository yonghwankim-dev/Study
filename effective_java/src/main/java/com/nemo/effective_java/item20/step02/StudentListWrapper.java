package com.nemo.effective_java.item20.step02;

import java.util.List;

public class StudentListWrapper {
    private final List<Student> wrappedList;

    public StudentListWrapper(List<Student> list) {
        this.wrappedList = list;
    }

    public boolean addStudent(Student student) {
        return wrappedList.add(student);
    }

    public boolean removeStudent(Student student) {
        return wrappedList.remove(student);
    }

    public void printStudentsAboveAge(int age) {
        System.out.println("Students above age " + age + ":");
        for (Student student : wrappedList) {
            if (student.getAge() > age) {
                System.out.println(student);
            }
        }
    }

    public void printAllStudents() {
        System.out.println("All students:");
        for (Student student : wrappedList) {
            System.out.println(student);
        }
    }

    public List<Student> getWrappedList() {
        return wrappedList;
    }
}
