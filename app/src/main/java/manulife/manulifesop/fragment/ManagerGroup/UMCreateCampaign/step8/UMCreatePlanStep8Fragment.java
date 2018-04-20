package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chick.indicator.CircleIndicatorPager;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.api.ObjectResponse.ForcastIncomeUM;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step7.UMCreatePlanStep7Contract;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step7.UMCreatePlanStep7Present;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.screen.screen1.UMCreatePlanStep8Screen1Fragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.screen.screen2.UMCreatePlanStep8Screen2Fragment;
import manulife.manulifesop.fragment.first.FirstFragment;
import manulife.manulifesop.util.Utils;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep8Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep8Present> implements UMCreatePlanStep8Contract.View {

    @BindView(R.id.txt_start_date)
    TextView txtStartDate;
    @BindView(R.id.txt_end_date)
    TextView txtEndDate;
    @BindView(R.id.total_income)
    TextView txtTotalIncomeAll;
    @BindView(R.id.total_income_monthly)
    TextView txtTotalIncomeMonthly;
    @BindView(R.id.txt_num_campaign)
    TextView txtNumCampaign;

    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.circle_indicator_pager)
    CircleIndicatorPager indicator;

    private List<BaseFragment> mListFragment;
    private CustomViewPagerAdapter mAdapter;


    public static UMCreatePlanStep8Fragment newInstance() {
        Bundle args = new Bundle();
        UMCreatePlanStep8Fragment fragment = new UMCreatePlanStep8Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step8;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep8Present(this,getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtStartDate.setText(mActivity.getStartDate());
        txtEndDate.setText(mActivity.getEndDate());
    }

    @Override
    public void showData(ForcastIncomeUM data) {

        double totalAll = data.data.personal.comm + data.data.personal.newAgentBonus+
                data.data.personal.monthlyBonus + data.data.personal.quarterBonus +
                data.data.personal.fcTetBonus + data.data.personal.yearlyMDRTBonus +
                data.data.personal.monthlyMDRTBonus+
                data.data.manager.newUMBonus + data.data.manager.monthlyOverride +
                data.data.manager.recruiterBonus+ data.data.manager.nABMangerBonus+
                data.data.manager.individualManagerBonus+ data.data.manager.coachingUMBonus+
                data.data.manager.newAgentQualitityBonus;
        double totalMonthly = totalAll/mActivity.getMonthNumber();

        txtTotalIncomeAll.setText(Utils.longTypeTextFormat((long)(totalAll*1000000)));
        txtTotalIncomeMonthly.setText(Utils.longTypeTextFormat((long)(totalMonthly*1000000)));
        txtNumCampaign.setText("("+mActivity.getMonthNumber()+" tháng)");

        mListFragment = new ArrayList<>();
        mListFragment.add(UMCreatePlanStep8Screen1Fragment.newInstance(data));
        mListFragment.add(UMCreatePlanStep8Screen2Fragment.newInstance(data));

        mAdapter = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
        }
        if (indicator != null) {
            indicator.setViewPager(viewPager);
        }
    }

    @Override
    public void showSuccess() {
        mActivity.showSuccessView();
    }

    @OnClick(R.id.btn_complete)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_complete: {
                mActivity.setInComeMonthly(txtTotalIncomeMonthly.getText().toString());
                showConfirm("Xác nhận", "Đồng ý tạo chiến dịch", "Đồng ý", "Hủy",
                        SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                            @Override
                            public void DiaglogPositive() {
                                List<Integer> dataPersonal = mActivity.getDataStep7();
                                List<Integer> dataUMStep3 = mActivity.getDataStep3();
                                List<Integer> dataUMStep4 = mActivity.getDataStep4();
                                List<Integer> dataUMStep5 = mActivity.getDataStep5();

                                mActionListener.createCampaign(
                                        mActivity.getStartDate(),
                                        mActivity.getEndDate(),
                                        dataPersonal.get(1),
                                        dataPersonal.get(2),
                                        dataPersonal.get(3),
                                        dataPersonal.get(0),
                                        dataUMStep3.get(0),
                                        dataUMStep3.get(1),
                                        dataUMStep3.get(2),
                                        dataUMStep4.get(3),
                                        dataUMStep4.get(0),
                                        dataUMStep5.get(0),
                                        dataUMStep4.get(1),
                                        dataUMStep4.get(2),
                                        dataUMStep4.get(2),
                                        dataUMStep5.get(1),
                                        mActivity.getDataStep6()
                                );
                            }

                            @Override
                            public void DiaglogNegative() {

                            }
                        });
                break;
            }
        }
    }

    @Override
    public void getForCastIncome() {
        List<Integer> dataPersonal = mActivity.getDataStep7();
        List<Integer> dataUMStep3 = mActivity.getDataStep3();
        List<Integer> dataUMStep4 = mActivity.getDataStep4();
        List<Integer> dataUMStep5 = mActivity.getDataStep5();

        mActionListener.getForcastIncome(
                mActivity.getStartDate(),
                mActivity.getEndDate(),
                dataPersonal.get(1),
                dataPersonal.get(2),
                dataPersonal.get(3),
                dataPersonal.get(0),
                dataUMStep3.get(0),
                dataUMStep3.get(1),
                dataUMStep3.get(2),
                dataUMStep4.get(3),
                dataUMStep4.get(0),
                dataUMStep5.get(0),
                dataUMStep4.get(1),
                dataUMStep4.get(2),
                dataUMStep4.get(2),
                dataUMStep5.get(1)
                );
    }
}
