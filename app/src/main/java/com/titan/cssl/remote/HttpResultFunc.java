package com.titan.cssl.remote;

import rx.functions.Func1;

/**
 * Created by hanyw on 2017/11/13/013.
 * 错误信息转换
 */

public class HttpResultFunc<T> implements Func1<Throwable, T> {

    @Override
    public T call(Throwable throwable) {
        return (T) ApiException.getApiExceptionMessage(throwable).message;
    }
}
