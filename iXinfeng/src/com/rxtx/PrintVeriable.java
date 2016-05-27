package com.rxtx;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class PrintVeriable {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Map<String, String> map;

        map = System.getenv();

        Set<Entry<String, String>> sm = map.entrySet();
        Iterator<Entry<String, String>> t = sm.iterator();
        while (t.hasNext())
        {
                Entry entry = t.next();
                if ((entry.getKey()+"").contains("java")) {
                    System.out.println("key: " + entry.getKey() + " " +
                            "value: " + entry.getValue());
                    
                }
        }
        
    }

}
