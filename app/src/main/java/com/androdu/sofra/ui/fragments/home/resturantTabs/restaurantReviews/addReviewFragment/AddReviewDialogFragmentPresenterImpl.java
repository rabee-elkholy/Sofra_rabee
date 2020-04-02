package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantReviews.addReviewFragment;

import android.util.Log;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.GeneralResponse;
import com.androdu.sofra.data.retrofitApi.ApiClient;
import com.androdu.sofra.data.retrofitApi.IRetrofitApi;
import com.androdu.sofra.utils.ToastCreator;
import com.androdu.sofra.utils.network.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReviewDialogFragmentPresenterImpl implements IAddReviewDialogFragment.Presenter{

    private IAddReviewDialogFragment.View mAddReviewFragmentView;

    public AddReviewDialogFragmentPresenterImpl(IAddReviewDialogFragment.View mAddReviewFragmentView) {
        this.mAddReviewFragmentView = mAddReviewFragmentView;
    }

    @Override
    public void created() {
        mAddReviewFragmentView.initViews();
        mAddReviewFragmentView.initListeners();
    }

    @Override
    public void addReview(int rate, String reviewText, int restaurantId, String apiToken) {
        if (NetworkState.isConnected(mAddReviewFragmentView.getActivity())) {
            final IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
            Call<GeneralResponse> call = retrofitApi.addReview(rate, reviewText, restaurantId, apiToken);
            call.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    mAddReviewFragmentView.hideProgress();
                    if (response.body().getStatus() == 1) {
                        mAddReviewFragmentView.onAddReviewSuccess(response.body().getMsg());
                    } else {
                        mAddReviewFragmentView.onError(response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    Log.d("categories", "failed: " + t.getMessage());
                }
            });
        } else {
            mAddReviewFragmentView.hideProgress();
            ToastCreator.onCreateErrorToast(mAddReviewFragmentView.getActivity(), "تأكد من اتصالك بالانترنت ظظ", R.drawable.cancel);
        }
    }
}
