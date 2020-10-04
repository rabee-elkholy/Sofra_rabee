package com.androdu.sofra.ui.fragments.home.cart.myCart;

import android.app.Activity;

import com.androdu.sofra.data.local.room.cartItem;

import java.util.List;

public interface IMyCartFragment {
    interface View {
        void initViews();

        void initListeners();

        void onGetItemsSuccess(List<cartItem> cartItems);

        void onError(String msg);

        Activity getActivity();
    }

    interface Presenter {
        void onCreated();
        void getCartItems();
    }
}
