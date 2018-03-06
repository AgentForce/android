package manulife.manulifesop.activity.FAGroup.clients.related.eventDetail;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.adapter.ObjectData.EventData;
import manulife.manulifesop.base.BaseActivity;


public class EventDetailActivity extends BaseActivity<EventDetailPresenter> implements EventDetailContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.txt_title_tyle)
    TextView txtTitleType;
    @BindView(R.id.txt_event_date)
    TextView txtEventDate;
    @BindView(R.id.txt_contact_name)
    TextView txtContactName;

    @BindView(R.id.layout_delete)
    LinearLayout layoutDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        mActionListener = new EventDetailPresenter(this, this);
        setupSupportForApp();
        initData();
    }
    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Chi tiết");
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

    private void initData(){
        EventData data = (EventData) getIntent().getSerializableExtra("data");
        if(data != null){
            txtTitleType.setText(data.getTypeEvent());
            txtEventDate.setText(data.getDateTime());
            txtContactName.setText(data.getName());
        }
    }



    @OnClick({R.id.layout_delete})
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.layout_delete:{
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
            }

        }
    }
}