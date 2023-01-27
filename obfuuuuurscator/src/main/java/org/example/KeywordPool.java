package org.example;

import java.util.HashMap;
import java.util.Map;

import static org.example.RandomGeneration.createRandomString;

public class KeywordPool {
    public static Map<String, String> keywordsMap = new HashMap<>(){
        {
            for (DataTypes dt: DataTypes.values()){
                this.put(dt.value, createRandomString());
            }
            Map<String, String> buf = new HashMap<>(this);
            for(String key: buf.keySet()){
                this.put(key + "\\*".replaceAll("", ""), createRandomString());
            }

            for (Keywords key: Keywords.values()){
                this.put(key.value, createRandomString());
            }
        }
    };
}
