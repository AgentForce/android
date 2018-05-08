package manulife.manulifesop.adapter.ObjectData;

import java.io.Serializable;

public class SpinnerMultiObject implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String key ;
	private String value ;
	private boolean isChecked;
	public SpinnerMultiObject() {

	}
	public SpinnerMultiObject(String key , String value, boolean isChecked) {
		this.key = key ; 
		this.value = value ;
		this.isChecked = isChecked;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
