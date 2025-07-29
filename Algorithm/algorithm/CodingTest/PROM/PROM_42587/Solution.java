package PROM.PROM_42587;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Solution {
    static class Document{
        int priority;
        int location;

        public Document(int priority, int location) {
            this.priority = priority;
            this.location = location;
        }
    }

    public static int solution(int[] priorities, int location) {
        int answer;
        List<Document> printer = toArrayList(priorities);
        List<Document> printOrders = new ArrayList<>();

        while(!printer.isEmpty()){
            if(containHigherPriorityDocument(printer)){
                moveFromFirstDocumentToLastOrder(printer);
                continue;
            }
            printOrders.add(printFirstDocument(printer));
        }

        answer = getPrintOrder(printOrders, location);
        return answer;
    }

    private static ArrayList<Document> toArrayList(int[] priorities){
        ArrayList<Document> result = new ArrayList<>();
        IntStream.range(0, priorities.length).forEach(i->result.add(new Document(priorities[i],i)));
        return result;
    }
    private static boolean containHigherPriorityDocument(List<Document> printer){
        Document first = printer.get(0);
        for(Document document : printer){
            if(first.priority < document.priority){
                return true;
            }
        }
        return false;
    }
    private static void moveFromFirstDocumentToLastOrder(List<Document> printer){
        printer.add(printer.remove(0));
    }

    private static Document printFirstDocument(List<Document> printer) {
        return printer.remove(0);
    }

    private static int getPrintOrder(List<Document> printOrders, int location){
        int result = 0;
        Document d;
        for(int i = 0; i < printOrders.size(); i++){
            d = printOrders.get(i);
            result = isEqualLocation(d.location, location) ? i+1 : result;
        }
        return result;
    }

    private static boolean isEqualLocation(int a, int b) {
        return a == b;
    }
}
