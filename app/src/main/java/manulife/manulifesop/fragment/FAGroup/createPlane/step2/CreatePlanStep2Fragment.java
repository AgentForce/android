package manulife.manulifesop.fragment.FAGroup.createPlane.step2;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.SmsListener;
import manulife.manulifesop.service.SmsReceiver;


/**
 * Created by Chick on 10/27/2017.
 */

public class CreatePlanStep2Fragment extends BaseFragment<CreatePlanActivity,CreatePlanStep2Present> implements CreatePlanStep2Contract.View {


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

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                //From the received text string you may do string operations to get the required OTP
                //It depends on your SMS format
                showMessage("Thong bao",messageText, SweetAlertDialog.SUCCESS_TYPE);
                Log.d("test",messageText+"_______________________________________");
                System.out.println(messageText+"_______________________________________");

                //Toast.makeText(getContext(),"Message: "+messageText,Toast.LENGTH_LONG).show();

                // If your OTP is six digits number, you may use the below code

                /*Pattern pattern = Pattern.compile(OTP_REGEX);
                Matcher matcher = pattern.matcher(messageText);
                String otp;
                while (matcher.find())
                {
                    otp = matcher.group();
                }

                Toast.makeText(MainActivity.this,"OTP: "+ otp ,Toast.LENGTH_LONG).show();*/

            }
        });
    }

    /*@OnClick(R.id.btn_loading)
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_loading:
            {
                //mActivity.showLoading("fragment job 1 call method in activity");
                //mActivity.finishLoading("load finish",true);
                //mActivity.viewFinishLoading();
                mActivity.goNextScreen(MainActivity.class);
                break;
            }
        }
    }*/
}
