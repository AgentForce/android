package manulife.manulifesop.adapter;

import android.content.Context;
import android.graphics.Color;
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
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import cn.refactor.library.SmoothCheckBox;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.addContact.AddContactPersonActivity;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;

/**
 * Created by ADMIN on 3/1/2018.
 */

public class ContactPersonAdapter extends RecyclerView.Adapter<ContactPersonAdapter.ViewHolder>
        implements
        StickyHeaderAdapter<ContactPersonAdapter.HeaderHolder> {

    private LayoutInflater inflater;
    private List<ContactPerson> mData;
    private Context mContext;

    public ContactPersonAdapter(Context context, List<ContactPerson> mData) {
        this.mData = mData;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<ContactPerson> mData){
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_contact_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtAvatar.setText(mData.get(position).getName().substring(0,1));
        if(mData.get(position).isAdded()){
            holder.userAvatar.setBorderColor(Color.parseColor("#F63D2B"));
        }else{
            holder.userAvatar.setBorderColor(Color.parseColor("#099D53"));
        }


        holder.smoothCheckBox.setClickable(false);
        holder.smoothCheckBox.setChecked(mData.get(position).isChecked());
        holder.txtName.setText(mData.get(position).getName());
        holder.txtPhone.setText(mData.get(position).getPhone());
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = !holder.smoothCheckBox.isChecked();
                if(checked == false){
                    holder.smoothCheckBox.setChecked(checked, true);
                    ((AddContactPersonActivity)mContext).updateContactChoosed(mData.get(position),false);
                    ((AddContactPersonActivity)mContext).setCheckStusList(position,false);
                }else
                {
                    //check checked list is lager than 10
                    if(((AddContactPersonActivity)mContext).getSizeChoosed() < 10){
                        holder.smoothCheckBox.setChecked(checked, true);
                        ((AddContactPersonActivity)mContext).updateContactChoosed(mData.get(position),true);
                        ((AddContactPersonActivity)mContext).setCheckStusList(position,true);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getHeaderId(int position) {
        return mData.get(position).getHeaderGroup();
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = inflater.inflate(R.layout.item_contact_person_header, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        viewholder.txtHeader.setText(mData.get(position).getName().substring(0, 1));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.smoothCheckBox)
        SmoothCheckBox smoothCheckBox;
        @BindView(R.id.img_user_avatar)
        RoundedImageView userAvatar;
        @BindView(R.id.txt_avatar)
        TextView txtAvatar;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_phone)
        TextView txtPhone;
        @BindView(R.id.layout_root)
        LinearLayout layoutRoot;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_header)
        TextView txtHeader;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
