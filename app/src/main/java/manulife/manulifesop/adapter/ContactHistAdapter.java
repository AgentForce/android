package manulife.manulifesop.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import manulife.manulifesop.R;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;

/**
 * Created by PhamTruong on 01/06/2017.
 */

public class ContactHistAdapter extends RecyclerView.Adapter<ContactHistAdapter.ViewHolder> {
    //Declares variables
    private Context mContext;
    private List<ActiveHistFA> mListObject = null;
    private FragmentManager fm;

    //Constructor
    public ContactHistAdapter(Context context, List<ActiveHistFA> arr) {
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
        View cardView = inflater.inflate(R.layout.item_contact_hist, null, false);
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
        final ActiveHistFA object = mListObject.get(position);
        holder.txtTitle.setText(object.getTitle());
        holder.txtContent.setText(object.getContent());
        if(position == 0){
            holder.lineTop.setVisibility(View.GONE);
        }else{
            holder.lineTop.setVisibility(View.VISIBLE);
        }
        if(position == (mListObject.size() -1)){
            holder.lineBot.setVisibility(View.GONE);
        }else{
            holder.lineBot.setVisibility(View.VISIBLE);
        }
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
        @BindView(R.id.lineTop)
        View lineTop;
        @BindView(R.id.lineBot)
        View lineBot;
        @BindView(R.id.txt_tile)
        TextView txtTitle;
        @BindView(R.id.txt_content)
        TextView txtContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
