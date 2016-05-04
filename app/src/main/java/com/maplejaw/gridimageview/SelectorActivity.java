package com.maplejaw.gridimageview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import com.maplejaw.gridimageview.photoutil.PhotoGalleyAdapter;
import com.maplejaw.gridimageview.photoutil.PhotoSelectorHelper;

import java.util.ArrayList;
import java.util.List;

public class SelectorActivity extends AppCompatActivity implements PhotoSelectorHelper.OnLoadPhotoListener {


    private GridView mGridView;
    private PhotoGalleyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selector);
        mGridView= (GridView) this.findViewById(R.id.gridView);
        mGridView.setAdapter(mAdapter=new PhotoGalleyAdapter(this));
        new PhotoSelectorHelper(this).getReccentPhotoList(this);
    }

    @Override
    public void onPhotoLoaded(List<String> photos) {
        mAdapter.notifyDataSetChanged(photos,true);
    }

    public void btnClick(View view){
        ArrayList<String> list=new ArrayList<String>();
        for(String s:PhotoGalleyAdapter.mSelectedImage){
            list.add(s);
        }
        PhotoGalleyAdapter.mSelectedImage.clear();
        Intent intent=new Intent();
        intent.putStringArrayListExtra("list",list);
        setResult(RESULT_OK,intent);
        finish();

    }


}
