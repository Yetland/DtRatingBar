# DtRatingBar
一个使用在android上的RatingBar。
##效果图
![Image text](https://github.com/Yetland/DtRatingBar/blob/master/demo.gif)
##功能
* 图片自定义
* 大小自定义
* 数量自定义
* 可以打开或关闭半星
##主要参数
* rating_sum 总数
* rating_check 得分数
* rating_width RatingView的宽度
* rating_height RatingView的高度
* rating_padding_left RatingView的paddingLeft
* rating_padding_right RatingView的paddingRight
* rating_padding_top RatingView的paddingTop
* rating_padding_bottom RatingView的paddingBottom
* rating_star_img 全星的图片
* rating_half_star_img 半星的图片
* rating_un_star_img 没星的图片
* rating_support_half 是否支持半星
##使用方法
通过xml形式配置DtRatingBar示例
```
<com.yetland.ratingbar.DtRatingBar
                android:id="@+id/rating_bar_1"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:rating_check="3.5"
                app:rating_half_star_img="@drawable/ic_half_star"
                app:rating_height="20"
                app:rating_padding_bottom="2"
                app:rating_padding_left="2"
                app:rating_padding_right="2"
                app:rating_padding_top="2"
                app:rating_star_img="@drawable/ic_star"
                app:rating_un_star_img="@drawable/ic_un_star"
                app:rating_width="20" />
```