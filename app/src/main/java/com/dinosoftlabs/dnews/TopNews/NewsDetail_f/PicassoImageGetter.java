//package com.dinosoftlabs.dnews.TopNews.NewsDetail_f;
//
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.text.Html;
//
//import com.dinosoftlabs.dnews.AppTextView;
//import com.dinosoftlabs.dnews.CodeClasses.Variables;
//import com.dinosoftlabs.dnews.social_app.R;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;
//
//public class PicassoImageGetter implements Html.ImageGetter {
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
//    @Override
//    public Drawable getDrawable(String source) {
//        BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder();
//
////        Picasso.get()
////                .load(Variables.BASEURL + Arr_1.getString("attachment"))
////                .placeholder(R.mipmap.ic_dnews)
////                .error(R.mipmap.ic_dnews)
////                .into(image_view);
//        Picasso.get()
//                .load(source)
//                .placeholder(R.mipmap.ic_dnews)
//                .into(drawable);
//
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
//        public void onBitmapFailed(Exception e, Drawable drawable) {
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
//}
