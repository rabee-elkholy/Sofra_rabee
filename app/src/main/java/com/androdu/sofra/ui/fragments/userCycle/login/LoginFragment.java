package com.androdu.sofra.ui.fragments.userCycle.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.androdu.sofra.R;
import com.androdu.sofra.data.local.SharedPreferencesManger;
import com.androdu.sofra.data.models.client.ClientLoginGeneralResponse;
import com.androdu.sofra.data.models.restaurant.RestaurantLoginGeneralRequest;
import com.androdu.sofra.ui.activities.UserCycleActivity;
import com.androdu.sofra.utils.Constants;
import com.androdu.sofra.utils.ToastCreator;
import com.androdu.sofra.utils.Validation;
import com.androdu.sofra.utils.network.NetworkState;

import static com.androdu.sofra.data.local.SharedPreferencesManger.TYPE;
import static com.androdu.sofra.data.local.SharedPreferencesManger.USER_PASSWORD;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements ILoginFragment.View {

    private View view;
    private EditText loginFragmentEdtEmail, loginFragmentEdtPassword;
    private TextView loginFragmentTvForgotPassword, loginFragmentTvCreateAccount;
    private Button loginFragmentBtnLogin;
    private ProgressBar loginFragmentProgressBar;
    private LoginPresenterImpl loginFragmentPresenter;
    private int userType;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        userType = ((UserCycleActivity) getActivity()).userType;
        Toast.makeText(getActivity(), ": " + userType, Toast.LENGTH_SHORT).show();
        loginFragmentPresenter = new LoginPresenterImpl(this);
        loginFragmentPresenter.created();
        return view;
    }

    @Override
    public void initViews() {
        loginFragmentEdtEmail = view.findViewById(R.id.login_fragment_til_email);
        loginFragmentEdtPassword = view.findViewById(R.id.login_fragment_til_password);
        loginFragmentTvForgotPassword = view.findViewById(R.id.login_fragment_tv_forget_password);
        loginFragmentTvCreateAccount = view.findViewById(R.id.login_fragment_tv_create_account);
        loginFragmentBtnLogin = view.findViewById(R.id.login_fragment_btn_login);
        loginFragmentProgressBar = view.findViewById(R.id.login_fragment_pb_progress);
    }

    @Override
    public void initListeners() {
        loginFragmentBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkState.isConnected(getActivity())) {
                    if (Validation.validationLength(getActivity(), loginFragmentEdtEmail.getText().toString().trim(), "أدخل البريد الاليكروني !")
                            && Validation.validationLength(getActivity(), loginFragmentEdtPassword.getText().toString().trim(), "أدخل كلمة المرور !")
                            && Validation.validationLength(getActivity(), loginFragmentEdtPassword.getText().toString().trim(), "يجب ان تكون كلمة المرور اكبر من 7 حروف.", 2)) {
                        loginFragmentPresenter.loginButtonClick(loginFragmentEdtEmail.getText().toString().trim(), loginFragmentEdtPassword.getText().toString().trim(), userType);
                    }
                } else {
                    ToastCreator.onCreateErrorToast(getActivity(), "أنت غير متصل بالانترنت ياباشا!", R.drawable.cancel);
                }
            }

        });

        loginFragmentTvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        loginFragmentTvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType == Constants.USER_TYPE_RESTAURANT) {
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
                }else {
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_clientRegisterFragment);
                }
            }
        });

    }

    @Override
    public void showProgress() {
        loginFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loginFragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRestaurantLoginSuccess(RestaurantLoginGeneralRequest restaurantLoginGeneralRequest) {
        SharedPreferencesManger.SaveData(getActivity(), USER_PASSWORD, loginFragmentEdtPassword.getText().toString().trim());
        SharedPreferencesManger.SaveData(getActivity(), TYPE, Constants.USER_TYPE_RESTAURANT);
        SharedPreferencesManger.saveRestaurantData(getActivity(), restaurantLoginGeneralRequest.getData());
        ToastCreator.onCreateSuccessToast(getActivity(), restaurantLoginGeneralRequest.getMsg(), R.drawable.checked);
    }

    @Override
    public void onClientLoginSuccess(ClientLoginGeneralResponse clientLoginGeneralResponse) {
        SharedPreferencesManger.SaveData(getActivity(), USER_PASSWORD, loginFragmentEdtPassword.getText().toString().trim());
        SharedPreferencesManger.SaveData(getActivity(), TYPE, Constants.USER_TYPE_CLIENT);
        SharedPreferencesManger.saveClientData(getActivity(), clientLoginGeneralResponse.getData());
        ToastCreator.onCreateSuccessToast(getActivity(), clientLoginGeneralResponse.getMsg(), R.drawable.checked);
        getActivity().finish();
    }

    @Override
    public void onLoginFailed(String errorMsg) {
        ToastCreator.onCreateErrorToast(getActivity(), errorMsg, R.drawable.cancel);
    }
}
