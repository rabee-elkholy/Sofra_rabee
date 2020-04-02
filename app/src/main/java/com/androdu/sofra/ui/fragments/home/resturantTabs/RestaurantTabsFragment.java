package com.androdu.sofra.ui.fragments.home.resturantTabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.androdu.sofra.R;
import com.androdu.sofra.adapter.ViewPagerAdapter;
import com.androdu.sofra.utils.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantTabsFragment extends Fragment {

    private int restaurantId;
    private String restaurantName, photoUrl;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private View view;
    private ViewPagerAdapter viewPagerAdapter;

    public RestaurantTabsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant_tabs, container, false);
        restaurantId = getArguments().getInt("restaurantId");
        restaurantName = getArguments().getString("restaurantName");
        photoUrl = getArguments().getString("photoUrl");

        tabLayout = view.findViewById(R.id.restaurant_tabs_fragment_tl_tabs);
        viewPager = view.findViewById(R.id.restaurant_tabs_fragment_vp_view_pager);

        Log.d("tabs", "onCreateView: ");
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), restaurantId, restaurantName, photoUrl));
        viewPager.disableScroll(true);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
