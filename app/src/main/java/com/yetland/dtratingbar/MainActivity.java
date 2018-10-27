package com.yetland.dtratingbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yetland.ratingbar.DtRatingBar;
import com.yetland.ratingbar.RatingView;

import java.text.MessageFormat;

/**
 * @author YETLAND
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DtRatingBar mRatingBar2;
    private DtRatingBar mDtRatingBar3;
    private DtRatingBar mDtRatingBar4;
    private TextView mTvRatingBar3Tip;

    private Button mBtChangeImage;
    private Button mBtChangeSum;
    private Button mBtChangeSize;
    private Button mBtChangeHalf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDtRatingBar4 = findViewById(R.id.rating_bar_4);
        mDtRatingBar3 = findViewById(R.id.rating_bar_3);
        mRatingBar2 = findViewById(R.id.rating_bar_2);

        mTvRatingBar3Tip = findViewById(R.id.tv_rating_bar_3_tip);

        mBtChangeImage = findViewById(R.id.bt_change_image);
        mBtChangeSum = findViewById(R.id.bt_change_sum);
        mBtChangeSize = findViewById(R.id.bt_change_size);
        mBtChangeHalf = findViewById(R.id.bt_change_half);

        mBtChangeImage.setOnClickListener(this);
        mBtChangeSum.setOnClickListener(this);
        mBtChangeSize.setOnClickListener(this);
        mBtChangeHalf.setOnClickListener(this);

        mDtRatingBar4.setEnabled(true);

        mDtRatingBar3.setEnabled(true);
        mDtRatingBar3.setSupportHalf(true);
        mDtRatingBar3.setOnRatingChangeListener(new DtRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(float rating, int stars) {
                mTvRatingBar3Tip.setText(MessageFormat.format("stars : {0} , rating = {1}", stars, rating));
            }
        });
        mBtChangeHalf.setText(mDtRatingBar3.isSupportHalf() ? "关闭半星" : "打开半星");

        RatingView.Builder builder = new RatingView.Builder()
                .context(this)
                .width(30)
                .height(30)
                .paddingLeft(2)
                .paddingRight(2)
                .paddingBottom(2)
                .paddingTop(2)
                .star(R.mipmap.ic_star2)
                .unStar(R.mipmap.ic_un_star2)
                .halfStar(R.mipmap.ic_half_star2);
        mRatingBar2.setBuilder(builder);
        mRatingBar2.setStars(6);
        mRatingBar2.setRating(4.2f);
        mRatingBar2.setSupportHalf(true);

    }

    @Override
    public void onClick(View v) {
        RatingView.Builder builder = mDtRatingBar3.getBuilder();
        switch (v.getId()) {
            case R.id.bt_change_image:
                builder.star(R.mipmap.ic_star2)
                        .unStar(R.mipmap.ic_un_star2)
                        .halfStar(R.mipmap.ic_half_star2);
                mDtRatingBar3.setBuilder(builder);
                break;
            case R.id.bt_change_sum:
                mDtRatingBar3.setStars(5);
                break;
            case R.id.bt_change_size:
                builder = builder
                        .width(35)
                        .height(35);
                mDtRatingBar3.setBuilder(builder);
                break;
            case R.id.bt_change_half:
                mDtRatingBar3.setSupportHalf(!mDtRatingBar3.isSupportHalf());
                mBtChangeHalf.setText(mDtRatingBar3.isSupportHalf() ? "关闭半星" : "打开半星");
                break;
            default:
                break;
        }
    }
}
