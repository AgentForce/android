package manulife.manulifesop.fragment.FAGroup.createPlane.step4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.fragment.FAGroup.createPlane.step3.CreatePlanStep3Contract;
import manulife.manulifesop.fragment.FAGroup.createPlane.step3.CreatePlanStep3Present;


/**
 * Created by Chick on 10/27/2017.
 */

public class CreatePlanStep4Fragment extends BaseFragment<CreatePlanActivity,CreatePlanStep4Present> implements CreatePlanStep4Contract.View {

    @BindView(R.id.btn_agree)
    Button btnAgree;



    public static CreatePlanStep4Fragment newInstance() {
        Bundle args = new Bundle();
        CreatePlanStep4Fragment fragment = new CreatePlanStep4Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_step4;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new CreatePlanStep4Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /*@Override
    public void onResume() {
        int contractNum = mActivity.getContractNum();
        if(rbMonthYear.getCheckedRadioButtonId() == R.id.rbn_month)
        {
            txtContractNum.setText(contractNum+"");
            txtMeetNum.setText((contractNum*3)+"");
            txtAppointmentNum.setText((contractNum*5)+"");
            txtCusNum.setText((contractNum*10)+"");
        }else
        {
            Toast.makeText(mActivity, "load via year", Toast.LENGTH_SHORT).show();
        }

        super.onResume();
    }*/

    @OnClick(R.id.btn_agree)
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_agree:
            {
                mActivity.showConfirm("Thông báo", "Bạn có đồng ý với kế hoạch kinh doanh?", "Đồng ý", "Quay lại"
                        , SweetAlertDialog.SUCCESS_TYPE, new CallBackConfirmDialog() {
                            @Override
                            public void DiaglogPositive() {

                            }

                            @Override
                            public void DiaglogNegative() {

                            }
                        });
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
