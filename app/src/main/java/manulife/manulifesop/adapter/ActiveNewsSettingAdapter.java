package manulife.manulifesop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import manulife.manulifesop.R;
import manulife.manulifesop.adapter.ObjectData.ActiveHistSetting;
import manulife.manulifesop.adapter.ObjectData.ActiveNewsSetting;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;

/**
 * Created by PhamTruong on 01/06/2017.
 */

public class ActiveNewsSettingAdapter extends RecyclerView.Adapter<ActiveNewsSettingAdapter.ViewHolder> {
    //Declares variables
    private Context mContext;
    private List<ActiveNewsSetting> mListObject = null;

    //Constructor
    public ActiveNewsSettingAdapter(Context context, List<ActiveNewsSetting> arr) {
        this.mContext = context;
        this.mListObject = arr;
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
        View cardView = inflater.inflate(R.layout.item_activity_news_setting, null, false);
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
        final ActiveNewsSetting object = mListObject.get(position);
        holder.txtTitle.setText(object.getTitle());
        holder.txtName.setText(object.getName());
        holder.txtTime.setText(object.getTime());
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
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.txt_name)
        TextView txtName;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
