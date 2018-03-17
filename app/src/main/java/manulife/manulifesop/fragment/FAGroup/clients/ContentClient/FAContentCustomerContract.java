package manulife.manulifesop.fragment.FAGroup.clients.ContentClient;

import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface FAContentCustomerContract {

    interface View extends BaseMVPView {
        void showCampaignsMonth(CampaignMonth data);
        void showConfirmAcvitveCampaign();
    }

    interface Action {
        void getCampaignMonth(int month);
    }
}
