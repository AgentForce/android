package manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content.contentDetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content.ContentManageEmployFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContentDetailManageEmployFragment extends BaseFragment<MainFAActivity, ContentDetailManageEmployPresent> implements ContentDetailManageEmployContract.View {

    //varialbe
    @BindView(R.id.expandable_layout_mid)
    ExpandableLayout expandableLayoutMid;
    @BindView(R.id.expandable_layout_list)
    ExpandableLayout expandableLayoutList;
    @BindView(R.id.rcv_data)
    RecyclerView rcvData;
    @BindView(R.id.layout_bot)
    LinearLayout layoutBot;
    @BindView(R.id.layout_mid)
    LinearLayout layoutMid;
    @BindView(R.id.layout_mid_include)
    LinearLayout layoutMidInclude;
    @BindView(R.id.layout_title_bot)
    LinearLayout layoutTitleBot;
    @BindView(R.id.img_show_contacts)
    ImageView imgShowContacts;
    @BindView(R.id.layout_search)
    LinearLayout layoutSearch;

    private int mProcessStep;
    private String mType;

    //test
    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mDataActiveHist;
    private LinearLayoutManager mLayoutManager;

    public static ContentDetailManageEmployFragment newInstance(int processStep, String type) {
        Bundle args = new Bundle();
        args.putInt("processStep",processStep);
        args.putString("type",type);
        ContentDetailManageEmployFragment fragment = new ContentDetailManageEmployFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_sm_employ_content;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContentDetailManageEmployPresent(this, getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProcessStep = getArguments().getInt("processStep",1);
        mType = getArguments().getString("type","");
        initHeightViaSelected();

        testloadlist();
    }

    private void testloadlist(){
        mDataActiveHist = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvData.setLayoutManager(mLayoutManager);

        for (int i = 0; i < 5; i++) {
            ActiveHistFA temp = new ActiveHistFA();

            temp.setAvatar("avatar " + i);
            temp.setTitle("title " + i);
            temp.setContent("Content " + i);
            temp.setPhone("Phone "+ i);
            temp.setProcessStatusName("process status " + i);
            temp.setProcessStep(2);
            temp.setId(12);
            mDataActiveHist.add(temp);
        }
        if (mAdapterActiveHist == null) {
            mAdapterActiveHist = new ActiveHistAdapter(getContext(), mDataActiveHist, new CallBackClickContact() {
                @Override
                public void onClickMenuRight(int position, int option) {
                    //Toast.makeText(mActivity, "Vi tri " + position + " option " + option, Toast.LENGTH_SHORT).show();
                    switch (option) {
                        case 0: {

                            break;
                        }
                        case 1: {
                            break;
                        }
                        case 2: {

                            break;
                        }
                    }
                }

                @Override
                public void onClickMainContent(int position) {

                }
            });
            rcvData.setAdapter(mAdapterActiveHist);
        } else {
            mAdapterActiveHist.notifyDataSetChanged();
        }
    }

    @Override
    public void initHeightViaSelected(){
        int pageSelected = ((ContentManageEmployFragment)getParentFragment()).getParrentSelectedPage();
        switch (mType){
            case "month":{
                if(pageSelected == 0){
                    initviewsHeight();
                }
                break;
            }
            case "year":{
                if(pageSelected == 1){
                    initviewsHeight();
                }
                break;
            }
            case "campaign":{
                if(pageSelected == 2){
                    initviewsHeight();
                }
                break;
            }
        }
    }

    @Override
    public void initviewsHeight() {

        //set margin bottom for viewpager percent
        if (getView() != null) {
            final ViewTreeObserver observer = layoutBot.getViewTreeObserver();

            if (observer.isAlive()) {
                observer.dispatchOnGlobalLayout(); // In case a previous call is waiting when this call is made
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        observer.removeOnGlobalLayoutListener(this);
                        RelativeLayout.LayoutParams layoutParams =
                                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        layoutParams.setMargins(0, 0, 0, layoutBot.getHeight());
                        layoutMid.setLayoutParams(layoutParams);
                        //set min height for lisview
                        LinearLayout.LayoutParams layoutParams2 =
                                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, layoutMidInclude.getHeight() - layoutTitleBot.getHeight() - layoutSearch.getHeight());
                        rcvData.setLayoutParams(layoutParams2);
                    }
                });
            }
        }
    }

    @OnClick({R.id.layout_title_bot})
    public void onClickView(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_title_bot: {
                if (expandableLayoutMid.isExpanded()) {
                    expandableLayoutMid.collapse(true);
                    expandableLayoutList.expand(true);
                    imgShowContacts.setBackgroundResource(R.drawable.ic_arrow_down);
                } else {
                    expandableLayoutMid.expand(true);
                    expandableLayoutList.collapse(true);
                    imgShowContacts.setBackgroundResource(R.drawable.ic_arrow_up);
                }
                break;
            }
        }
    }
}
