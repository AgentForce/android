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
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;

/**
 * Created by PhamTruong on 01/06/2017.
 */

public class ActiveHistAdapter extends RecyclerView.Adapter<ActiveHistAdapter.ViewHolder> {
    //Declares variables
    private Context mContext;
    private List<ActiveHistFA> mListObject = null;
    private FragmentManager fm;
    private CallBackClickContact mCallback;

    //Constructor
    public ActiveHistAdapter(Context context, List<ActiveHistFA> arr, CallBackClickContact callBackClickContact) {
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
        View cardView = inflater.inflate(R.layout.item_active_hist, null, false);
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
        if (object.getTitle() != null && object.getTitle().length() > 1) {
            holder.txtAvatarTitle.setText(
                    object.getTitle().substring(0,1)
            );
        }
        //visible menu right for introduce contact
        if(mContext instanceof IntroduceContactActivity){
            holder.layoutMenuRightClick.setVisibility(View.GONE);
            holder.layoutMenuRightView.setAlpha(0);
        }else{
            holder.layoutMenuRightClick.setVisibility(View.VISIBLE);
            holder.layoutMenuRightView.setAlpha(1f);
        }

        holder.txtTitle.setText(object.getTitle());
        holder.txtContent.setText(object.getContent());
        holder.userAvatar.setBorderColor(mContext.getResources().getColor(R.color.colorSecond));
        holder.layoutMenuRightClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.layoutMenuRightClick);
                popup.inflate(R.menu.option_menu_active_hist);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_detail:
                                mCallback.onClickMenuRight(position,0);
                                break;
                            case R.id.menu_call:
                                mCallback.onClickMenuRight(position,1);
                                break;
                            case R.id.menu_create_event:
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
        TextView txtAvatarTitle;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_content)
        TextView txtContent;
        @BindView(R.id.layout_menu_right)
        View layoutMenuRightClick;
        @BindView(R.id.menu_right)
        LinearLayout layoutMenuRightView;
        @BindView(R.id.layout_root)
        LinearLayout layoutRoot;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
