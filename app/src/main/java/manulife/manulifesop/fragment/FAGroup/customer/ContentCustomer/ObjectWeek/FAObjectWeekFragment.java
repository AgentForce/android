package manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer.ObjectWeek;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.base.BaseFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAObjectWeekFragment extends BaseFragment<MainFAActivity, FAObjectWeekPresent> implements FAObjectWeekContract.View {

    @BindView(R.id.layout_contact_step1)
    LinearLayout layoutContactStep1;
    @BindView(R.id.layout_meeting_step1)
    LinearLayout layoutMeetingStep1;
    @BindView(R.id.layout_advisory_step1)
    LinearLayout layoutAdvisoryStep1;
    @BindView(R.id.layout_sign_step1)
    LinearLayout layoutaSignStep1;
    @BindView(R.id.layout_introduce_step1)
    LinearLayout layoutaIntroduceStep1;

    public static FAObjectWeekFragment newInstance() {
        Bundle args = new Bundle();
        FAObjectWeekFragment fragment = new FAObjectWeekFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_fa_customer_content_week;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FAObjectWeekPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews(){

        layoutContactStep1.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutContactStep1.getLayoutParams();
                params.height = layoutContactStep1.getWidth();

                layoutContactStep1.setLayoutParams(params);
                layoutMeetingStep1.setLayoutParams(params);
                layoutAdvisoryStep1.setLayoutParams(params);
                layoutaSignStep1.setLayoutParams(params);
                layoutaIntroduceStep1.setLayoutParams(params);

            }
        });
    }
}
