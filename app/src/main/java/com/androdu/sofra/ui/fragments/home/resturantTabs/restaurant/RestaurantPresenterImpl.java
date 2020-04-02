package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurant;

import android.util.Log;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.restaurantCategory.RestaurantCategoriesGeneralResponse;
import com.androdu.sofra.data.models.retaurantItem.RestaurantItemsGeneralResponse;
import com.androdu.sofra.data.retrofitApi.ApiClient;
import com.androdu.sofra.data.retrofitApi.IRetrofitApi;
import com.androdu.sofra.utils.ToastCreator;
import com.androdu.sofra.utils.network.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantPresenterImpl implements IRestaurantFragment.Presenter {
    private IRestaurantFragment.View mRestaurantView;

    public RestaurantPresenterImpl(IRestaurantFragment.View mRestaurantView) {
        this.mRestaurantView = mRestaurantView;
    }

    @Override
    public void created() {
        mRestaurantView.initViews();
        mRestaurantView.initListeners();
        mRestaurantView.showProgress();
    }

    @Override
    public void getRestaurantItems(final int restaurantId, final int pageNum, int categoryId) {
        if (NetworkState.isConnected(mRestaurantView.getActivity())) {
            final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
            Call<RestaurantItemsGeneralResponse> call = retrofitApi.getRestaurantItems(restaurantId, categoryId);
            call.enqueue(new Callback<RestaurantItemsGeneralResponse>() {
                @Override
                public void onResponse(Call<RestaurantItemsGeneralResponse> call, Response<RestaurantItemsGeneralResponse> response) {

                    mRestaurantView.hideProgress();
                    mRestaurantView.hideLoadMore();
                    mRestaurantView.hideRefresh();
                    if (response.body().getStatus() == 1) {
                        mRestaurantView.onGetRestaurantItemsSuccess(response.body(), pageNum);
                        Log.d("categories", "items" + restaurantId);
                        if (response.body().getRestaurantItemsPage().getRestaurantItems().isEmpty())
                            mRestaurantView.setMsg("لا يوجد عناصر في هذه القائمة!");
                        else
                            mRestaurantView.setMsg("");

                    } else {
                        Log.d("categories", "failed");
                    }
                }

                @Override
                public void onFailure(Call<RestaurantItemsGeneralResponse> call, Throwable t) {
                    Log.d("categories", "failed: " + t.getMessage());
                }
            });
        } else {
            mRestaurantView.hideProgress();
            mRestaurantView.hideLoadMore();
            mRestaurantView.hideRefresh();
            ToastCreator.onCreateErrorToast(mRestaurantView.getActivity(), "تأكد من اتصالك بالانترنت ظظ", R.drawable.cancel);
        }
    }

    @Override
    public void getCategories(int restaurantId) {
        if (NetworkState.isConnected(mRestaurantView.getActivity())) {
            final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
            Call<RestaurantCategoriesGeneralResponse> call = retrofitApi.getRestaurantCategories(restaurantId);
            call.enqueue(new Callback<RestaurantCategoriesGeneralResponse>() {
                @Override
                public void onResponse(Call<RestaurantCategoriesGeneralResponse> call, Response<RestaurantCategoriesGeneralResponse> response) {
                    if (response.body().getStatus() == 1) {
                        mRestaurantView.onGetCategoriesSuccess(response.body());
                    } else {
                        Log.d("categories", "failed");
                    }
                }

                @Override
                public void onFailure(Call<RestaurantCategoriesGeneralResponse> call, Throwable t) {
                    Log.d("categories", "failed: " + t.getMessage());
                }
            });
        } else {
            mRestaurantView.hideProgress();
            mRestaurantView.hideLoadMore();
            mRestaurantView.hideRefresh();
            ToastCreator.onCreateErrorToast(mRestaurantView.getActivity(), "تأكد من اتصالك بالانترنت ظظ", R.drawable.cancel);
        }
    }


}
