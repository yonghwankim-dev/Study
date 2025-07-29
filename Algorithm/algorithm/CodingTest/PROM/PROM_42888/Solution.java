package PROM.PROM_42888;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

	private static final String ENTER  = "Enter";
	private static final String LEAVE  = "Leave";
	private static final String CHANGE = "Change";
	private static List<String> chatLog;
	private static Map<String,String> nickNameMap;

    public static String[] solution(String[] record) {
		String[] answer;
        chatLog = new ArrayList();
        nickNameMap = new HashMap<String, String>(); // key : ���̵�, value : �г���
		Stream<String> recordStream = Arrays.stream(record);

		// �Է� ���ڵ带 ó��
		recordStream.forEach(r->processRecord(r));
        // ���� ���̵� �г������� ��ȯ
		answer = changeIdToNickName();
        return answer;
    }

	private static void processRecord(String record){
		String[] tokens = record.split(" ");
		String cmd = tokens[0];
		String userId = tokens[1];
		String nickName = isEnterOrChange(cmd) ? tokens[2] : "";

		if(isEnter(cmd)){
			nickNameMap.put(userId, nickName);
			chatLog.add(getEnterMessage(userId));
		}else if(isLeave(cmd)){
			chatLog.add(getLeaveMessage(userId));
		}else if(isChange(cmd)){
			nickNameMap.put(userId, nickName);
		}
	}

	private static boolean isEnterOrChange(String text){
		return text.equals(ENTER) || text.equals(CHANGE);
	}

	private static boolean isEnter(String text){
		return text.equals(ENTER);
	}

	private static boolean isLeave(String text){
		return text.equals(LEAVE);
	}

	private static boolean isChange(String text){
		return text.equals(CHANGE);
	}

	private static String getEnterMessage(String id){
		return id+"���� ���Խ��ϴ�.";
	}

	private static String getLeaveMessage(String id){
		return id+"���� �������ϴ�.";
	}

	private static String[] changeIdToNickName(){
		String[] result = chatLog.stream()
				                 .map(s->{
									 String id = s.substring(0, s.indexOf("��")); // "��"���ڸ� �������� userId ����
									 return s.replace(id, nickNameMap.get(id));}) // ���� ���̵� �г������� ����
				                 .collect(Collectors.toList()).toArray(String[]::new);
		return result;
	}

	
	public static void main(String[] args) {
		String[] record = {"Enter uid1234 Muzi", "Enter uid4567 Prodo","Leave uid1234","Enter uid1234 Prodo","Change uid4567 Ryan"};
		
		String[] result = solution(record);
		System.out.println(Arrays.asList(result).toString());
	}
}
