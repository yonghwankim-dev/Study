package PROM.PROM_155651;

import java.util.*;
import java.util.stream.Collectors;

class Solution {
    static class Book{
        int start;
        int end;

        public Book(String bookStart, String bookEnd){
            String[] bookStartArr = bookStart.split(":");
            String[] bookEndArr = bookEnd.split(":");
            this.start = Integer.parseInt(bookStartArr[0]) * 60 + Integer.parseInt(bookStartArr[1]);
            this.end = Integer.parseInt(bookEndArr[0]) * 60 + Integer.parseInt(bookEndArr[1]);
        }

        @Override
        public String toString() {
            return String.format("[%d ~ %d]", start, end);
        }
    }

    public int solution(String[][] book_time) {
        int answer = 0;
        Map<Integer, Integer> room = new HashMap<>();
        List<Book> books = Arrays.stream(book_time)
                                 .map(book -> new Book(book[0], book[1]))
                                 .collect(Collectors.toList());
        for(Book book :books){
            for(int i = book.start; i<= book.end + 9; i++){
                room.put(i, room.getOrDefault(i,0) + 1);
                int cnt = room.getOrDefault(i, 0);
                answer = Math.max(answer, cnt);
            }
        }

        return answer;
    }
}
