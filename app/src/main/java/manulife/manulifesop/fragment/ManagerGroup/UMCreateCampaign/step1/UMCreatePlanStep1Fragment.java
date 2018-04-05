package manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.UMCreatePlan.UMCreatePlanActivity;
import manulife.manulifesop.adapter.ObjectData.SpinnerObject;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class UMCreatePlanStep1Fragment extends BaseFragment<UMCreatePlanActivity, UMCreatePlanStep1Present> implements UMCreatePlanStep1Contract.View {

    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.spinner_type)
    Spinner spinnerStype;
    @BindView(R.id.spinner_choose_time)
    Spinner spinnerChooseTime;

    @BindView(R.id.txt_en_date)
    TextView txtEndDate;
    @BindView(R.id.edt_start_date)
    EditText edtStartDate;

    public static UMCreatePlanStep1Fragment newInstance() {
        Bundle args = new Bundle();
        UMCreatePlanStep1Fragment fragment = new UMCreatePlanStep1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_create_plan_step1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new UMCreatePlanStep1Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        edtStartDate.setText(Utils.convertDateToString(Calendar.getInstance().getTime(), "dd/MM/yyyy"));

        List<SpinnerObject> dataspinner = new ArrayList<>();
        dataspinner.add(new SpinnerObject("0", "Theo tháng"));
        dataspinner.add(new SpinnerObject("1", "Theo quý"));
        dataspinner.add(new SpinnerObject("2", "Theo năm"));

        ArrayAdapter<SpinnerObject> adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.textview_spinner, dataspinner);
        adapterSpinner.setDropDownViewResource(R.layout.row_one_spinner_item);

        spinnerStype.setAdapter(adapterSpinner);
        spinnerStype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                setDataForSpinnerChoose(((SpinnerObject) adapterView.getSelectedItem()).getKey());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setDataForSpinnerChoose(String style) {
        switch (style) {
            case "0": {//theo tháng
                spinnerChooseTime.setAlpha(1f);
                spinnerChooseTime.setEnabled(true);
                List<SpinnerObject> dataspinner = new ArrayList<>();
                int selectedMonth = Integer.valueOf(Utils.convertStringDateToStringDate(
                        edtStartDate.getText().toString(), "dd/MM/yyyy", "MM"));
                for (int i = selectedMonth - 1; i < 12; i++) {
                    dataspinner.add(new SpinnerObject(String.valueOf(i), "Tháng " + (i + 1)));
                }
                ArrayAdapter<SpinnerObject> adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.textview_spinner, dataspinner);
                adapterSpinner.setDropDownViewResource(R.layout.row_one_spinner_item);
                spinnerChooseTime.setAdapter(adapterSpinner);
                spinnerChooseTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Integer.valueOf(((SpinnerObject) adapterView.getSelectedItem()).getKey()));
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                        txtEndDate.setText(Utils.convertDateToString(calendar.getTime(), "dd/MM/yyyy"));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                break;
            }
            case "1": {//theo quý
                spinnerChooseTime.setAlpha(1f);
                spinnerChooseTime.setEnabled(true);
                List<SpinnerObject> dataspinner = new ArrayList<>();
                int currentMonth = Integer.valueOf(Utils.convertStringDateToStringDate(
                        edtStartDate.getText().toString(), "dd/MM/yyyy", "MM"));
                if (currentMonth < 3) {
                    dataspinner.add(new SpinnerObject("2", "Quý 1"));
                    dataspinner.add(new SpinnerObject("5", "Quý 2"));
                    dataspinner.add(new SpinnerObject("8", "Quý 3"));
                    dataspinner.add(new SpinnerObject("11", "Quý 4"));
                } else if (currentMonth < 6) {
                    dataspinner.add(new SpinnerObject("5", "Quý 2"));
                    dataspinner.add(new SpinnerObject("8", "Quý 3"));
                    dataspinner.add(new SpinnerObject("11", "Quý 4"));
                } else if (currentMonth < 9) {
                    dataspinner.add(new SpinnerObject("8", "Quý 3"));
                    dataspinner.add(new SpinnerObject("11", "Quý 4"));
                } else if (currentMonth <= 12) {
                    dataspinner.add(new SpinnerObject("11", "Quý 4"));
                }

                ArrayAdapter<SpinnerObject> adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.textview_spinner, dataspinner);
                adapterSpinner.setDropDownViewResource(R.layout.row_one_spinner_item);
                spinnerChooseTime.setAdapter(adapterSpinner);
                spinnerChooseTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Integer.valueOf(((SpinnerObject) adapterView.getSelectedItem()).getKey()));
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                        txtEndDate.setText(Utils.convertDateToString(calendar.getTime(), "dd/MM/yyyy"));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                break;
            }
            case "2": {
                spinnerChooseTime.setAlpha(0);
                spinnerChooseTime.setEnabled(false);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                txtEndDate.setText(Utils.convertDateToString(calendar.getTime(), "dd/MM/yyyy"));
                break;
            }
        }
    }

    @Override
    public void showDatePicker(String type) {
        final View dialogView = View.inflate(getContext(), R.layout.date_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        //set max min date for date picker
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.MONTH, 0);
        ((DatePicker) dialogView.findViewById(R.id.date_picker)).setMinDate(currentCalendar.getTimeInMillis());
        currentCalendar.set(Calendar.MONTH, 11);
        ((DatePicker) dialogView.findViewById(R.id.date_picker)).setMaxDate(currentCalendar.getTimeInMillis());

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
                        Calendar calendarNextOneDay = Calendar.getInstance();
                        calendarNextOneDay.add(Calendar.DAY_OF_YEAR, -1);

                        if (calendar.getTime().after(calendarNextOneDay.getTime())) {
                            edtStartDate.setText(selectDate);
                            setDataForSpinnerChoose(((SpinnerObject) spinnerStype.getSelectedItem()).getKey());
                        } else
                            showMessage("Thông báo", "Ngày bắt đầu phải lớn hơn ngày hiện tại!", SweetAlertDialog.WARNING_TYPE);

                        alertDialog.dismiss();
                    }
                });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    @OnClick({R.id.btn_next, R.id.edt_start_date})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                mActivity.showNextFragment(edtStartDate.getText().toString(),
                        txtEndDate.getText().toString());
                break;
            }
            case R.id.edt_start_date: {
                showDatePicker("start");
                break;
            }
        }
    }

}
