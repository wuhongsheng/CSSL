package com.titan.cssl.projdetails;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.titan.cssl.BR;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ItemProjMeasureChildBinding;
import com.titan.cssl.databinding.ItemProjMeasureParentBinding;
import com.titan.model.ProjDetailMeasure;
import com.titan.util.MaterialDialogUtil;
import com.titan.util.MyFileUtil;
import com.titan.util.ResourcesManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by hanyw on 2017/11/3/003.
 * 水保措施
 */

public class ProjmeasureExpLVAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> pList;
    private List<List<ProjDetailMeasure.subBean>> cList;
    private ProjDetailViewModel viewModel;
    private AsyncTask task;

    public ProjmeasureExpLVAdapter(Context context, List<String> pList,List<List<ProjDetailMeasure.subBean>> cList,
                                   ProjDetailViewModel viewModel){
        this.pList = pList;
        this.cList = cList;
        this.mContext = context;
        this.viewModel = viewModel;
    }

    @Override
    public int getGroupCount() {
        return pList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return cList.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return pList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return cList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ItemProjMeasureParentBinding binding;
        if (view==null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_proj_measure_parent,
                    viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setVariable(BR.value, pList.get(i));
        return binding.getRoot();
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ItemProjMeasureChildBinding binding;
        if (view==null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_proj_measure_child,
                    viewGroup,false);
        }else {
            binding = DataBindingUtil.getBinding(view);
        }
//        List<String> jpgList = new ArrayList<>();
//        List<String> docList = new ArrayList<>();
//        for (int j = 0; j < 3; j++) {
//            jpgList.add("测试图片"+j+".jpg");
//            docList.add("测试文档"+j+".doc");
//        }
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,R.layout.item_arrayadapter_test,jpgList);
//        binding.projPhotoList.setAdapter(arrayAdapter);
//        setListViewHeightBasedOnChildren(binding.projPhotoList);
//        binding.projPhotoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                String path = "http://b.hiphotos.baidu.com/image/pic/item/a686c9177f3e6709085282ec31c79f3df8dc5557.jpg";
////                task = new getImageCacheAsyncTask(mContext).execute(path);
//            }
//        });
//        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(mContext,R.layout.item_arrayadapter_test,docList);
//        binding.projDocList.setAdapter(arrayAdapter1);
//        setListViewHeightBasedOnChildren(binding.projDocList);
//        binding.projDocList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                String path = "/storage/emulated/0/接口需求.docx";
////                mContext.startActivity(MyFileUtil.getWordFileIntent(path));
//            }
//        });
        binding.setViewmodel(viewModel);
        binding.setVariable(BR.subBean,cList.get(i).get(i1));
        return binding.getRoot();
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

//    private class getImageCacheAsyncTask extends AsyncTask<String, Void, File> {
//        private final Context context;
//        private MaterialDialog dialog;
//        public getImageCacheAsyncTask(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = MaterialDialogUtil.showLoadProgress(mContext, "正在加载...", new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialogInterface) {
//                    task.cancel(true);
//                }
//            });
//            dialog.show();
//        }
//
//        @Override
//        protected File doInBackground(String... params) {
//            String imgUrl =  params[0];
//            try {
//                return Glide.with(context)
//                        .load(imgUrl)
//                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                        .get();
//            } catch (Exception ex) {
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(File result) {
//            if (result == null) {
//                Message message = new Message();
//                message.what = ProjmeasureFragment.LOAD_ERROR;
////                mHandler.sendMessage(message);
//                MaterialDialogUtil.stopProgress();
//                return;
//            }
//            //得到缓存文件
//            Log.e("tag",result.getAbsolutePath());
//            //自定义路径
//            String path = ResourcesManager.getInstance(mContext).getDCIMPath() + "/"
//                    + ResourcesManager.getPicName();
//            File file = new File(path);
//            //若文件存在直接打开
//            if (file.exists()){
//                mContext.startActivity(MyFileUtil.getImageFileIntent(path));
//                MaterialDialogUtil.stopProgress();
//                return;
//            }
//            //不存在就缓存到自定义路径
//            try {
//                MyFileUtil.copyFile(result.getAbsolutePath(),path);
//                MyFileUtil.clearImageDiskCache(mContext);
//                mContext.startActivity(MyFileUtil.getImageFileIntent(path));
//            } catch (IOException e) {
//                Log.e("tag","image error:"+e);
//            }
//            MaterialDialogUtil.stopProgress();
//        }
//    }
}