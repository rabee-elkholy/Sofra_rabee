package com.androdu.sofra.ui.fragments.userCycle.restaurantRegister.firstRegister;

import android.util.Log;

import com.androdu.sofra.data.models.city.CitiesGeneralResponse2;
import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.region.Region;
import com.androdu.sofra.data.models.region.RegionsGeneralResponse;
import com.androdu.sofra.data.retrofitApi.ApiClient;
import com.androdu.sofra.data.retrofitApi.IRetrofitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstRegisterPresenterImpl implements IFirstRegisterFragment.Presenter {
    IFirstRegisterFragment.View mRegisterView;

    public FirstRegisterPresenterImpl(IFirstRegisterFragment.View mRegisterView) {
        this.mRegisterView = mRegisterView;
    }


    @Override
    public void created() {
        mRegisterView.initViews();
        mRegisterView.initListeners();
    }

    @Override
    public void getCities() {
        mRegisterView.showProgress();
        final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        Call<CitiesGeneralResponse2> call = retrofitApi.getCities();
        call.enqueue(new Callback<CitiesGeneralResponse2>() {
            @Override
            public void onResponse(Call<CitiesGeneralResponse2> call, Response<CitiesGeneralResponse2> response) {
                mRegisterView.hideProgress();
                if (response.body().getStatus() == 1) {
                    mRegisterView.onGetCitiesSuccess(response.body().getCityList());
                    for (City c : response.body().getCityList()) {
                        Log.d("city", "name: " + c.getName());
                    }

                } else {
                    Log.d("city", "failed");

                }
            }

            @Override
            public void onFailure(Call<CitiesGeneralResponse2> call, Throwable t) {
                mRegisterView.hideProgress();
                Log.d("city", "failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void getRegions(int cityId) {
        mRegisterView.showProgress();
        final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        Call<RegionsGeneralResponse> call = retrofitApi.getRegions(cityId);
        call.enqueue(new Callback<RegionsGeneralResponse>() {
            @Override
            public void onResponse(Call<RegionsGeneralResponse> call, Response<RegionsGeneralResponse> response) {
                mRegisterView.hideProgress();
                if (response.body().getStatus() == 1) {
                    if (response.body().getRegionsPage().getRegionList().isEmpty()) {
                        mRegisterView.onGetRegionsSuccess(null);
                    } else {
                        mRegisterView.onGetRegionsSuccess(response.body().getRegionsPage().getRegionList());
                        Log.d("city", "page" + response.body().getRegionsPage().getCurrentPage() + " :");
                        for (Region c : response.body().getRegionsPage().getRegionList()) {
                            Log.d("city", "name: " + c.getName());
                        }
                    }

                } else {
                    mRegisterView.onErrorOccurs(response.body().getMsg());
                    Log.d("city", "failed");

                }
            }

            @Override
            public void onFailure(Call<RegionsGeneralResponse> call, Throwable t) {
                mRegisterView.hideProgress();
                mRegisterView.onErrorOccurs(t.getMessage());
                Log.d("city", "failed: " + t.getMessage());

            }
        });

    }
}
