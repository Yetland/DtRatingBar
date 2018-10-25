package com.yetland.ratingbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author YETLAND 2018/10/23 12:12
 */
public class RatingView extends LinearLayout {

    private Context mContext;
    private ImageView mImageView;
    private static final int STATE_UN_STAR = 0;
    private static final int STATE_HALF_STAR = 1;
    private static final int STATE_STAR = 2;
    private int mStarImageId;
    private int mUnStarImageId;
    private int mHalfStarImageId;

    private int mRatingViewWidth;
    private int mRatingViewHeight;
    private int mRatingViewPaddingLeft;
    private int mRatingViewPaddingRight;
    private int mRatingViewPaddingTop;
    private int mRatingViewPaddingBottom;

    public RatingView(Context context, Builder builder) {
        super(context);
        mRatingViewWidth = builder.mRatingViewWidth;
        mRatingViewHeight = builder.mRatingViewHeight;
        mRatingViewPaddingLeft = builder.mRatingViewPaddingLeft;
        mRatingViewPaddingRight = builder.mRatingViewPaddingRight;
        mRatingViewPaddingTop = builder.mRatingViewPaddingTop;
        mRatingViewPaddingBottom = builder.mRatingViewPaddingBottom;
        mStarImageId = builder.mStarImageId;
        mHalfStarImageId = builder.mHalfStarImageId;
        mUnStarImageId = builder.mUnStarImageId;
        init(context);
    }

    public RatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.layout_rating_view, this);
        mImageView = (ImageView) findViewById(R.id.iv_star);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int w = getChildMeasureSpec(widthMeasureSpec, 0, getRatingViewWidth());
            int h = getChildMeasureSpec(heightMeasureSpec, 0, getRatingViewHeight());
            childView.measure(w, h);
        }
        setMeasuredDimension(getRatingViewWidth() + getRatingViewPaddingLeft() + getRatingViewPaddingRight(),
                getRatingViewHeight() + getRatingViewPaddingTop() + getRatingViewPaddingBottom());
        setPadding(getRatingViewPaddingLeft(), getRatingViewPaddingTop(), getRatingViewPaddingRight(), getRatingViewPaddingBottom());
    }

    public static Builder with() {
        return new Builder();
    }

    public static class Builder {
        int mStarImageId = R.drawable.ic_star;
        int mUnStarImageId = R.drawable.ic_un_star;
        int mHalfStarImageId = R.drawable.ic_half_star;
        int mRatingViewWidth = 12;
        int mRatingViewHeight = 12;
        int mRatingViewPaddingLeft = 2;
        int mRatingViewPaddingRight = 2;
        int mRatingViewPaddingTop = 0;
        int mRatingViewPaddingBottom = 0;
        Context mContext;

        public Builder context(Context context) {
            mContext = context;
            return this;
        }

        public Builder width(int width) {
            mRatingViewWidth = width;
            return this;
        }

        public Builder height(int height) {
            mRatingViewHeight = height;
            return this;
        }

        public Builder paddingLeft(int paddingLeft) {
            mRatingViewPaddingLeft = paddingLeft;
            return this;
        }

        public Builder paddingRight(int paddingRight) {
            mRatingViewPaddingRight = paddingRight;
            return this;
        }

        public Builder paddingTop(int paddingTop) {
            mRatingViewPaddingTop = paddingTop;
            return this;
        }

        public Builder paddingBottom(int paddingBottom) {
            mRatingViewPaddingBottom = paddingBottom;
            return this;
        }

        public Builder star(int starImageId) {
            mStarImageId = starImageId;
            return this;
        }

        public Builder unStar(int unStarImageId) {
            mUnStarImageId = unStarImageId;
            return this;
        }

        public Builder halfStar(int halfStarImageId) {
            mHalfStarImageId = halfStarImageId;
            return this;
        }

        public RatingView build() {
            return new RatingView(mContext, this);
        }
    }

    public int getRatingViewWidth() {
        return transform(mRatingViewWidth);
    }

    public void setRatingViewWidth(int ratingViewWidth) {
        mRatingViewWidth = ratingViewWidth;
    }

    public int getRatingViewHeight() {
        return transform(mRatingViewHeight);
    }

    public void setRatingViewHeight(int ratingViewHeight) {
        mRatingViewHeight = ratingViewHeight;
    }

    public int getRatingViewPaddingLeft() {
        return transform(mRatingViewPaddingLeft);
    }

    public void setRatingViewPaddingLeft(int ratingViewPaddingLeft) {
        mRatingViewPaddingLeft = ratingViewPaddingLeft;
    }

    public int getRatingViewPaddingRight() {
        return transform(mRatingViewPaddingRight);
    }

    public void setRatingViewPaddingRight(int ratingViewPaddingRight) {
        mRatingViewPaddingRight = ratingViewPaddingRight;
    }

    public int getRatingViewPaddingTop() {
        return transform(mRatingViewPaddingTop);
    }

    public void setRatingViewPaddingTop(int ratingViewPaddingTop) {
        mRatingViewPaddingTop = ratingViewPaddingTop;
    }

    public int getRatingViewPaddingBottom() {
        return transform(mRatingViewPaddingBottom);
    }

    public void setRatingViewPaddingBottom(int ratingViewPaddingBottom) {
        mRatingViewPaddingBottom = ratingViewPaddingBottom;
    }

    public int getStarImageId() {
        return mStarImageId;
    }

    public void setStarImageId(int starImageId) {
        mStarImageId = starImageId;
    }

    public int getUnStarImageId() {
        return mUnStarImageId;
    }

    public void setUnStarImageId(int unStarImageId) {
        mUnStarImageId = unStarImageId;
    }

    public int getHalfStarImageId() {
        return mHalfStarImageId;
    }

    public void setHalfStarImageId(int halfStarImageId) {
        mHalfStarImageId = halfStarImageId;
    }

    private int transform(int px) {
        return dp2px(px2dp(px));
    }

    /**
     * set image resource
     *
     * @param id image id
     */
    private void setImageId(int id) {
        mImageView.setImageResource(id);
    }

    /**
     * set current view status
     *
     * @param position position
     * @param rating   rating
     */
    public void setState(int position, float rating) {
        setImageId(getImageRes(position, rating));
    }

    /**
     * get status
     *
     * @param position position
     * @param rating   rating
     * @return image id
     */
    private int getImageRes(int position, float rating) {
        int id = R.drawable.ic_un_star;
        switch (getState(position, rating)) {
            case STATE_UN_STAR:
                id = getUnStarImageId();
                break;
            case STATE_HALF_STAR:
                id = getHalfStarImageId();
                break;
            case STATE_STAR:
                id = getStarImageId();
                break;
            default:
                break;
        }
        return id;
    }

    /**
     * @param position position
     * @param rating   rating
     * @return status
     * {@link #STATE_STAR,#STATE_HALF_STAR,#STATE_UN_STAR}
     */
    private int getState(int position, float rating) {
        position++;
        float dis = rating - position;
        if (dis >= 0) {
            return STATE_STAR;
        } else {
            if (dis >= -0.5) {
                return STATE_HALF_STAR;
            } else {
                return STATE_UN_STAR;
            }
        }
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }

    private int px2dp(float px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px,
                mContext.getResources().getDisplayMetrics());
    }
}
