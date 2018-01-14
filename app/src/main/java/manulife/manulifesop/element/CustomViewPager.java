package manulife.manulifesop.element;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

    private boolean isSwipe = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isSwipe ? super.onInterceptTouchEvent(event) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isSwipe ? super.onTouchEvent(event) : false;
    }

    public void setSwipe(boolean isSwipe) {
        this.isSwipe = isSwipe;
    }
}
