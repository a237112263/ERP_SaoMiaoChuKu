package com.keyi.erp_saomiaochuku.utils;

import android.content.Context;

import com.keyi.erp_saomiaochuku.bean.SMSData;
import com.keyi.erp_saomiaochuku.db.Code;
import com.keyi.erp_saomiaochuku.db.DBHelper;
import com.keyi.erp_saomiaochuku.db.DBcodeHelper;
import com.keyi.erp_saomiaochuku.db.User;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by Administrator on 2016/4/27.
 */
public class DatasUtils {
    public static final String strToken = "&Token=";
    public static final String yanzhengUrl = "http://appweb.keyierp.com/sms.aspx?m=";
    public static final String mobUrl = "http://appweb.keyierp.com/ERP/Login.aspx?mobile=";
    public static final String shopshowUrl = "http://erpscan.keyierp.com/ERP_Stock/MergerMsg.aspx?mobile=";
    public static final String finishShopUrl="http://erpscan.keyierp.com/ERP_Stock/ScanCutStock.aspx?mobile=";
    public static final String tagCheckUrl="http://erpscan.keyierp.com/ERP_Stock/PrintTag.aspx?mobile=";
    public static final String SMSData(Context context) {
        ACache aCache = ACache.get(context);
        SMSData smsData = (SMSData) aCache.getAsObject("SMSData");
        return smsData.getData().toString();
    }

    public static final String MobilNumber(Context context) {
        ACache aCache = ACache.get(context);
        return aCache.getAsString("MobilNumber").toString();
    }

    public static final String shopShowUrl(Context context, String s) {
        StringBuffer stringBuffer = new StringBuffer()
                .append(shopshowUrl)
                .append(MobilNumber(context))
                .append(strToken)
                .append(SMSData(context))
                .append("&batchGuid=")
                .append(s);
        return stringBuffer.toString();
    }
    public static final String finishShopUrl(Context context, String s,String tags) {
        StringBuffer stringBuffer = new StringBuffer()
                .append(finishShopUrl)
                .append(MobilNumber(context))
                .append(strToken)
                .append(SMSData(context))
                .append("&BatchGuid=")
                .append(s)
                .append("&Tags=")
                .append(tags);
        return stringBuffer.toString();
    }
    public static final String getTagCheckUrlUrl(Context context, String s) {
        StringBuffer stringBuffer = new StringBuffer()
                .append(tagCheckUrl)
                .append(MobilNumber(context))
                .append(strToken)
                .append(SMSData(context))
                .append("&Tag=")
                .append(s);
        return stringBuffer.toString();
    }

    public static final String numberZhuanHanzi(int number) {
        String s = null;
        switch (number) {
            case 1:
                s = "一";
                break;
            case 2:
                s = "二";
                break;
            case 3:
                s = "三";
                break;
            case 4:
                s = "四";
                break;
            case 5:
                s = "五";
                break;
            case 6:
                s = "六";
                break;
            case 7:
                s = "七";
                break;
            case 8:
                s = "八";
                break;
            case 9:
                s = "九";
                break;
        }
        return s;
    }

    public static final List<User> queryAll(Context context) {
        try {
            List<User> users = DBHelper.getHelper(context).getUserDao().queryForAll();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final List<Code> queryTagAll(Context context) {
        try {
            List<Code> users = DBcodeHelper.getHelper(context).getUserDao().queryForAll();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
