package com.luck.viewjumpwithanimator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/4/12
 *************************************************************************************/
public class PicDetailActivity extends Activity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    private boolean finishing;
    private float mScaleX;
    private float mScaleY;
    private ImageView mThumb;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detail);
        float screenWidth = ScreenUtils.getScreenWidth();
        float screenHeight = ScreenUtils.getScreenHeight();
        mScaleX = screenWidth / getResources().getDimensionPixelOffset(R.dimen.pic_width);
        mScaleY = screenHeight / getResources().getDimensionPixelOffset(R.dimen.pic_height);
        Log.d(TAG, "缩放：屏幕：宽" + screenWidth + "  高：" + screenHeight + "   x:" + mScaleX + "  y:" + mScaleY);
        initView();
        initListener();
        execute();
    }

    private void initView() {
        mThumb = findViewById(R.id.imageView);
    }

    private void initListener() {
        mThumb.setOnClickListener(this);
        
    }
    
    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    private void execute() {
        mThumb.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mThumb.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        performEnterAnimation();
                    }
                });
    }

    private void performEnterAnimation() {
        initLocation();
        final ValueAnimator translateVaX = translateVaX(mThumb.getX(), 0);
        final ValueAnimator translateVaY = translateVaY(mThumb.getY(), 0);
        final ValueAnimator scaleVaX = scaleVaX(1f / mScaleX, 1f, false);
        final ValueAnimator scaleVaY = scaleVaY(1f / mScaleY, 1f, false);
        final ValueAnimator alphaVa = alphaVa(0, 1f);
        startAnimation(translateVaX, translateVaY, scaleVaX, scaleVaY, alphaVa);
    }

    /**
     * 将该页面里的view修改为上个页面的大小及位置
     */
    private void initLocation() {
        mThumb.setScaleX(1f / mScaleX);
        mThumb.setScaleY(1f / mScaleY);
        final float translateX = getTranslateX();
        final float translateY = getTranslateY();
        mThumb.setX(translateX);
        mThumb.setY(translateY);
        Log.d(TAG, "获取X:" + translateX + "  y:" + translateY + "  scaleX:" + (1f / mScaleX) + " scaleY:" + (1f / mScaleY));

    }

    /**
     * 获取x的偏移量
     * @return
     */
    private float getTranslateX() {
        float originX = getIntent().getIntExtra("x", 0);
        int[] location = new int[2];
        mThumb.getLocationOnScreen(location);
        Log.d(TAG, "getTranslateX:" + originX + "  location:" + location[0]);
        return originX - (float) location[0];
    }

    /**
     * 获取y的偏移量
     * @return
     */
    private float getTranslateY() {
        float originY = getIntent().getIntExtra("y", 0);
        int[] location = new int[2];
        mThumb.getLocationOnScreen(location);
        Log.d(TAG, "getTranslateY:" + originY + "  location:" + location[1]);
        return originY - (float) location[1];
    }

    /**
     * 退出的时候执行一下退出动画
     */
    @Override
    public void onBackPressed() {
        if (!finishing) {
            finishing = true;
            performExitAnimation();
        }
    }

    private void performExitAnimation() {
        final float translateX = getTranslateX();
        final float translateY = getTranslateY();
        Log.d(TAG, "退出：x:" + mThumb.getX() + "  Y:" + mThumb.getY() + "  translateX:" + translateX + "  translateY:" + translateY);
        final ValueAnimator translateVaX = translateVaX(mThumb.getX(), mThumb.getX() + translateX);
        final ValueAnimator translateVaY = translateVaY(mThumb.getY(), mThumb.getY() + translateY);
        final ValueAnimator scaleVaX = scaleVaX(1f, 1f / mScaleX, true);
        final ValueAnimator scaleVaY = scaleVaY(1f, 1f / mScaleY, true);
        final ValueAnimator alphaVa = alphaVa(1f, 0.5f);
        exitListener(translateVaY);
        startAnimation(translateVaX, translateVaY, scaleVaX, scaleVaY, alphaVa);
    }

    @NonNull
    private ValueAnimator translateVaX(float from, float to) {
        ValueAnimator translateVaX = ValueAnimator.ofFloat(from, to);
        translateVaX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                mThumb.setX(value);
            }
        });
        return translateVaX;
    }

    @NonNull
    private ValueAnimator translateVaY(float from, float to) {
        ValueAnimator translateVaY = ValueAnimator.ofFloat(from, to);
        translateVaY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                mThumb.setY(value);
            }
        });
        return translateVaY;
    }

    /**
     * 关闭页面时候的缩放要以左上角为原点，否则会以中心点为原点进行缩放，这样计算出来的xy偏移量就不准确了
     * @param from
     * @param to
     * @param isExit
     * @return
     */
    @NonNull
    private ValueAnimator scaleVaX(float from, float to, boolean isExit) {
        if (isExit) {
            mThumb.setPivotX(from);
        }
        ValueAnimator scaleVaX = ValueAnimator.ofFloat(from, to);
        scaleVaX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                mThumb.setScaleY(value);
            }
        });
        return scaleVaX;
    }

    @NonNull
    private ValueAnimator scaleVaY(float from, float to, boolean isExit) {
        if (isExit) {
            mThumb.setPivotY(from);
        }
        ValueAnimator scaleVa = ValueAnimator.ofFloat(from, to);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                mThumb.setScaleX(value);

            }
        });
        return scaleVa;
    }


    @NonNull
    private ValueAnimator alphaVa(float from, float to) {
        ValueAnimator alphaVa = ValueAnimator.ofFloat(from, to);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                mThumb.setAlpha(value);
            }
        });
        return alphaVa;
    }

    private void exitListener(ValueAnimator translateVa) {
        translateVa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private void startAnimation(ValueAnimator translateVaX, ValueAnimator translateVaY, ValueAnimator scaleVaX,
                                ValueAnimator scaleVaY, ValueAnimator alphaVa) {
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(translateVaX).with(translateVaY).with(scaleVaX).with(scaleVaY).with(alphaVa);
        animSet.setDuration(350);
        animSet.start();
    }
}
