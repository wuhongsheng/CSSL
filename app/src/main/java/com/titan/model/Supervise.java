package com.titan.model;

import java.util.List;

/**
 * Created by hanyw on 2017/12/4/004.
 * 日常监督记录
 */

public class Supervise {
    private Boolean result;
    private List<SuperviseSub> content;
    private String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<SuperviseSub> getContent() {
        return content;
    }

    public void setContent(List<SuperviseSub> content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class SuperviseSub {

        private String USER;
        private String CreateTime;
        private String Reason;


        public String getUSER() {
            return USER;
        }

        public void setUSER(String USER) {
            this.USER = USER;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }
    }
}
