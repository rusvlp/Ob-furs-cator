package org.example;

public enum DataTypes {
    DT_CHAR("char"),
    DT_SHORT("short"),
    DT_INT("int"),
    DT_LONG("long"),
    DT_FLOAT("float"),
    DT_DOUBLE("double");

    public String value;
    DataTypes(String value){
        this.value =value;
    }
}
