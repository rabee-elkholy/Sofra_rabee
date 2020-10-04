package com.androdu.sofra.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class OrdersViewPagerAdapter extends FragmentPagerAdapter {

    public OrdersViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                return new RestaurantFragment(restaurantId);
//            case 1:
//                return new RestaurantReviewsFragment(restaurantId, restaurantName, photoUrl);
//            case 2:
//                return new RestaurantDetailsFragment(restaurantId);
//        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "طلبات جديدة";
        }
        else if (position == 1) {
            title = "طلبات حالية";
        }
        else if (position == 2) {
            title = "طلبات سابقة";
        }
        return title;
    }
}
