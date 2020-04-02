package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantReviews;

import android.util.Log;

import com.androdu.sofra.data.models.review.RestaurantReviewsGeneralRequest;
import com.androdu.sofra.data.retrofitApi.ApiClient;
import com.androdu.sofra.data.retrofitApi.IRetrofitApi;
import com.androdu.sofra.utils.network.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantReviewsPresenterImpl implements IRestaurantReviewsFragment.Presenter {
    private IRestaurantReviewsFragment.View mRestaurantReviewView;

    public RestaurantReviewsPresenterImpl(IRestaurantReviewsFragment.View mRestaurantReviewView) {
        this.mRestaurantReviewView = mRestaurantReviewView;
    }

    @Override
    public void created() {
        mRestaurantReviewView.initViews();
        mRestaurantReviewView.initListeners();
        mRestaurantReviewView.showProgress();
    }

    @Override
    public void getRestaurantReviews(final int restaurantId, final int pageNumber) {
        if (NetworkState.isConnected(mRestaurantReviewView.getActivity())) {
            final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
            Call<RestaurantReviewsGeneralRequest> call = retrofitApi.getRestaurantsReviews(restaurantId, pageNumber);
            call.enqueue(new Callback<RestaurantReviewsGeneralRequest>() {
                @Override
                public void onResponse(Call<RestaurantReviewsGeneralRequest> call, Response<RestaurantReviewsGeneralRequest> response) {
                    mRestaurantReviewView.hideProgress();
                    if (response.body().getStatus() == 1) {
                        mRestaurantReviewView.onGetRestaurantReviewsSuccess(response.body(), pageNumber);
                        Log.d("categories", "Reviews" + restaurantId + ":" + pageNumber);


                    } else {
                        mRestaurantReviewView.setMsg(response.body().getMsg());
                        Log.d("categories", "failed");
                    }
                }

                @Override
                public void onFailure(Call<RestaurantReviewsGeneralRequest> call, Throwable t) {
                    Log.d("categories", "failed: " + t.getMessage());
                }
            });
        } else {
            mRestaurantReviewView.hideProgress();
            mRestaurantReviewView.setMsg("تأكد من اتصالك بالانترنت ");
        }
    }

    @Override
    public void addReview(int rateType, String reviewText) {

    }


}
