package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.restaurant.Restaurant;
import com.androdu.sofra.data.models.restaurant.RestaurantDetailsGeneralResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailsFragment extends Fragment implements IRestaurantDetailsFragment.View {
    private TextView restaurantDetailsFragmentTvAvailability,
            restaurantDetailsFragmentTvCity,
            restaurantDetailsFragmentTvRegion,
            restaurantDetailsFragmentTvMinimumCharger,
            restaurantDetailsFragmentTvDeliveryCost,
            restaurantDetailsFragmentTvMsg;

    private ProgressBar restaurantDetailsFragmentPbProgressBar;
    private View view;
    private int restaurantId;
    private LinearLayout restaurantDetailsFragmentLlContainer;


    public RestaurantDetailsFragment() {
        // Required empty public constructor
    }

    public RestaurantDetailsFragment(int restaurantId) {
        this.restaurantId = restaurantId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        RestaurantDetailsPresenterImpl presenter = new RestaurantDetailsPresenterImpl(this);
        presenter.created();
        presenter.getRestaurantDetails(restaurantId);

        return view;
    }

    @Override
    public void initViews() {
        restaurantDetailsFragmentTvAvailability = view.findViewById(R.id.restaurant_details_fragment_tv_availability);
        restaurantDetailsFragmentTvCity = view.findViewById(R.id.restaurant_details_fragment_tv_city);
        restaurantDetailsFragmentTvRegion = view.findViewById(R.id.restaurant_details_fragment_tv_region);
        restaurantDetailsFragmentTvMinimumCharger = view.findViewById(R.id.restaurant_details_fragment_tv_minimum_charger);
        restaurantDetailsFragmentTvDeliveryCost = view.findViewById(R.id.restaurant_details_fragment_tv_delivery_cost);
        restaurantDetailsFragmentTvMsg = view.findViewById(R.id.restaurants_details_fragment_tv_msg);
        restaurantDetailsFragmentPbProgressBar = view.findViewById(R.id.restaurant_details_fragment_pb_progress);
        restaurantDetailsFragmentLlContainer = view.findViewById(R.id.restaurant_details_fragment_ll_container);
    }

    @Override
    public void showProgress() {
        restaurantDetailsFragmentPbProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        restaurantDetailsFragmentPbProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMsg(String msg) {
        restaurantDetailsFragmentTvMsg.setText(msg);
    }

    @Override
    public void onGetRestaurantDetailsSuccess(RestaurantDetailsGeneralResponse restaurantDetailsGeneralResponse) {
        restaurantDetailsFragmentLlContainer.setVisibility(View.VISIBLE);
        Restaurant restaurant = restaurantDetailsGeneralResponse.getRestaurant();

        if (restaurant.getAvailability().equals("open"))
            restaurantDetailsFragmentTvAvailability.setText("مفتوح");
        else
            restaurantDetailsFragmentTvAvailability.setText("مغلق");

        restaurantDetailsFragmentTvCity.setText(restaurant.getRegion().getCity().getName());
        restaurantDetailsFragmentTvRegion.setText(restaurant.getRegion().getName());
        restaurantDetailsFragmentTvMinimumCharger.setText(restaurant.getMinimumCharger() + " ج");
        restaurantDetailsFragmentTvDeliveryCost.setText(restaurant.getDeliveryCost() + " ج");
    }

    @Override
    public void onError(String errorMsg) {

    }
}
