package manulife.manulifesop.activity.FAGroup.personal.activityHistSetting;

import java.util.List;

import manulife.manulifesop.adapter.ObjectData.ActiveHistSetting;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface ActivityHistSettingContract {
    interface View extends BaseMVPView {
        void showData(List<ActiveHistSetting> data,int currentPage, int lastPage);
    }

    interface Action {
        void getActivitiHist(int page, int limit);
    }
}
