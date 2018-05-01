package manulife.manulifesop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import manulife.manulifesop.R;
import manulife.manulifesop.adapter.ObjectData.UMStep6;

/**
 * Created by ADMIN on 3/1/2018.
 */

public class CreatePlanUMStep6Adapter extends RecyclerView.Adapter<CreatePlanUMStep6Adapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<UMStep6> mData;
    private Context mContext;

    public CreatePlanUMStep6Adapter(Context context, List<UMStep6> mData) {
        this.mData = mData;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_create_plan_um_step6, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtMonth.setText("Tháng " + mData.get(position).getMonth());
        holder.txtStartAgent.setText(String.valueOf(mData.get(position).getAgentStart()));
        holder.txtAgentAdd.setText(String.valueOf(mData.get(position).getAgentAdd()));
        holder.txtAgentDecrease.setText(String.valueOf(mData.get(position).getAgentDecrease()));

        if (mData.get(position).isCollapse())
            holder.layoutExpand.collapse();
        else
            holder.layoutExpand.expand();

        //show hide layout
        holder.layoutTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCollapse = !mData.get(position).isCollapse();
                mData.get(position).setCollapse(isCollapse);
                if (isCollapse)
                    holder.layoutExpand.collapse(true);
                else
                    holder.layoutExpand.expand(true);
            }
        });

        //add and sub employee
        holder.layoutAgentAdd_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = mData.get(position).getAgentAdd() + 1;
                holder.txtAgentAdd.setText(String.valueOf(number));
                mData.get(position).setAgentAdd(number);
                reloadData(position);
            }
        });
        holder.layoutAgentAdd_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = mData.get(position).getAgentAdd() - 1;
                int numStartNext = mData.get(position).getAgentStart()
                        + number - mData.get(position).getAgentDecrease();
                if (number >= 0 && numStartNext >=0) {
                    holder.txtAgentAdd.setText(String.valueOf(number));
                    mData.get(position).setAgentAdd(number);
                    reloadData(position);
                }
            }
        });

        //add and sub the decrease agent
        holder.layoutAgentDecrease_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = mData.get(position).getAgentDecrease() + 1;
                int numStartNext = mData.get(position).getAgentStart()
                        + mData.get(position).getAgentAdd() - number;
                if(numStartNext >=0) {
                    holder.txtAgentDecrease.setText(String.valueOf(number));
                    mData.get(position).setAgentDecrease(number);
                    reloadData(position);
                }
            }
        });
        holder.layoutAgentDecrease_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = mData.get(position).getAgentDecrease() - 1;
                if (number >= 0) {
                    holder.txtAgentDecrease.setText(String.valueOf(number));
                    mData.get(position).setAgentDecrease(number);
                    reloadData(position);
                }
            }
        });
    }

    private void reloadData(int currentPosition) {

        for (int i = currentPosition + 1; i < mData.size(); i++) {
            int newAgent = mData.get(i-1).getAgentStart() +
                    mData.get(i-1).getAgentAdd()
                    - mData.get(i-1).getAgentDecrease();
            mData.get(i).setAgentStart(newAgent);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_month)
        TextView txtMonth;
        @BindView(R.id.txt_agent_number)
        TextView txtStartAgent;

        @BindView(R.id.txt_add_num)
        TextView txtAgentAdd;

        @BindView(R.id.txt_agent_num_decrease)
        TextView txtAgentDecrease;

        @BindView(R.id.layout_add_employee)
        LinearLayout layoutAgentAdd_Add;
        @BindView(R.id.layout_sub_employee)
        LinearLayout layoutAgentAdd_sub;

        @BindView(R.id.layout_add_agent)
        LinearLayout layoutAgentDecrease_add;
        @BindView(R.id.layout_sub_agent)
        LinearLayout layoutAgentDecrease_sub;

        @BindView(R.id.layout_top)
        LinearLayout layoutTop;
        @BindView(R.id.layout_expand)
        ExpandableLayout layoutExpand;

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
