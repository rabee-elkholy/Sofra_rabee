package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantReviews.addReviewFragment;

import android.app.Activity;

public interface IAddReviewDialogFragment {

    interface View{
        void initViews();
        void initListeners();
        void showProgress();
        void hideProgress();
        void onAddReviewSuccess(String msg);
        void onError(String error);
        Activity getActivity();
    }

    interface Presenter{
        void created();
        void addReview(int rate, String reviewText, int restaurantId, String apiToken);
    }
}
