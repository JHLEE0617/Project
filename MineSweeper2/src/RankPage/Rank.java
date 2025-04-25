package RankPage;

import java.util.*;

public class Rank {

    //아마 이거를 다른 자료 구조로 바꾸면 될걸 우선순위 큐 이런걸로
    private static ArrayList<Map<String,Object>> scoreList = new ArrayList<>();



    public static void addScore(String level, int i){
        Map<String, Object> record =  new HashMap<>();

        record.put("level", level);
        record.put("score", i);
        scoreList.add(record);
        Collections.sort(scoreList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                if((int)o1.get("score") > (int)o2.get("score")){
                    return -1;
                }
                else return 1;
            }
        });
    }


    public static void printScore(){
        System.out.print(scoreList);
    }
    public static ArrayList<Map<String, Object>> returnScoreList(){
        return scoreList;
    }
}
