package com.yetland.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author YETLAND 2018/10/23 11:19
 */
public class DtRatingBar extends LinearLayout {

    /**
     * stars sum
     */
    private int mStars;
    /**
     * rating
     */
    private float mRating;
    /**
     * child view's builder
     */
    private RatingView.Builder mBuilder;
    /**
     * support half
     */
    private boolean mSupportHalf;
    private Context mContext;
    /**
     * child view width
     */
    private int mChildWidth;
    /**
     * precision is 100 just like 0.00
     */
    public static int RATE = 100;
    /**
     * listener
     */
    private OnRatingChangeListener mOnRatingChangeListener;

    public DtRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DtRatingBar);

        mRating = typedArray.getFloat(R.styleable.DtRatingBar_rating_check, 0f);
        mStars = typedArray.getInt(R.styleable.DtRatingBar_rating_sum, 5);

        mSupportHalf = typedArray.getBoolean(R.styleable.DtRatingBar_rating_support_half, true);
        mRating = getRatingValue(mRating);

        int childViewWidth = typedArray.getInt(R.styleable.DtRatingBar_rating_width, 12);
        int childViewHeight = typedArray.getInt(R.styleable.DtRatingBar_rating_height, 12);

        int childViewPaddingLeft = typedArray.getInt(R.styleable.DtRatingBar_rating_padding_left, 2);
        int childViewPaddingRight = typedArray.getInt(R.styleable.DtRatingBar_rating_padding_right, 2);
        int childViewPaddingTop = typedArray.getInt(R.styleable.DtRatingBar_rating_padding_top, 0);
        int childViewPaddingBottom = typedArray.getInt(R.styleable.DtRatingBar_rating_padding_bottom, 0);

        int childViewStarImg = typedArray.getResourceId(R.styleable.DtRatingBar_rating_star_img, R.drawable.ic_star);
        int childViewHalfStarImg = typedArray.getResourceId(R.styleable.DtRatingBar_rating_half_star_img, R.drawable.ic_half_star);
        int childViewUnStarImg = typedArray.getResourceId(R.styleable.DtRatingBar_rating_un_star_img, R.drawable.ic_un_star);

        mBuilder = new RatingView.Builder()
                .context(mContext)
                .width(childViewWidth)
                .height(childViewHeight)
                .paddingLeft(childViewPaddingLeft)
                .paddingRight(childViewPaddingRight)
                .paddingBottom(childViewPaddingBottom)
                .paddingTop(childViewPaddingTop)
                .star(childViewStarImg)
                .halfStar(childViewHalfStarImg)
                .unStar(childViewUnStarImg);
        typedArray.recycle();
        setEnabled(false);
        initView();
    }

    /**
     * set builder
     *
     * @param builder builder
     */
    public void setBuilder(RatingView.Builder builder) {
        mBuilder = builder;
        mRating = getRatingValue(mRating);
        initView();
    }

    /**
     * set stars sum
     *
     * @param stars stars
     */
    public void setStars(int stars) {
        this.mStars = stars;
        mRating = getRatingValue(mRating);
        initView();
    }

    /**
     * set rating
     *
     * @param rating rating
     */
    public void setRating(String rating) {
        if (!TextUtils.isEmpty(rating)) {
            try {
                float value = Float.valueOf(rating);
                setRating(value);
            } catch (NumberFormatException ex) {
                setRating(0);
            }
        } else {
            setRating(0);
        }
    }

    /**
     * set rating
     *
     * @param rating rating
     */
    public void setRating(float rating) {
        // ensure rating size
        rating = rating > mStars ? mStars : rating;

        mRating = getRatingValue(rating);
        initView();
    }


    /**
     * init
     */
    private void initView() {
        removeAllViews();
        if (mBuilder == null) {
            return;
        }
        for (int i = 0; i < mStars; i++) {
            RatingView ratingView = new RatingView(mContext, mBuilder);
            ratingView.setState(i, mRating);
            mChildWidth = ratingView.getRatingViewWidth() + ratingView.getRatingViewPaddingLeft() + ratingView.getRatingViewPaddingRight();
            addView(ratingView);
        }
    }


    /**
     * update child view
     */
    private void updateView() {
        for (int i = 0; i < mStars; i++) {
            RatingView ratingView = (RatingView) getChildAt(i);
            ratingView.setState(i, mRating);
        }
    }

    /**
     * support half star
     *
     * @param supportHalf true or false
     */
    public void setSupportHalf(boolean supportHalf) {
        mSupportHalf = supportHalf;
        mRating = getRatingValue(mRating);
        initView();
    }

    public boolean isSupportHalf() {
        return mSupportHalf;
    }

    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        mOnRatingChangeListener = onRatingChangeListener;
    }

    public RatingView.Builder getBuilder() {
        return mBuilder;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    performClick();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    break;
                default:
                    break;
            }

            int width = getWidth();
            float x = event.getX();
            // size limit
            x = x > width ? width : x;
            x = x < 0 ? 0 : x;

            mRating = x / mChildWidth;
            mRating = getRatingValue(mRating);
            if (mOnRatingChangeListener != null) {
                mOnRatingChangeListener.onChange(mRating, mStars);
            }
            updateView();
            return true;
        } else {
            return false;
        }
    }

    /**
     * rating transform
     *
     * @param rating before rating
     * @return after rating
     */
    private float getRatingValue(float rating) {
        // half
        int half = RATE / 2;
        rating = rating * RATE;
        int xx = (int) (rating % RATE);
        int yy = (int) (rating / RATE);

        if (xx > 0) {
            if (mSupportHalf) {
                // support half
                if (xx <= half) {
                    // <= half ,  plus 0.5
                    rating = (float) (yy + 0.5);
                } else {
                    // > half , plus 1
                    rating = yy + 1;
                }
            } else {
                // if not support half ,plus 1
                rating = yy + 1;
            }
        } else {
            rating = yy;
        }
        return rating;
    }

    public interface OnRatingChangeListener {

        /**
         * on rating change
         *
         * @param rating rating
         * @param stars  stars sum
         */
        void onChange(float rating, int stars);
    }
}
