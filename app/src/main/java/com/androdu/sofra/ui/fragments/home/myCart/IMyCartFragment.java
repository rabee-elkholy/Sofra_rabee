package com.androdu.sofra.ui.fragments.home.myCart;

import android.app.Activity;

import com.androdu.sofra.data.local.room.Item;

import java.util.List;

public interface IMyCartFragment {
    interface View {
        void initViews();

        void initListeners();

        void onGetItemsSuccess(List<Item> items);

        void onError(String msg);

        Activity getActivity();
    }

    interface Presenter {
        void onCreated();
        void getCartItems();
    }
}
