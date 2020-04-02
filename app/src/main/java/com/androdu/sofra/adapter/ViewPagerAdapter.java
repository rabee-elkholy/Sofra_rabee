package com.androdu.sofra.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.androdu.sofra.ui.fragments.home.resturantTabs.restaurant.RestaurantFragment;
import com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantDetails.RestaurantDetailsFragment;
import com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantReviews.RestaurantReviewsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int restaurantId;

    private String restaurantName, photoUrl;

    public ViewPagerAdapter(FragmentManager fragmentManager, int restaurantId, String restaurantName, String photoUrl) {
        super(fragmentManager);
        this.restaurantId = restaurantId;
        this.photoUrl = photoUrl;
        this.restaurantName = restaurantName;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RestaurantFragment(restaurantId);
            case 1:
                return new RestaurantReviewsFragment(restaurantId, restaurantName, photoUrl);
            case 2:
                return new RestaurantDetailsFragment(restaurantId);
        }
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
            title = "قائمة الطعام";
        }
        else if (position == 1) {
            title = "التقييمات";
        }
        else if (position == 2) {
            title = "حول المطعم";
        }
        return title;
    }
}
