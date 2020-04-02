package com.androdu.sofra.ui.fragments.userCycle.clientRegister;

import android.util.Log;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.city.CitiesGeneralResponse2;
import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.client.ClientLoginGeneralResponse;
import com.androdu.sofra.data.models.region.Region;
import com.androdu.sofra.data.models.region.RegionsGeneralResponse;
import com.androdu.sofra.data.retrofitApi.ApiClient;
import com.androdu.sofra.data.retrofitApi.IRetrofitApi;
import com.androdu.sofra.utils.ToastCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.androdu.sofra.utils.HelperMethod.convertToRequestBody;

public class ClientRegisterPresenterImpl implements IClientRegisterFragment.Presenter {
    IClientRegisterFragment.View mClientRegisterView;


    ClientRegisterPresenterImpl(IClientRegisterFragment.View mClientRegisterView) {
        this.mClientRegisterView = mClientRegisterView;
    }

    @Override
    public void created() {
        mClientRegisterView.initViews();
        mClientRegisterView.initListeners();
    }

    @Override
    public void getCities() {
        mClientRegisterView.showProgress();
        final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        Call<CitiesGeneralResponse2> call = retrofitApi.getCities();
        call.enqueue(new Callback<CitiesGeneralResponse2>() {
            @Override
            public void onResponse(Call<CitiesGeneralResponse2> call, Response<CitiesGeneralResponse2> response) {
                mClientRegisterView.hideProgress();
                if (response.body().getStatus() == 1) {
                    mClientRegisterView.onGetCitiesSuccess(response.body().getCityList());
                    for (City c : response.body().getCityList()) {
                        Log.d("city", "name: " + c.getName());
                    }

                } else {
                    Log.d("city", "failed");

                }
            }

            @Override
            public void onFailure(Call<CitiesGeneralResponse2> call, Throwable t) {
                mClientRegisterView.hideProgress();
                Log.d("city", "failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void getRegions(int cityId) {
        mClientRegisterView.showProgress();
        final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        Call<RegionsGeneralResponse> call = retrofitApi.getRegions(cityId);
        call.enqueue(new Callback<RegionsGeneralResponse>() {
            @Override
            public void onResponse(Call<RegionsGeneralResponse> call, Response<RegionsGeneralResponse> response) {
                mClientRegisterView.hideProgress();
                if (response.body().getStatus() == 1) {
                    if (response.body().getRegionsPage().getRegionList().isEmpty()) {
                        mClientRegisterView.onGetRegionsSuccess(null);
                    } else {
                        mClientRegisterView.onGetRegionsSuccess(response.body().getRegionsPage().getRegionList());
                        Log.d("city", "page" + response.body().getRegionsPage().getCurrentPage() + " :");
                        for (Region c : response.body().getRegionsPage().getRegionList()) {
                            Log.d("city", "name: " + c.getName());
                        }
                    }

                } else {
                    mClientRegisterView.onErrorOccurs(response.body().getMsg());
                    Log.d("city", "failed");

                }
            }

            @Override
            public void onFailure(Call<RegionsGeneralResponse> call, Throwable t) {
                mClientRegisterView.hideProgress();
                mClientRegisterView.onErrorOccurs(t.getMessage());
                Log.d("city", "failed: " + t.getMessage());

            }
        });

    }

    @Override
    public void onLoginButtonClick(String name, String email, String phone, String cityId, final String regionId, String password, String imgPath) {
        mClientRegisterView.showProgress();
        //Create a file object using file path

        final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        Call<ClientLoginGeneralResponse> call = retrofitApi.clientRegister(convertToRequestBody(name),
                convertToRequestBody(email),
                convertToRequestBody(phone),
                convertToRequestBody(regionId),
                convertToRequestBody(password),
                convertToRequestBody(password),
                convertFileToMultipart(imgPath, "profile_image")
        );

        call.enqueue(new Callback<ClientLoginGeneralResponse>() {
            @Override
            public void onResponse(Call<ClientLoginGeneralResponse> call, Response<ClientLoginGeneralResponse> response) {
                mClientRegisterView.hideProgress();
                if (response.body().getStatus() == 1)
                    mClientRegisterView.onClientRegisterSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ClientLoginGeneralResponse> call, Throwable t) {
                mClientRegisterView.hideProgress();
                ToastCreator.onCreateErrorToast(mClientRegisterView.getActivity(), "Error", R.drawable.cancel);
                Log.d("RegisterError", "onFailure: " + t.getMessage());
            }
        });

    }
}
