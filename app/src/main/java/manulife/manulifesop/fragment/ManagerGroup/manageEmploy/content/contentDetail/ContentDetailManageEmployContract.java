package manulife.manulifesop.fragment.ManagerGroup.manageEmploy.content.contentDetail;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ContentDetailManageEmployContract {

    interface View extends BaseMVPView {
        void initviewsHeight();
        void initHeightViaSelected();
    }

    interface Action {

    }
}
