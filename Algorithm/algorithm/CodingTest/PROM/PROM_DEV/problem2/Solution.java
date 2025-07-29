package PROM.PROM_DEV.problem2;

import java.util.*;
import java.util.stream.IntStream;

class Solution {
    static class Computer{
        String name;
        int ipNum;             // ���� �ֱٿ� ����߾��� ip ��ȣ
        boolean isAllocatedIP; // ������ IP �Ҵ�޾Ҵ��� ����, true : �Ҵ����, false : �Ҵ������ ����

        public Computer(String name) {
            this.name = name;
            this.ipNum = -1;
            isAllocatedIP = false;
        }

        public static void allocatedIP(Computer computer, int n) {
            computer.ipNum = n;
            computer.isAllocatedIP = true;
        }
    }
    enum Status{EMPTY, USED}

    static class IP implements Comparable<IP>{
        int lastIpNum;                   // ������ ��ȣ
        Status status;           // [EMPTY, USED]
        boolean isPastAllocated; // �Ҵ������ �־����� ����, true : �Ҵ��, false : �Ҵ���� ����

        public IP(int lastIpNum) {
            this.lastIpNum = lastIpNum;
            status = Status.EMPTY;
            isPastAllocated = false;
        }

        public boolean isEmpty(){
            return status == Status.EMPTY;
        }

        public static void allocatedIP(IP ip){
            ip.status = Status.USED;
            ip.isPastAllocated = true;
        }

        public static void releasedIP(IP ip){
            ip.status = Status.EMPTY;
        }

        @Override
        public int compareTo(IP o) {
            return this.lastIpNum - o.lastIpNum;
        }
    }

    private static final String IP_ADDRESS = "192.168.0.";
    private static ArrayList<IP> ips;
    private static Set<Computer> computers;
    private static Set<String> computerNames;
    private static String REQUEST = "request";
    private static String RELEASE = "release";

    public static String[] solution(int n, String[] queries) {
        computerNames = new HashSet<>();
        computers = new HashSet<>();
        ips = createIPArrayList(n);
        String[] answer;
        List<String> answerList = new ArrayList<>();
        IP finedIP;

        for(String query : queries){
            String[] info = query.split(" ");
            String name     = info[0];
            String category = info[1];
            Computer computer;

            // ��ǻ���� ù ��û�� ���
            if(!computerNames.contains(name)){
                computerNames.add(name);
                computers.add(new Computer(name));
            }
            computer = findComputerByName(name);

            // IP ��û
            if(category.equals(REQUEST)){
                // ������ IP �Ҵ���� �� ����
                if(computer.isAllocatedIP){
                    finedIP = findRecentlyUsedIP(computer);
                    IP.allocatedIP(finedIP);
                    Computer.allocatedIP(computer, finedIP.lastIpNum);
                    answerList.add(getSuccessMessage(computer.name, finedIP.lastIpNum));
                    continue;
                }

                // ������ IP �Ҵ���� �� ����
                finedIP = findEmptyIP();
                if(finedIP == null){ // IP �Ҵ� �źε�
                    answerList.add(getFailMessage(computer.name));
                    continue;
                }
                IP.allocatedIP(finedIP);
                Computer.allocatedIP(computer, finedIP.lastIpNum);
                answerList.add(getSuccessMessage(computer.name, finedIP.lastIpNum));
                continue;
            }
            // IP �ݳ�
            IP.releasedIP(findIP(computer.ipNum));
        }
        answer = toStringArray(answerList);
        return answer;
    }



    private static Computer findComputerByName(String name){
        for(Computer computer : computers){
            if(computer.name.equals(name)){
                return computer;
            }
        }
        return null;
    }

    private static IP findEmptyIP(){
        // �ѹ��� �Ҵ���� �ʾҴ� IP Ž��
        for(IP ip : ips){
            if(ip.isEmpty() && ip.isPastAllocated == false){
                return ip;
            }
        }

        // ��� �Ҵ������ �ְų� ��� �����
        for(IP ip : ips){
            if(ip.status.equals(Status.EMPTY)){
                return ip;
            }
        }

        // ��� IP�� �����
        return null;
    }

    private static IP findRecentlyUsedIP(Computer computer) {
        // ��û�� ���� �ֱٿ� ����ߴ� IP Ž��
        for(IP ip : ips){
            // ��ǻ�Ͱ� �ֱٿ� ����ߴ� IP�̰� �Ҵ��� ������ ���
            if(ip.lastIpNum == computer.ipNum && ip.status.equals(Status.EMPTY)){
                return ip;
            }
        }
        // ��û�� ���� �ֱ� ����ߴ� IP�� ������� ���
        return findEmptyIP();
    }

    private static IP findIP(int lastIpNum){
        for(IP ip : ips){
            if(ip.lastIpNum == lastIpNum){
                return ip;
            }
        }
        return null;
    }

    private static ArrayList<IP> createIPArrayList(int n){
        ArrayList<IP> result = new ArrayList<>();
        IntStream.rangeClosed(1, n).forEach(i->result.add(new IP(i)));
        return result;
    }

    private static String getSuccessMessage(String name, int ipNum){
        return String.format("%s %s%d",name,IP_ADDRESS,ipNum);
    }

    private static String getFailMessage(String name){
        return String.format("%s reject", name);
    }

    private static String[] toStringArray(List<String> answerList) {
        String[] result = new String[answerList.size()];
        for(int i = 0; i < answerList.size(); i++){
            result[i] = answerList.get(i);
        }
        return result;
    }
}
