package XiaoZhao.JavaLanguage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HashMap常用API
 */
public class HashMap_API {

    public static void main(String[] args){

    }

    public static Map<Integer,Integer> getHashMap(){ //PUT
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        map.put(1,10);
        map.put(2,20);
        map.put(3,30);
        return map;
    }

    public static void test_traverse(Map<Integer,Integer> map){ //HashMap的遍历
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
