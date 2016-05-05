package com.maplejaw.gridimageview.photoutil;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;


public class PhotoSelectorHelper {

	private ContentResolver resolver;
	private Handler handler;

	public PhotoSelectorHelper(Context context) {
		resolver = context.getContentResolver();
		handler=new Handler(Looper.getMainLooper());
	}

	public void getReccentPhotoList(final OnLoadPhotoListener listener) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				final List<String> photos =  getCurrent();
				handler.post(new Runnable() {
					@Override
					public void run() {
						listener.onPhotoLoaded(photos);
					}
				});

			}
		}).start();
	}



	/** 获取最近照片列表 */
	public List<String> getCurrent() {
		List<String> photos = new ArrayList<String>();
		Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns.DATA,
				MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE }, MediaStore.Images.ImageColumns.SIZE+" > "+1024*10 , null, MediaStore.Images.ImageColumns.DATE_ADDED+"  DESC");
		if (cursor == null) {
			return photos;
		}

		while (cursor.moveToNext()){
			photos.add(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
		}

		if(!cursor.isClosed()){
			cursor.close();
		}

		return photos;
	}




	/** 获取本地图库照片回调 */
	public interface OnLoadPhotoListener {
		 void onPhotoLoaded(List<String> photos);
	}



}

