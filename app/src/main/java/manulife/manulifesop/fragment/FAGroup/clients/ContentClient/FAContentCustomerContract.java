package manulife.manulifesop.fragment.FAGroup.clients.ContentClient;

import java.util.List;

import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface FAContentCustomerContract {

    interface View extends BaseMVPView {
        void showCampaignsMonth(CampaignMonth data, List<BaseFragment> fragmentList);
        void showConfirmAcvitveCampaign();

        void showDialogEditCampaign();
        void updateData();
    }

    interface Action {
        void getCampaignMonth(int month);
        void updateCampaignWeek(int month,int contractW1,int contractW2,int contractW3,int contractW4);
        void increaseContactCampaign(int month, int increaseNumber);
    }
}
