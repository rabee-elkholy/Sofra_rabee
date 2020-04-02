package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurant;

import android.app.Activity;

import com.androdu.sofra.data.models.restaurantCategory.RestaurantCategoriesGeneralResponse;
import com.androdu.sofra.data.models.retaurantItem.RestaurantItemsGeneralResponse;

public interface IRestaurantFragment {

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
        void onGetRestaurantItemsSuccess(RestaurantItemsGeneralResponse itemsGeneralResponse, int pageNum);

        void onGetCategoriesSuccess(RestaurantCategoriesGeneralResponse categoriesGeneralResponse);

        void onError(String errorMsg);

        Activity getActivity();
    }

    interface Presenter {
        void created();
        void getRestaurantItems(int restaurantId, int pageNum, int categoryId);
        void getCategories(int restaurantId);
    }
}
