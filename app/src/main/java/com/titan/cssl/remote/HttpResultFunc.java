package com.titan.cssl.remote;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by hanyw on 2017/11/13/013.
 */

public class HttpResultFunc<T> implements Func1<Throwable, T> {

    @Override
    public T call(Throwable throwable) {
        return (T) ApiException.getApiExceptionMessage(throwable).message;
    }
}
