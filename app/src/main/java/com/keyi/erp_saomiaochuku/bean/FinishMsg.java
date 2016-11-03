package com.keyi.erp_saomiaochuku.bean;

/**
 * Created by Administrator on 2016/8/31.
 */
public class FinishMsg {

    /**
     * IsOK : true
     * ErrMsg : null
     * Data : {"SendNo":"8E5B4A55-DA34-4953-9D7D-7BBF8948C561","Remark":"减库存成功","Isdel":null}
     */

    private boolean IsOK;
    private Object ErrMsg;
    /**
     * SendNo : 8E5B4A55-DA34-4953-9D7D-7BBF8948C561
     * Remark : 减库存成功
     * Isdel : null
     */

    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String SendNo;
        private String Remark;
        private Object Isdel;

        public String getSendNo() {
            return SendNo;
        }

        public void setSendNo(String SendNo) {
            this.SendNo = SendNo;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public Object getIsdel() {
            return Isdel;
        }

        public void setIsdel(Object Isdel) {
            this.Isdel = Isdel;
        }
    }
}
