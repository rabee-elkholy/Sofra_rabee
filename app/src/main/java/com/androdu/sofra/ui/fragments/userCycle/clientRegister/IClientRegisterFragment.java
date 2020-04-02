package com.androdu.sofra.ui.fragments.userCycle.clientRegister;

import android.app.Activity;

import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.client.ClientLoginGeneralResponse;
import com.androdu.sofra.data.models.region.Region;

import java.util.List;

public interface IClientRegisterFragment {
    interface View {
        void initViews();

        void initListeners();

        void showProgress();

        void hideProgress();

        void onErrorOccurs(String errorMsg);

        void onGetCitiesSuccess(List<City> cities);

        void onGetRegionsSuccess(List<Region> regions);

        void onClientRegisterSuccess(ClientLoginGeneralResponse clientLoginGeneralResponse);

        Activity getActivity();
    }

    interface Presenter {
        void created();
        void getCities();
        void getRegions(int cityId);
        void onLoginButtonClick(String name, String email, String phone, String cityId, String regionId, String password, String imgPath);
    }
}
