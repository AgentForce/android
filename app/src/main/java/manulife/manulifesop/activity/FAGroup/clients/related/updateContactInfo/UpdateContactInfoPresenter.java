package manulife.manulifesop.activity.FAGroup.clients.related.updateContactInfo;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.BuildConfig;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.api.ObjectInput.InputAddContact;
import manulife.manulifesop.api.ObjectInput.InputChangeRelead;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import retrofit2.Response;

/**
 * Created by trinm on 12/01/2018.
 */

public class UpdateContactInfoPresenter extends BasePresenter<UpdateContactInfoContract.View> implements UpdateContactInfoContract.Action {

    private Context mContext;
    private int mPosition;

    public UpdateContactInfoPresenter(UpdateContactInfoContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void updateContactInfo(int position, String name, String phone,int age, int gender,
                                  int income, int marital, int relationship,
                                  int source, String description) {
        mPosition = position;

        mPresenterView.showLoading("Cập nhật thông tin người thứ" + (position + 1));
        //create checksum
        String checksum = "12345";
        InputAddContact data = new InputAddContact();
        data.campId = ProjectApplication.getInstance().getCampaignWeekId();
        data.phone = phone.trim().replace("-","").replace("+","");
        data.name = name;
        data.age = age;
        data.gender = gender;
        data.incomeMonthly = income;
        data.maritalStatus = marital;
        data.relationship = relationship;
        data.source = source;
        data.leadType = 1;//khách hàng là 1 (FA mặc định)
        data.description = description;

        getCompositeDisposable().add(ApiService.getServer().addContact(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mPresenterView.finishLoading(throwable.getMessage(),false);
    }

    private void handleResponse(BaseResponse baseResponse) {
        if(baseResponse.statusCode == 1){
            mPresenterView.finishLoading();
            mPresenterView.loadNextContact(mPosition + 1);
        }else
        {
            mPresenterView.finishLoading(baseResponse.msg,false);
        }
    }

    @Override
    public void changeReleadToContact(int releadID, int campaignID, int age, int gender, int income, int marital, int relationship, int source, String description) {
        mPresenterView.showLoading("Chuyển sang KH liên hệ");
        //create checksum
        String checksum = "12345";
        InputChangeRelead data = new InputChangeRelead();
        data.reLeadId = releadID;
        data.campId = campaignID;
        data.age = age;
        data.gender = gender;
        data.incomeMonthly = income;
        data.maritalStatus = marital;
        data.relationship = relationship;
        data.source = source;
        data.leadType = 1;//khách hàng là 1 (FA mặc định)
        data.description = description;

        getCompositeDisposable().add(ApiService.getServer().changeIntroduceContact(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseChangeToContact, this::handleError));
    }

    private void handleResponseChangeToContact(BaseResponse baseResponse) {
        if(baseResponse.statusCode == 1){
            mPresenterView.finishChangeToContact();
            mPresenterView.finishLoading();
        }else{
            mPresenterView.finishLoading(baseResponse.msg,false);
        }
    }
}
