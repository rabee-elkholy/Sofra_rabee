package com.androdu.sofra.ui.fragments.home.myCart;

import com.androdu.sofra.data.local.room.Item;
import com.androdu.sofra.data.local.room.RoomDao;

import java.util.List;
import java.util.concurrent.Executors;

import static com.androdu.sofra.data.local.room.RoomManger.getInstance;

public class MyCartFragmentPresenterImpl implements IMyCartFragment.Presenter{
    IMyCartFragment.View mMyCartView;

    MyCartFragmentPresenterImpl(IMyCartFragment.View mMyCartView) {
        this.mMyCartView = mMyCartView;
    }

    @Override
    public void onCreated() {
        mMyCartView.initViews();
        mMyCartView.initListeners();
    }

    @Override
    public void getCartItems() {
        final RoomDao roomDao = getInstance(mMyCartView.getActivity()).roomDao();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Item> items = roomDao.getAll();
                mMyCartView.onGetItemsSuccess(items);
            }
        });
    }
}
