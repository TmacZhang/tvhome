package com.mothership.tvhome.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mothership.tvhome.R;
import com.mothership.tvhome.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tv.ui.metro.model.DisplayItem;

import java.net.URI;

/**
 * Created by wangwei on 2/26/16.
 */
public class CardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";

    protected static Context mContext;
    private static int CARD_WIDTH = 200;
    private static int CARD_HEIGHT = 300;

    static class ViewHolder extends Presenter.ViewHolder {
        private DisplayItem mItems;
        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private PicassoImageCardViewTarget mImageCardViewTarget;
        public ImageView mImageView;
        public ViewHolder(View view) {
            super(view);
            //mCardView = (ImageCardView) view;
            mImageCardViewTarget = new PicassoImageCardViewTarget(mCardView);
            mDefaultCardImage = null;//mContext.getResources().getDrawable(R.drawable.movie);
            mImageView = (ImageView) view;
        }

        public void setMovie(DisplayItem m) {
            mItems = m;
        }

        public DisplayItem getMovie() {
            return mItems;
        }

        public ImageCardView getCardView() {
            return mCardView;
        }

        protected void updateCardViewImage(URI uri) {
            Picasso.with(mContext)
                    .load(uri.toString())
                    .resize(Utils.convertDpToPixel(mContext, getWidth()),
                            Utils.convertDpToPixel(mContext, getHeight()))
                    .error(mDefaultCardImage)
                    .into(mImageCardViewTarget);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");
        mContext = parent.getContext();

        //ImageCardView cardView = new ImageCardView(mContext);
        //cardView.setFocusable(true);
        //cardView.setFocusableInTouchMode(true);
        //cardView.setBackgroundColor(mContext.getResources().getColor(R.color.fastlane_background));
        ImageView imageView = new ImageView(mContext);
        imageView.setPadding(20, 20, 20, 20);
        imageView.setFocusable(true);
        imageView.setFocusableInTouchMode(true);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(getWidth(), getHeight()));
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        DisplayItem mItem = (DisplayItem) item;
        ((ViewHolder) viewHolder).setMovie(mItem);


        Log.d(TAG, "onBindViewHolder");
        /*if (mItem.getCardImageUrl() != null) {
            ((ViewHolder) viewHolder).mCardView.setTitleText(mItem.getTitle());
            ((ViewHolder) viewHolder).mCardView.setContentText(mItem.getStudio());
            ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            ((ViewHolder) viewHolder).updateCardViewImage(mItem.getCardImageURI());
        }*/

        //((ViewHolder) viewHolder).mImageView.setLayoutParams(new ViewGroup.LayoutParams((int)(CARD_WIDTH * Math.random()*2), (int)(CARD_HEIGHT * Math.random()*2)));
        ((ViewHolder) viewHolder).mImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.defaultposter));
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
    }

    @Override
    public void onViewAttachedToWindow(Presenter.ViewHolder viewHolder) {
        // TO DO
    }

    public static class PicassoImageCardViewTarget implements Target {
        private ImageCardView mImageCardView;

        public PicassoImageCardViewTarget(ImageCardView imageCardView) {
            mImageCardView = imageCardView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            mImageCardView.setMainImage(bitmapDrawable);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
            mImageCardView.setMainImage(drawable);
        }

        @Override
        public void onPrepareLoad(Drawable drawable) {
            // Do nothing, default_background manager has its own transitions
        }
    }

    public static int getWidth(){
        return CARD_WIDTH;
    };

    public static int getHeight(){
        return CARD_HEIGHT;
    };

    public Context getContext(){return mContext;}


}