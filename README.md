# GridImageView

一个高仿微信，QQ，微博，贴吧等选择上传图片时的展示控件，继承自ViewGroup，支持网格排列，以及横向排列，使用起来超级简单。
## 效果图
* 网格布局
<img src="image/image1.png"  width="50%"/>
* 水平布局
<img src="image/image2.png"  width="50%"/>

## 使用说明
1. 首先在布局中引用 ` GridImageView`  

 ```xml
 <RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
   >
    <!--这里是xml布局-->
    <com.maplejaw.gridimageview.gridimageview.GridImageView
        android:id="@+id/gridImageView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
  </RelativeLayout>
 ```
2. 设置 `GridImageView` 适配器

 ``` java
  mGridImageView.setAdapter(new GridImageViewAdapter<String>(){
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String path) {
              //这里进行图片展示，可以自由配置加载框架
              //  Picasso.with(context).load("file://"+path).centerCrop().resize(400,400).into(imageView);
            }

            @Override
            protected void onAddClick(Context context, List<String> list) {
               //这里是点击添加按钮时的回调。list为已有图片的集合，常用于记住上次选择的图片
            }
        } );
 ```
3. 添加数据

 ``` java
  mGridImageView.setImageData(list,clearLastData);//第一个参数为list，第二个参数表示是否清除上次的图片集合，
 ```
4. 获取数据，用于上传等等

 ```java
  List<String> l=mGridImageView.getImgDataList();
 ```
## 实现原理
 见我的博客中 [**自定义ViewGroup系列**](http://www.maplejaw.com)。





