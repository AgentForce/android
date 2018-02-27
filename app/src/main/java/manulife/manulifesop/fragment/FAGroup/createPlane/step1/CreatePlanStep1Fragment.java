package manulife.manulifesop.fragment.FAGroup.createPlane.step1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.createPlane.step2.CreatePlanStep2Fragment;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class CreatePlanStep1Fragment extends BaseFragment<CreatePlanActivity, CreatePlanStep1Present> implements CreatePlanStep1Contract.View {

    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.edt_start_date)
    EditText edtStartDate;
    @BindView(R.id.edt_end_date)
    EditText edtEndDate;


    public static CreatePlanStep1Fragment newInstance() {
        Bundle args = new Bundle();
        CreatePlanStep1Fragment fragment = new CreatePlanStep1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_step1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new CreatePlanStep1Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showDatePicker(final String type) {
        final View dialogView = View.inflate(getContext(), R.layout.date_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        dialogView.findViewById(R.id.btn_date_time_set).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView
                                .findViewById(R.id.date_picker);

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);

                        String selectDate = Utils.convertDateToString(calendar.getTime(), "dd/MM/yyyy");

                        if (type.equals("start")) {
                            edtStartDate.setText(selectDate);
                        } else {
                            //check end date is larger than start date
                            if (edtStartDate.getText().length() > 1) {
                                if (Utils.convertStringToDate(edtStartDate.getText().toString(), "dd/MM/yyyy").before(calendar.getTime()))
                                    edtEndDate.setText(selectDate);
                                else
                                    showMessage("Thông báo", "Ngày kết thúc phải lớn hơn ngày bắt đầu!", SweetAlertDialog.WARNING_TYPE);
                            } else {
                                showMessage("Thông báo", "Chưa chọn ngày bắt đầu!", SweetAlertDialog.WARNING_TYPE);
                            }
                        }
                        alertDialog.dismiss();

                    }
                });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }


    @OnClick({R.id.btn_next, R.id.edt_start_date, R.id.edt_end_date})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.showNextFragment(0, edtStartDate.getText().toString(),
                        edtEndDate.getText().toString(), 0, 0, 0);
                break;
            }
            case R.id.edt_start_date: {
                showDatePicker("start");
                break;
            }
            case R.id.edt_end_date: {
                showDatePicker("end");
                break;
            }
        }
    }

}
