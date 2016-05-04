package com.maplejaw.gridimageview.gridimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.maplejaw.gridimageview.R;


/**
 * @author maplejaw
 */
public class GridItemView extends ImageView {
    private OnDelButtonClickL mDelClickL;//监听删除
    private Paint mPaint;//画笔
    private Bitmap mDelBitmap;//删除图片
    private Rect mDelBound;//删除矩形框
    private int mDelBoundPadding=5;//默认为5
    public GridItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridItemView(Context context) {
        super(context);
        init();
    }

    private void init(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0x88000000);
        mDelBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.del_img);
        mDelBound=new Rect();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDelBound.set(getWidth()-getPaddingRight()-mDelBitmap.getWidth()-mDelBoundPadding*2,getPaddingTop(),getWidth()-getPaddingRight(),getPaddingTop()+mDelBitmap.getHeight()+mDelBoundPadding*2);
        canvas.drawRect(mDelBound,mPaint);//先画一个删除框
        canvas.drawBitmap(mDelBitmap,mDelBound.left+mDelBoundPadding,mDelBound.top+mDelBoundPadding,null);//然后画一个删除按钮


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean touchable = event.getX() > mDelBound.left
                    && event.getX() < mDelBound.right&&event.getY()>mDelBound.top&&event.getY()<mDelBound.bottom;

            if (touchable&&mDelClickL!=null) {//点击删除键
                mDelClickL.onDelClickL();
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    public void setDelBoundPadding(int boundPadding){
        this.mDelBoundPadding=boundPadding;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }

    public void setOnDelClickL(OnDelButtonClickL l){
        this.mDelClickL=l;
    }
    public interface OnDelButtonClickL{
        void onDelClickL();
    }
}