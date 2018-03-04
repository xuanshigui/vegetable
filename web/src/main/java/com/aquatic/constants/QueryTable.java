package com.aquatic.constants;

public enum QueryTable {
    SC("shuichan", "price_shuichan"), XQ("xuqin", "price_xuqin");

    String type;
    String table;

    QueryTable(String type, String table) {
        this.table = table;
        this.type = type;
    }

    public String getTable() {
        return table;
    }

    public String getType() {
        return type;
    }
}
