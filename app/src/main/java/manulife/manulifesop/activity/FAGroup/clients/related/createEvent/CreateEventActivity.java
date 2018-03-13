package manulife.manulifesop.activity.FAGroup.clients.related.createEvent;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.base.BaseActivity;


public class CreateEventActivity extends BaseActivity<CreateEventPresenter> implements CreateEventContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_contact);
        mActionListener = new CreateEventPresenter(this, this);
        type = getIntent().getStringExtra("type");
        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        setupSupportForApp();
    }
    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Tạo sự kiện");
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
