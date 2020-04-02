package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androdu.sofra.R;
import com.androdu.sofra.adapter.RestaurantCategoriesAdapter;
import com.androdu.sofra.adapter.RestaurantItemsAdapter;
import com.androdu.sofra.data.models.restaurantCategory.RestaurantCategoriesGeneralResponse;
import com.androdu.sofra.data.models.restaurantCategory.RestaurantCategory;
import com.androdu.sofra.data.models.retaurantItem.RestaurantItem;
import com.androdu.sofra.data.models.retaurantItem.RestaurantItemsGeneralResponse;
import com.androdu.sofra.utils.HelperMethod;
import com.androdu.sofra.utils.OnEndLess;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment implements IRestaurantFragment.View {
    private int restaurantId;
    private RecyclerView restaurantFragmentRcCategories, restaurantFragmentRcItems;
    private RestaurantCategoriesAdapter restaurantFragmentCategoriesAdapter;
    private RestaurantItemsAdapter restaurantFragmentItemsAdapter;
    List<RestaurantCategory> categoriesList;
    List<RestaurantItem> itemsList;
    private RestaurantPresenterImpl restaurantPresenter;
    private ProgressBar restaurantFragmentProgressBar;
    private TextView restaurantFragmentTvMsg;
    private RelativeLayout restaurantFragmentLoadMore;
    private SwipeRefreshLayout restaurantFragmentRefresh;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;

    private static RestaurantFragment restaurantFragment = null;

    public static RestaurantFragment newInstance(int restaurantId) {
        if (restaurantFragment == null)
            restaurantFragment = new RestaurantFragment(restaurantId);

        return restaurantFragment;
    }


    private View view;

    private int currentCategoryId = 0;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    public RestaurantFragment(int restaurantId) {
        this.restaurantId = restaurantId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        restaurantPresenter = new RestaurantPresenterImpl(this);
        restaurantPresenter.created();
        restaurantPresenter.getCategories(restaurantId);

        return view;
    }

    @Override
    public void initViews() {
        restaurantFragmentRcCategories = view.findViewById(R.id.restaurant_fragment_rc_categories);
        restaurantFragmentRcItems = view.findViewById(R.id.restaurant_fragment_rc_items);
        restaurantFragmentRefresh = view.findViewById(R.id.restaurant_fragment_srl_swipe_refresh_layout);
        restaurantFragmentLoadMore = view.findViewById(R.id.restaurant_fragment_load_more);
        restaurantFragmentProgressBar = view.findViewById(R.id.restaurant_fragment_pb_progress);
        restaurantFragmentTvMsg = view.findViewById(R.id.restaurant_fragment_tv_msg);

        restaurantFragmentRcCategories.getRecycledViewPool().setMaxRecycledViews(0, 0);

    }

    @Override
    public void initValues() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        currentCategoryId = 0;

        categoriesList = new ArrayList<>();

        restaurantFragmentCategoriesAdapter = new RestaurantCategoriesAdapter(getActivity(), categoriesList);

        restaurantFragmentRcCategories.setAdapter(restaurantFragmentCategoriesAdapter);

        restaurantFragmentCategoriesAdapter.SetOnItemClickListener(new RestaurantCategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, RestaurantCategory model) {
                showProgress();
                currentCategoryId = model.getId();
                restaurantPresenter.getRestaurantItems(restaurantId, 1, currentCategoryId);
            }
        });


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initListeners() {
        LinearLayoutManager layoutManagerH = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        restaurantFragmentRcCategories.setLayoutManager(layoutManagerH);

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        restaurantFragmentRcItems.setLayoutManager(linearLayout);

        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        showLoadMore();
                        HelperMethod.disappearKeypad(getActivity(), view);
                        restaurantPresenter.getRestaurantItems(restaurantId, current_page, currentCategoryId);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        restaurantFragmentRcItems.addOnScrollListener(onEndLess);

        restaurantFragmentRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restaurantPresenter.getCategories(restaurantId);
            }
        });

    }

    @Override
    public void showProgress() {
        restaurantFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        restaurantFragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMore() {
        restaurantFragmentLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadMore() {
        restaurantFragmentLoadMore.setVisibility(View.GONE);
    }

    @Override
    public void hideRefresh() {
        restaurantFragmentRefresh.setRefreshing(false);
    }

    @Override
    public void setMsg(String msg) {
        restaurantFragmentTvMsg.setText(msg);
    }

    @Override
    public void onGetRestaurantItemsSuccess(RestaurantItemsGeneralResponse itemsGeneralResponse, int pageNum) {
        if (pageNum == 1) {
            onEndLess.previousTotal = 0;
            onEndLess.current_page = 1;
            onEndLess.previous_page = 1;
            itemsList = new ArrayList<>();
            restaurantFragmentItemsAdapter = new RestaurantItemsAdapter(getActivity(), itemsList);
            restaurantFragmentItemsAdapter.SetOnItemClickListener(new RestaurantItemsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, RestaurantItem model) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", model.getName());
                        bundle.putString("description", model.getDescription());
                        bundle.putString("photo_url", model.getPhotoUrl());
                        bundle.putInt("restaurant_id", Integer.parseInt(model.getRestaurantId()));
                        bundle.putInt("item_id", model.getId());
                        bundle.putFloat("price", Float.parseFloat(model.getPrice()));
                        bundle.putFloat("offer_price", Float.parseFloat(model.getOfferPrice()));
                        bundle.putBoolean("has_offer", model.isHasOffer());

                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.action_restaurantTabsFragment_to_restaurantItemDetailsFragment, bundle);

                    }catch (Exception e) {

                    }
                }
            });


            restaurantFragmentRcItems.setAdapter(restaurantFragmentItemsAdapter);
        }
        maxPage = itemsGeneralResponse.getRestaurantItemsPage().getLastPage();

        itemsList.addAll(itemsGeneralResponse.getRestaurantItemsPage().getRestaurantItems());
        restaurantFragmentItemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetCategoriesSuccess(RestaurantCategoriesGeneralResponse categoriesGeneralResponse) {
        initValues();
        RestaurantCategory fakeCategory = new RestaurantCategory();
        fakeCategory.setName("الكل");
        fakeCategory.setId(0);
        categoriesList.add(fakeCategory);
        categoriesList.addAll(categoriesGeneralResponse.getRestaurantCategories());
        restaurantFragmentCategoriesAdapter.notifyDataSetChanged();

        restaurantPresenter.getRestaurantItems(restaurantId, 1, 0);
    }

    @Override
    public void onError(String errorMsg) {

    }
}
