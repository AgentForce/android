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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import manulife.manulifesop.R;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;

/**
 * Created by PhamTruong on 01/06/2017.
 */

public class ActiveHistAdapter extends RecyclerView.Adapter<ActiveHistAdapter.ViewHolder> {
    //Declares variables
    private Context mContext;
    private List<ActiveHistFA> mListObject = null;
    private FragmentManager fm;

    //Constructor
    public ActiveHistAdapter(Context context, List<ActiveHistFA> arr) {
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
        if (object.getAvatar() != null && object.getAvatar().length() > 1) {

        }
        holder.txtTitle.setText(object.getTitle());
        holder.txtContent.setText(object.getContent());
        holder.userAvatar.setBorderColor(mContext.getResources().getColor(R.color.colorSecond));
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.menuRight);
                popup.inflate(R.menu.option_menu_active_hist);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                //handle menu1 click
                                Toast.makeText(mContext, "menu 1", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu2:
                                //handle menu2 click
                                Toast.makeText(mContext, "menu 2", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu3:
                                Toast.makeText(mContext, "menu 3", Toast.LENGTH_SHORT).show();
                                //handle menu3 click
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
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
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_content)
        TextView txtContent;
        @BindView(R.id.menu_right)
        ImageButton menuRight;
        @BindView(R.id.layout_root)
        RelativeLayout layoutRoot;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}