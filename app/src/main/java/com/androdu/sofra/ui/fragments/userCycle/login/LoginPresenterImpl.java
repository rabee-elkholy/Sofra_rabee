package com.androdu.sofra.ui.fragments.userCycle.login;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.client.ClientLoginGeneralResponse;
import com.androdu.sofra.data.models.restaurant.RestaurantLoginGeneralRequest;
import com.androdu.sofra.data.retrofitApi.ApiClient;
import com.androdu.sofra.data.retrofitApi.IRetrofitApi;
import com.androdu.sofra.utils.Constants;
import com.androdu.sofra.utils.ToastCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenterImpl implements ILoginFragment.Presenter {
    private ILoginFragment.View mLoginView;

    LoginPresenterImpl(ILoginFragment.View mLoginView) {
        this.mLoginView = mLoginView;
    }

    @Override
    public void created() {
        mLoginView.initViews();
        mLoginView.initListeners();
    }

    @Override
    public void loginButtonClick(String email, final String password, int userType) {
            mLoginView.showProgress();
            IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
            if (userType == Constants.USER_TYPE_RESTAURANT) {
                Call<RestaurantLoginGeneralRequest> call = retrofitApi.restaurantLogin(email, password);
                call.enqueue(new Callback<RestaurantLoginGeneralRequest>() {
                    @Override
                    public void onResponse(Call<RestaurantLoginGeneralRequest> call, Response<RestaurantLoginGeneralRequest> response) {
                        mLoginView.hideProgress();
                        try {
                            if (response.body().getStatus() == 1) {
                                mLoginView.onRestaurantLoginSuccess(response.body());
                            } else {
                                mLoginView.onLoginFailed(response.body().getMsg());
                            }

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantLoginGeneralRequest> call, Throwable t) {
                        mLoginView.hideProgress();
                        ToastCreator.onCreateErrorToast(mLoginView.getActivity(), "حدث حطأ برجاء أعادة المحاولة!", R.drawable.cancel);
                    }
                });
            } else {
                Call<ClientLoginGeneralResponse> call = retrofitApi.clientLogin(email, password);
                call.enqueue(new Callback<ClientLoginGeneralResponse>() {
                    @Override
                    public void onResponse(Call<ClientLoginGeneralResponse> call, Response<ClientLoginGeneralResponse> response) {
                        mLoginView.hideProgress();
                        try {
                            if (response.body().getStatus() == 1) {
                                mLoginView.onClientLoginSuccess(response.body());
                            } else {
                                mLoginView.onLoginFailed(response.body().getMsg());
                            }

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<ClientLoginGeneralResponse> call, Throwable t) {
                        mLoginView.hideProgress();
                        ToastCreator.onCreateErrorToast(mLoginView.getActivity(), "حدث حطأ برجاء أعادة المحاولة!", R.drawable.cancel);
                    }
                });
            }

    }


}
