package com.androdu.sofra.ui.fragments.home.allRestaurants;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.androdu.sofra.adapter.AllRestaurantsAdapter;
import com.androdu.sofra.data.local.room.RoomManger;
import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.restaurant.Restaurant;
import com.androdu.sofra.data.models.restaurant.RestaurantsGeneralRequest;
import com.androdu.sofra.ui.activities.HomeActivity;
import com.androdu.sofra.ui.fragments.LocationDialogFragment;
import com.androdu.sofra.utils.HelperMethod;
import com.androdu.sofra.utils.OnEndLess;
import com.androdu.sofra.utils.ToastCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllRestaurantsFragment extends Fragment implements IAllRestaurantsFragment.View {

    private View view;
    private ProgressBar allRestaurantsFragmentProgressBar;
    private RecyclerView allRestaurantsFragmentRestaurantsList;
    private AllRestaurantsAdapter allRestaurantsAdapter;
    private RelativeLayout allRestaurantsFragmentLoadMore;
    private EditText allRestaurantsFragmentEdtRestaurantName, allRestaurantsFragmentEdtCity;
    private ImageButton allRestaurantsFragmentIbtnFilter;
    private SwipeRefreshLayout allRestaurantsFragmentRefresh;
    private TextView allRestaurantsFragmentTvMsg;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private LinearLayoutManager linearLayout;
    private AllRestaurantsPresenterImpl allRestaurantsFragmentPresenter;
    private List<Restaurant> restaurantsDataList = new ArrayList<>();
    private List<City> cities;
    private int cityId = 0;
    private String keyWord = "";
    private boolean isFiltered = false;


    public AllRestaurantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_restaurants, container, false);
        HomeActivity homeActivity = (HomeActivity) getActivity();
        TextView appBarTitle = homeActivity.appBar.findViewById(R.id.app_bar_tv_title);
        appBarTitle.setText("المطاعم");
        allRestaurantsFragmentPresenter = new AllRestaurantsPresenterImpl(this);
        allRestaurantsFragmentPresenter.created();
        allRestaurantsFragmentPresenter.getCities();


        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                RoomManger.getInstance(getActivity()).roomDao().deleteAll();
                Log.d("room", "run: room items deleted.");
            }
        });
        return view;
    }

    @Override
    public void initViews() {
        allRestaurantsFragmentRestaurantsList = view.findViewById(R.id.all_restaurants_fragment_recycler_view);
        allRestaurantsFragmentProgressBar = view.findViewById(R.id.all_restaurants_fragment_pb_progress);
        allRestaurantsFragmentLoadMore = view.findViewById(R.id.load_more);
        allRestaurantsFragmentEdtRestaurantName = view.findViewById(R.id.all_restaurants_fragment_edt_name);
        allRestaurantsFragmentEdtCity = view.findViewById(R.id.all_restaurants_fragment_edt_city);
        allRestaurantsFragmentIbtnFilter = view.findViewById(R.id.all_restaurants_fragment_ibtn_filter);
        allRestaurantsFragmentRefresh = view.findViewById(R.id.all_restaurants_fragment_srl_swipe_refresh_layout);
        allRestaurantsFragmentTvMsg = view.findViewById(R.id.all_restaurants_fragment_tv_msg);

        restaurantsDataList = new ArrayList<>();
        allRestaurantsAdapter = new AllRestaurantsAdapter(getActivity(), restaurantsDataList);
        allRestaurantsFragmentRestaurantsList.setAdapter(allRestaurantsAdapter);

    }

    @Override
    public void initListeners() {
        linearLayout = new LinearLayoutManager(getActivity());
        allRestaurantsFragmentRestaurantsList.setLayoutManager(linearLayout);
        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        showLoadMore();
                        allRestaurantsFragmentPresenter.getAllRestaurants(current_page, keyWord, cityId, isFiltered);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        allRestaurantsFragmentRestaurantsList.addOnScrollListener(onEndLess);

        allRestaurantsFragmentEdtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cities == null) {
                    allRestaurantsFragmentPresenter.getCities();
                } else {
                    HelperMethod.disappearKeypad(getActivity(), v);
                    List<City> cities2 = new ArrayList<>();
                    City city = new City();
                    city.setName("الكل");
                    city.setId(0);
                    cities2.add(city);
                    cities2.addAll(cities);
                    LocationDialogFragment locationDialogFragment = new LocationDialogFragment(
                            getActivity(),
                            new LocationDialogFragment.ILocationDialog() {
                                @Override
                                public void onSelect(int id, String name) {
                                    allRestaurantsFragmentEdtCity.setText(name);
                                    cityId = id;
                                }
                            },
                            cities2,
                            null,
                            "اختر المدينة:            "
                    );
                    locationDialogFragment.show(getActivity().getSupportFragmentManager(), "city");
                }
            }
        });

        allRestaurantsFragmentIbtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cities == null) {
                    showProgress();
                    allRestaurantsFragmentPresenter.getCities();
                } else {
                    if (cityId != 0 || !allRestaurantsFragmentEdtRestaurantName.getText().toString().isEmpty()) {
                        showProgress();
                        HelperMethod.disappearKeypad(getActivity(), view);
                        isFiltered = true;
                        keyWord = allRestaurantsFragmentEdtRestaurantName.getText().toString().trim();
                        allRestaurantsFragmentPresenter.getAllRestaurants(1, keyWord, cityId, isFiltered);
                    }
                }
            }
        });

        allRestaurantsFragmentRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (cities == null) {
                    allRestaurantsFragmentPresenter.getCities();
                } else {
                    HelperMethod.disappearKeypad(getActivity(), view);
                    allRestaurantsFragmentPresenter.getAllRestaurants(1, keyWord, cityId, isFiltered);
                }
            }
        });


    }

    @Override
    public void showProgress() {
        allRestaurantsFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        allRestaurantsFragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMore() {
        allRestaurantsFragmentLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadMore() {
        allRestaurantsFragmentLoadMore.setVisibility(View.GONE);
    }

    @Override
    public void hideRefresh() {
        allRestaurantsFragmentRefresh.setRefreshing(false);
    }

    @Override
    public void setMsg(String msg) {
        allRestaurantsFragmentTvMsg.setText(msg);
    }

    @Override
    public void onGetAllRestaurantsSuccess(RestaurantsGeneralRequest restaurantGeneralRequest, int pageNum) {

        if (pageNum == 1)
            initValues();

        maxPage = restaurantGeneralRequest.getData().getLastPage();
        restaurantsDataList.addAll(restaurantGeneralRequest.getData().getRestaurantsList());
        allRestaurantsAdapter.notifyDataSetChanged();

        allRestaurantsAdapter.SetOnItemClickListener(new AllRestaurantsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Restaurant model) {
                ToastCreator.onCreateSuccessToast(getActivity(), model.getRegion().getCity().getName());
                Bundle bundle = new Bundle();
                bundle.putInt("restaurantId", model.getId());
                bundle.putString("restaurantName", model.getName());
                bundle.putString("photoUrl", model.getPhotoUrl());
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_restaurantsFragment_to_restaurantTabsFragment, bundle);
            }
        });

    }

    @Override
    public void onGetCitiesSuccess(List<City> cities) {
        this.cities = cities;
        allRestaurantsFragmentPresenter.getAllRestaurants(1, keyWord, cityId, isFiltered);

    }

    @Override
    public void onGetRestaurantsFailed(String errorMsg) {

    }

    @Override
    public void initValues() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        restaurantsDataList = new ArrayList<>();
        allRestaurantsAdapter = new AllRestaurantsAdapter(getActivity(), restaurantsDataList);
        allRestaurantsFragmentRestaurantsList.setAdapter(allRestaurantsAdapter);
    }

}
