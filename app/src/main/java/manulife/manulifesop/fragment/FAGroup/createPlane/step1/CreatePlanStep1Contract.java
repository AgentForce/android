package manulife.manulifesop.fragment.FAGroup.createPlane.step1;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.io.File;

import manulife.manulifesop.base.BaseMVPView;


/**
 * Created by Chick on 10/27/2017.
 */

public interface CreatePlanStep1Contract {

    interface View extends BaseMVPView {
        void showDatePicker(final String type);
    }

    interface Action {

    }
}
