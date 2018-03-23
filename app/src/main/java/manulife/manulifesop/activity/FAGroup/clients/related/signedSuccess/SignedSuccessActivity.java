package manulife.manulifesop.activity.FAGroup.clients.related.signedSuccess;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.eventDetail.EventDetailContract;
import manulife.manulifesop.activity.FAGroup.clients.related.eventDetail.EventDetailPresenter;
import manulife.manulifesop.adapter.ObjectData.EventData;
import manulife.manulifesop.base.BaseActivity;


public class SignedSuccessActivity extends BaseActivity<SignedSuccessPresenter> implements SignedSuccessContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.edt_revenue)
    TextView txtRevenue;
    @BindView(R.id.edt_profit)
    TextView txtProfit;
    @BindView(R.id.edt_contact_num)
    TextView txtNumContract;
    @BindView(R.id.rb_group)
    RadioGroup rbGroup;

    @BindView(R.id.btn_done)
    Button btnDone;

    @BindView(R.id.layout_root)
    RelativeLayout layoutRoot;

    private int mLeadID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_success);
        hideKeyboardOutside(layoutRoot);
        mActionListener = new SignedSuccessPresenter(this, this);
        mLeadID = getIntent().getIntExtra("leadID", 0);
        setupSupportForApp();
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
        return true;
    }

    @OnClick({R.id.layout_btn_back, R.id.btn_done})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
            case R.id.btn_done: {
                if (validateInput()) {
                    int commissionRate = Integer.valueOf(txtProfit.getText().toString());
                    int revenue = Integer.valueOf(txtRevenue.getText().toString());
                    int numContract = Integer.valueOf(txtNumContract.getText().toString());
                    int productType =
                            (rbGroup.getCheckedRadioButtonId() == R.id.rb_main) ? 1 : 2;
                    mActionListener.submitContract(mLeadID, commissionRate, revenue, numContract, productType);
                }
                break;
            }

        }
    }
}
