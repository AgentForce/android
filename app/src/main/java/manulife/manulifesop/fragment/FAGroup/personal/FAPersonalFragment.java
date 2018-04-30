package manulife.manulifesop.fragment.FAGroup.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.personal.personalInfo.PersonalInfoActivity;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAPersonalFragment extends BaseFragment<MainFAActivity, FAPersonalPresent> implements FAPersonalContract.View {

    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_employee_id)
    TextView txtID;
    @BindView(R.id.txt_avatar)
    TextView txtAvatar;

    private UserProfile mDataProfie;


    public static FAPersonalFragment newInstance() {
        Bundle args = new Bundle();
        FAPersonalFragment fragment = new FAPersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FAPersonalPresent(this, getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.showHideActionbar(false);
        mActionListener.getUserProfile(SOPSharedPreferences.getInstance(getContext()).getUserName());
    }

    @Override
    public void showData(UserProfile data) {
        mDataProfie = data;
        txtName.setText(data.data.fullName);
        txtID.setText("ID: " + data.data.username);
        String fullName = data.data.fullName;
        txtAvatar.setText(fullName.substring(fullName.lastIndexOf(" ")+1)
                .substring(0,1));
    }

    @OnClick({R.id.layout_logout, R.id.layout_support, R.id.layout_introduce,
            R.id.layout_setting, R.id.layout_info, R.id.layout_activity_hist,
            R.id.layout_activity,R.id.layout_personal_object,R.id.layout_emulation_program})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_logout: {
                showConfirm("Xác nhận", "Đăng xuất khỏi tài khoản?", "Đồng ý",
                        "Hủy", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                            @Override
                            public void DiaglogPositive() {
                                SOPSharedPreferences.getInstance(getContext()).saveTokenUser("", "");
                                SOPSharedPreferences.getInstance(getContext()).saveUser("",-1);
                                SOPSharedPreferences.getInstance(getContext()).saveIsFA(true);
                                //mActivity.logoutProcess();
                                mActivity.backToPrevious(new Bundle());
                            }

                            @Override
                            public void DiaglogNegative() {

                            }
                        });

                break;
            }
            case R.id.layout_support: {
                showMessage("Inform", "Support", SweetAlertDialog.WARNING_TYPE);
                break;
            }
            case R.id.layout_introduce: {
                showMessage("Inform", "Introduce", SweetAlertDialog.WARNING_TYPE);
                break;
            }
            case R.id.layout_setting: {
                showMessage("Inform", "Setting", SweetAlertDialog.WARNING_TYPE);
                break;
            }
            case R.id.layout_info: {
                Bundle data = new Bundle();
                data.putSerializable("data",mDataProfie);
                mActivity.goNextScreen(PersonalInfoActivity.class,data);
                break;
            }
            case R.id.layout_activity_hist: {
                showMessage("Inform", "Nhật ký hoạt động", SweetAlertDialog.WARNING_TYPE);
                break;
            }
            case R.id.layout_activity: {
                showMessage("Inform", "Tin tức hoạt động", SweetAlertDialog.WARNING_TYPE);
                break;
            }
            case R.id.layout_personal_object: {
                showMessage("Inform", "Mục tiêu cá nhân", SweetAlertDialog.WARNING_TYPE);
                break;
            }
            case R.id.layout_emulation_program: {
                showMessage("Inform", "Chương trình thi đua", SweetAlertDialog.WARNING_TYPE);
                break;
            }


        }
    }

}
