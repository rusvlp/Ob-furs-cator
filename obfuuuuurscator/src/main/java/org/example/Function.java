package org.example;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class Function {
    String dataType = "";
    String name = "";
    String obfuscatedName = "";
    String args = "";
    String body = "";
    public Function(){

    }

    public Function(Function f){
        this.dataType = f.dataType;
        this.name = f.name;
        this.args = f.args;
        this.body = f.body;
    }

    public Function(String dataType){
        this.dataType = dataType;
    }


}
