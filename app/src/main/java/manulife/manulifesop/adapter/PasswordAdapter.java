package manulife.manulifesop.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import manulife.manulifesop.R;

/**
 * Created by PhamTruong on 01/06/2017.
 */

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.ViewHolder> {
    //Declares variables
    private Context context;
    private List<Boolean> listObject = null;

    //Constructor
    public PasswordAdapter(Context context, List<Boolean> arr) {
        this.context = context;
        this.listObject = arr;
    }

    /**
     * Create view holder for recycle view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.item_password, null, false);
        ViewHolder viewHolder = new ViewHolder(cardView);
        return viewHolder;
    }

    /**
     * Set data for recycle view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final boolean object = listObject.get(position);
        if(object)
        {
            holder.imgPassword.setAlpha(1f);
        }else
        {
            holder.imgPassword.setAlpha(0.5f);
        }
    }

    /**
     * Get number of item in List
     * @return
     */
    @Override
    public int getItemCount() {
        return listObject.size();
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
        @BindView(R.id.img_password)
        RoundedImageView imgPassword;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
