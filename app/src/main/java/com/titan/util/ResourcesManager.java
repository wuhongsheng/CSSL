package com.titan.util;

import android.content.Context;
import android.os.storage.StorageManager;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ResourcesManager implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ROOT_MAPS = "/maps";
    public static final String DCIM = "/DCIM";
    private Context mContext;
    public static final String image = "/image";
    public static final String otms = "/otms";
    private static final String otitan_map = "/otitan.map";
    public static final String otitan = "/otitan";
    private static ResourcesManager resourcesManager;
    public static final String NAME = "NAME"; // 图片名字键
    public static final String BITMAP = "BITMAP"; // 图片BITMAP键

    public synchronized static ResourcesManager getInstance(Context context) {
        if (resourcesManager == null) {
            try {
                resourcesManager = new ResourcesManager(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resourcesManager;
    }

    private ResourcesManager(Context context) throws Exception {
        this.mContext = context;
    }

    /**
     * 获取手机内部存储地址和外部存储地址
     */
    public String[] getMemoryPath() {

        StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        String[] paths = null;
        try {
            paths = (String[]) sm.getClass().getMethod("getVolumePaths").invoke(sm);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return paths;
    }

    /**
     * 创建文件夹
     */
    public static void createFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = null;
    }


    /**
     * @return 照片存储地址
     */
    public String getDCIMPath() {
        String dataPath = "文件可用地址";
        String[] memoryPath = getMemoryPath();
        for (int i = 0; i < memoryPath.length; i++) {
            File file = new File(memoryPath[i] + DCIM);
            if (file.exists()) {
                dataPath = memoryPath[i] + DCIM;
                break;
            }
        }
        return dataPath;
    }

    /**
     * @return 照片名称
     */
    public static String getPicName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'img'yyyyMMddHHmmss", Locale.CHINA);
        return sdf.format(date) + ".jpg";
    }

    public static String getCSPicName(String csName){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(csName+"_yyyyMMddHHmmss", Locale.CHINA);
        return sdf.format(date) + ".jpg";
    }
    /**
     * 取文件可用地址
     */
    public String getFilePath(String path) {
        String dataPath = "文件可用地址";
        String[] memoryPath = getMemoryPath();
        for (int i = 0; i < memoryPath.length; i++) {
            File file = new File(memoryPath[i] + ROOT_MAPS + path);
            if (file.exists() && file.isFile()) {
                dataPath = memoryPath[i] + ROOT_MAPS + path;
                break;
            }
        }
        return dataPath;
    }

    /**
     * 获取文件夹可用地址
     */
    public String getFolderPath(String path) {
        String dataPath = "文件夹可用地址";
        String[] memoryPath = getMemoryPath();
        for (int i = 0; i < memoryPath.length; i++) {
            File file = new File(memoryPath[i] + ROOT_MAPS + path);
            if (file.exists()) {
                dataPath = memoryPath[i] + ROOT_MAPS + path;
                break;
            } else {
                file.mkdirs();
                //break;
                /*if (path.equals("")) {
					file.mkdirs();
				}*/
            }
        }
        return dataPath;
    }

    /**
     * 获取基础地图数据路径
     *
     * @return
     */
    public String getBaseLayerPath() {
        String dataPath = "文件夹可用地址";
        String[] memoryPath = getMemoryPath();
        for (int i = 0; i < memoryPath.length; i++) {
            File file = new File(memoryPath[i] + ROOT_MAPS + otitan_map);
            if (file.exists()) {
                dataPath = memoryPath[i] + ROOT_MAPS + otitan_map;
                break;
            } else if (i == memoryPath.length - 1) {
                file.mkdirs();
                dataPath = file.getAbsolutePath();
                break;
            }
        }
        return dataPath;
    }

    /**
     * 获取影像文件列表
     */
    public List<File> getImgTitlePath() {
        return getPahts(otitan_map, "image");
    }

    /**
     * 获取基础图文件列表
     */
    public List<File> getBaseTitleFiles() {
        return getPahts(otitan_map, "title");
    }

    /**
     * 根据关键过滤
     *
     * @param path
     * @param keyword
     * @return
     */
    public List<File> getPahts(String path, String keyword) {
        List<File> list = new ArrayList<File>();
        String[] array = getMemoryPath();
        for (int i = 0; i < array.length; i++) {
            File file = new File(array[i] + ROOT_MAPS + path);
            if (file.exists()) {
                for (int m = 0; m < file.listFiles().length; m++) {
                    if (file.listFiles()[m].isFile()
                            && file.listFiles()[m].getName().contains(keyword)) {
                        list.add(file.listFiles()[m]);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 删除指定文件夹下指定图片
     */
    public void deleteImage(String path, String ydh) {
        File f = new File(path);
        if (f.exists()) {
            File[] fl = f.listFiles();
            for (int i = 0; i < fl.length; i++) {
                if (fl[i].toString().endsWith(".jpg")
                        && fl[i].toString().contains(ydh)) {
                    fl[i].delete();
                }
            }
        }
    }

    /**
     * 删除指定文件夹下指定名字图片
     */
    public void deleteImageForName(String path, String imagename) {
        File f = new File(path);
        if (f.exists()) {
            File[] fl = f.listFiles();
            for (int i = 0; i < fl.length; i++) {
                if (fl[i].getName().toString().trim().equals(imagename.trim())) {
                    fl[i].delete();
                }
            }
        }
    }
}
