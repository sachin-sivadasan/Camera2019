package com.schn.camera2019.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SquareImageView extends ImageView {

    public SquareImageView(final Context context) {
        this(context, null);
    }

    public SquareImageView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec), height = MeasureSpec.getSize(heightMeasureSpec);
        final ViewGroup.LayoutParams lp = getLayoutParams();
        if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT && lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            super.onMeasure(makeSpec(heightMeasureSpec, MeasureSpec.EXACTLY), makeSpec(heightMeasureSpec, MeasureSpec.EXACTLY));
        } else if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT && lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            super.onMeasure(makeSpec(widthMeasureSpec, MeasureSpec.EXACTLY), makeSpec(widthMeasureSpec, MeasureSpec.EXACTLY));
        } else {
            if (width > height) {
                super.onMeasure(makeSpec(heightMeasureSpec, MeasureSpec.EXACTLY), makeSpec(heightMeasureSpec, MeasureSpec.EXACTLY));
            } else {
                super.onMeasure(makeSpec(widthMeasureSpec, MeasureSpec.EXACTLY), makeSpec(widthMeasureSpec, MeasureSpec.EXACTLY));
            }
        }
    }

    private static int makeSpec(int spec, int mode) {
        return MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(spec), mode);
    }

}