package manulife.manulifesop.fragment.FAGroup.createPlane.step3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.fragment.FAGroup.createPlane.step2.CreatePlanStep2Contract;
import manulife.manulifesop.fragment.FAGroup.createPlane.step2.CreatePlanStep2Present;


/**
 * Created by Chick on 10/27/2017.
 */

public class CreatePlanStep3Fragment extends BaseFragment<CreatePlanActivity, CreatePlanStep3Present> implements CreatePlanStep3Contract.View {

    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.txt_contract_num)
    TextView txtContractNum;
    @BindView(R.id.txt_meet_num)
    TextView txtMeetNum;
    @BindView(R.id.txt_appointment_num)
    TextView txtAppointmentNum;
    @BindView(R.id.txt_cus_num)
    TextView txtCusNum;

    @BindView(R.id.rb_month_year)
    RadioGroup rbMonthYear;


    public static CreatePlanStep3Fragment newInstance() {
        Bundle args = new Bundle();
        CreatePlanStep3Fragment fragment = new CreatePlanStep3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_step3;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new CreatePlanStep3Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateDate(int contractNum) {
        if (rbMonthYear.getCheckedRadioButtonId() == R.id.rbn_year) {
            contractNum = contractNum * 12;
        }
        txtContractNum.setText(contractNum + "");
        txtMeetNum.setText((contractNum * 3) + "");
        txtAppointmentNum.setText((contractNum * 5) + "");
        txtCusNum.setText((contractNum * 10) + "");
    }


    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.showNextFragment(0,"","",0,0,0);
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
