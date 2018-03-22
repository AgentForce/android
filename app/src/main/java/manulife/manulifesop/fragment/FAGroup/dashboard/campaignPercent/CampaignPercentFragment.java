package manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.warkiz.widget.IndicatorSeekBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chick.indicator.CircleIndicatorPager;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectMonth.FAObjectMonthFragment;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectWeek.FAObjectWeekFragment;
import manulife.manulifesop.fragment.FAGroup.dashboard.FADashBoardFragment;
import manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent.onePercent.OnePercentFragment;
import manulife.manulifesop.util.Utils;

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

    private List<Integer> mListPercent;


    //variable for change campaign
    private List<Integer> mListContractWeek;
    private int mTotalContract;
    private int mCurrentWeek;
    private DashboardResult mData;

    IndicatorSeekBar sbStep1, sbStep2, sbStep3, sbStep4;

    TextView txtcontractStep1, txtmeetingStep1, txtadvisoryStep1,
            txtsignStep1, txtintroduceStep1;
    LinearLayout layoutRoot, layoutRoot2, layoutRoot3, layoutRoot4;

    TextView txtcontractStep2, txtmeetingStep2, txtadvisoryStep2,
            txtsignStep2, txtintroduceStep2;

    TextView txtcontractStep3, txtmeetingStep3, txtadvisoryStep3,
            txtsignStep3, txtintroduceStep3;

    TextView txtcontractStep4, txtmeetingStep4, txtadvisoryStep4,
            txtsignStep4, txtintroduceStep4;

    private int mCurrentProcessStep1, mCurrentProcessStep2, mCurrentProcessStep3;

    //variable for edit month
    IndicatorSeekBar sbMonthStep1, sbMonthStep2, sbMonthStep3, sbMonthStep4, sbMonthStep5;
    TextView txtMonthOK;
    List<Integer> mListMonthTarget;

    public static CampaignPercentFragment newInstance(String type, List<Integer> listPercent, DashboardResult dataWeekMonth) {
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putSerializable("dataCampaign", dataWeekMonth);
        args.putSerializable("percent", (Serializable) listPercent);
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
        mActionListener = new CampaignPercentPresent(this,getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPercentFragmentType = getArguments().getString("type", "");
        this.mListPercent = (List<Integer>) getArguments().getSerializable("percent");
        this.mData = (DashboardResult) getArguments().getSerializable("dataCampaign");
        initViewpager();
    }

    private void initViewpager() {
        mListFragment = new ArrayList<>();
        mListFragment.add(OnePercentFragment.newInstance("Liên hệ", mListPercent.get(0), getResources().getColor(R.color.color_dashboard_contact)));
        mListFragment.add(OnePercentFragment.newInstance("Hẹn gặp", mListPercent.get(1), getResources().getColor(R.color.color_dashboard_meeting)));
        mListFragment.add(OnePercentFragment.newInstance("Tư vấn", mListPercent.get(2), getResources().getColor(R.color.color_dashboard_advisory)));
        mListFragment.add(OnePercentFragment.newInstance("Ký hợp đồng", mListPercent.get(3), getResources().getColor(R.color.color_dashboard_sign)));
        mListFragment.add(OnePercentFragment.newInstance("Giới thiệu", mListPercent.get(4), getResources().getColor(R.color.color_dashboard_introduce)));

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

    @Override
    public void updateData() {
        ((FADashBoardFragment)this.getParentFragment()).updateData();
    }

    private void showDialogEditContract() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_contract_week, null);

        //mData.data.currentWeek = 1;
        calculatePerCentContractPerWeek(mData);

        initAllViewsDialog(dialogView);
        reloadViewsWeek1();
        reloadViewsWeek2();
        reloadViewsWeek3();
        reloadViewsWeek4();
        initViewsDialogWeek1(dialogView);
        initViewsDialogWeek2(dialogView);
        initViewsDialogWeek3(dialogView);
        initViewsDialogWeek4(dialogView);

        TextView txtCancel, txtOK;
        txtCancel = dialogView.findViewById(R.id.txt_cancel);
        txtOK = dialogView.findViewById(R.id.txt_ok);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirm("Xác nhận", "Đồng ý chỉnh sửa mục tiêu tuần?", "Đồng ý",
                        "Hủy", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                            @Override
                            public void DiaglogPositive() {
                                alertDialog.dismiss();
                                mActionListener.updateCampaignWeek(Utils.getCurrentMonth(getContext()),
                                        Integer.valueOf(txtsignStep1.getText().toString()),
                                        Integer.valueOf(txtsignStep2.getText().toString()),
                                        Integer.valueOf(txtsignStep3.getText().toString()),
                                        Integer.valueOf(txtsignStep4.getText().toString()));
                            }

                            @Override
                            public void DiaglogNegative() {

                            }
                        });
            }
        });


        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void calculatePerCentContractPerWeek(DashboardResult data) {
        mCurrentWeek = data.data.currentWeek;
        int totalContract = 0;
        mListContractWeek = new ArrayList<>();
        for (int i = 0; i < data.data.campaign.size(); i++) {
            totalContract += data.data.campaign.get(i).targetContractSale;
            mListContractWeek.add(data.data.campaign.get(i).targetContractSale);
        }
        mTotalContract = totalContract;
    }

    private void initAllViewsDialog(View dialogView) {
        layoutRoot = dialogView.findViewById(R.id.layout_root_week1);
        sbStep1 = dialogView.findViewById(R.id.sb_step1);
        txtcontractStep1 = dialogView.findViewById(R.id.txt_step1_contact);
        txtmeetingStep1 = dialogView.findViewById(R.id.txt_step1_meeting);
        txtadvisoryStep1 = dialogView.findViewById(R.id.txt_step1_advisory);
        txtsignStep1 = dialogView.findViewById(R.id.txt_step1_sign);
        txtintroduceStep1 = dialogView.findViewById(R.id.txt_step1_introduce);

        layoutRoot2 = dialogView.findViewById(R.id.layout_root_week2);
        sbStep2 = dialogView.findViewById(R.id.sb_step2);
        txtcontractStep2 = dialogView.findViewById(R.id.txt_step2_contact);
        txtmeetingStep2 = dialogView.findViewById(R.id.txt_step2_meeting);
        txtadvisoryStep2 = dialogView.findViewById(R.id.txt_step2_advisory);
        txtsignStep2 = dialogView.findViewById(R.id.txt_step2_sign);
        txtintroduceStep2 = dialogView.findViewById(R.id.txt_step2_introduce);

        layoutRoot3 = dialogView.findViewById(R.id.layout_root_week3);
        sbStep3 = dialogView.findViewById(R.id.sb_step3);
        txtcontractStep3 = dialogView.findViewById(R.id.txt_step3_contact);
        txtmeetingStep3 = dialogView.findViewById(R.id.txt_step3_meeting);
        txtadvisoryStep3 = dialogView.findViewById(R.id.txt_step3_advisory);
        txtsignStep3 = dialogView.findViewById(R.id.txt_step3_sign);
        txtintroduceStep3 = dialogView.findViewById(R.id.txt_step3_introduce);

        layoutRoot4 = dialogView.findViewById(R.id.layout_root_week4);
        sbStep4 = dialogView.findViewById(R.id.sb_step4);
        txtcontractStep4 = dialogView.findViewById(R.id.txt_step4_contact);
        txtmeetingStep4 = dialogView.findViewById(R.id.txt_step4_meeting);
        txtadvisoryStep4 = dialogView.findViewById(R.id.txt_step4_advisory);
        txtsignStep4 = dialogView.findViewById(R.id.txt_step4_sign);
        txtintroduceStep4 = dialogView.findViewById(R.id.txt_step4_introduce);
    }

    private void reloadViewsWeek1() {
        int newContract = mListContractWeek.get(0);
        int newPercent = Math.round((float) newContract / mTotalContract * 100);
        sbStep1.setProgress(newPercent);

        mCurrentProcessStep1 = newPercent;

        txtcontractStep1.setText(String.valueOf(newContract * 10));
        txtmeetingStep1.setText(String.valueOf(newContract * 5));
        txtadvisoryStep1.setText(String.valueOf(newContract * 3));
        txtsignStep1.setText(String.valueOf(newContract));
        txtintroduceStep1.setText(String.valueOf(newContract * 8));
    }

    private void reloadViewsWeek2() {
        int newContract = mListContractWeek.get(1);
        int newPercent = Math.round((float) newContract / mTotalContract * 100);
        sbStep2.setProgress(newPercent);
        mCurrentProcessStep2 = newPercent;

        txtcontractStep2.setText(String.valueOf(newContract * 10));
        txtmeetingStep2.setText(String.valueOf(newContract * 5));
        txtadvisoryStep2.setText(String.valueOf(newContract * 3));
        txtsignStep2.setText(String.valueOf(newContract));
        txtintroduceStep2.setText(String.valueOf(newContract * 8));
    }

    private void reloadViewsWeek3() {
        int newContract = mListContractWeek.get(2);
        int newPercent = Math.round((float) newContract / mTotalContract * 100);
        sbStep3.setProgress(newPercent);
        mCurrentProcessStep3 = newPercent;

        txtcontractStep3.setText(String.valueOf(newContract * 10));
        txtmeetingStep3.setText(String.valueOf(newContract * 5));
        txtadvisoryStep3.setText(String.valueOf(newContract * 3));
        txtsignStep3.setText(String.valueOf(newContract));
        txtintroduceStep3.setText(String.valueOf(newContract * 8));
    }

    private void reloadViewsWeek4() {
        int newContract = mListContractWeek.get(3);
        int newPercent = Math.round((float) newContract / mTotalContract * 100);
        sbStep4.setProgress(newPercent);

        txtcontractStep4.setText(String.valueOf(newContract * 10));
        txtmeetingStep4.setText(String.valueOf(newContract * 5));
        txtadvisoryStep4.setText(String.valueOf(newContract * 3));
        txtsignStep4.setText(String.valueOf(newContract));
        txtintroduceStep4.setText(String.valueOf(newContract * 8));
    }

    private void initViewsDialogWeek1(View dialogView) {

        //just enable edit current to week 3
        if (mCurrentWeek > 1) {
            sbStep1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            layoutRoot.setAlpha(0.7f);
        }

        sbStep1.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            int newContract;
            int temp;

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if (fromUserTouch) {
                    /*intTemp = Math.round(Integer.valueOf(txtcontractStep1.getText().toString())
                            * progress / (int) txtcontractStep1.getTag());
                    txtcontractStep1.setText(String.valueOf(intTemp));*/
                    newContract = Math.round((float) progress * mTotalContract / 100);
                    temp = mTotalContract - newContract;
                    if (newContract != mListContractWeek.get(0) && temp >= 0) {
                        //10:5:3:1:8
                        txtcontractStep1.setText(String.valueOf(newContract * 10));
                        txtmeetingStep1.setText(String.valueOf(newContract * 5));
                        txtadvisoryStep1.setText(String.valueOf(newContract * 3));
                        txtsignStep1.setText(String.valueOf(newContract));
                        txtintroduceStep1.setText(String.valueOf(newContract * 8));

                        //change percent week 2 - 3 - 4
                        mListContractWeek.set(0, newContract);
                        temp = temp / 3;
                        for (int i = 1; i < mListContractWeek.size(); i++) {
                            mListContractWeek.set(i, temp);
                        }

                        temp = (mTotalContract - newContract) % 3;
                        if (temp == 1) {
                            mListContractWeek.set(3, mListContractWeek.get(3) + 1);
                        } else if (temp == 2) {
                            mListContractWeek.set(2, mListContractWeek.get(2) + 1);
                            mListContractWeek.set(3, mListContractWeek.get(3) + 1);
                        }
                        reloadViewsWeek2();
                        reloadViewsWeek3();
                        reloadViewsWeek4();
                        mCurrentProcessStep1 = progress;
                    }
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
                sbStep1.setProgress(mCurrentProcessStep1);
            }
        });
    }

    private void initViewsDialogWeek2(View dialogView) {

        //just enable edit current to week 3
        if (mCurrentWeek > 2) {
            sbStep2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            layoutRoot2.setAlpha(0.7f);
        }

        sbStep2.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            int newContract;
            int temp;

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if (fromUserTouch) {
                    /*intTemp = Math.round(Integer.valueOf(txtcontractStep1.getText().toString())
                            * progress / (int) txtcontractStep1.getTag());
                    txtcontractStep1.setText(String.valueOf(intTemp));*/
                    newContract = Math.round((float) progress * mTotalContract / 100);
                    temp = (mTotalContract - newContract - mListContractWeek.get(0));
                    if (newContract != mListContractWeek.get(1) && temp >= 0) {
                        //10:5:3:1:8
                        txtcontractStep2.setText(String.valueOf(newContract * 10));
                        txtmeetingStep2.setText(String.valueOf(newContract * 5));
                        txtadvisoryStep2.setText(String.valueOf(newContract * 3));
                        txtsignStep2.setText(String.valueOf(newContract));
                        txtintroduceStep2.setText(String.valueOf(newContract * 8));
                        //change percent week 3 - 4
                        mListContractWeek.set(1, newContract);
                        temp = (mTotalContract - newContract - mListContractWeek.get(0)) / 2;
                        for (int i = 2; i < mListContractWeek.size(); i++) {
                            mListContractWeek.set(i, temp);
                        }
                        temp = (mTotalContract - newContract - mListContractWeek.get(0)) % 2;
                        if (temp == 1) {
                            mListContractWeek.set(3, mListContractWeek.get(3) + 1);
                        }
                        reloadViewsWeek3();
                        reloadViewsWeek4();
                        mCurrentProcessStep2 = progress;
                    }
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
                sbStep2.setProgress(mCurrentProcessStep2);
            }
        });
    }

    private void initViewsDialogWeek3(View dialogView) {

        //just enable edit current to week 3
        if (mCurrentWeek > 3) {
            sbStep3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            layoutRoot3.setAlpha(0.7f);
        }

        sbStep3.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            int newContract;
            int temp;

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if (fromUserTouch) {
                    /*intTemp = Math.round(Integer.valueOf(txtcontractStep1.getText().toString())
                            * progress / (int) txtcontractStep1.getTag());
                    txtcontractStep1.setText(String.valueOf(intTemp));*/
                    newContract = Math.round((float) progress * mTotalContract / 100);
                    temp = mTotalContract - newContract - mListContractWeek.get(0) - mListContractWeek.get(1);
                    if (newContract != mListContractWeek.get(2) && temp >= 0) {
                        //10:5:3:1:8
                        txtcontractStep3.setText(String.valueOf(newContract * 10));
                        txtmeetingStep3.setText(String.valueOf(newContract * 5));
                        txtadvisoryStep3.setText(String.valueOf(newContract * 3));
                        txtsignStep3.setText(String.valueOf(newContract));
                        txtintroduceStep3.setText(String.valueOf(newContract * 8));
                        //change percent week 4
                        mListContractWeek.set(2, newContract);
                        mListContractWeek.set(3, temp);
                        reloadViewsWeek4();
                        mCurrentProcessStep3 = progress;
                    }
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
                sbStep3.setProgress(mCurrentProcessStep3);
            }
        });
    }

    private void initViewsDialogWeek4(View dialogView) {

        //just enable edit current to week 3
        sbStep4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        layoutRoot4.setAlpha(0.7f);
    }

    //editMonth
    private void showDialogEditObjectMonth() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_object_month, null);

        initAllViewsMonth(dialogView);
        initViewMonthStep1(dialogView);
        initViewMonthStep2(dialogView);
        initViewMonthStep3(dialogView);
        initViewMonthStep4(dialogView);
        initViewMonthStep5(dialogView);

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void initAllViewsMonth(View view) {
        sbMonthStep1 = view.findViewById(R.id.sb_contract);
        sbMonthStep2 = view.findViewById(R.id.sb_meeting);
        sbMonthStep3 = view.findViewById(R.id.sb_advisory);
        sbMonthStep4 = view.findViewById(R.id.sb_sign);
        sbMonthStep5 = view.findViewById(R.id.sb_introduce);

        txtMonthOK = view.findViewById(R.id.txt_ok);
        txtMonthOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Gọi api update", Toast.LENGTH_SHORT).show();
            }
        });

        mListMonthTarget = new ArrayList<>();
        int targetstep1 = 0, targetstep2 = 0, targetstep3 = 0, targetstep4 = 0, targetstep5 = 0;
        for (int i = 0; i < mData.data.campaign.size(); i++) {
            targetstep1 += mData.data.campaign.get(i).targetCallSale;
            targetstep2 += mData.data.campaign.get(i).targetMetting;
            targetstep3 += mData.data.campaign.get(i).targetPresentation;
            targetstep4 += mData.data.campaign.get(i).targetContractSale;
            targetstep5 += mData.data.campaign.get(i).targetReLead;
        }
        mListMonthTarget.add(targetstep1);
        mListMonthTarget.add(targetstep2);
        mListMonthTarget.add(targetstep3);
        mListMonthTarget.add(targetstep4);
        mListMonthTarget.add(targetstep5);

    }

    private void initViewMonthStep1(View view) {
        sbMonthStep1.setMin(mListMonthTarget.get(0));
        sbMonthStep1.setMax(mListMonthTarget.get(0) + 100);
        sbMonthStep1.setProgress(mListMonthTarget.get(0));

        sbMonthStep1.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            int currentProcess = sbMonthStep1.getProgress();

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if (fromUserTouch) {
                    currentProcess = progress;
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
                if (currentProcess % 10 > 0) {
                    if (currentProcess % 10 > 5) {
                        sbMonthStep1.setProgress(((currentProcess / 10) * 10) + 10);
                    } else {
                        sbMonthStep1.setProgress(((currentProcess / 10) * 10));
                    }
                }
                reloadAllStepMonth(sbMonthStep1.getProgress() / 10,1);
            }
        });
        sbMonthStep1.setMin(mListMonthTarget.get(0));
    }

    private void initViewMonthStep2(View view) {
        sbMonthStep2.setMin(mListMonthTarget.get(1));
        sbMonthStep2.setMax(mListMonthTarget.get(1) + 50);
        sbMonthStep2.setProgress(mListMonthTarget.get(1));

        sbMonthStep2.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            int currentProcess = sbMonthStep2.getProgress();

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if (fromUserTouch) {
                    currentProcess = progress;
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
                if (currentProcess % 5 > 0) {
                    if (currentProcess % 5 >= 3) {
                        sbMonthStep2.setProgress(((currentProcess / 5) * 5) + 5);
                    } else {
                        sbMonthStep2.setProgress(((currentProcess / 5) * 5));
                    }
                }
                reloadAllStepMonth(sbMonthStep2.getProgress() / 5,2);
            }
        });
    }

    private void initViewMonthStep3(View view) {
        sbMonthStep3.setMin(mListMonthTarget.get(2));
        sbMonthStep3.setMax(mListMonthTarget.get(2) + 30);
        sbMonthStep3.setProgress(mListMonthTarget.get(2));

        sbMonthStep3.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            int currentProcess = sbMonthStep3.getProgress();

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if (fromUserTouch) {
                    currentProcess = progress;
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
                if (currentProcess % 3 > 0) {
                    if (currentProcess % 3 >= 2) {
                        sbMonthStep3.setProgress(((currentProcess / 3) * 3) + 3);
                    } else {
                        sbMonthStep3.setProgress(((currentProcess / 3) * 3));
                    }
                }
                reloadAllStepMonth(sbMonthStep3.getProgress() / 3,3);
            }
        });
    }

    private void initViewMonthStep4(View view) {
        sbMonthStep4.setMin(mListMonthTarget.get(3));
        sbMonthStep4.setMax(mListMonthTarget.get(3) + 10);
        sbMonthStep4.setProgress(mListMonthTarget.get(3));

        sbMonthStep4.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            int currentProcess = sbMonthStep4.getProgress();

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if (fromUserTouch) {
                    currentProcess = progress;
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

                reloadAllStepMonth(sbMonthStep4.getProgress(),4);
            }
        });
    }

    private void initViewMonthStep5(View view) {
        sbMonthStep5.setMin(mListMonthTarget.get(4));
        sbMonthStep5.setMax(mListMonthTarget.get(4) + 80);
        sbMonthStep5.setProgress(mListMonthTarget.get(4));

        sbMonthStep5.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            int currentProcess = sbMonthStep3.getProgress();

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if (fromUserTouch) {
                    currentProcess = progress;
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
                if (currentProcess % 8 > 0) {
                    if (currentProcess % 8 > 4) {
                        sbMonthStep5.setProgress(((currentProcess / 8) * 8) + 8);
                    } else {
                        sbMonthStep5.setProgress(((currentProcess / 8) * 8));
                    }
                }
                reloadAllStepMonth(sbMonthStep5.getProgress() / 8,5);
            }
        });
    }

    private void reloadAllStepMonth(int numContract, int fromStep) {

        sbMonthStep1.setProgress(numContract * 10);
        sbMonthStep2.setProgress(numContract * 5);
        sbMonthStep3.setProgress(numContract * 3);
        sbMonthStep4.setProgress(numContract);
        sbMonthStep5.setProgress(numContract * 8);

        if (sbMonthStep5.getProgress() == sbMonthStep5.getMax()) {
            float currentMax = sbMonthStep5.getMax();
            sbMonthStep5.setMax(currentMax + 80);
            sbMonthStep5.setMin(currentMax);
            sbMonthStep5.setProgress(currentMax);
        } else if (sbMonthStep5.getProgress() == sbMonthStep5.getMin() && sbMonthStep5.getProgress() > mListMonthTarget.get(4)) {
            float currentMin = sbMonthStep5.getMin();
            sbMonthStep5.setMax(currentMin);
            sbMonthStep5.setMin(currentMin - 80);
            sbMonthStep5.setProgress(currentMin);
        }

        if (sbMonthStep4.getProgress() == sbMonthStep4.getMax()) {
            float currentMax = sbMonthStep4.getMax();
            sbMonthStep4.setMax(currentMax + 10);
            sbMonthStep4.setMin(currentMax);
            sbMonthStep4.setProgress(currentMax);
        } else if (sbMonthStep4.getProgress() == sbMonthStep4.getMin() && sbMonthStep4.getProgress() > mListMonthTarget.get(3)) {
            float currentMin = sbMonthStep4.getMin();
            sbMonthStep4.setMax(currentMin);
            sbMonthStep4.setMin(currentMin - 10);
            sbMonthStep4.setProgress(currentMin);
        }

        if (sbMonthStep3.getProgress() == sbMonthStep3.getMax()) {
            float currentMax = sbMonthStep3.getMax();
            sbMonthStep3.setMax(currentMax + 30);
            sbMonthStep3.setMin(currentMax);
            sbMonthStep3.setProgress(currentMax);
        } else if (sbMonthStep3.getProgress() == sbMonthStep3.getMin() && sbMonthStep3.getProgress() > mListMonthTarget.get(2)) {
            float currentMin = sbMonthStep3.getMin();
            sbMonthStep3.setMax(currentMin);
            sbMonthStep3.setMin(currentMin - 30);
            sbMonthStep3.setProgress(currentMin);
        }

        if (sbMonthStep2.getProgress() == sbMonthStep2.getMax()) {
            float currentMax = sbMonthStep2.getMax();
            sbMonthStep2.setMax(currentMax + 50);
            sbMonthStep2.setMin(currentMax);
            sbMonthStep2.setProgress(currentMax);
        } else if (sbMonthStep2.getProgress() == sbMonthStep2.getMin() && sbMonthStep2.getProgress() > mListMonthTarget.get(1)) {
            float currentMin = sbMonthStep2.getMin();
            sbMonthStep2.setMax(currentMin);
            sbMonthStep2.setMin(currentMin - 50);
            sbMonthStep2.setProgress(currentMin);
        }

        if (sbMonthStep1.getProgress() == sbMonthStep1.getMax()) {
            float currentMax = sbMonthStep1.getMax();
            sbMonthStep1.setMax(currentMax + 100);
            sbMonthStep1.setMin(currentMax);
            sbMonthStep1.setProgress(currentMax);
        } else if (sbMonthStep1.getProgress() == sbMonthStep1.getMin() && sbMonthStep1.getProgress() > mListMonthTarget.get(0)) {
            float currentMin = sbMonthStep1.getMin();
            sbMonthStep1.setMax(currentMin);
            sbMonthStep1.setMin(currentMin - 100);
            sbMonthStep1.setProgress(currentMin);
        }
    }
}
