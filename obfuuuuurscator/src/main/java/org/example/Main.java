package org.example;

import java.io.File;
import java.io.FileReader;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int size = 8;

    static Map<String, String> keywords = new HashMap<>(){
        {
            this.put("int", createRandomString());
            this.put("char", createRandomString());
            this.put("short", createRandomString());
            this.put("long", createRandomString());
            this.put("float", createRandomString());
            this.put("double", createRandomString());
            this.put("void", createRandomString());
        }
    };

    public static void main(String[] args) throws Exception{
        String path = "";
        if (args.length == 0 || args[0] == ""){
            path = "C:\\Users\\Vlad\\Desktop\\obfuuuuuuuurscatur\\source.cpp";
        }



        String res = readFile(path);
        for (String key: keywords.keySet()){
            res = res.replaceAll(key, keywords.get(key));
        }
        res = res.replaceAll("/n", " ");

        System.out.println(res);
    }

    static String readFile(String path) throws Exception{
        FileReader fr = new FileReader(path);
        File f = new File(path);
        char[] chars = new char[(int)f.length()];
        fr.read(chars);
        String st = new String(chars);
        return st;
    }

    static String createRandomString(){
        Random random = ThreadLocalRandom.current();
        byte[] r = new byte[size]; //Means 2048 bit
        random.nextBytes(r);
        return new String(r);
    }
}