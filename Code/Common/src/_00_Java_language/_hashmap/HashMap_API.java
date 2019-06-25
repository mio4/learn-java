package _00_Java_language._hashmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HashMap常用API
 */
public class HashMap_API {

    private static Map<String,String> map;

    public static void main(String[] args){
        initialize();
        addNullKey();
    }

    public static void initialize(){
        map = new HashMap<>();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        map.put("key4","value4");
    }

    public static void addNullKey(){
        map.put(null,"null value");
        System.out.println(map.get(null));
    }

    public static void test_traverse(Map<Integer,Integer> map){ //HashMap的遍历
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
