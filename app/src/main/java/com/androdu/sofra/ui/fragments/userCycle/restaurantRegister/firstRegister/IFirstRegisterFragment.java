package com.androdu.sofra.ui.fragments.userCycle.restaurantRegister.firstRegister;

import android.app.Activity;

import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.region.Region;

import java.util.List;

public interface IFirstRegisterFragment {
    interface View {
        void initViews();

        void initListeners();

        void showProgress();

        void hideProgress();

        void onErrorOccurs(String errorMsg);

        void onGetCitiesSuccess(List<City> cities);

        void onGetRegionsSuccess(List<Region> regions);

        Activity getActivity();
    }

    interface Presenter {
        void created();
        void getCities();
        void getRegions(int cityId);
    }
}
