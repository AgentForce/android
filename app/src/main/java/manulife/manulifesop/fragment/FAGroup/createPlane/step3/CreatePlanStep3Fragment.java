package manulife.manulifesop.fragment.FAGroup.createPlane.step3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class CreatePlanStep3Fragment extends BaseFragment<CreatePlanActivity,CreatePlanStep3Present> implements CreatePlanStep3Contract.View {

    @BindView(R.id.btn_agree)
    Button btnAgree;

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
