package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantReviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.R;
import com.androdu.sofra.adapter.RestaurantReviewsAdapter;
import com.androdu.sofra.data.local.SharedPreferencesManger;
import com.androdu.sofra.data.models.review.RestaurantReview;
import com.androdu.sofra.data.models.review.RestaurantReviewsGeneralRequest;
import com.androdu.sofra.ui.activities.UserCycleActivity;
import com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantReviews.addReviewFragment.AddReviewDialogFragment;
import com.androdu.sofra.utils.Constants;
import com.androdu.sofra.utils.HelperMethod;
import com.androdu.sofra.utils.OnEndLess;
import com.androdu.sofra.utils.ToastCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantReviewsFragment extends Fragment implements IRestaurantReviewsFragment.View{
    private int restaurantId;
    private RecyclerView restaurantReviewsFragmentRcReviews;
    private Button restaurantReviewsFragmentBtnAddReview;
    private TextView restaurantReviewsFragmentTvMsg;
    private ProgressBar restaurantReviewsFragmentPbProgressBar;
    private RelativeLayout restaurantReviewsFragmentLoadMore;
    private List<RestaurantReview> reviewsList;
    private RestaurantReviewsAdapter restaurantReviewsAdapter;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private View view;
    private RestaurantReviewsPresenterImpl presenter;

    private String name, photoUrl;


    public RestaurantReviewsFragment() {
        // Required empty public constructor
    }

    public RestaurantReviewsFragment(int restaurantId, String name, String photoUrl) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.photoUrl = photoUrl;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant_reviews, container, false);

        presenter = new RestaurantReviewsPresenterImpl(this);
        presenter.created();
        presenter.getRestaurantReviews(restaurantId, 1);
        return view;
    }

    @Override
    public void initViews() {
        restaurantReviewsFragmentRcReviews = view.findViewById(R.id.restaurant_reviews_fragment_rc_reviews);
        restaurantReviewsFragmentBtnAddReview = view.findViewById(R.id.restaurant_reviews_fragment_btn_add_review);
        restaurantReviewsFragmentTvMsg = view.findViewById(R.id.restaurant_reviews_fragment_tv_msg);
        restaurantReviewsFragmentPbProgressBar = view.findViewById(R.id.restaurant_reviews_fragment_pb_progress);
        restaurantReviewsFragmentLoadMore = view.findViewById(R.id.restaurant_reviews_fragment_load_more);


    }

    @Override
    public void initListeners() {
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        restaurantReviewsFragmentRcReviews.setLayoutManager(linearLayout);

        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        showLoadMore();
                        HelperMethod.disappearKeypad(getActivity(), view);
                        presenter.getRestaurantReviews(restaurantId, current_page);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        restaurantReviewsFragmentRcReviews.addOnScrollListener(onEndLess);

        restaurantReviewsFragmentBtnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharedPreferencesManger.loadClientData(getActivity()) != null) {
                    AddReviewDialogFragment addReviewDialogFragment = new AddReviewDialogFragment(
                            RestaurantReviewsFragment.this,
                            restaurantId,
                            name,
                            photoUrl);

                    addReviewDialogFragment.show(getChildFragmentManager(), "sdsds");
                } else {
                    Intent intent = new Intent(getActivity(), UserCycleActivity.class);
                    intent.putExtra("userType", Constants.USER_TYPE_CLIENT);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void showProgress() {
        restaurantReviewsFragmentPbProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        restaurantReviewsFragmentPbProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMore() {
        restaurantReviewsFragmentLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadMore() {
        restaurantReviewsFragmentLoadMore.setVisibility(View.GONE);
    }

    @Override
    public void setMsg(String msg) {
        restaurantReviewsFragmentTvMsg.setText(msg);
    }

    @Override
    public void onGetRestaurantReviewsSuccess(RestaurantReviewsGeneralRequest restaurantReviewsGeneralRequest, int pageNumber) {
        if (pageNumber == 1) {
            onEndLess.previousTotal = 0;
            onEndLess.current_page = 1;
            onEndLess.previous_page = 1;
            reviewsList = new ArrayList<>();
            restaurantReviewsAdapter = new RestaurantReviewsAdapter(getActivity(), reviewsList);
            restaurantReviewsFragmentRcReviews.setAdapter(restaurantReviewsAdapter);
        }
        maxPage = restaurantReviewsGeneralRequest.getData().getLastPage();

        reviewsList.addAll(restaurantReviewsGeneralRequest.getData().getRestaurantReviewList());
        restaurantReviewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddReviewSuccess(String msg) {
        ToastCreator.onCreateSuccessToast(getActivity(), msg, R.drawable.checked);
        presenter.getRestaurantReviews(restaurantId, 1);
    }

    @Override
    public void onError(String errorMsg) {

    }
}
