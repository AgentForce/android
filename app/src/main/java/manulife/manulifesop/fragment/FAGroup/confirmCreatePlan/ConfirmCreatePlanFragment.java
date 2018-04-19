package manulife.manulifesop.fragment.FAGroup.confirmCreatePlan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.activity.ManagerGroup.SMCreatePlan.SMCreatePlanActivity;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class ConfirmCreatePlanFragment extends BaseFragment<MainFAActivity,ConfirmCreatePlanPresent> implements ConfirmCreatePlanContract.View {

    @BindView(R.id.btn_start)
    Button btnStart;

    public static ConfirmCreatePlanFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title",title);
        ConfirmCreatePlanFragment fragment = new ConfirmCreatePlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_show_create_plane;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ConfirmCreatePlanPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.showHideActionbar(true);
        mActivity.updateActionbarTitle(getArguments().getString("title","Trang chủ"));
    }

    @OnClick(R.id.btn_start)
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_start:
            {
                int level = SOPSharedPreferences.getInstance(getContext()).getLevel();
                String userName = SOPSharedPreferences.getInstance(getContext()).getUserName();
                if(level < 10) {
                    //SM
                    Bundle data = new Bundle();
                    data.putString("name", userName);
                    mActivity.goNextScreen(SMCreatePlanActivity.class, data);
                }else if(level < 16){
                    //UM
                    Bundle data = new Bundle();
                    data.putString("name", userName);
                    mActivity.goNextScreen(UMCreatePlanActivity.class, data);
                }else{
                    //FA
                    Bundle data = new Bundle();
                    data.putString("name", userName);
                    mActivity.goNextScreen(CreatePlanActivity.class, data);
                }
                break;
            }
        }
    }

}
