package manulife.manulifesop.fragment.FAGroup.contactPerson.tab1;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.contact.ContactPersonActivity;
import manulife.manulifesop.activity.FAGroup.contact.addContact.AddContactPersonActivity;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.RecyclerItemClickListener;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanContract;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanPresent;
import manulife.manulifesop.fragment.FAGroup.dashboard.FADashBoardFragment;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactPersonTab1Fragment extends BaseFragment<ContactPersonActivity, ContactPersonTab1Present> implements ContactPersonTab1Contract.View {

    @BindView(R.id.rcv_contact)
    RecyclerView listContact;
    @BindView(R.id.txt_add_from_telephone)
    TextView txtAddFromTelephone;
    @BindView(R.id.txt_add_new)
    TextView txtAddNew;
    @BindView(R.id.txt_add_from_introduce)
    TextView txtAddFromIntroduce;


    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mData;
    private LinearLayoutManager mLayoutManager;

    public static ContactPersonTab1Fragment newInstance() {
        Bundle args = new Bundle();
        ContactPersonTab1Fragment fragment = new ContactPersonTab1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            Toast.makeText(mActivity, "load more", Toast.LENGTH_SHORT).show();
            loadDataContact();
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_contact_person_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContactPersonTab1Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        loadDataContact();
    }

    private void initViews() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
    }

    private void loadDataContact() {
        listContact.setLayoutManager(mLayoutManager);

        for (int i = 0; i < 10; i++) {
            ActiveHistFA temp = new ActiveHistFA();
            temp.setAvatar("avatar " + i);
            temp.setTitle("title code input " + i);
            temp.setContent("content code input " + i);
            mData.add(temp);
        }
        if (mAdapterActiveHist == null) {
            mAdapterActiveHist = new ActiveHistAdapter(getContext(), mData, new CallBackClickContact() {
                @Override
                public void onClickMenuRight(int position, int option) {
                    Toast.makeText(mActivity, "vi tri " + position + " options " + option, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClickMainContent(int position) {
                    Toast.makeText(mActivity, mData.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            listContact.setAdapter(mAdapterActiveHist);
        } else {
            mAdapterActiveHist.notifyDataSetChanged();
        }

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
                0, 3, new onLoadingMoreDataTask(), mLayoutManager));
    }

    @OnClick({R.id.txt_add_from_telephone, R.id.txt_add_new, R.id.txt_add_from_introduce})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_add_from_telephone: {
                mActivity.goNextScreen(AddContactPersonActivity.class);
                break;
            }
            case R.id.txt_add_new: {
                Toast.makeText(mActivity, "txt_add_new", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.txt_add_from_introduce: {
                Toast.makeText(mActivity, "txt_add_from_introduce", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

}
