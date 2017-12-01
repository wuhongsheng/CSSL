package com.titan.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by hanyw on 2017/11/13/013.
 * 返回值model
 */

public class ResultModel<T> implements Serializable {
    /**
     * 表示操作是否成功; true:成功, false:失败
     */
    private Boolean result;
    /**
     * 具体数据 "content":{"NUM":"","NAME":"","TIME":"","STATE":"","TYPE":"","DISTANCE":""}
     */
//    private T content;

    private List<Map<String,T>> content;
    /**
     * 错误信息
     */
    private String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

//    public T getContent() {
//        return content;
//    }
//
//    public void setContent(T content) {
//        this.content = content;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Map<String, T>> getContent() {
        return content;
    }

    public void setContent(List<Map<String, T>> content) {
        this.content = content;
    }
}
