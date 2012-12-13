
package com.portal;

public enum DatabaseTables {
    TABLE1(new TableDefs() {
        public String getName() { return "table1"; }
        public String getDbname() { return "photo1"; }
        public String getInsertSt() { return "insert into table1 values (?,?)"; }
        public String getSelectSt() { return "select * from table1 where id = ?"; }
    }),
    TABLE2(new TableDefs() {
        public String getName() { return "table2"; }
        public String getDbname() { return "photo2"; }
        public String getInsertSt() { return "insert into table2 values (?,?)"; }
        public String getSelectSt() { return "select * from table2 where id = ?"; }
    }),
    TABLE3(new TableDefs() {
        public String getName() { return "table3"; }
        public String getDbname() { return "photo3"; }
        public String getInsertSt() { return "insert into table3 values (?,?)"; }
        public String getSelectSt() { return "select * from table3 where id = ?"; }
    }),
    ;
    
    private final TableDefs defs; //proxy for instance methods
    
    DatabaseTables(TableDefs defs) {
        this.defs = defs;
    }
    
    public String getName() {
        return defs.getName();
    }
    
    public String getInsertSt() {
        return defs.getInsertSt();
    }
    
    public String getSelectSt() {
        return defs.getSelectSt();
    }
    
    
    public static DatabaseTables fromDbName(String typePrefix) {
        if ("table1".equals(typePrefix)) return TABLE1;
        if ("table2".equals(typePrefix)) return TABLE2;
        if ("table3".equals(typePrefix)) return TABLE3;
        throw new IllegalArgumentException("There is table type with string " + typePrefix);
    }
        
    private static interface TableDefs {
        String getName();
        String getDbname();
        String getInsertSt();
        String getSelectSt();
    }
}
