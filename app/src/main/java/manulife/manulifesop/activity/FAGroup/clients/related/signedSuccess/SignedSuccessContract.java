package manulife.manulifesop.activity.FAGroup.clients.related.signedSuccess;

import java.util.ArrayList;

import manulife.manulifesop.adapter.ObjectData.SpinnerObject;
import manulife.manulifesop.base.BaseMVPView;

/**
 * Created by trinm on 12/01/2018.
 */

public interface SignedSuccessContract {
    interface View extends BaseMVPView {
        void finishSubmit();
        boolean validateInput();
        void initProducts(ArrayList<SpinnerObject> dataMainProduct, ArrayList<SpinnerObject> dataSubProduct);
        void showDatePicker();
    }

    interface Action {
        void getProductsSign();
        void submitContract(int leadID,int CommissionRate, int Revenue,
                            int NumContract, String chooseMainProduct,
                            String chooseSubProduct,String dateSign);
    }
}
