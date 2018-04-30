package manulife.manulifesop.fragment.ManagerGroup.manageSale.content;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface ContentManageSaleContract {

    interface View extends BaseMVPView {
        void initTabMenu();
        void scrollToTabAfterLayout();
        void showContentDetail(int processStep, String type);

        int getParrentSelectedPage();
        void setViewsHeight();

        void setManagerName(String name);
        int getUserIDProcessing();
    }

    interface Action {

    }
}
