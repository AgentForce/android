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
    @BindView(R.id.sb_profit)
    IndicatorSeekBar seekProfit;
    @BindView(R.id.sb_contract_price)
    IndicatorSeekBar seekContractPrice;


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
    public void setProgressIncome(float value) {
        if(value > 100){
            int minProgress = (int)(value/100)*100;
            seekBarIncome.setMin(minProgress);
            seekBarIncome.setMax(minProgress + 100);

            txtIncomeMin.setText(minProgress + "tr");
            txtIncomeMax.setText(minProgress + 100 + "tr");
        }
        seekBarIncome.setProgress(value);
    }

    private void initEventsSeekBar() {
        seekBarIncome.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {

            int currentProcess = seekBarIncome.getProgress();

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if (fromUserTouch) {
                    currentProcess = progress;
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
                //set progress step
                if (currentProcess % 5 > 0) {
                    if (currentProcess % 5 > 5) {
                        seekBar.setProgress(((currentProcess / 5) * 5) + 5);
                    } else {
                        seekBar.setProgress(((currentProcess / 5) * 5));
                    }
                }

                float min = seekBar.getMin();
                float max = seekBar.getMax();
                if (seekBar.getProgress() == max) {
                    int minNew = (min == 10) ? 100 : (int) (min + 100);

                    txtIncomeMin.setText((int) (minNew) + "tr");
                    txtIncomeMax.setText((int) (max + 100) + "tr");

                    seekBarIncome.setMax(max + 100);
                    seekBarIncome.setMin(minNew);
                    seekBarIncome.setProgress(minNew);

                } else if (seekBar.getProgress() == min && min > 10) {

                    int minNew = ((min - 100) > 10) ? (int) (min - 100) : 10;
                    txtIncomeMin.setText(minNew + "tr");
                    txtIncomeMax.setText((int) (max - 100) + "tr");
                    seekBarIncome.setMax(max - 100);
                    seekBarIncome.setMin(minNew);
                    seekBarIncome.setProgress(max - 100);
                }
            }
        });
    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                //get contract num
                float contractNumFloat = (float) (seekBarIncome.getProgress() * 100) / (float) seekProfit.getProgress() / (float) seekContractPrice.getProgress();
                int contractNum = Math.round(contractNumFloat);
                mActivity.showNextFragment(contractNum, "", "", seekBarIncome.getProgress(), seekContractPrice.getProgress(), seekProfit.getProgress());
                break;
            }
        }
    }
}
