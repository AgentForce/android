package manulife.manulifesop.fragment.FAGroup.events;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAEventsFragment extends BaseFragment<MainFAActivity, FAEventsPresent> implements FAEventsContract.View {

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;

    public static FAEventsFragment newInstance() {
        Bundle args = new Bundle();
        FAEventsFragment fragment = new FAEventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_events;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FAEventsPresent(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mActionListener.getDataDashboard();
        mActivity.showHideActionbar(true);
        setTitleFromDate(Calendar.getInstance().getTime());
        initCalendarEvents();
        //addEventToDate(null);
        mActionListener.getAllActivitisInMonth(Utils.getCurrentMonth(getContext()));
    }
    private void setTitleFromDate(Date date){
        SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
        mActivity.updateActionbarTitle("Tháng " + df.format(date.getTime()));
    }

    private void initCalendarEvents(){
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setMonthRange(2);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Toast.makeText(mActivity, df.format(dateClicked), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setTitleFromDate(firstDayOfNewMonth);
            }
        });
    }

    @Override
    public void addEventToDate(Date date) {
        Calendar test = Calendar.getInstance();
        test.add(Calendar.DAY_OF_MONTH,1);
        compactCalendarView.addEvent(new Event(Color.parseColor("#FF0000"),test.getTime().getTime()));
        compactCalendarView.addEvent(new Event(Color.BLUE,test.getTime().getTime()));
        compactCalendarView.addEvent(new Event(Color.GREEN,test.getTime().getTime()));
    }

    /*@OnClick(R.id.btn_start)
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_start:
            {
                mActivity.goNextScreen(CreatePlanActivity.class);
                break;
            }
        }
    }*/

}
