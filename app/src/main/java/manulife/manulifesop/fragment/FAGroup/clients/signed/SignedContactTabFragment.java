package manulife.manulifesop.fragment.FAGroup.clients.signed;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.consultant.ConsultantActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.activity.FAGroup.clients.signed.SignedPersonActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.fragment.FAGroup.clients.consultant.ConsultantContactTabContract;
import manulife.manulifesop.fragment.FAGroup.clients.consultant.ConsultantContactTabPresent;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class SignedContactTabFragment extends BaseFragment<SignedPersonActivity, SignedContactTabPresent> implements SignedContactTabContract.View {

    @BindView(R.id.rcv_contact)
    RecyclerView listContact;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private String mType;
    private int mTarget;
    private int mMonth;

    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mData;
    private LinearLayoutManager mLayoutManager;


    public static SignedContactTabFragment newInstance(String type, int target, int month) {
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putInt("target", target);
        args.putInt("month", month);
        SignedContactTabFragment fragment = new SignedContactTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            Toast.makeText(mActivity, "load more", Toast.LENGTH_SHORT).show();
            //loadDataContact();
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_appointment_contact_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new SignedContactTabPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getString("type", "");
        mTarget = getArguments().getInt("target", 0);
        mMonth = getArguments().getInt("month", 0);
        initViews();
        loadDataContact();
    }

    private void initViews() {
        //type = appointment, seen, calllater
        if (mType != null && !mType.equals("")) {
            switch (mType) {
                case Contants.SIGNED_SUCCESS: {
                    txtTitle.setText("Khách hàng ký hợp đồng thành công(" +
                            ProjectApplication.getInstance().getSignSuccess().data.count
                            + "/" + mTarget + ")");
                    break;
                }
                case Contants.SIGNED_NOT_APPLY: {
                    txtTitle.setText("Khách hàng chưa nộp hồ sơ");
                    break;
                }
                case Contants.SIGNED_BHXH: {
                    txtTitle.setText("Khách hàng hoàn tất BHXH");
                    break;
                }
                case Contants.SIGNED_APPLIED: {
                    txtTitle.setText("Khách hàng đã nộp hồ sơ");
                    break;
                }
                case Contants.SIGNED_WAIT_APPROVE: {
                    txtTitle.setText("Khách hàng chờ duyệt hồ sơ");
                    break;
                }
            }
        }
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
    }

    private void loadDataContact() {
        listContact.setLayoutManager(mLayoutManager);

        UsersList data = new UsersList();
        switch (mType) {
            case Contants.SIGNED_SUCCESS: {
                data = ProjectApplication.getInstance().getSignSuccess();
                break;
            }
            case Contants.SIGNED_NOT_APPLY: {
                data = ProjectApplication.getInstance().getSignNotApply();
                break;
            }
            case Contants.SIGNED_BHXH: {
                data = ProjectApplication.getInstance().getSignBHXH();
                break;
            }
            case Contants.SIGNED_APPLIED: {
                data = ProjectApplication.getInstance().getSignApplied();
                break;
            }
            case Contants.SIGNED_WAIT_APPROVE: {
                data = ProjectApplication.getInstance().getSignWaitApprove();
                break;
            }
        }


        for (int i = 0; i < data.data.rows.size(); i++) {
            ActiveHistFA temp = new ActiveHistFA();
            temp.setId(data.data.rows.get(i).id);
            temp.setAvatar("avatar " + i);
            temp.setTitle(data.data.rows.get(i).name);
            temp.setContent(data.data.rows.get(i).phone);
            mData.add(temp);
        }


        mAdapterActiveHist = new ActiveHistAdapter(getContext(), mData, new CallBackClickContact() {
            @Override
            public void onClickMenuRight(int position, int option) {
                Toast.makeText(mActivity, "vi tri " + position + " options " + option, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickMainContent(int position) {
                gotoConactDetail(mData.get(position).getId());
            }
        });
        listContact.setAdapter(mAdapterActiveHist);

        //set space between two items
        int[] ATTRS = new int[]{android.R.attr.listDivider};
        TypedArray a = getContext().obtainStyledAttributes(ATTRS);
        Drawable divider = a.getDrawable(0);
        int insetLeft = getResources().getDimensionPixelSize(R.dimen.margin_left_DividerItemDecoration);
        int insetRight = getResources().getDimensionPixelSize(R.dimen.margin_right_DividerItemDecoration);
        InsetDrawable insetDivider = new InsetDrawable(divider, insetLeft, 0, insetRight, 0);
        a.recycle();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listContact.getContext(),
                mLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(insetDivider);
        listContact.addItemDecoration(dividerItemDecoration);


        listContact.clearOnScrollListeners();
        listContact.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                Integer.valueOf(data.data.page)
                , Utils.genLastPage(data.data.count,
                Integer.valueOf(data.data.limit))
                , new onLoadingMoreDataTask(), mLayoutManager));
    }

    @Override
    public void gotoConactDetail(int id) {
        Bundle data = new Bundle();
        data.putString("type", mType);
        data.putString("type_menu", Contants.CONSULTANT_MENU);
        data.putInt("id",id);
        mActivity.goNextScreen(ContactDetailActivity.class, data);
    }


    /*@OnClick({R.id.txt_add_from_telephone, R.id.txt_add_new})
    public void onClickEvent(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_add_from_telephone: {
                mActivity.goNextScreen(AddContactPersonActivity.class);
                break;
            }
            case R.id.txt_add_new: {
                showpDialogAddNew();
                break;
            }
            case R.id.txt_add_from_introduce: {
                mActivity.goNextScreen(IntroduceContactActivity.class);
                break;
            }
        }
    }*/

}
