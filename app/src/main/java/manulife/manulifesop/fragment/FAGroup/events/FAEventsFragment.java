package manulife.manulifesop.fragment.FAGroup.events;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.createEvent.CreateEventActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.eventDetail.EventDetailActivity;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.adapter.EventCalendarAdapter;
import manulife.manulifesop.adapter.ObjectData.EventCalendar;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.util.Contants;
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
    private List<EventCalendar> mData;

    private Date mCurrentDate;
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
        mActionListener = new FAEventsPresent(this,getContext());
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
        mCurrentDate = Calendar.getInstance().getTime();
        //mActionListener.getEventsOneDay(mCurrentDate);
    }

    private void initViews() {
        setTitleFromDate(Calendar.getInstance().getTime());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listEvent.setLayoutManager(mLayoutManager);
        mData = new ArrayList<>();
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
                mCurrentDate = dateClicked;
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
    public void updateData() {
        mActionListener.getEventsOneDay(mCurrentDate);
    }

    @Override
    public void showDataEvents(List<EventCalendar> dataEvent) {
        mData.clear();
        mData.addAll(dataEvent);
        if(mAdapter == null) {
            mAdapter = new EventCalendarAdapter(getContext(), mData, new CallBackClickContact() {
                @Override
                public void onClickMenuRight(int position, int option) {
                    switch (option) {
                        case 0: {
                            Bundle data = new Bundle();
                            data.putInt("eventID", mData.get(position).getId());
                            goNextScreenFragment(EventDetailActivity.class, data, Contants.EVENT_DETAIL);
                            break;
                        }
                        case 1: {
                            Bundle data = new Bundle();
                            data.putSerializable("eventID", mData.get(position).getId());
                            goNextScreenFragment(CreateEventActivity.class, data, Contants.UPDATE_EVENT);
                            break;
                        }
                        case 2: {
                            mActionListener.updateEventDone(mData.get(position).getId());
                            break;
                        }
                    }
                }

                @Override
                public void onClickMainContent(int position) {
                    //Toast.makeText(mActivity, "Main click", Toast.LENGTH_SHORT).show();
                    Bundle data = new Bundle();
                    data.putInt("eventID", mData.get(position).getId());
                    goNextScreenFragment(EventDetailActivity.class, data, Contants.EVENT_DETAIL);
                }
            });
            listEvent.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(resultCode == Activity.RESULT_OK && requestCode == Contants.EVENT_DETAIL){
        if(resultCode == Activity.RESULT_OK){
            mActionListener.getEventsOneDay(mCurrentDate);
        }
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
