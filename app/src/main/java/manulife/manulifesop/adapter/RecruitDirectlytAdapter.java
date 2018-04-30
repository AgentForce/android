package manulife.manulifesop.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.introduceContact.IntroduceContactActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.introduceRecruitment.IntroduceRecruitmentActivity;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.adapter.ObjectData.RecruitmentDirectlyData;
import manulife.manulifesop.api.ObjectResponse.RecruitmentDirectly;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;

/**
 * Created by PhamTruong on 01/06/2017.
 */

public class RecruitDirectlytAdapter extends RecyclerView.Adapter<RecruitDirectlytAdapter.ViewHolder> {
    //Declares variables
    private Context mContext;
    private List<RecruitmentDirectlyData> mListObject = null;
    private FragmentManager fm;
    private CallBackClickContact mCallback;

    private boolean mIsRecruitment;

    //Constructor
    public RecruitDirectlytAdapter(Context context, List<RecruitmentDirectlyData> arr, boolean isRecruitment, CallBackClickContact callBackClickContact) {
        this.mContext = context;
        this.mListObject = arr;
        this.mCallback = callBackClickContact;

        this.mIsRecruitment = isRecruitment;
    }

    /**
     * Create view holder for recycle view
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.item_sale_directly, null, false);
        if(mIsRecruitment)
            cardView = inflater.inflate(R.layout.item_recruit_directly, null, false);
        fm = ((AppCompatActivity) mContext).getSupportFragmentManager();
        ViewHolder viewHolder = new ViewHolder(cardView);
        return viewHolder;
    }

    /**
     * Set data for recycle view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RecruitmentDirectlyData object = mListObject.get(position);
        if(object.getFullName() != null && object.getFullName().length() > 0)
        {
            holder.txtAvatarTitle.setText(object.getFullName().substring(0,1));
        }
        holder.txtName.setText(object.getFullName());
        holder.txtStep1.setText(String.valueOf(object.getCurrentStep1()));
        holder.txtStep2.setText(String.valueOf(object.getCurrentStep2()));
        holder.txtStep3.setText(String.valueOf(object.getCurrentStep3()));
        holder.txtStep4.setText(String.valueOf(object.getCurrentStep4()));
        holder.txtStep5.setText(String.valueOf(object.getCurrentStep5()));

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onClickMainContent(position);
            }
        });
    }

    /**
     * Get number of item in List
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mListObject.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Create widgets
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        //Declares variables
        @BindView(R.id.txt_avatar)
        TextView txtAvatarTitle;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_step1)
        TextView txtStep1;
        @BindView(R.id.txt_step2)
        TextView txtStep2;
        @BindView(R.id.txt_step3)
        TextView txtStep3;
        @BindView(R.id.txt_step4)
        TextView txtStep4;
        @BindView(R.id.txt_step5)
        TextView txtStep5;
        @BindView(R.id.layout_root)
        RelativeLayout layoutRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
