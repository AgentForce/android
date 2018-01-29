package manulife.manulifesop.fragment.FAGroup.createPlane.step2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.adapter.PasswordAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.SmsListener;
import manulife.manulifesop.service.SmsReceiver;
import manulife.manulifesop.util.Utils;


/**
 * Created by Chick on 10/27/2017.
 */

public class CreatePlanStep2Fragment extends BaseFragment<CreatePlanActivity,CreatePlanStep2Present> implements CreatePlanStep2Contract.View {

    @BindView(R.id.list_pass)
    RecyclerView listPass;
    @BindView(R.id.txt_pass)
    TextView txtPass;

    List<Boolean> mDataList;
    PasswordAdapter mAdapter;

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

        initPasswordView();

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

    public void initPasswordView()
    {
        //init list password
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listPass.setLayoutManager(layoutManager);

        mDataList = new ArrayList<>();
        mDataList.add(false);
        mDataList.add(false);
        mDataList.add(false);
        mDataList.add(false);
        mDataList.add(false);
        mDataList.add(false);

        mAdapter = new PasswordAdapter(getContext(),mDataList);
        listPass.setAdapter(mAdapter);

        //add listener txt_pass
        txtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tmpText = editable.toString();

                for(int i=1;i<=mDataList.size();i++)
                {
                    if(i <= tmpText.length()) {
                        mDataList.set(i-1, true);
                    }else
                    {
                        mDataList.set(i-1,false);
                    }
                }
                mAdapter.notifyDataSetChanged();
                if(tmpText.length()>=6)
                {
                    Toast.makeText(mActivity, tmpText, Toast.LENGTH_SHORT).show();
                }
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
