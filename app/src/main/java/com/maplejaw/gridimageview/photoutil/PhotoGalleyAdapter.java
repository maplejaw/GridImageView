package com.maplejaw.gridimageview.photoutil;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.maplejaw.gridimageview.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PhotoGalleyAdapter extends BaseAdapter {
    public static List<String> mSelectedImage = new LinkedList<>();

    protected Context mContext;
    protected List<String> mList;
    protected LayoutInflater mLayoutInflater;


    public PhotoGalleyAdapter(Context context) {
        this(context, null);
    }

    public PhotoGalleyAdapter(Context context, List<String> models) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        if (models == null) {
            models = new ArrayList<>();
        }
        this.mList = models;

    }







    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {

        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;

       final String path = getItem(position);
        if(convertView==null){
            imageView= (ImageView) mLayoutInflater.inflate(R.layout.grid_photo_item,parent,false);

        }else{
            imageView= (ImageView) convertView;
        }
        if(mSelectedImage.contains(path)){
            imageView.setColorFilter(0x80000000);
        }else {
            imageView.setColorFilter(null);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectedImage.contains(path)){
                    mSelectedImage.remove(path);
                    imageView.setColorFilter(null);
                }else{
                    mSelectedImage.add(path);
                    imageView.setColorFilter(0x80000000);
                }
            }
        });

        Picasso.with(mContext).load("file://"+path).centerCrop().resize(400,400).into(imageView);

        return imageView;
    }












    /** 更新数据 */
    public void notifyDataSetChanged(List<String> models,boolean isRefresh) {
        if(isRefresh){
            this.mList.clear();
        }
        if(models==null||models.size()==0){
            this.notifyDataSetChanged();
            return;
        }

        this.mList.addAll(models);
        this.notifyDataSetChanged();
    }



}
