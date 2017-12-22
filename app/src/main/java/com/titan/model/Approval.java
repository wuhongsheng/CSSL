package com.titan.model;

import java.util.List;

/**
 * Created by hanyw on 2017/12/4/004.
 * 行政审批记录
 */

public class Approval {

    private Boolean result;
    private List<Approve> content;
    private String message;

    public List<Approve> getContent() {
        return content;
    }

    public void setContent(List<Approve> content) {
        this.content = content;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * USER : jzd_admin
     * CreateTime : 2017/12/4 0:00:00
     * Reason : 测试
     */

    public static class Approve{

        /**
         * Reason : 监督站，转行政审批。。。。。。
         * CreateTime : 2017/12/1 22:25:16
         * imageurl : /UpLoadFiles/superadmin/2017-11/1dd11a00-f386-4adf-89a4-088e985918e8_3f827e8a-d9c3-47ea-9fd0-9075756d8a90_qm1.jpg
         */

        private String Reason;
        private String CreateTime;
        private String imageurl;

        public String getReason() {
            return Reason;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

    }
}
