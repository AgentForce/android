package manulife.manulifesop.activity.FAGroup.clients.related.signedSuccess;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.updateContactInfo.UpdateContactInfoActivity;
import manulife.manulifesop.adapter.ObjectData.SpinnerObject;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.element.customViews.MultiSelectionObjectSpinner;
import manulife.manulifesop.util.NumberTextWatcherForThousand;
import manulife.manulifesop.util.Utils;


public class SignedSuccessActivity extends BaseActivity<SignedSuccessPresenter> implements SignedSuccessContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.txt_sign_date)
    TextView txtSignDate;

    @BindView(R.id.edt_revenue)
    EditText txtRevenue;
    @BindView(R.id.edt_profit)
    TextView txtProfit;
    @BindView(R.id.edt_contact_num)
    TextView txtNumContract;

    @BindView(R.id.btn_done)
    Button btnDone;

    @BindView(R.id.layout_root)
    RelativeLayout layoutRoot;

    @BindView(R.id.spinner_main_produce)
    MultiSelectionObjectSpinner spinnerMainProduct;
    @BindView(R.id.spinner_sub_produce)
    MultiSelectionObjectSpinner spinnerSubProduct;

    private int mLeadID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_success);
        hideKeyboardOutside(layoutRoot);
        mActionListener = new SignedSuccessPresenter(this, this);
        mLeadID = getIntent().getIntExtra("leadID", 0);
        setupSupportForApp();
        initEventViews();
        mActionListener.getProductsSign();
    }

    private void initEventViews(){
        txtRevenue.addTextChangedListener(new NumberTextWatcherForThousand(txtRevenue));
        Calendar calendar = Calendar.getInstance();
        txtSignDate.setText(Utils.convertDateToString(calendar.getTime(),"dd/MM/yyyy"));
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Ký hợp đồng thành công");
        layoutBackButton.setVisibility(View.VISIBLE);

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        viewStatusBar.setLayoutParams(params);
    }

    @Override
    public void initProducts(ArrayList<SpinnerObject> dataMainProduct, ArrayList<SpinnerObject> dataSubProduct) {
        spinnerMainProduct.SetResourceSpinner(dataMainProduct,this,false,false);
        spinnerMainProduct.SetTitleDialog("Sản phẩm chính");

        spinnerSubProduct.SetResourceSpinner(dataSubProduct,this,false,false);
        spinnerSubProduct.SetTitleDialog("Sản phẩm phụ");
    }

    @Override
    public void finishSubmit() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean validateInput() {
        if (txtRevenue.getText().length() < 1) {
            txtRevenue.setError("Nhập doanh thu");
            return false;
        }
        if (txtProfit.getText().length() < 1) {
            txtProfit.setError("Nhập hoa hồng");
            return false;
        }
        if (txtNumContract.getText().length() < 1) {
            txtNumContract.setError("Nhập số lượng hợp đồng");
            return false;
        }
        if(spinnerMainProduct.getSelectedKeyItemsAsString().equals("0")){
            showMessage("Thông báo","Chọn sản phẩm chính!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if(spinnerSubProduct.getSelectedKeyItemsAsString().equals("0")){
            showMessage("Thông báo","Chọn sản phẩm phụ", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        return true;
    }

    @Override
    public void showDatePicker() {
        final View dialogView = View.inflate(SignedSuccessActivity.this, R.layout.date_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(SignedSuccessActivity.this).create();

        //set max min date for date picker
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.YEAR,1950);
        ((DatePicker) dialogView.findViewById(R.id.date_picker)).setMinDate(currentCalendar.getTimeInMillis());
        currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.MONTH,11);
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

                        txtSignDate.setText(selectDate);

                        alertDialog.dismiss();
                    }
                });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }


    @OnClick({R.id.layout_btn_back, R.id.btn_done, R.id.layout_sign_date})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_sign_date:{
                showDatePicker();
                break;
            }
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
            case R.id.btn_done: {
                if (validateInput()) {
                    int commissionRate = Integer.valueOf(txtProfit.getText().toString());
                    int revenue = Integer.valueOf(
                            NumberTextWatcherForThousand.trimCommaOfString(
                                    txtRevenue.getText().toString()));
                    int numContract = Integer.valueOf(txtNumContract.getText().toString());

                    mActionListener.submitContract(mLeadID, commissionRate, revenue,
                            numContract, spinnerMainProduct.getSelectedKeyItemsAsString(),
                            spinnerSubProduct.getSelectedKeyItemsAsString(),
                            txtSignDate.getText().toString());
                }
                break;
            }

        }
    }
}
