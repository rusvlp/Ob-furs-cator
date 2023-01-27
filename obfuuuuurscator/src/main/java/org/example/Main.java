package org.example;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.example.FunctionProcessor.*;
import static org.example.IdentifierReplacer.getIdentifiers;
import static org.example.IdentifierReplacer.replaceIdentifiers;
import static org.example.KeywordReplacer.replaceKeywords;
import static org.example.KeywordReplacer.setDefines;
import static org.example.Misc.addUnreachableCode;
import static org.example.Misc.getIncludes;

public class Main {


    public static void main(String[] args) throws Exception{
        String path = "";
        if (args.length == 0 || args[0] == ""){
            path = "/Users/Vlad/Desktop/Ob-furs-cator/source.cpp";
        }
        String resultPath = "/Users/Vlad/Desktop/Ob-furs-cator/source_obfuscated.cpp";



        String src = readFile(path);
        //List<Function> functions = parseFunctionList(res);
        String tmp = src;

        //System.out.println(res);
        String includes = getIncludes(src);


        src = replaceIdentifiers(src);
        src = processFunctions(src);
        System.out.println(src);
        src = replaceKeywords(src);
        src = setDefines(src);
        src = includes + "\n" + src;

        //System.out.println(src);
        writeResult(resultPath, src);



    }

    public static void sandbox(){

    }


    static String readFile(String path) throws Exception{
        FileReader fr = new FileReader(path);
        File f = new File(path);
        char[] chars = new char[(int)f.length()];
        fr.read(chars);
        String st = new String(chars);
        fr.close();
        return st;

    }



    static void writeResult(String filename, String content){
        try{
            File f = new File(filename);
            if (!f.createNewFile()){

            }
            FileWriter fw = new FileWriter(filename);
            fw.write(content);
            fw.close();
        } catch (IOException e){
            System.out.println("An error occurred");
        }
    }
}