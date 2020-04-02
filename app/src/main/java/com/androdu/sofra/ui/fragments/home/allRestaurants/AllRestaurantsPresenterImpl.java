package com.androdu.sofra.ui.fragments.home.allRestaurants;

import android.util.Log;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.city.CitiesGeneralResponse2;
import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.restaurant.RestaurantsGeneralRequest;
import com.androdu.sofra.data.retrofitApi.ApiClient;
import com.androdu.sofra.data.retrofitApi.IRetrofitApi;
import com.androdu.sofra.utils.ToastCreator;
import com.androdu.sofra.utils.network.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllRestaurantsPresenterImpl implements IAllRestaurantsFragment.Presenter {
    private IAllRestaurantsFragment.View mAllRestaurantsView;

    AllRestaurantsPresenterImpl(IAllRestaurantsFragment.View mAllRestaurantsView) {
        this.mAllRestaurantsView = mAllRestaurantsView;
    }

    @Override
    public void created() {
        mAllRestaurantsView.initViews();
        mAllRestaurantsView.initListeners();
    }

    @Override
    public void getAllRestaurants(final int pageNum, String name, int cityId, boolean isFiltered) {
        if (NetworkState.isConnected(mAllRestaurantsView.getActivity())) {
            IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
            Call<RestaurantsGeneralRequest> call;
            if (isFiltered) {
                if (cityId == 0)
                    call = retrofitApi.getRestaurantsByPageAndFilter(pageNum, name);
                else
                    call = retrofitApi.getRestaurantsByPageAndFilter(pageNum, name, cityId);
            } else {
                call = retrofitApi.getRestaurantsByPage(pageNum);
            }

            call.enqueue(new Callback<RestaurantsGeneralRequest>() {
                @Override
                public void onResponse(Call<RestaurantsGeneralRequest> call, Response<RestaurantsGeneralRequest> response) {
                    mAllRestaurantsView.hideProgress();
                    mAllRestaurantsView.hideLoadMore();
                    mAllRestaurantsView.hideRefresh();
                    if (response.body().getStatus() == 1) {
                        mAllRestaurantsView.onGetAllRestaurantsSuccess(response.body(), pageNum);
                        mAllRestaurantsView.setMsg("");
                    } else {
                        mAllRestaurantsView.setMsg(response.body().getMsg());
                        mAllRestaurantsView.initValues();
                    }
                }

                @Override
                public void onFailure(Call<RestaurantsGeneralRequest> call, Throwable t) {
                    mAllRestaurantsView.hideProgress();
                    mAllRestaurantsView.hideRefresh();
                    mAllRestaurantsView.hideLoadMore();
                    Log.d("restaurants", "onFailure: " + t.getMessage());
                }
            });
        } else {
            mAllRestaurantsView.hideProgress();
            mAllRestaurantsView.hideLoadMore();
            mAllRestaurantsView.hideRefresh();
            ToastCreator.onCreateErrorToast(mAllRestaurantsView.getActivity(), "تأكد من اتصالك بالانترنت ظظ", R.drawable.cancel);
        }
    }

    @Override
    public void getCities() {
        if (NetworkState.isConnected(mAllRestaurantsView.getActivity())) {
            mAllRestaurantsView.showProgress();
            final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
            Call<CitiesGeneralResponse2> call = retrofitApi.getCities();
            call.enqueue(new Callback<CitiesGeneralResponse2>() {
                @Override
                public void onResponse(Call<CitiesGeneralResponse2> call, Response<CitiesGeneralResponse2> response) {
                    if (response.body().getStatus() == 1) {
                        mAllRestaurantsView.onGetCitiesSuccess(response.body().getCityList());
                        for (City c : response.body().getCityList()) {
                            Log.d("city", "name: " + c.getName());
                        }

                    } else {
                        Log.d("city", "failed");
                    }
                }

                @Override
                public void onFailure(Call<CitiesGeneralResponse2> call, Throwable t) {
                    Log.d("city", "failed: " + t.getMessage());
                }
            });
        } else {
            mAllRestaurantsView.hideProgress();
            mAllRestaurantsView.hideLoadMore();
            mAllRestaurantsView.hideRefresh();
            ToastCreator.onCreateErrorToast(mAllRestaurantsView.getActivity(), "تأكد من اتصالك بالانترنت ظظ", R.drawable.cancel);
        }
    }

}
