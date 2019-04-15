//package com.dinosoftlabs.dnews.social_app;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.text.Html;
//import android.text.Spanned;
//import android.util.AttributeSet;
//import android.widget.TextView;
//
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;
//
//public class PicassoImageGetter   implements Html.ImageGetter {
//
////    public HtmlTextView(Context context, AttributeSet attrs) {
////        super(context, attrs);
////        TypedArray typedArray = context.obtainStyledAttributes(attrs, null);
////        String html = context.getResources().getString(typedArray.getResourceId(R.styleable.HtmlTextView_android_text, 0));
////        typedArray.recycle();
////
////        Spanned spannedFromHtml = Html.fromHtml(html, new DrawableImageGetter(), null);
////        setText(spannedFromHtml);
////    }
////
////    private class DrawableImageGetter implements Html.ImageGetter {
////        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
////        @Override
////        public Drawable getDrawable(String source) {
////            Resources res = getResources();
////            int drawableId = res.getIdentifier(source, "drawable", getContext().getPackageName());
////            Drawable drawable = res.getDrawable(drawableId, getContext().getTheme());
////
////            int size = (int) getTextSize();
////            int width = size;
////            int height = size;
////
//////            int width = drawable.getIntrinsicWidth();
//////            int height = drawable.getIntrinsicHeight();
////
////            drawable.setBounds(0, 0, width, height);
////            return drawable;
////        }
////    }
//
//    private AppTextView textView = null;
//
//    public PicassoImageGetter() {
//
//    }
//
//    public PicassoImageGetter(AppTextView target) {
//        textView = target;
//    }
//
//    @Override
//    public Drawable getDrawable(String source) {
//        BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder();
//        Picasso.with(App.get())
//                .load(source)
//                .placeholder(R.mipmap.ic_dnews)
//                .into(drawable);
//        return drawable;
//    }
//
//    private class BitmapDrawablePlaceHolder extends BitmapDrawable implements Target {
//
//        protected Drawable drawable;
//
//        @Override
//        public void draw(final Canvas canvas) {
//            if (drawable != null) {
//                drawable.draw(canvas);
//            }
//        }
//
//        public void setDrawable(Drawable drawable) {
//            this.drawable = drawable;
//            int width = drawable.getIntrinsicWidth();
//            int height = drawable.getIntrinsicHeight();
//            drawable.setBounds(0, 0, width, height);
//            setBounds(0, 0, width, height);
//            if (textView != null) {
//                textView.setText(textView.getText());
//            }
//        }
//
//        @Override
//        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//            setDrawable(new BitmapDrawable(App.get().getResources(), bitmap));
//        }
//
//        @Override
//        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//        }
//
//
//
//        @Override
//        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//        }
//
//    }
//
//
//}
