package com.keyi.erp_saomiaochuku.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/9/19.
 */
@DatabaseTable(tableName = "weiyima")
public class Code {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "WeiYiCode")
    private String WeiYiCode;
    public Code(){

    }


    public Code( String weiYiCode) {
        WeiYiCode = weiYiCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeiYiCode() {
        return WeiYiCode;
    }

    public void setWeiYiCode(String weiYiCode) {
        WeiYiCode = weiYiCode;
    }
}
