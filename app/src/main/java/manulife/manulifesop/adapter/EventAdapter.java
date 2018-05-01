package manulife.manulifesop.adapter;

import android.content.Context;
import android.graphics.Color;
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
import manulife.manulifesop.adapter.ObjectData.EventData;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    //Declares variables
    private Context mContext;
    private List<EventData> mListObject = null;
    private FragmentManager fm;
    private CallBackClickContact mCallback;

    //Constructor
    public EventAdapter(Context context, List<EventData> arr, CallBackClickContact callBackClickContact) {
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
        View cardView = inflater.inflate(R.layout.item_event, null, false);
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
        final EventData object = mListObject.get(position);

        if(object.isStatus()){
            holder.userAvatar.setBorderColor(Color.parseColor("#099D53"));
        }else{
            holder.userAvatar.setBorderColor(Color.parseColor("#F63D2B"));
        }

        holder.txtAvatar.setText(object.getName().substring(0,1));
        holder.txtName.setText(object.getName());
        holder.txtTitleType.setText(object.getTypeEvent());
        holder.txtDateTime.setText(object.getDateTime());

        holder.layoutMenuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.layoutMenuRight);
                popup.inflate(R.menu.option_menu_event);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_detail:
                                mCallback.onClickMenuRight(position,0);
                                break;
                            case R.id.menu_edit:
                                mCallback.onClickMenuRight(position,1);
                                break;
                            case R.id.menu_done:
                                mCallback.onClickMenuRight(position,2);
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, mListObject.get(position).getTitle(), Toast.LENGTH_SHORT).show();
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
        @BindView(R.id.img_user_avatar)
        RoundedImageView userAvatar;
        @BindView(R.id.txt_avatar)
        TextView txtAvatar;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_title_tyle)
        TextView txtTitleType;
        @BindView(R.id.txt_date_time)
        TextView txtDateTime;
        /*@BindView(R.id.menu_right)
        LinearLayout menuRight;*/
        @BindView(R.id.layout_menu_right)
        View layoutMenuRight;
        @BindView(R.id.layout_root)
        LinearLayout layoutRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
