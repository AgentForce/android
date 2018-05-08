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
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.introduceContact.IntroduceContactActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.introduceRecruitment.IntroduceRecruitmentActivity;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.adapter.ObjectData.NotifyData;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;

/**
 * Created by PhamTruong on 01/06/2017.
 */

public class NofifyAdapter extends RecyclerView.Adapter<NofifyAdapter.ViewHolder> {
    //Declares variables
    private Context mContext;
    private List<NotifyData> mListObject = null;
    private CallBackClickContact mCallback;

    //Constructor
    public NofifyAdapter(Context context, List<NotifyData> arr, CallBackClickContact callBackClickContact) {
        this.mContext = context;
        this.mListObject = arr;
        this.mCallback = callBackClickContact;
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
        View cardView = inflater.inflate(R.layout.item_notify, parent, false);
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
        final NotifyData object = mListObject.get(position);

        holder.txtDate.setText(object.getTime());
        holder.txtContent.setText(object.getContent());
        holder.txtTitle.setText(object.getTitle());
        if(object.isRead())
            holder.imgRead.setVisibility(View.GONE);
        else
            holder.imgRead.setVisibility(View.VISIBLE);
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
        @BindView(R.id.img_read)
        RoundedImageView imgRead;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_content)
        TextView txtContent;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.layout_root)
        LinearLayout layoutRoot;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
