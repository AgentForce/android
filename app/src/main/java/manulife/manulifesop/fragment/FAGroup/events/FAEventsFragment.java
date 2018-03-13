package manulife.manulifesop.fragment.FAGroup.events;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.eventDetail.EventDetailActivity;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.EventAdapter;
import manulife.manulifesop.adapter.EventCalendarAdapter;
import manulife.manulifesop.adapter.ObjectData.EventCalendar;
import manulife.manulifesop.adapter.ObjectData.EventData;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step2.ContactDetailStep2Fragment;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAEventsFragment extends BaseFragment<MainFAActivity, FAEventsPresent> implements FAEventsContract.View {

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;
    @BindView(R.id.list_events)
    RecyclerView listEvent;

    private EventCalendarAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

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
        initViews();
        initCalendarEvents();
        //addEventToDate(null);
        mActionListener.getAllActivitisInMonth(Utils.getCurrentMonth(getContext()));
        mActionListener.getEventsOneDay(Calendar.getInstance().getTime());
    }

    private void initViews() {
        setTitleFromDate(Calendar.getInstance().getTime());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    private void setTitleFromDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
        mActivity.updateActionbarTitle("Tháng " + df.format(date.getTime()));
    }

    private void initCalendarEvents() {
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setMonthRange(2);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                mActionListener.getEventsOneDay(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setTitleFromDate(firstDayOfNewMonth);
            }
        });


    }

    @Override
    public void addEventToDate(Date date, List<String> colors) {
        for (int i = 0; i < colors.size(); i++) {
            compactCalendarView.addEvent(new Event(Color.parseColor(colors.get(i)), date.getTime()));
        }
    }

    @Override
    public void showDataEvents(List<EventCalendar> data) {
        listEvent.setLayoutManager(mLayoutManager);
        mAdapter = new EventCalendarAdapter(getContext(), data, new CallBackClickContact() {
            @Override
            public void onClickMenuRight(int position, int option) {
                Toast.makeText(mActivity, "vi tri " + position + " options " + option, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickMainContent(int position) {
                Toast.makeText(mActivity, "Main click", Toast.LENGTH_SHORT).show();
            }
        });
        listEvent.setAdapter(mAdapter);
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
