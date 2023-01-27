package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.RandomGeneration.createRandomString;

public class Misc {
    public static List<String> unreachableCodePool = new ArrayList<>(){
        {
            List<String> ids = new ArrayList<>(){
                {
                    this.add(createRandomString());
                    this.add(createRandomString());
                    this.add(createRandomString());
                }
            };
            this.add("int " + ids.get(0) + " = 0;"  + "if ("+ ids.get(0) +" == 1){for (int i = 0; i<228; i++){}}");
            this.add("for (int i = 0; i<1337; i++){printf(\"hello world \")}");
        }
    };


    public static String getIncludes(String src){
        String includes = "";//src.split("#")[src.split("#").length-1].split("\n")[0];
        for (int i = 0; i<src.split("#").length; i++){
            if (i == src.split("#").length-1){
                includes += "#" + src.split("#")[i].split("\n")[0];
            } else {
                includes += "#" + src.split("#")[i];
            }
        }
        includes = includes.substring(1);

        return includes;
    }

    public static String addUnreachableCode(String source){
        List<Integer> toAdd =new ArrayList<>();
        for (int i = 0; i<source.length()-2; i++){
            if (source.charAt(i+1) == ')' && source.charAt(i+2) == '{'){
                toAdd.add(i+2);
            }

        }

        System.out.println(toAdd);
        for (int i = 0; i<toAdd.size(); i++){
            source = new StringBuilder(source).insert(toAdd.get(i+1), unreachableCodePool.get(0)).toString();
            for (int j = 0; j<toAdd.size(); j++){
                toAdd.add(i, toAdd.get(i) + toAdd.get(j));
            }
        }



        return source;

    }

}
