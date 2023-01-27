package org.example;

public enum Keywords {
    KW_RETURN("return"),
    KW_IF("if"),
    KW_ELSE("else"),
    KW_SWITCH("switch"),
    KW_CASE("case"),
    KW_BREAK("break"),
    KW_FOR("for"),
    KW_WHILE("while"),
    KW_DO("do"),
    KW_CONTINUE("continue"),
    KW_STRUCT("struct"),
    KW_TYPEDEF("typedef")
    ;

    public String value;
    Keywords(String value){
        this.value = value;
    }
}
