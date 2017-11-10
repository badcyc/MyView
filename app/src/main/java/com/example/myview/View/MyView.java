package com.example.myview.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.myview.R;

/**
 * Created by cyc20 on 2017/11/7.
 */

public class MyView extends View {
    private int bgColor;
    private int foreColor;
    private int textColor;
    private int textSize;
    private Paint mPaint;
    private Paint circlePaint;
    private int currentPercent;
    private int widthbg;
    private int heightbg;
    private Point startPoint;
    private int waveHeight=60;
    private int waveWidth=100;
    private int viewBottom;
    private int viewTop;
    public MyView(Context context){
        this(context,null);
    }
    public MyView(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }
    public MyView(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        TypedArray array=context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyView,defStyle,0);
        int n=array.getIndexCount();
        for (int i=0;i<n;i++){
            int attr=array.getIndex(i);
            switch (attr){
                case R.styleable.MyView_backgroundColor:
                    bgColor=array.getColor(attr,Color.RED);
                    break;
                case R.styleable.MyView_foreColor:
                    foreColor=array.getColor(attr,Color.BLUE);
                    break;
                case R.styleable.MyView_textColor:
                    textColor=array.getColor(attr,Color.WHITE);
                    break;
                case R.styleable.MyView_titleSize:
                    textSize=array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
                default:break;
            }
        }
        array.recycle();
        init();
        Log.d("myview","start");
    }

    public void init(){
        mPaint=new Paint();
        circlePaint=new Paint();
        mPaint.setAntiAlias(true);//是否抗锯齿;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        circlePaint.setColor(bgColor);
        Path circlePath = new Path();
        circlePath.addCircle(widthbg / 2, heightbg / 2, widthbg / 2, Path.Direction.CW);
        canvas.clipPath(circlePath);
        canvas.drawPaint(circlePaint);
        canvas.drawCircle(widthbg / 2, heightbg / 2, widthbg / 2, circlePaint);
        mPaint.setColor(textColor);
        canvas.drawCircle(widthbg / 2, heightbg / 2, widthbg / 2,mPaint);

        mPaint.setColor(textColor);
        mPaint.setTextSize(widthbg/10);
        canvas.drawText(String.valueOf(currentPercent),widthbg*1/2,widthbg/2,mPaint);

        Path path=new Path();
        startPoint.y=(int)(heightbg-(currentPercent/100.0*heightbg));
        path.moveTo(startPoint.x,startPoint.y);
        int j=1;
        for (int i=1;i<=8;i++){
            if (i%2==1){
                path.quadTo(startPoint.x+(waveWidth*j),startPoint.y-waveHeight,startPoint.x+(waveWidth*2)*i,startPoint.y);
            }else {
                path.quadTo(startPoint.x+(waveWidth*j),startPoint.y+waveHeight,startPoint.x+(waveWidth*2)*i,startPoint.y);
            }
            j+=2;
        }
        path.lineTo(widthbg,heightbg);
        path.lineTo(startPoint.x,heightbg);
        path.lineTo(startPoint.x,startPoint.y);
        path.close();
        mPaint.setColor(foreColor);
        canvas.drawPath(path,mPaint);
        if (currentPercent%2==0) {
            startPoint.x += 20;
            if (startPoint.x + 100 >= 0) {
                startPoint.x = -400;
            }
        }
        //time++;
        //canvas.drawLine(0,heightbg,widthbg,heightbg,mPaint);
    }

  //  public void setNumber(int targetPercent){
    //    this.targetPercent=targetPercent;
    //}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width,height;
        if (widthMode==MeasureSpec.AT_MOST){
            width=widthSize;
        }else {
            width=widthSize*1/2;
        }
        if (heightMode==MeasureSpec.AT_MOST){
            height=heightSize;
        }else {
            height=heightSize*1/2;
        }
        widthbg=width;
        heightbg=height;
        startPoint=new Point(-400,height);
        viewBottom=getBottom()-getPaddingBottom();
        viewTop=getTop()+getPaddingTop();
        setMeasuredDimension(width,height);
 }
 public void setCurrentPercent(final int percent){

     ValueAnimator percentAnimator=ValueAnimator.ofInt(0,percent);
     percentAnimator.setDuration(5000/100*percent);
     percentAnimator.setTarget(percent);
     percentAnimator.setInterpolator(new LinearInterpolator());
     percentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
         @Override
         public void onAnimationUpdate(ValueAnimator valueAnimator) {
             MyView.this.currentPercent=(int)valueAnimator.getAnimatedValue();
           /*  startPoint.x+=40;
             if(startPoint.x+40>=widthbg){
                 startPoint.x=-120;
             }
             */
             invalidate();

         }
     });
     percentAnimator.start();
 }
}
