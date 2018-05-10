package manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ContactMonth;

import java.util.List;

import manulife.manulifesop.adapter.ObjectData.ContactAllFA;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface FAContactMonthContract {

    interface View extends BaseMVPView {
        void loadContactMonth(List<ContactAllFA> data,int currentPage, int lastPage);
        void addTextChangeListener();
        void loadFirstData();
        void gotoConactDetail(int id,int progressStep,int statusstep);
    }

    interface Action {
        void getContactMonth(int month,String search, int page);
        void moveContactToCurrent(int leadID);
    }
}
