package com.dinosoftlabs.dnews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class AppTextView  extends android.support.v7.widget.AppCompatTextView {


    private static Paint tPaint = new Paint();

    private static final Canvas sCanvas = new Canvas();
    public static final String DEFAULTCOLOR = "#ffffffff";

    private boolean isNeedDraw = false;

    private static final String INFINITY_UNICODE = "\u221e";
    private static float mFewTextSize;
    private static float mManytextSize;

    public boolean isNeedDraw() {
        return isNeedDraw;
    }

    public void setNeedDraw(boolean isNeedDraw) {
        this.isNeedDraw = isNeedDraw;
    }

    private int mCount = 0;

    public int getCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
    }

    public AppTextView(Context context) {
        super(context);
        init();
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setFocusable(true);
        tPaint.setAntiAlias(true);
        tPaint.setColor(Color.WHITE);

    }


//    private ComponentName getComponent(Object info) {
//        if (info instanceof ComponentName) {
//            return (ComponentName)info;
//        } else if (info instanceof ApplicationInfo) {
//            //return ((ApplicationInfo) info).componentName;
//        } else if (info instanceof ShortcutInfo) {
//           // return ((ShortcutInfo) info).intent.getComponent();
//        } else {
//            return null;
//        }
//    }

}
