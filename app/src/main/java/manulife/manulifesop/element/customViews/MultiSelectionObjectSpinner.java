package manulife.manulifesop.element.customViews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import manulife.manulifesop.R;
import manulife.manulifesop.adapter.ObjectData.SpinnerMultiObject;
import manulife.manulifesop.adapter.ObjectData.SpinnerObject;
import manulife.manulifesop.adapter.SpinnerMultiCheckAdapter;

public class MultiSelectionObjectSpinner extends android.support.v7.widget.AppCompatSpinner implements
        OnMultiChoiceClickListener {

    String titleDialog = "";
    String[] _items = null;
    boolean[] mSelection = null;
    boolean[] mSelectionOld = null;
    Context context;
    ArrayAdapter<SpinnerObject> simple_adapter;

    List<SpinnerObject> listItems = new ArrayList<SpinnerObject>();
    private String strKeyChoice = "";

    //new code
    EditText txtSearch;
    ListView listview;
    SpinnerMultiCheckAdapter adapter;
    ArrayList<SpinnerMultiObject> listData;

    AlertDialog d;
    AlertDialog.Builder builder;

    ProgressBar progressBar;

    private boolean isJustChooseOne = false;

    public void SetTitleDialog(String title) {
        this.titleDialog = title;
    }

    public MultiSelectionObjectSpinner(Context context) {
        super(context);
        try {

            this.context = context;
        } catch (Exception e) {
            Log.e("MultiSelectObject", e.getMessage());
        }

    }

    public void SetResourceSpinner(List<SpinnerObject> listItem, Context context) {
        this.context = context;
        try {
            this.context = getContext();
            simple_adapter = new ArrayAdapter<SpinnerObject>(this.context, R.layout.textview_spinner);
            simple_adapter.setDropDownViewResource(R.layout.simple_spinner_item);
            super.setAdapter(simple_adapter);
            setItems(listItem);
            this.listItems = listItem;

            //set first select
            mSelection[0] = true;
            SpinnerObject tmp = new SpinnerObject("chose", listItem.get(0).getValue());
            simple_adapter.clear();
            simple_adapter.add(tmp);

        } catch (Exception e) {
            Log.e("SetResourceSpinner", e.getMessage());
        }
    }

    public void SetResourceSpinner(List<SpinnerObject> listItem, Context context,boolean isNotChooe0IfExistChoose,boolean justChooseOne) {
        this.context = context;
        try {
            this.context = getContext();
            simple_adapter = new ArrayAdapter<SpinnerObject>(this.context, R.layout.textview_spinner);
            simple_adapter.setDropDownViewResource(R.layout.simple_spinner_item);
            super.setAdapter(simple_adapter);
            setItems(listItem);
            this.listItems = listItem;

            this.isJustChooseOne = justChooseOne;

            if(!isNotChooe0IfExistChoose) {
                //set first select
                mSelection[0] = true;
                SpinnerObject tmp = new SpinnerObject("chose", listItem.get(0).getValue());
                simple_adapter.clear();
                simple_adapter.add(tmp);
            }

        } catch (Exception e) {
            Log.e("SetResourceSpinner", e.getMessage());
        }
    }

    public void UpdateResourceSpinner(List<SpinnerObject> listItem) {
        try {
            //setItems(listItem);
            //set select items
            boolean[] mSelectiontmp = new boolean[listItem.size()];
            Arrays.fill(mSelectiontmp, false);
            for (int i = 0; i < mSelection.length; i++) {
                if (mSelection[i]) {
                    String key = listItems.get(i).getKey();
                    for (int j = 0; j < listItem.size(); j++) {
                        if (listItem.get(j).getKey().equals(key)) {
                            mSelectiontmp[j] = true;
                        }
                    }
                }
            }
            mSelection = mSelectiontmp.clone();
            BindingListItemDialog(listItem);
            this.listItems = listItem;

        } catch (Exception e) {
            Log.e("SetResourceSpinner", e.getMessage());
        }
    }

    public MultiSelectionObjectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length && which > -1) {
            mSelection[which] = isChecked;
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        try {
            builder = new AlertDialog.Builder(getContext());
            builder.setTitle(titleDialog);
            mSelectionOld = mSelection.clone();
            //listData = (ArrayList<SpinnerMultiObject>) listData.clone();
            //listDataNotChangeOld = (ArrayList<SpinnerMultiObject>) listData.clone();
            builder.setView(R.layout.dialog_spinner_filter);
            builder.setPositiveButton("Chọn",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity
                            SpinnerObject tmp = buildSelectedItemObject();
                            if (tmp != null) {
                                simple_adapter.clear();
                                simple_adapter.add(tmp);
                                dialog.dismiss();
                            } else {
                                mSelection = mSelectionOld.clone();
                                dialog.dismiss();
                            }

                        }
                    });
            builder.setNegativeButton("Hủy",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //mSelection = mSelectionOld.clone();
                            mSelection = mSelectionOld.clone();
                            dialog.cancel();
                        }
                    });

            builder.setCancelable(false);

            d = builder.create();
            d.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    setupUI(d);
                }
            });
            d.show();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("performClick", e.getMessage());
        }

        return true;
    }

    private void setupUI(AlertDialog view) {
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        listview = (ListView) view.findViewById(R.id.lv_spinner);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!isJustChooseOne) {
                    adapter.setSelect(position);
                    adapter.notifyDataSetChanged();
                    for (int i = 0; i < listItems.size(); i++) {
                        if (listItems.get(i).getKey().equals(adapter.getItem(position).getKey())) {
                            mSelection[i] = adapter.getItem(position).isChecked();
                            break;
                        }
                    }
                }else{
                    adapter.setSelectOne(position);
                    adapter.notifyDataSetChanged();
                    for (int i = 0; i < listItems.size(); i++) {
                        if (listItems.get(i).getKey().equals(adapter.getItem(position).getKey())) {
                            mSelection[i] = adapter.getItem(position).isChecked();
                        }else{
                            mSelection[i] = false;
                        }
                    }
                    Log.d("test","one " + position);
                }
            }
        });

        //gernerate data
        listData = new ArrayList<>();
        for (int i = 0; i < listItems.size(); i++) {
            listData.add(new SpinnerMultiObject(listItems.get(i).getKey(), listItems.get(i).getValue(), mSelection[i]));
        }
        adapter = new SpinnerMultiCheckAdapter(context, R.layout.textview_spinner2, listData);
        listview.setAdapter(adapter);

        txtSearch = (EditText) view.findViewById(R.id.txt_search);
        //get text after 2 seconds
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            Handler handler = new Handler(Looper.getMainLooper() /*UI thread*/);
            Runnable workRunnable;

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(workRunnable);
                workRunnable = new Runnable() {
                    @Override
                    public void run() {
                        doSmth(txtSearch.getText().toString());
                    }
                };
                handler.postDelayed(workRunnable, 1500 /*delay*/);
            }

            private final void doSmth(String str) {
                progressBar.setAlpha(1.0f);
                listData = new ArrayList<SpinnerMultiObject>();
                for (int i = 0; i < listItems.size(); i++) {
                    String tmp = listItems.get(i).getValue().toLowerCase();
                    if (tmp.contains(str.toLowerCase())) {
                        listData.add(new SpinnerMultiObject(listItems.get(i).getKey(),listItems.get(i).getValue(),mSelection[i]));
                    }
                }
                adapter = new SpinnerMultiCheckAdapter(context,R.layout.row_one_multi_check,listData);
                listview.setAdapter(adapter);
                progressBar.setAlpha(0f);
            }
        });
    }


    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(List<SpinnerObject> items) {

        BindingListItemDialog(items);
        mSelection = new boolean[_items.length];
        Arrays.fill(mSelection, false);
    }

    public void setSelectionNew(String[] selection) {
        StringBuilder sb = new StringBuilder();
        // StringBuilder sbKey = new StringBuilder();
        boolean foundOne = false;

        for (String cell : selection) {
            for (int j = 0; j < this.listItems.size(); ++j) {
                if (listItems.get(j).getKey().equals(cell)) {
                    mSelection[j] = true;
                    if (foundOne) {
                        sb.append(",");
                        // sbKey.append(",");
                    }
                    foundOne = true;

                    // sbKey.append(listItems.get(j).getKey());
                    sb.append(listItems.get(j).getValue());
                }
            }
        }
        // strKeyChoice = sbKey.toString();
        SpinnerObject obj = new SpinnerObject("chose", sb.toString());
        simple_adapter.clear();
        simple_adapter.add(obj);

    }

    public void setSelectionNew2(String[] selection) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbKey = new StringBuilder();
        boolean foundOne = false;

        for (String cell : selection) {
            for (int j = 0; j < this.listItems.size(); ++j) {
                if (listItems.get(j).getKey().equals(cell)) {
                    mSelection[j] = true;
                    if (foundOne) {
                        sb.append(",");
                        sbKey.append(",");
                    }
                    foundOne = true;

                    sbKey.append(listItems.get(j).getKey());
                    sb.append(listItems.get(j).getValue());
                }
            }
        }
        if(sb.toString().length() > 1)
        {
            mSelection[0] = false;
        }
        strKeyChoice = sbKey.toString();
        SpinnerObject obj = new SpinnerObject("chose", sb.toString());
        simple_adapter.clear();
        simple_adapter.add(obj);

    }

    public void setSelectionNew(List<SpinnerObject> selection) {
        StringBuilder sb = new StringBuilder();
        // StringBuilder sbKey = new StringBuilder();
        boolean foundOne = false;

        for (SpinnerObject cell : selection) {
            for (int j = 0; j < this.listItems.size(); ++j) {
                if (listItems.get(j).getKey().equals(cell.getKey())) {
                    mSelection[j] = true;
                    if (foundOne) {
                        sb.append(",");
                        // sbKey.append(",");
                    }
                    foundOne = true;
                    sb.append(listItems.get(j).getValue());
                }
            }
        }
        // strKeyChoice = sbKey.toString();
        SpinnerObject obj = new SpinnerObject("chose", sb.toString());
        simple_adapter.clear();
        simple_adapter.add(obj);

    }

    public void UpdateUi() {
        SpinnerObject tmp = buildSelectedItemObject();
        if (tmp != null) {
            simple_adapter.clear();
            simple_adapter.add(tmp);
            mSelectionOld = mSelection.clone();
        } else {
            mSelection = mSelectionOld.clone();
            Arrays.fill(mSelection, false);
            mSelection[0] = true;
            simple_adapter.clear();
            simple_adapter.add(new SpinnerObject("chose",listItems.get(0).getValue()));
        }
    }

    private SpinnerObject buildSelectedItemObject() {

        StringBuilder sbKey = new StringBuilder();
        StringBuilder sbValue = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < this.listItems.size(); ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sbKey.append(",");
                    sbValue.append(",");
                }
                foundOne = true;
                SpinnerObject objTmp = this.listItems.get(i);

                sbKey.append(objTmp.getKey());
                sbValue.append(objTmp.getValue());
            }
        }
        strKeyChoice = sbKey.toString();
        SpinnerObject obj = new SpinnerObject("chose", sbValue.toString());
        //khong chon value 0 khi da co du lieu
        if (sbKey.length() < 1) {
            return null;
        } else {
            String tmp[] = sbKey.toString().split(",");
            String tmpValue[] = sbValue.toString().split(",");
            if (tmp.length > 1) {
                if (tmp[0].equals("0")) {
                    foundOne = false;
                    StringBuilder sbKey2 = new StringBuilder();
                    StringBuilder sbValue2 = new StringBuilder();
                    for (int i = 1; i < tmp.length; i++) {
                        if (foundOne) {
                            sbKey2.append(",");
                            sbValue2.append(",");
                        }
                        foundOne = true;

                        sbKey2.append(tmp[i]);
                        sbValue2.append(tmpValue[i]);
                    }
                    strKeyChoice = sbKey2.toString();
                    SpinnerObject obj2 = new SpinnerObject("chose", sbValue2.toString());
                    mSelection[0] = false;
                    return obj2;
                }
            }
        }
        return obj;
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(",");
                }
                foundOne = true;
                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }

    public String getSelectedKeyItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < this.listItems.size(); ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(",");
                }
                foundOne = true;
                sb.append(this.listItems.get(i).getKey());
            }
        }
        return sb.toString();
    }

    private void BindingListItemDialog(List<SpinnerObject> items) {
        Log.e("BindingListItemDialog ", items.size() + "");
        _items = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            _items[i] = items.get(i).getValue();
        }
    }

    public String GetStringKeyChoice() {
        return strKeyChoice;
    }

    public void SetStringKeyChoice(String key) {
        strKeyChoice = key;
    }
}
