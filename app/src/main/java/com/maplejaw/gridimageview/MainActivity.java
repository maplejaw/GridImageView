package com.maplejaw.gridimageview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.maplejaw.gridimageview.gridimageview.GridImageView;
import com.maplejaw.gridimageview.gridimageview.GridImageViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridImageView<String> mGridImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridImageView= (GridImageView<String>) this.findViewById(R.id.gridImageView);
        mGridImageView.setAdapter(new GridImageViewAdapter<String>(){
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String path) {
                Picasso.with(context).load("file://"+path).centerCrop().resize(400,400).into(imageView);
            }

            @Override
            protected void onAddClick(Context context, List<String> list) {
                Intent intent=new Intent(MainActivity.this,SelectorActivity.class);
                startActivityForResult(intent,1234);
            }

            @Override
            protected int getShowStyle() {
                return GridImageView.STYLE_GRID;
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<String> list) {
                super.onItemImageClick(context, index, list);
                Toast.makeText(getApplicationContext(),"--->"+index,Toast.LENGTH_SHORT).show();
            }
        } );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }

        switch (requestCode) {
            case 1234:
                List<String> list = data.getStringArrayListExtra("list");
                //  PictureUtil.cropPhoto(this, Uri.parse("file://"+list.get(0)));
                mGridImageView.setImageData(list,true);
                List<String> l=mGridImageView.getImgDataList();
                break;

        }
    }
}
