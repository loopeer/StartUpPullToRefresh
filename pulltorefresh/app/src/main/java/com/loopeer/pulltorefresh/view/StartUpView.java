package com.loopeer.pulltorefresh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.loopeer.pulltorefresh.R;

public class StartUpView extends View {

    private float mWidth;
    private float mBorderWidth = 8;
    private float mPoint;
    private int index = -1;
    private static Handler mHandler;
    private Runnable mRunnable;
    private int mLightColor;
    private int mDarkColor;
    private Paint mBPaint;
    private Paint mTPaint;
    private RectF mBorderRect;
    private Path mOnePath;
    private Path mTwoPath;
    private Path mThreePath;
    private Path mFourPath;
    private int[] tColor;
    private float angle = 0;

    public StartUpView(Context context) {
        this(context, null);
    }

    public StartUpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StartUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHandler = new Handler();
        mLightColor = getResources().getColor(R.color.startuplight);
        mDarkColor = getResources().getColor(R.color.startupdark);
        tColor = new int[]{Color.WHITE, mDarkColor, mLightColor, mDarkColor};
    }

    private Paint creatBPaint() {
        Paint paint = new Paint();
        paint.setColor(mLightColor);
        paint.setAntiAlias(true);
        paint.setDither(true);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(mBorderWidth);
        return paint;
    }

    private Paint creatTPaint() {
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        return paint;
    }

    private Path creatPath(float oneX, float oneY, float twoX, float twoY) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(oneX, oneY);
        path.lineTo(twoX, twoY);
        path.close();
        return path;
    }

    public void rotate(float angle) {
        this.angle = angle;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mPoint = mWidth / 2 - mBorderWidth - 30;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBPaint = creatBPaint();
        mTPaint = creatTPaint();
        float borderPoint = mWidth / 2 - mBorderWidth / 2 - 30;
        mBorderRect = new RectF(-borderPoint, -borderPoint, borderPoint, borderPoint);
        mOnePath = creatPath(mPoint, mPoint, mPoint, -mPoint);
        mTwoPath = creatPath(-mPoint , -mPoint, mPoint , -mPoint);
        mThreePath = creatPath(-mPoint, -mPoint, -mPoint, mPoint);
        mFourPath = creatPath(-mPoint , mPoint, mPoint , mPoint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mWidth / 2);

        canvas.rotate(-angle);

        canvas.drawRect(mBorderRect, mBPaint);

        mTPaint.setColor(0 == index ? mLightColor : tColor[0]);
        canvas.drawPath(mOnePath, mTPaint);
        mTPaint.setColor(1 == index ? mLightColor : tColor[1]);
        canvas.drawPath(mTwoPath, mTPaint);
        mTPaint.setColor(2 == index ? mLightColor : tColor[2]);
        canvas.drawPath(mThreePath, mTPaint);
        mTPaint.setColor(3 == index ? mLightColor : tColor[3]);
        canvas.drawPath(mFourPath, mTPaint);
    }

    public void stopBlink() {
        mHandler.removeCallbacks(mRunnable);
        index = -1;
        invalidate();
    }

    public void startBlink() {
        index = -1;
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, 250);
                index = ++index >= tColor.length ? 0 : index;
                postInvalidate();
            }
        };
        mHandler.post(mRunnable);
    }

}
