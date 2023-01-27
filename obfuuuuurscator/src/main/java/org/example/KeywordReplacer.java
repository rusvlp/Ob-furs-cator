package org.example;

public class KeywordReplacer {
    static int size = 8;



    public static String setDefines(String src){
        String defines = "";
        for (String key: KeywordPool.keywordsMap.keySet()){
            defines+= "#define " + KeywordPool.keywordsMap.get(key) + " " + (key.contains("\\*")?key.substring(0, key.length()-2) + "*":key) + "\n";
        }
        return defines + src;
    }

    public static String replaceKeywords(String src){
        for (String key: KeywordPool.keywordsMap.keySet()){
            src = src.replaceAll(key + " ", KeywordPool.keywordsMap.get(key) + " ");
        }
        return src;
    }
}
