package com.titan.cssl.localcensor;

/**
 * Created by hanyw on 2017/11/6/006.
 * 现场审查接口
 */

public interface ProjLocalCensor {
    /**
     * 添加照片
     */
    void addImage();

    /**
     * 提交数据
     */
    void localCensorSubmit();

    /**
     * 删除照片
     * @param position 照片数组下标
     */
    void del(int position);
}
