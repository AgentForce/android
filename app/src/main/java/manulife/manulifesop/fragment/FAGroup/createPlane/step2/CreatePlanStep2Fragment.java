package manulife.manulifesop.fragment.FAGroup.createPlane.step2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.warkiz.widget.IndicatorSeekBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.adapter.PasswordAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.SmsListener;
import manulife.manulifesop.service.SmsReceiver;
import manulife.manulifesop.util.Utils;


/**
 * Created by Chick on 10/27/2017.
 */

public class CreatePlanStep2Fragment extends BaseFragment<CreatePlanActivity, CreatePlanStep2Present> implements CreatePlanStep2Contract.View {

    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.sb_income)
    IndicatorSeekBar seekBarIncome;
    @BindView(R.id.txt_income_min)
    TextView txtIncomeMin;
    @BindView(R.id.txt_income_max)
    TextView txtIncomeMax;


    public static CreatePlanStep2Fragment newInstance() {
        Bundle args = new Bundle();
        CreatePlanStep2Fragment fragment = new CreatePlanStep2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_step2;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new CreatePlanStep2Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEventsSeekBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void changeIncomeSeedBar(float min, float max, float current) {
        seekBarIncome.getBuilder()
                .setMax(max)
                .setProgress(current)
                .setMin(min)
                .apply();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(valueChangeSeekBar data) {
        txtIncomeMin.setText((int)data.getMin()+"tr");
        txtIncomeMax.setText((int)data.getMax()+"tr");
        changeIncomeSeedBar(data.getMin(), data.getMax(), data.getCurrent());
    }

    private class valueChangeSeekBar {
        private float min, max, current;

        public valueChangeSeekBar(float min, float max, float current) {
            this.min = min;
            this.max = max;
            this.current = current;
        }

        public float getMin() {
            return min;
        }

        public float getMax() {
            return max;
        }

        public float getCurrent() {
            return current;
        }
    }

    private void initEventsSeekBar() {
        seekBarIncome.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                float min = seekBar.getMin();
                float max = seekBar.getMax();
                if (progress == max) {
                    EventBus.getDefault().post(new valueChangeSeekBar(min, max + 100, max));
                }
            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.showNextFragment();
                break;
            }
        }
    }

    /*SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                //From the received text string you may do string operations to get the required OTP
                //It depends on your SMS format
                showMessage("Thong bao",messageText, SweetAlertDialog.SUCCESS_TYPE);
                Log.d("test",messageText+"_______________________________________");
                System.out.println(messageText+"_______________________________________");

                //Toast.makeText(getContext(),"Message: "+messageText,Toast.LENGTH_LONG).show();

                // If your OTP is six digits number, you may use the below code

                *//*Pattern pattern = Pattern.compile(OTP_REGEX);
                Matcher matcher = pattern.matcher(messageText);
                String otp;
                while (matcher.find())
                {
                    otp = matcher.group();
                }

                Toast.makeText(MainActivity.this,"OTP: "+ otp ,Toast.LENGTH_LONG).show();*//*

            }
        });*/
}
