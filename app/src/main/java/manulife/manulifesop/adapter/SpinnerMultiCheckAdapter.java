package manulife.manulifesop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import manulife.manulifesop.R;
import manulife.manulifesop.adapter.ObjectData.SpinnerMultiObject;

public class SpinnerMultiCheckAdapter extends ArrayAdapter<SpinnerMultiObject> {
	private Context context;
	private ArrayList<SpinnerMultiObject> listData;

	public SpinnerMultiCheckAdapter(Context context, int resource, ArrayList<SpinnerMultiObject> objects) {
		super(context, resource, objects);
		this.context = context;
		listData = objects;
	}

	public void setSelect(int pos)
	{
		listData.get(pos).setChecked(!listData.get(pos).isChecked());
		for(int i=1;i<listData.size();i++){
			if(listData.get(i).isChecked()){
				listData.get(0).setChecked(false);
				break;
			}
		}
	}

	public void setSelectOne(int pos){
		for(int i=0;i<listData.size();i++){
			if(i != pos){
				listData.get(i).setChecked(false);
			}else{
				if(!listData.get(i).isChecked()){
					listData.get(i).setChecked(true);
				}
			}
		}
	}

	@Override
	public int getCount() {
		return super.getCount();
	}

	@Override
	public SpinnerMultiObject getItem(int position) {
		if (listData != null) {
			return listData.get(position);
		}
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_one_multi_check, parent, false);

			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}

		final SpinnerMultiObject Obj = getItem(position);
		if (Obj != null) {
			holder.tvFileName = (TextView) convertView.findViewById(R.id.tvContent);
			holder.tvFileName.setText(Obj.getValue());
			CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chbContent);
			checkBox.setChecked(Obj.isChecked());
		}
		return convertView;
	}

	private final class ViewHolder{

		private TextView tvFileName;
		private CheckBox checkBox;

		private ViewHolder(View parent){
			tvFileName = (TextView) parent.findViewById(R.id.tvContent);
			checkBox = (CheckBox) parent.findViewById(R.id.chbContent);
		}

	}

}