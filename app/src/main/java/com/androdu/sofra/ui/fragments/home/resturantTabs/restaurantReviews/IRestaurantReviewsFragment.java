package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantReviews;

import android.app.Activity;

import com.androdu.sofra.data.models.review.RestaurantReviewsGeneralRequest;

public interface IRestaurantReviewsFragment {

    interface View {
        void initViews();
        void initListeners();
        void showProgress();
        void hideProgress();
        void showLoadMore();
        void hideLoadMore();
        void setMsg(String msg);
        void onGetRestaurantReviewsSuccess(RestaurantReviewsGeneralRequest restaurantReviewsGeneralRequest, int pageNumber);
        void onAddReviewSuccess(String msg);
        void onError(String errorMsg);

        Activity getActivity();
    }

    interface Presenter {
        void created();
        void getRestaurantReviews(int restaurantId, int pageNumber);
        void addReview(int rateType, String reviewText);
    }
}
