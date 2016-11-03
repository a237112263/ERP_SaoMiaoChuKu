package com.keyi.erp_saomiaochuku.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class ShopMsg implements Serializable{
    private static final long serialVersionUID = -468746461L;

    /**
     * IsOK : true
     * ErrMsg : null
     * Data : [{"MergerSysNo":"MT15101600000251","OuterIid":"XB-C112","OuterSkuId":"XB-C112三门鞋柜","Num":"2","WareName":"默认仓库","SuppName":"小贝壳家居（华宇家居）"}]
     */

    private boolean IsOK;
    private Object ErrMsg;
    /**
     * MergerSysNo : MT15101600000251
     * OuterIid : XB-C112
     * OuterSkuId : XB-C112三门鞋柜
     * Num : 2
     * WareName : 默认仓库
     * SuppName : 小贝壳家居（华宇家居）
     */

    private List<DataBean> Data;

    public boolean isIsOK() {
        return IsOK;
    }

    public void setIsOK(boolean IsOK) {
        this.IsOK = IsOK;
    }

    public Object getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(Object ErrMsg) {
        this.ErrMsg = ErrMsg;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String MergerSysNo;
        private String OuterIid;
        private String OuterSkuId;
        private String Num;
        private String WareName;
        private String SuppName;

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

        public String getWareName() {
            return WareName;
        }

        public void setWareName(String WareName) {
            this.WareName = WareName;
        }

        public String getSuppName() {
            return SuppName;
        }

        public void setSuppName(String SuppName) {
            this.SuppName = SuppName;
        }
    }
}
