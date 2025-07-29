package PROM.PROM_92334;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    public static int[] solution(String[] ids, String[] reports, int k) {
        // step0 : 신고내역을 저장하기 위한 해시맵 초기화
        int[] answer = new int[ids.length];
        Map<String, ArrayList<String>> reportMap = createReportMap(ids);
        Map<String, Integer> reportCounterMap = createReportCounterMap(ids);

        // step1 : 유저id가 신고한 ID 저장
        countReport(reports, reportMap, reportCounterMap);

        // step2 : 정지될 id 추가
        List<String> banIds = getBanList(reportCounterMap, k);

        // step3 : 각각의 유저에게 보낼 메일 횟수세기
        int i = 0;
        for(String id : ids){
            List<String> list = reportMap.get(id);
            answer[i] = countMail(banIds, list);
            i++;
        }

        return answer;
    }

    private static int countMail(List<String> banIds, List<String> list) {
        int result = 0;
        for(String id : list){
            if(banIds.contains(id)){
                result++;
            }
        }
        return result;
    }


    public static Map<String, ArrayList<String>> createReportMap(String[] ids){
        Map<String, ArrayList<String>> reportMap = new HashMap<String, ArrayList<String>>(); // 유저id가 신고한 내역
        for(String id : ids){
            reportMap.putIfAbsent(id, new ArrayList<String>());
        }
        return reportMap;
    }

    public static Map<String, Integer> createReportCounterMap(String[] ids){
        Map<String, Integer> reportCounterMap = new HashMap<String, Integer>();	// 유저id 신고받은 횟수
        for(String id : ids){
            reportCounterMap.putIfAbsent(id, 0);
        }
        return reportCounterMap;
    }

    private static void countReport(String[] reports,
                                    Map<String, ArrayList<String>> reportMap,
                                    Map<String, Integer> reportCounterMap){
        String reporter, target;

        for(String report : reports){
            String[] r = report.split(" ");
            reporter = r[0];
            target = r[1];

            // 신고자가 같은 유저를 여러번 수행하는 경우, 카운트하지 않음
            if(!reportMap.get(reporter).contains(target))
            {
                reportMap.get(reporter).add(target);				            // 신고내역 추가
                reportCounterMap.put(target, reportCounterMap.get(target)+1);	// 신고횟수 누적
            }
        }
    }


    private static List<String> getBanList(Map<String, Integer> reportCounterMap, int k) {
        List<String> result = new ArrayList<>();
        for(String id : reportCounterMap.keySet())
        {
            if(isBan(id, reportCounterMap, k))
            {
                result.add(id);
            }
        }
        return result;
    }

    private static boolean isBan(String id, Map<String, Integer> reportCounterMap, int k){
        return reportCounterMap.get(id) >= k ? true : false;
    }
}

