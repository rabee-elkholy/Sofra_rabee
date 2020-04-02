package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantDetails;

import android.util.Log;

import com.androdu.sofra.data.models.restaurant.RestaurantDetailsGeneralResponse;
import com.androdu.sofra.data.retrofitApi.ApiClient;
import com.androdu.sofra.data.retrofitApi.IRetrofitApi;
import com.androdu.sofra.utils.network.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailsPresenterImpl implements IRestaurantDetailsFragment.Presenter {
    private IRestaurantDetailsFragment.View mRestaurantDetailsView;

    public RestaurantDetailsPresenterImpl(IRestaurantDetailsFragment.View mRestaurantDetailsView) {
        this.mRestaurantDetailsView = mRestaurantDetailsView;
    }

    @Override
    public void created() {
        mRestaurantDetailsView.initViews();
        mRestaurantDetailsView.showProgress();
    }

    @Override
    public void getRestaurantDetails(final int restaurantId) {
        if (NetworkState.isConnected(mRestaurantDetailsView.getActivity())) {
            final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
            Call<RestaurantDetailsGeneralResponse> call = retrofitApi.getRestaurantsDetails(restaurantId);
            call.enqueue(new Callback<RestaurantDetailsGeneralResponse>() {
                @Override
                public void onResponse(Call<RestaurantDetailsGeneralResponse> call, Response<RestaurantDetailsGeneralResponse> response) {
                    mRestaurantDetailsView.hideProgress();
                    if (response.body().getStatus() == 1) {
                        mRestaurantDetailsView.onGetRestaurantDetailsSuccess(response.body());
                        Log.d("categories", "Details" + restaurantId);

                    } else {
                        mRestaurantDetailsView.setMsg(response.body().getMsg());
                        Log.d("categories", "failed");
                    }
                }

                @Override
                public void onFailure(Call<RestaurantDetailsGeneralResponse> call, Throwable t) {
                    Log.d("categories", "failed: " + t.getMessage());
                }
            });
        } else {
            mRestaurantDetailsView.hideProgress();
            mRestaurantDetailsView.setMsg("تأكد من اتصالك بالانترنت ");
        }
    }


}
