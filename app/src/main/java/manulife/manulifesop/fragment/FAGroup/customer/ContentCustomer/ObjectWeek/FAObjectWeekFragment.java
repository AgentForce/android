package manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer.ObjectWeek;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.warkiz.widget.IndicatorSeekBar;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
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
    @BindView(R.id.txt_edit_week)
    TextView txtEditWeekObject;

    private AlertDialog alertDialog;

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

    @OnClick(R.id.txt_edit_week)
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.txt_edit_week:
            {
                showDialogEditContract();
                break;
            }
        }
    }

    private void showDialogEditContract() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_contract_week, null);

        initViewsDialogWeek(dialogView);

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void initViewsDialogWeek(View dialogView) {

        IndicatorSeekBar sbStep1;
        TextView txtcontractStep1, txtmeetingStep1, txtadvisoryStep1,
                txtsignStep1, txtintroduceStep1;
        TextView txtCancel, txtOK;

        sbStep1 = dialogView.findViewById(R.id.sb_step1);

        txtcontractStep1 = dialogView.findViewById(R.id.txt_step1_contact);
        txtmeetingStep1 = dialogView.findViewById(R.id.txt_step1_meeting);
        txtadvisoryStep1 = dialogView.findViewById(R.id.txt_step1_advisory);
        txtsignStep1 = dialogView.findViewById(R.id.txt_step1_sign);
        txtintroduceStep1 = dialogView.findViewById(R.id.txt_step1_introduce);

        txtCancel = dialogView.findViewById(R.id.txt_cancel);
        txtOK = dialogView.findViewById(R.id.txt_ok);

        txtcontractStep1.setTag(30);

        sbStep1.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            int intTemp;
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if(fromUserTouch)
                {
                    intTemp = Math.round(Integer.valueOf(txtcontractStep1.getText().toString())
                            * progress / (int)txtcontractStep1.getTag());
                    txtcontractStep1.setText(String.valueOf(intTemp));
                    //intTemp = Math.round((float)(Integer.valueOf(txtcontractStep1.getText().toString())/progress)/100);
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

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "Đồng ý", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
