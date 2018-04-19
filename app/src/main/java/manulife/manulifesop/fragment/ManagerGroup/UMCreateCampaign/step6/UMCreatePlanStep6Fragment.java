package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step6;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.adapter.ContactHistAdapter;
import manulife.manulifesop.adapter.CreatePlanUMStep6Adapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.adapter.ObjectData.UMStep6;
import manulife.manulifesop.api.ObjectResponse.UMForcastRecruit;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step5.UMCreatePlanStep5Contract;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step5.UMCreatePlanStep5Present;


/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep6Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep6Present> implements UMCreatePlanStep6Contract.View {

    @BindView(R.id.rcv_data)
    RecyclerView listData;

    private CreatePlanUMStep6Adapter mAdapter;
    private List<UMStep6> mData;
    private LinearLayoutManager mLayoutManager;

    public static UMCreatePlanStep6Fragment newInstance() {
        Bundle args = new Bundle();
        UMCreatePlanStep6Fragment fragment = new UMCreatePlanStep6Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_um_step6;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep6Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
        loadData();
    }

    @Override
    public void loadData() {
        showLoading("Xử lý dữ liệu");
        listData.setLayoutManager(mLayoutManager);

        UMStep6 tmp;
        //init for first
        UMForcastRecruit dataStep2 = mActivity.getDataStep2();
        int totalAgent = dataStep2.data.goalSetup.fa + dataStep2.data.goalSetup.um+
                dataStep2.data.noGoalSetup.fa + dataStep2.data.noGoalSetup.um;

        List<Integer> dataStep3 = mActivity.getDataStep3();
        tmp = new UMStep6();
        tmp.setMonth(1);
        tmp.setAgentStart(totalAgent);
        tmp.setAgentAdd(dataStep3.get(0));
        tmp.setAgentDecrease(1);
        tmp.setCollapse(true);
        mData.add(tmp);

        for (int i = 2; i <= 12; i++) {
            tmp = new UMStep6();
            tmp.setMonth(i);
            tmp.setAgentStart(mData.get(i-2).getAgentStart() + mData.get(i-2).getAgentAdd() - mData.get(i-2).getAgentDecrease());
            tmp.setAgentAdd(dataStep3.get(0));
            tmp.setAgentDecrease(1);
            tmp.setCollapse(true);
            mData.add(tmp);
        }

        mAdapter = new CreatePlanUMStep6Adapter(getContext(), mData);
        listData.setAdapter(mAdapter);
        finishLoading();
    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.setDataStep6(mData);
                mActivity.showNextFragment();
                break;
            }
        }
    }
}
