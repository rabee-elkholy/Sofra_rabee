package com.androdu.sofra.ui.fragments.userCycle.login;

import android.app.Activity;

import com.androdu.sofra.data.models.client.ClientLoginGeneralResponse;
import com.androdu.sofra.data.models.restaurant.RestaurantLoginGeneralRequest;

public interface ILoginFragment {

    interface View {
        void initViews();
        void initListeners();
        void showProgress();
        void hideProgress();
        void onRestaurantLoginSuccess(RestaurantLoginGeneralRequest restaurantLoginGeneralRequest);
        void onClientLoginSuccess(ClientLoginGeneralResponse clientLoginGeneralResponse);
        void onLoginFailed(String errorMsg);
        Activity getActivity();
    }

    interface Presenter {
        void created();
        void loginButtonClick(String email, String password, int userType);
    }
}
