package com.keyi.erp_saomiaochuku.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class TagMsg {


    /**
     * IsOK : true
     * ErrMsg : null
     * Data : [{"OuterIid":"东南西北","OuterSkuId":"东南西北","WareName":"正式仓库","SuppName":"零度"}]
     */

    private boolean IsOK;
    private Object ErrMsg;
    /**
     * OuterIid : 东南西北
     * OuterSkuId : 东南西北
     * WareName : 正式仓库
     * SuppName : 零度
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
        private String OuterIid;
        private String OuterSkuId;
        private String WareName;
        private String SuppName;

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
