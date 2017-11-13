package com.titan.cssl.remote;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by hanyw on 2017/11/13/013.
 */

public class ApiException extends Exception {
    public String message;
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException getApiExceptionMessage(Throwable e){
        ApiException ex=new ApiException();
        if (e instanceof HttpException){//HTTP错误
            HttpException httpException = (HttpException) e;
            switch(httpException.code()){
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                    ex.message = "服务请求超时，请稍后重试。";
                    break;
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                    ex.message = "服务端异常，请与管理员联系";
                    break;
                default:
                    ex.message = "网络错误";  //均视为网络错误
                    break;
            }
        }else if(e instanceof SocketTimeoutException){
            ex.message = "连接超时，请检查网络连接或稍后重试";  //均视为网络错误
        }else if (e instanceof RuntimeException){    //服务器返回的错误
            RuntimeException resultException = (RuntimeException) e;
            ex.message = resultException.getMessage();
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){
            ex.message = "解析错误";            //均视为解析错误
        }else if(e instanceof ConnectException){
            ex.message = "连接失败";  //均视为网络错误
        }else {
            ex.message = "未知错误";   //未知错误
        }
        return ex;
    }
}
