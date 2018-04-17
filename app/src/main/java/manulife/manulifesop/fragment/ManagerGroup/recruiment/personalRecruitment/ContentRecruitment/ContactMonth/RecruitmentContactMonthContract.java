package manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.ContentRecruitment.ContactMonth;

import java.util.List;

import manulife.manulifesop.adapter.ObjectData.ContactAllFA;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface RecruitmentContactMonthContract {

    interface View extends BaseMVPView {
        void loadContactMonth(List<ContactAllFA> data, int currentPage, int lastPage);
        void addTextChangeListener();
        void loadFirstData();
        void gotoConactDetail(int id, int progressStep, int statusstep);
    }

    interface Action {
        void getContactMonthRecruitment(int month, String search, int page);
    }
}
