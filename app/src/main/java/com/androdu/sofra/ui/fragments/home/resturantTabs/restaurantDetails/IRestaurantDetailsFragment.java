package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantDetails;

import android.app.Activity;

import com.androdu.sofra.data.models.restaurant.RestaurantDetailsGeneralResponse;

public interface IRestaurantDetailsFragment {

    interface View {
        void initViews();
        void showProgress();
        void hideProgress();
        void setMsg(String msg);
        void onGetRestaurantDetailsSuccess(RestaurantDetailsGeneralResponse restaurantDetailsGeneralResponse);
        void onError(String errorMsg);

        Activity getActivity();
    }

    interface Presenter {
        void created();
        void getRestaurantDetails(int restaurantId);
    }
}
