package chick.indicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class CircleIndicatorPager extends LinearLayout {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;
    private ViewPager mViewpager;
    private int mIndicatorMargin = -1;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;
    private int mIndicatorBackgroundResId = R.drawable.white_radius;
    private int mIndicatorUnselectedBackgroundResId = R.drawable.white_radius;

    private int mLastPosition = -1;

    //new code
    private List<Integer> mListIndicatorBackgroundResId = null;
    private boolean mMaintainPrevious;

    public CircleIndicatorPager(Context context) {
        super(context);
        init(context, null);
    }

    public CircleIndicatorPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleIndicatorPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicatorPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            mLastPosition = -1;
            createIndicators();
            mViewpager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.addOnPageChangeListener(mInternalPageChangeListener);
            mInternalPageChangeListener.onPageSelected(mViewpager.getCurrentItem());
        }
    }

    public void setViewPager(ViewPager viewPager, List<Integer> ListIndicatorBackgroundResId) {
        mViewpager = viewPager;
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            mLastPosition = -1;
            createIndicators();
            mViewpager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.addOnPageChangeListener(mInternalPageChangeListener);
            mInternalPageChangeListener.onPageSelected(mViewpager.getCurrentItem());
            mListIndicatorBackgroundResId = ListIndicatorBackgroundResId;
        }
    }

    /**
     * Create and configure Indicator in Java code.
     */
    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin) {
        configureIndicator(indicatorWidth, indicatorHeight, indicatorMargin,
                R.drawable.white_radius, R.drawable.white_radius);
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin,
                                   @DrawableRes int indicatorBackgroundId,
                                   @DrawableRes int indicatorUnselectedBackgroundId) {

        mIndicatorWidth = indicatorWidth;
        mIndicatorHeight = indicatorHeight;
        mIndicatorMargin = indicatorMargin;

        mIndicatorBackgroundResId = indicatorBackgroundId;
        mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundId;

        checkIndicatorConfig(getContext());
    }

    private void init(Context context, AttributeSet attrs) {
        handleTypedArray(context, attrs);
        checkIndicatorConfig(context);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        //old code
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicatorPager);
        mIndicatorWidth =
                typedArray.getDimensionPixelSize(R.styleable.CircleIndicatorPager_ci_width, -1);
        mIndicatorHeight =
                typedArray.getDimensionPixelSize(R.styleable.CircleIndicatorPager_ci_height, -1);
        mIndicatorMargin =
                typedArray.getDimensionPixelSize(R.styleable.CircleIndicatorPager_ci_margin, -1);

        mIndicatorBackgroundResId =
                typedArray.getResourceId(R.styleable.CircleIndicatorPager_ci_drawable,
                        R.drawable.white_radius);
        mIndicatorUnselectedBackgroundResId =
                typedArray.getResourceId(R.styleable.CircleIndicatorPager_ci_drawable_unselected,
                        mIndicatorBackgroundResId);

        int orientation = typedArray.getInt(R.styleable.CircleIndicatorPager_ci_orientation, -1);
        setOrientation(orientation == VERTICAL ? VERTICAL : HORIZONTAL);

        int gravity = typedArray.getInt(R.styleable.CircleIndicatorPager_ci_gravity, -1);
        setGravity(gravity >= 0 ? gravity : Gravity.CENTER);

        //new code
        mMaintainPrevious = typedArray.getBoolean(R.styleable.CircleIndicatorPager_ci_maintain_previous,
                false);

        typedArray.recycle();
    }

    private void checkIndicatorConfig(Context context) {
        mIndicatorWidth = (mIndicatorWidth < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;
        mIndicatorHeight =
                (mIndicatorHeight < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorHeight;
        mIndicatorMargin =
                (mIndicatorMargin < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorMargin;

        mIndicatorBackgroundResId = (mIndicatorBackgroundResId == 0) ? R.drawable.white_radius
                : mIndicatorBackgroundResId;
        mIndicatorUnselectedBackgroundResId =
                (mIndicatorUnselectedBackgroundResId == 0) ? mIndicatorBackgroundResId
                        : mIndicatorUnselectedBackgroundResId;
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

            if (mViewpager.getAdapter() == null || mViewpager.getAdapter().getCount() <= 0) {
                return;
            }

            //old code
            /*View currentIndicator;
            if (mLastPosition >= 0 && mLastPosition > position && (currentIndicator = getChildAt(mLastPosition)) != null) {
                currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
            }*/
            //new code
            View currentIndicator;
            if (mLastPosition >= 0 && (currentIndicator = getChildAt(mLastPosition)) != null) {
                if (mMaintainPrevious == true && mLastPosition > position) {
                    currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
                } else if (mMaintainPrevious == false && mLastPosition != position) {
                    currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
                }
            }

            //old code
            /*View selectedIndicator = getChildAt(position);
            if (selectedIndicator != null) {
                selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
            }*/
            //new code
            View selectedIndicator = getChildAt(position);
            if (selectedIndicator != null) {
                if (mListIndicatorBackgroundResId != null && mListIndicatorBackgroundResId.size() == mViewpager.getAdapter().getCount()) {
                    selectedIndicator.setBackgroundResource(mListIndicatorBackgroundResId.get(position));
                } else {
                    selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
                }
            }
            mLastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void createIndicators() {
        removeAllViews();
        int count = mViewpager.getAdapter().getCount();
        if (count <= 0) {
            return;
        }
        int currentItem = mViewpager.getCurrentItem();

        for (int i = 0; i < count; i++) {
            if (currentItem == i) {
                addIndicator(mIndicatorBackgroundResId);
            } else {
                addIndicator(mIndicatorUnselectedBackgroundResId);
            }
        }
    }

    private void addIndicator(@DrawableRes int backgroundDrawableId) {

        View Indicator = new View(getContext());
        Indicator.setBackgroundResource(backgroundDrawableId);
        addView(Indicator, mIndicatorWidth, mIndicatorHeight);
        LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();
        lp.leftMargin = mIndicatorMargin;
        lp.rightMargin = mIndicatorMargin;
        Indicator.setLayoutParams(lp);
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //new code
    public void setListIndicatorBackgroundResId(List<Integer> listIndicatorBackgroundResId) {
        this.mListIndicatorBackgroundResId = listIndicatorBackgroundResId;
    }
}