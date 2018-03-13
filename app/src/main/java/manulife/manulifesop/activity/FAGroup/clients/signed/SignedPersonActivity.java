package manulife.manulifesop.activity.FAGroup.clients.signed;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.contact.ContactPersonContract;
import manulife.manulifesop.activity.FAGroup.clients.contact.ContactPersonPresenter;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.clients.contactPerson.ContactPersonTab1Fragment;
import manulife.manulifesop.util.Contants;


public class SignedPersonActivity extends BaseActivity<SignedPersonPresenter> implements SignedPersonContract.View {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_contact);
        mActionListener = new SignedPersonPresenter(this,this);
        hideKeyboardOutside(layoutRoot,this);
        setupSupportForApp();
    }



    private void setupSupportForApp() {

        txtActionbarTitle.setText("Danh sách ký hợp đồng tháng 12");
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

    @OnClick({R.id.layout_btn_back})
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.layout_btn_back:{
                onBackPressed();
                break;
            }
        }
    }
}
