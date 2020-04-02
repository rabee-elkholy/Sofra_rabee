package com.androdu.sofra.ui.fragments.userCycle.restaurantRegister.secondRegister;

import android.app.Activity;

public interface ISecondRegisterFragment {
    interface View {
        void initViews();

        void initListeners();

        void showProgress();

        void hideProgress();

        void onRegisterSuccess(String successMsg);

        void onErrorOccurs(String errorMsg);

        Activity getActivity();
    }

    interface Presenter {
        void created();

        void onLoginButtonClick(String restaurantName,
                                String email,
                                int deliveryTime,
                                String cityId,
                                String regionId,
                                String password,
                                String minCharger,
                                String deliveryCost,
                                String phoneNumber,
                                String whatsAppNumber,
                                String imgPath);

    }
}
