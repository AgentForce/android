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
import manulife.manulifesop.api.ObjectInput.InputUpdateContact;
import manulife.manulifesop.api.ObjectResponse.BaseResponse;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.DeviceInfo;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;
import retrofit2.Response;

/**
 * Created by trinm on 12/01/2018.
 */

public class UpdateContactInfoPresenter extends BasePresenter<UpdateContactInfoContract.View> implements UpdateContactInfoContract.Action {

    private Context mContext;
    private int mPosition;
    private String mPhone;

    public UpdateContactInfoPresenter(UpdateContactInfoContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

    @Override
    public void addContactInfo(int position, String name, String phone,int age, int gender,
                                  int income, int marital, int relationship,
                                  int source, String description) {
        mPosition = position;
        mPhone = phone.trim().replace("-","").replace("+","")
                .replace(" ","");

        //mPresenterView.showLoading("Cập nhật thông tin người thứ" + (position + 1));
        mPresenterView.showLoading("Cập nhật thông tin");

        InputAddContact data = new InputAddContact();
        data.campId = ProjectApplication.getInstance().getCampaignWeekId();
        data.phone = phone.trim().replace("-","").replace("+","")
        .replace(" ","");
        data.name = name;
        data.age = age;
        data.gender = gender;
        data.incomeMonthly = income;
        data.maritalStatus = marital;
        data.relationship = relationship;
        data.source = source;
        data.leadType = 1;//khách hàng là 1 (FA mặc định)
        data.description = description;

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().addContact(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    @Override
    public void addRecruitInfo(int position, String name, String phone, int age, int gender, int income, int marital, int relationship, int source, String description) {
        mPosition = position;
        mPhone = phone.trim().replace("-","").replace("+","")
                .replace(" ","");

        mPresenterView.showLoading("Cập nhật thông tin người thứ" + (position + 1));

        InputAddContact data = new InputAddContact();
        data.campId = ProjectApplication.getInstance().getCampaignWeekId();
        data.phone = phone.trim().replace("-","").replace("+","")
                .replace(" ","");
        data.name = name;
        data.age = age;
        data.gender = gender;
        data.incomeMonthly = income;
        data.maritalStatus = marital;
        data.relationship = relationship;
        data.source = source;
        data.leadType = 2;//tuyển dụng là 2 // thực sự không bắt
        data.description = description;

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().addRecruit(
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
            SOPSharedPreferences.getInstance(mContext).saveAddedPhone(mPhone);
            mPresenterView.loadNextContact(mPosition + 1);
        }else
        {
            mPresenterView.finishLoading(baseResponse.msg,false);
        }
    }

    @Override
    public void changeReleadToContact(int releadID, int campaignID, int age, int gender, int income, int marital, int relationship, int source, String description) {
        mPresenterView.showLoading("Chuyển sang KH liên hệ");

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

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().changeIntroduceContact(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponseChangeToContact, this::handleError));
    }

    @Override
    public void changeReleadToRecruit(int releadID, int campaignID, int age, int gender, int income, int marital, int relationship, int source, String description) {
        mPresenterView.showLoading("Chuyển sang KH tuyển dụng");

        InputChangeRelead data = new InputChangeRelead();
        data.reLeadId = releadID;

        data.age = age;data.campId = campaignID;
        data.gender = gender;
        data.incomeMonthly = income;
        data.maritalStatus = marital;
        data.relationship = relationship;
        data.source = source;
        data.leadType = 2;//tuyển dụng là 2
        data.description = description;

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().changeIntroduceRecruit(
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

    @Override
    public void updateContactInfo(int releadID, String name, int age, int gender, int income, int marital, int relationship, int source, String description) {
        mPresenterView.showLoading("Cập nhật thông tin");
        InputUpdateContact data = new InputUpdateContact();
        data.name = name;
        data.age = age;
        data.gender = gender;
        data.incomeMonthly = income;
        data.maritalStatus = marital;
        data.relationship = relationship;
        data.source = source;
        data.description = description;

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().updateContact(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum,releadID, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.computation())
                .subscribe(this::handleResponseUpdateInfo, this::handleError));
    }

    @Override
    public void updateRecruitInfo(int releadID, String name, int age, int gender, int income, int marital, int relationship, int source, String description) {
        mPresenterView.showLoading("Cập nhật thông tin");

        InputUpdateContact data = new InputUpdateContact();
        data.name = name;
        data.age = age;
        data.gender = gender;
        data.incomeMonthly = income;
        data.maritalStatus = marital;
        data.relationship = relationship;
        data.source = source;
        data.description = description;

        String checksum = Utils.getSignature(new Gson().toJson(data));

        getCompositeDisposable().add(ApiService.getServer().updateRecruit(
                SOPSharedPreferences.getInstance(mContext).getAccessToken(),
                Contants.clientID, DeviceInfo.ANDROID_OS_VERSION, BuildConfig.VERSION_NAME, DeviceInfo.DEVICE_NAME, DeviceInfo.DEVICEIMEI,
                checksum,releadID, data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.computation())
                .subscribe(this::handleResponseUpdateInfo, this::handleError));
    }

    private void handleResponseUpdateInfo(BaseResponse baseResponse) {
        if(baseResponse.statusCode == 1){
            mPresenterView.finishLoading();
            mPresenterView.finishChangeToContact();
        }else{
            mPresenterView.finishLoading(baseResponse.msg,false);
        }
    }
}
