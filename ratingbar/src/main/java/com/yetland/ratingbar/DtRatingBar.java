package com.yetland.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author YETLAND
 * @date 2018/10/23 11:19
 */
public class DtRatingBar extends LinearLayout {

    /**
     * 共有几个星星
     */
    private int mStars;
    /**
     * 得分
     */
    private float mRating;
    /**
     * 子view的builder
     */
    private RatingView.Builder mBuilder;
    /**
     * 是否支持半星打分
     */
    private boolean mSupportHalf = true;
    private Context mContext;
    /**
     * 子view的宽度
     */
    private int mChildWidth;
    /**
     * 精度为100，即0.00
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
     * 设置builder
     *
     * @param builder builder
     */
    public void setBuilder(RatingView.Builder builder) {
        mBuilder = builder;
        mRating = getRatingValue(mRating);
        initView();
    }

    /**
     * 手动设置得分
     *
     * @param stars stars
     */
    public void setStars(int stars) {
        this.mStars = stars;
        mRating = getRatingValue(mRating);
        initView();
    }

    /**
     * 设置rating
     *
     * @param rating 得分
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
     * 设置得分是多少
     *
     * @param rating 得分
     */
    public void setRating(float rating) {
        // 得分限制
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
     * 更新子view的状态
     */
    private void updateView() {
        for (int i = 0; i < mStars; i++) {
            RatingView ratingView = (RatingView) getChildAt(i);
            ratingView.setState(i, mRating);
        }
    }

    /**
     * 是否支持打半星
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
            // 越界判断
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
     * rating的转换。涉及到支持半星的操作
     *
     * @param rating 原得分
     * @return 计算后的得分
     */
    private float getRatingValue(float rating) {
        // 一半，50
        int half = RATE / 2;
        rating = rating * RATE;
        // 余数
        int xx = (int) (rating % RATE);
        // 整除的数
        int yy = (int) (rating / RATE);

        if (xx > 0) {
            if (mSupportHalf) {
                // 支持打半星
                if (xx < half) {
                    // 小于一半的+0.5
                    rating = (float) (yy + 0.5);
                } else {
                    // 大于一半的+1
                    rating = yy + 1;
                }
            } else {
                // 不支持打半星，就直接+1
                rating = yy + 1;
            }
        } else {
            // 说明整除了
            rating = yy;
        }
        return rating;
    }

    public interface OnRatingChangeListener {

        /**
         * 选择分数
         *
         * @param rating 分数
         * @param stars  总数
         */
        void onChange(float rating, int stars);
    }
}
