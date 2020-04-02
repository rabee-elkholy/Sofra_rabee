package com.androdu.sofra.ui.fragments.userCycle.restaurantRegister.secondRegister;

import android.util.Log;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.restaurant.RestaurantLoginGeneralRequest;
import com.androdu.sofra.data.retrofitApi.ApiClient;
import com.androdu.sofra.data.retrofitApi.IRetrofitApi;
import com.androdu.sofra.utils.HelperMethod;
import com.androdu.sofra.utils.ToastCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.sofra.utils.HelperMethod.convertToRequestBody;

public class SecondRegisterPresenterImpl implements ISecondRegisterFragment.Presenter {
    ISecondRegisterFragment.View mSecondRegisterView;

    public SecondRegisterPresenterImpl(ISecondRegisterFragment.View mSecondRegisterView) {
        this.mSecondRegisterView = mSecondRegisterView;
    }


    @Override
    public void created() {
        mSecondRegisterView.initViews();
        mSecondRegisterView.initListeners();
    }

    @Override
    public void onLoginButtonClick(final String restaurantName, String email, int deliveryTime, String cityId, String regionId, String password, String minCharger, String deliveryCost, String phoneNumber, String whatsAppNumber, String imgPath) {
        mSecondRegisterView.showProgress();
        //Create a file object using file path

        ApiClient.getApiClient().create(IRetrofitApi.class).restaurantRegister(
                convertToRequestBody(restaurantName),
                convertToRequestBody(email),
                convertToRequestBody(password),
                convertToRequestBody(password),
                convertToRequestBody(phoneNumber),
                convertToRequestBody(whatsAppNumber),
                convertToRequestBody(regionId),
                convertToRequestBody(deliveryCost),
                convertToRequestBody(minCharger),
                HelperMethod.convertFileToMultipart(imgPath, "photo"),
                convertToRequestBody(String.valueOf(deliveryTime))
        ).enqueue(new Callback<RestaurantLoginGeneralRequest>() {
            @Override
            public void onResponse(Call<RestaurantLoginGeneralRequest> call, Response<RestaurantLoginGeneralRequest> response) {
                mSecondRegisterView.hideProgress();
                if (response.body().getStatus() == 1) {
                    mSecondRegisterView.onRegisterSuccess(response.body().getMsg());
                } else {
                    mSecondRegisterView.onErrorOccurs(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<RestaurantLoginGeneralRequest> call, Throwable t) {
                mSecondRegisterView.hideProgress();
                ToastCreator.onCreateErrorToast(mSecondRegisterView.getActivity(), "Error", R.drawable.cancel);
                Log.d("RegisterError", "onFailure: " + t.getMessage());
            }
        });

    }


}
