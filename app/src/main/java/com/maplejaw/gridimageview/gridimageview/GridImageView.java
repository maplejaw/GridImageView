package com.maplejaw.gridimageview.gridimageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GridImageView<T> extends ViewGroup {

    private GridImageViewAdapter<T> mAdapter;
    private List<T> mImgDataList=new ArrayList<>();
    private int mGap=5; //间隙,默认为5px
    private int mColumnCount=4; // 列数
    private int mGridSize; //每个条目的大小
    private ImageView mAddView;//添加图片的按钮

    public GridImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mAddView=new ImageView(context);
        mAddView.setOnClickListener(new OnClickListener() {//添加mAddView
            @Override
            public void onClick(View v) {
                if (mAdapter != null) {
                    mAdapter.onAddClick(getContext(),mImgDataList);
                }
            }
        });
        addView(mAddView,generateDefaultLayoutParams());
    }
    public GridImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridImageView(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//测量GridImageView占据的大小

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height;
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
        int totalCount=mImgDataList.size()+1;//把mAddView也给算进去
        mGridSize = (totalWidth - mGap * (mColumnCount - 1)) / mColumnCount; //算出每个条目的大小，以宽度为标准。
        int mRowCount= (int) Math.ceil((totalCount*1.0)/mColumnCount);//算出行数
        height = mGridSize * mRowCount + mGap * (mRowCount - 1) + getPaddingTop() + getPaddingBottom();//计算出高度
        setMeasuredDimension(width, height);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childrenCount = mImgDataList.size();
        for (int i = 0; i < childrenCount; i++) {
            ImageView childrenView = (ImageView) getChildAt(i);

            if (mAdapter != null) {
                mAdapter.onDisplayImage(getContext(), childrenView, mImgDataList.get(i));
            }
            int rowNum = i / mColumnCount;
            int columnNum = i % mColumnCount;
            int left = (mGridSize + mGap) * columnNum + getPaddingLeft();
            int top = (mGridSize + mGap) * rowNum + getPaddingTop();
            int right = left + mGridSize;
            int bottom = top + mGridSize;
            childrenView.layout(left, top, right, bottom);
        }


        int rowNum = (childrenCount) / mColumnCount;
        int columnNum = (childrenCount) % mColumnCount;
        int left = (mGridSize + mGap) * columnNum + getPaddingLeft();
        int top = (mGridSize + mGap) * rowNum + getPaddingTop();
        int right = left + mGridSize;
        int bottom = top + mGridSize;
        mAddView.layout(left, top, right, bottom);//调整mAddView的位置
    }



    //根据数据，调整ImageView的数量
    public void setImageData(List<T> lists,boolean clearLastData) {
        if (lists == null || lists.isEmpty()) {
            return;
        }
        if(clearLastData){
            mImgDataList.clear();
        }
        mImgDataList.addAll(lists);
        refreshDataSet();
    }




    private void refreshDataSet() {//根据数据，调整ImageView的数量
        int oldViewCount = getChildCount();//上次的item个数
        int newViewCount = mImgDataList.size()+1;//这次的item个数
        if (oldViewCount > newViewCount) {
            removeViews(newViewCount-1, oldViewCount - newViewCount);//位置减去1，以免移除mAddView
        } else if (oldViewCount < newViewCount) {
            for (int i = oldViewCount; i < newViewCount; i++) {
                ImageView iv = getImageView(i-1);//索引减去1，保证单击事件位置的准确性。
                addView(iv, i-1,generateDefaultLayoutParams());//索引减去1，保证添加在mAddView之前
            }
        }
        requestLayout();
    }

    /**
     * 获得 ImageView
     * 保证了 ImageView 的重用
     *
     * @param position 位置
     */
    private GridItemView getImageView(final int position) {
            GridItemView imageView= new GridItemView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter != null) {
                        mAdapter.onItemImageClick(getContext(), position, mImgDataList);
                    }
                }
            });

            imageView.setOnDelClickL(new GridItemView.OnDelButtonClickL() {
                @Override
                public void onDelClickL() {
                    mImgDataList.remove(position);
                    refreshDataSet();
                }
            });
            return imageView;
    }


    /**
     * 设置适配器
     *
     * @param adapter 适配器
     */
    public void setAdapter(GridImageViewAdapter<T> adapter) {
        mAdapter = adapter;
        mAddView.setImageResource(adapter.generateAddIcon());
    }


    /**
     * 设置宫格间距
     *
     * @param gap 宫格间距 px
     */
    public void setGap(int gap) {
        mGap = gap;
    }

    public List<T> getImgDataList(){
        return  mImgDataList;
    }



    /**
     * 请除所有数据
     */
    public void clear(){
        mImgDataList.clear();
        refreshDataSet();
    }

    public void remove(int position){
        mImgDataList.remove(position);
        refreshDataSet();
    }

    public void add(T t){
        mImgDataList.add(t);
        refreshDataSet();
    }

    public void addAll(List<T> l){
        mImgDataList.addAll(l);
        refreshDataSet();
    }

}

