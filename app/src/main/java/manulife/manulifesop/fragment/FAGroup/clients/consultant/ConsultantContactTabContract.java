package manulife.manulifesop.fragment.FAGroup.clients.consultant;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ConsultantContactTabContract {

    interface View extends BaseMVPView {
        void gotoConactDetail(int id);
    }

    interface Action {

    }
}
