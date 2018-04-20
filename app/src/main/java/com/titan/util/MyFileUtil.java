package com.titan.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


/**
 * Created by hanyw on 2017/11/9/009.
 * 文件帮助类
 */
public class MyFileUtil {

    /**
     * 打开图片
     *
     * @param Path 地址
     * @return intent
     */
    public static Intent getImageFileIntent(String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }


    /**
     * 打开pdf文件
     *
     * @param Path 地址
     * @return intent
     */
    public static Intent getPdfFileIntent(String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * 打开word文件
     *
     * @param Path 地址
     * @return intent
     */
    public static Intent getWordFileIntent(String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * 新建文件夹
     *
     * @param filePath 地址
     */
    public static void makeRootDirectory(String filePath) {
        File file;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    /**
     * 复制文件
     *
     * @param oldPathFile 准备复制的文件源
     * @param newPathFile 拷贝到新绝对路径带文件名
     * @throws IOException
     */
    public static void copyFile(String oldPathFile, String newPathFile) throws IOException {
        int bytesum = 0;
        int byteread = 0;
        File oldfile = new File(oldPathFile);
        if (oldfile.exists()) { //文件存在时
            InputStream inStream = new FileInputStream(oldPathFile); //读入原文件
            FileOutputStream fs = new FileOutputStream(newPathFile);
            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
        }

    }

    /**
     * 清除图片磁盘缓存
     */
    public static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件转成base64 字符串
     *
     * @param path 文件路径
     */
    public static String encodeBase64File(String path) throws IOException {
        File file = new File(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (file.exists()) {
            Bitmap image = BitmapFactory.decodeFile(path);
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//100表示不压缩，把压缩后的数据存放到baos中
            int options = 90;
            while (baos.toByteArray().length / 1024 > 500) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
                baos.reset(); // 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;// 每次都减少10
            }
            byte[] buffer = baos.toByteArray();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } else {
            return "";
        }
    }

    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void decoderBase64File(String base64Code, String targetPath) throws IOException {
        File desFile = new File(targetPath);
        FileOutputStream fos;
        byte[] decodeBytes = Base64.decode(base64Code.getBytes(), Base64.DEFAULT);
        fos = new FileOutputStream(desFile);
        fos.write(decodeBytes);
        fos.close();
    }

    /**
     * 将base64字符保存文本文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void toFile(String base64Code, String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    public static String getProperties(Context context, String proName) {
        String pro_value;
        Properties props = new Properties();
        try {
            InputStream in = context.getAssets().open("config");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            props.load(bufferedReader);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        pro_value = props.getProperty(proName);
        if (pro_value == null) {
            pro_value = "";
        }
        return pro_value;
    }

}
