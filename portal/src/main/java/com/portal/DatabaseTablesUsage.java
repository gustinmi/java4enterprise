package com.portal;

public class DatabaseTablesUsage {

    
    public void demo() throws Exception{
        
        //Get this value from some json servlet
        
        final String tableName = "table1";
        final String param1 = "some val 1";
        final String param2 = "some val 2";
        
        final DatabaseTables type = DatabaseTables.fromDbName(tableName);
        final String sql = type.getInsertSt();
        final boolean ok;
        
        switch (type) {
            case TABLE1:
                //Database.instance.save(sql, param1,param2);
                ok = true;
                break;
            case TABLE2:
                //Database.instance.save(sql, param1,param2);
                ok = true;
                break;
            case TABLE3:
                //Database.instance.save(sql, param1,param2);
                ok = true;
                break;
            default:
                ok = false;
                throw new Exception("Received an unknown attachment type" + type);
        }
        
    
        
        
    }
    
    
}
