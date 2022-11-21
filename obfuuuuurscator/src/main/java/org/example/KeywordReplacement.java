package org.example;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum KeywordReplacement {


    INT("int", createRandomString()),
    LONG("long", createRandomString()),
    SHORT("short", createRandomString()),
    CHAR("char", createRandomString()),
    DOUBLE("double", createRandomString()),
    FLOAT("float", createRandomString()),
    UNSIGNED("unsigned", createRandomString()),
    VOID("void", createRandomString()),
    FOR("for", createRandomString());


    static final int size = 8;
    String from;
    String to;
    KeywordReplacement(String from, String to){
        this.from = from;
        this.to = to;
    }

    static String createRandomString(){
        Random random = ThreadLocalRandom.current();
        byte[] r = new byte[size]; //Means 2048 bit
        random.nextBytes(r);
        return new String(r);
    }


}
