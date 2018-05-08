package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step7;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.warkiz.widget.IndicatorSeekBar;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep7Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep7Present> implements UMCreatePlanStep7Contract.View {

    @BindView(R.id.txt_contract_per_month)
    TextView txtContactPerMonth;
    @BindView(R.id.txt_FYC_per_contract)
    TextView txtFYCPerContact;
    @BindView(R.id.txt_FYC)
    TextView txtFYC;

    @BindView(R.id.sb_step7_row5)
    IndicatorSeekBar sbStep7Row5;
    @BindView(R.id.txt_min)
    TextView txtRYPMin;
    @BindView(R.id.txt_max)
    TextView txtRYPMax;


    public static UMCreatePlanStep7Fragment newInstance() {
        Bundle args = new Bundle();
        UMCreatePlanStep7Fragment fragment = new UMCreatePlanStep7Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step7;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep7Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEventsSeekBar();
    }

    private void initEventsSeekBar() {
        sbStep7Row5.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {

            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                float min = seekBar.getMin();
                float max = seekBar.getMax();
                if (seekBar.getProgress() == max) {
                    int minNew = (min == 5000) ? 700 : (int) (min + 200);

                    txtRYPMin.setText((int) (minNew) + "tr");
                    txtRYPMax.setText((int) (max + 200) + "tr");

                    sbStep7Row5.setMax(max + 200);
                    sbStep7Row5.setMin(minNew);
                    sbStep7Row5.setProgress(minNew);

                } else if (seekBar.getProgress() == min && min > 500) {

                    int minNew = ((min - 200) > 500) ? (int) (min - 200) : 500;
                    txtRYPMin.setText(minNew + "tr");
                    txtRYPMax.setText((int) (max - 100) + "tr");
                    sbStep7Row5.setMax(max - 200);
                    sbStep7Row5.setMin(minNew);
                    sbStep7Row5.setProgress(max - 200);
                }
            }
        });
    }

    @OnClick({R.id.btn_next, R.id.layout_add_step7_row1, R.id.layout_sub_step7_row1,
            R.id.layout_add_step7_row2, R.id.layout_sub_step7_row2,
            R.id.layout_add_step7_row4, R.id.layout_sub_step7_row4})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                int RYPNum = Math.round((float) ((float)sbStep7Row5.getProgress()*1.5/100));
                mActivity.setDataStep7(Integer.valueOf(txtContactPerMonth.getText().toString()),
                        Integer.valueOf(txtFYCPerContact.getText().toString()),
                        Integer.valueOf(txtFYC.getText().toString()),
                        RYPNum);
                mActivity.showNextFragment();
                break;
            }
            case R.id.layout_add_step7_row1: {
                int newValue = Integer.valueOf(txtContactPerMonth.getText().toString()) + 1;
                txtContactPerMonth.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step7_row1: {
                int newValue = Integer.valueOf(txtContactPerMonth.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtContactPerMonth.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_step7_row2: {
                int newValue = Integer.valueOf(txtFYCPerContact.getText().toString()) + 1;
                txtFYCPerContact.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step7_row2: {
                int newValue = Integer.valueOf(txtFYCPerContact.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtFYCPerContact.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_add_step7_row4: {
                int newValue = Integer.valueOf(txtFYC.getText().toString()) + 1;
                txtFYC.setText(String.valueOf(newValue));
                break;
            }
            case R.id.layout_sub_step7_row4: {
                int newValue = Integer.valueOf(txtFYC.getText().toString()) - 1;
                if (newValue < 0) newValue = 0;
                txtFYC.setText(String.valueOf(newValue));
                break;
            }
        }
    }
}
