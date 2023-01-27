package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.RandomGeneration.createRandomString;

public class IdentifierReplacer {


    public static List<String> getIdentifiers(String src){
        int count = 0;
        List<String> identifiers = new ArrayList<>();


        for (String dt: KeywordPool.keywordsMap.keySet()){
        // for initializers
            for (String st: src.split(dt +" ")){

                String current = st.split("=")[0];
                if (containsAny(current, ",", "}", "{", "(", ")", "#")){
                    String toAdd = current.charAt(current.length()-1) == ' '? current.substring(0, current.length()-1): current;
                    identifiers.add(toAdd);
                }
            }

            for (String st: src.split(dt +" ")){
                //for definitions
                String current = st.split(";")[0];
                if (containsAny(current, ",", "}", "{", "(", ")", "#", "=")){
                    String toAdd = current.charAt(current.length()-1) == ' '? current.substring(0, current.length()-1): current;
                    identifiers.add(toAdd);
                }
                //for multiple
                current = st.split(",")[0];
                if (containsAny(current, ",", "}", "{", "(", ")", "#", "=")){
                    String toAdd = current.charAt(current.length()-1) == ' '? current.substring(0, current.length()-1): current;
                    identifiers.add(toAdd);

                }
                current = st.split("\\)")[0];
                if (containsAny(current, ",", "}", "{", "(", ")", "#", "=")){
                    String toAdd = current.charAt(current.length()-1) == ' '? current.substring(0, current.length()-1): current;
                    identifiers.add(toAdd);
                }
            }



        // for initializers pointers
            for (String st: src.split(dt +"\\* ")){
                String current = st.split("=")[0];
                if (containsAny(current, ",", "}", "{", "(", ")", "#")){
                    String toAdd = current.charAt(current.length()-1) == ' '? current.substring(0, current.length()-1): current;
                    identifiers.add(toAdd);
                }
            }
        //for definitions pointers
            for (String st: src.split(dt +"\\* ")){
                String current = st.split(";")[0];
                if (containsAny(current, ",", "}", "{", "(", ")", "#", "=")){
                    String toAdd = current.charAt(current.length()-1) == ' '? current.substring(0, current.length()-1): current;
                    identifiers.add(toAdd);
                }

                current = st.split(",")[0];
                if (containsAny(current, ",", "}", "{", "(", ")", "#", "=")){
                    String toAdd = current.charAt(current.length()-1) == ' '? current.substring(0, current.length()-1): current;
                    identifiers.add(toAdd);
                }
                current = st.split("\\)")[0];
                if (containsAny(current, ",", "}", "{", "(", ")", "#", "=")){
                    String toAdd = current.charAt(current.length()-1) == ' '? current.substring(0, current.length()-1): current;
                    identifiers.add(toAdd);
                }
            }
        }



        return identifiers;
    }



    public static String replaceIdentifiers(String src){
        String toRet = src;
        Map<String, String> idReplacers = new HashMap<>();
        List<String> identifiers = getIdentifiers(src);
        for (String id: identifiers) {
            idReplacers.put(id, createRandomString());
        }


        System.out.println(idReplacers);

        for (String key: idReplacers.keySet()){

            toRet = toRet.replaceAll(key + " ", idReplacers.get(key) + " ");
            toRet = toRet.replaceAll(key + ";", idReplacers.get(key) + ";");
            toRet = toRet.replaceAll(key + ",", idReplacers.get(key) + ",");
            //equations operators
            toRet = toRet.replaceAll(key + "\\)", idReplacers.get(key) + "\\)");
            toRet = toRet.replaceAll(key + "\\<", idReplacers.get(key) + "\\<");
            toRet = toRet.replaceAll(key + " \\<", idReplacers.get(key) + " \\<");
            toRet = toRet.replaceAll(key + "\\>", idReplacers.get(key) + "\\>");
            toRet = toRet.replaceAll(key + " \\>", idReplacers.get(key) + " \\>");
            //math operators
            toRet = toRet.replaceAll(key + "\\+", idReplacers.get(key) + "\\+");
            toRet = toRet.replaceAll(key + " \\+", idReplacers.get(key) + "\\+");
            toRet = toRet.replaceAll(key + "\\-", idReplacers.get(key) + "\\-");
            toRet = toRet.replaceAll(key + " \\-", idReplacers.get(key) + " \\-");
            toRet = toRet.replaceAll(key + "\\/", idReplacers.get(key) + "\\/");
            toRet = toRet.replaceAll(key + " \\/", idReplacers.get(key) + " \\/");
            toRet = toRet.replaceAll(key + "\\*", idReplacers.get(key) + "\\*");
            toRet = toRet.replaceAll(key + " \\*", idReplacers.get(key) + " \\*");

            //array
            toRet = toRet.replaceAll(key + "\\[", idReplacers.get(key) + "\\[");
            toRet = toRet.replaceAll(key + " \\[", idReplacers.get(key) + " \\[");
            toRet = toRet.replaceAll(key + "\\]", idReplacers.get(key) + "\\]");
            toRet = toRet.replaceAll(key + " \\]", idReplacers.get(key) + " \\]");

            //toRet = toRet.replaceAll(key + "*", idReplacers.get(key) + "*");
            //toRet = toRet.replaceAll(key + "/", idReplacers.get(key) + "/");
           // toRet = toRet.replaceAll(key + "=", idReplacers.get(key));

        }
        return toRet;
    }

    public static boolean containsAny(String src, String ... wcs){
        for (String w: wcs){
            if (src.contains(w)){
                return false;
            }
        }
        return true;
    }
}
