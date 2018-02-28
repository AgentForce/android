package manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.warkiz.widget.IndicatorSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chick.indicator.CircleIndicatorPager;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent.onePercent.OnePercentFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class CampaignPercentFragment extends BaseFragment<MainFAActivity, CampaignPercentPresent> implements CampaignPercentContract.View {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.circle_indicator_pager)
    CircleIndicatorPager indicator;

    private String mPercentFragmentType;

    private AlertDialog alertDialog;

    private CustomViewPagerAdapter mAdapter;
    private List<BaseFragment> mListFragment;

    public static CampaignPercentFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        CampaignPercentFragment fragment = new CampaignPercentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_dashboard_fa_percent_campaign;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new CampaignPercentPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPercentFragmentType = getArguments().getString("type", "");
        initViewpager();
    }

    private void initViewpager() {
        mListFragment = new ArrayList<>();
        mListFragment.add(OnePercentFragment.newInstance("Liên hệ", 60, getResources().getColor(R.color.color_dashboard_contact)));
        mListFragment.add(OnePercentFragment.newInstance("Hẹn gặp", 50, getResources().getColor(R.color.color_dashboard_meeting)));
        mListFragment.add(OnePercentFragment.newInstance("Tư vấn", 40, getResources().getColor(R.color.color_dashboard_advisory)));
        mListFragment.add(OnePercentFragment.newInstance("Ký hợp đồng", 30, getResources().getColor(R.color.color_dashboard_sign)));
        mListFragment.add(OnePercentFragment.newInstance("Giới thiệu", 20, getResources().getColor(R.color.color_dashboard_introduce)));

        //create list color indicator
        List<Integer> listBackground = new ArrayList<>();
        listBackground.add(R.drawable.step1_radius);
        listBackground.add(R.drawable.step2_radius);
        listBackground.add(R.drawable.step3_radius);
        listBackground.add(R.drawable.step4_radius);
        listBackground.add(R.drawable.step5_radius);

        mAdapter = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment);

        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
        }
        if (indicator != null) {
            indicator.setViewPager(viewPager, listBackground);
        }
    }

    public void editNumberContract() {
        if (mPercentFragmentType.equals("week")) {
            showDialogEditContract();
        } else if (mPercentFragmentType.equals("month")) {
            showDialogEditObjectMonth();
        } else {
            Toast.makeText(mActivity, "year", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogEditContract() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_contract_week, null);

        initViewsDialogWeek(dialogView);

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void initViewsDialogWeek(View dialogView) {

        IndicatorSeekBar sbStep1;
        TextView txtcontractStep1, txtmeetingStep1, txtadvisoryStep1,
                txtsignStep1, txtintroduceStep1;
        TextView txtCancel, txtOK;

        sbStep1 = dialogView.findViewById(R.id.sb_step1);

        txtcontractStep1 = dialogView.findViewById(R.id.txt_step1_contact);
        txtmeetingStep1 = dialogView.findViewById(R.id.txt_step1_meeting);
        txtadvisoryStep1 = dialogView.findViewById(R.id.txt_step1_advisory);
        txtsignStep1 = dialogView.findViewById(R.id.txt_step1_sign);
        txtintroduceStep1 = dialogView.findViewById(R.id.txt_step1_introduce);

        txtCancel = dialogView.findViewById(R.id.txt_cancel);
        txtOK = dialogView.findViewById(R.id.txt_ok);

        txtcontractStep1.setTag(30);

        sbStep1.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            int intTemp;
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if(fromUserTouch)
                {
                    intTemp = Math.round(Integer.valueOf(txtcontractStep1.getText().toString())
                    * progress / (int)txtcontractStep1.getTag());
                    txtcontractStep1.setText(String.valueOf(intTemp));
                    //intTemp = Math.round((float)(Integer.valueOf(txtcontractStep1.getText().toString())/progress)/100);
                }
            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "Đồng ý", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogEditObjectMonth() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_object_month, null);

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
}
