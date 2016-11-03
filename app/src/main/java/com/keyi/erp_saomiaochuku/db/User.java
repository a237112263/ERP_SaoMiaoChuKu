package com.keyi.erp_saomiaochuku.db;

/**
 * Created by Administrator on 2016/6/6.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "erp_saomiaochuku")
public class User {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "MergerSysNo")
    private String MergerSysNo;
    @DatabaseField(columnName = "OuterIid")
    private String OuterIid;
    @DatabaseField(columnName = "OuterSkuId")
    private String OuterSkuId;
    @DatabaseField(columnName = "Num")
    private String Num;
    @DatabaseField(columnName = "WareHouseName")
    private String WareHouseName;
    @DatabaseField(columnName = "ZhuangTai")
    private String ZhuangTai;
    @DatabaseField(columnName = "suppname")
    private String Suppname;
    //  private List<User.Bean> list;
    @DatabaseField(columnName = "YiSaoNum")
    private int YiSaoNum;

    public String getZhuangTai() {
        return ZhuangTai;
    }


    public void setZhuangTai(String zhuangTai) {
        ZhuangTai = zhuangTai;
    }

    public User() {
    }


    public User(int id, String mergerSysNo, String outerIid, String outerSkuId, String num, String wareHouseName, String zhuangTai, String suppname, int yiSaoNum) {
        this.id = id;
        MergerSysNo = mergerSysNo;
        OuterIid = outerIid;
        OuterSkuId = outerSkuId;
        Num = num;
        WareHouseName = wareHouseName;
        ZhuangTai = zhuangTai;
        Suppname = suppname;
        YiSaoNum = yiSaoNum;
    }

    public int getYiSaoNum() {
        return YiSaoNum;
    }

    public void setYiSaoNum(int yiSaoNum) {
        YiSaoNum = yiSaoNum;
    }

    public String getSuppname() {
        return Suppname;
    }

    public void setSuppname(String suppname) {
        Suppname = suppname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMergerSysNo() {
        return MergerSysNo;
    }

    public void setMergerSysNo(String MergerSysNo) {
        this.MergerSysNo = MergerSysNo;
    }

    public String getOuterIid() {
        return OuterIid;
    }

    public void setOuterIid(String OuterIid) {
        this.OuterIid = OuterIid;
    }

    public String getOuterSkuId() {
        return OuterSkuId;
    }

    public void setOuterSkuId(String OuterSkuId) {
        this.OuterSkuId = OuterSkuId;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String Num) {
        this.Num = Num;
    }

    public String getWareHouseName() {
        return WareHouseName;
    }

    public void setWareHouseName(String WareHouseName) {
        this.WareHouseName = WareHouseName;
    }

//    private class Bean {
//
//    }
}
