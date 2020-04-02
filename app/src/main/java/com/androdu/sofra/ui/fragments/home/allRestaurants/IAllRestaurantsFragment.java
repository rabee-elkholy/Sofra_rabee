package com.androdu.sofra.ui.fragments.home.allRestaurants;

import android.app.Activity;

import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.restaurant.RestaurantsGeneralRequest;

import java.util.List;

public interface IAllRestaurantsFragment {

    interface View {
        void initViews();
        void initValues();
        void initListeners();
        void showProgress();
        void hideProgress();
        void showLoadMore();
        void hideLoadMore();
        void hideRefresh();
        void setMsg(String msg);
        void onGetAllRestaurantsSuccess(RestaurantsGeneralRequest restaurantGeneralRequest, int pageNum);

        void onGetCitiesSuccess(List<City> cities);

        void onGetRestaurantsFailed(String errorMsg);
        Activity getActivity();
    }

    interface Presenter {
        void created();
        void getAllRestaurants(int pageNum, String name, int cityId, boolean isFiltered);
        void getCities();
    }
}
