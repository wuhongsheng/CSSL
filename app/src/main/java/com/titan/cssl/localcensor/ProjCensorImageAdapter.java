package com.titan.cssl.localcensor;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemCensorImgBinding;

import java.util.List;

/**
 * Created by hanyw on 2017/11/6/006.
 */

public class ProjCensorImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;
    public ProjCensorImageAdapter(Context context, List<String> list){
        this.mContext = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemCensorImgBinding binding = null;
        try {
            if (view==null){
                binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.item_censor_img,viewGroup,false);
            }else {
                binding = DataBindingUtil.getBinding(view);
            }
//        binding.setVariable(BR.url,list.get(i));
            Log.e("tag","list:"+list);
            binding.itemCensorImg.setImageBitmap(decodeSampledBitmap(list.get(i),100,100));

        } catch (Exception e) {
            Log.e("tag","result2:"+e);
        }
        return binding.getRoot();
    }

    /**
     * 压缩图片
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmap(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //设为true可以先获取图片属性，但不加载图片
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //计算压缩比例
        options.inSampleSize = computSampleSize(options, reqWidth, reqHeight);
        //重新设为false，根据上面计算的宽高重新加载图片，得到压缩后的图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 计算图片压缩比例
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int computSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //获取图片的原始宽度和高度
        final int height = options.outHeight;
        final int width = options.outWidth;
        //先设图片比例为1
        int inSampleSize = 1;
        //判断原图和需要压缩的尺寸大小
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            //通过三元运算符选择小的一个比例
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
