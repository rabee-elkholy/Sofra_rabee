package com.androdu.sofra.ui.fragments.userCycle.restaurantRegister.firstRegister;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.region.Region;
import com.androdu.sofra.ui.fragments.LocationDialogFragment;
import com.androdu.sofra.utils.HelperMethod;
import com.androdu.sofra.utils.ToastCreator;
import com.androdu.sofra.utils.Validation;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstRegisterFragment extends Fragment implements IFirstRegisterFragment.View {

    private View view;
    private EditText registerFragmentEdtRestaurantName,
            registerFragmentEdtEmail,
            registerFragmentEdtDeliveryTime,
            registerFragmentEdtCity,
            registerFragmentEdtRegion,
            registerFragmentEdtPassword,
            registerFragmentEdtConfirmPassword,
            registerFragmentEdtMinCharger,
            registerFragmentEdtDeliveryCost;
    private Button registerFragmentBtnConfirm;
    private ProgressBar registerFragmentProgressBar;
    private FirstRegisterPresenterImpl registerFragmentPresenter;
    private List<City> cities = new ArrayList<>();
    private List<Region> regions = new ArrayList<>();
    private int cityId = 0;
    private int regionId = 0;

    public FirstRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first_register, container, false);
        registerFragmentPresenter = new FirstRegisterPresenterImpl(this);
        registerFragmentPresenter.created();
        registerFragmentPresenter.getCities();

        return view;
    }

    @Override
    public void initViews() {
        registerFragmentEdtRestaurantName = view.findViewById(R.id.login_fragment_edt_restaurant_name);
        registerFragmentEdtEmail = view.findViewById(R.id.login_fragment_edt_email);
        registerFragmentEdtDeliveryTime = view.findViewById(R.id.login_fragment_edt_delivery_time);
        registerFragmentEdtCity = view.findViewById(R.id.login_fragment_Edt_city);
        registerFragmentEdtRegion = view.findViewById(R.id.login_fragment_edt_region);
        registerFragmentEdtPassword = view.findViewById(R.id.login_fragment_edt_password);
        registerFragmentEdtConfirmPassword = view.findViewById(R.id.login_fragment_edt_confirm_password);
        registerFragmentEdtMinCharger = view.findViewById(R.id.login_fragment_edt_minimum_charger);
        registerFragmentEdtDeliveryCost = view.findViewById(R.id.login_fragment_edt_delivery_cost);
        registerFragmentBtnConfirm = view.findViewById(R.id.login_fragment_btn_confirm);
        registerFragmentProgressBar = (ProgressBar) view.findViewById(R.id.register_fragment_pb_progress);

    }

    @Override
    public void initListeners() {
        registerFragmentEdtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.disappearKeypad(getActivity(), v);
                LocationDialogFragment locationDialogFragment = new LocationDialogFragment(
                        getActivity(),
                        new LocationDialogFragment.ILocationDialog() {
                            @Override
                            public void onSelect(int id, String name) {
                                registerFragmentEdtCity.setText(name);
                                cityId = id;
                                registerFragmentPresenter.getRegions(cityId);
                            }
                        },
                        cities,
                        null,
                        "اختر المدينة:            "
                );
                locationDialogFragment.show(getActivity().getSupportFragmentManager(), "city");
            }
        });

        registerFragmentEdtRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.disappearKeypad(getActivity(), v);
                if (registerFragmentEdtCity.getText().toString().isEmpty()) {
                    ToastCreator.onCreateErrorToast(getActivity(), "اختر المدينة اولا");
                } else {
                    LocationDialogFragment locationDialogFragment = new LocationDialogFragment(
                            getActivity(),
                            new LocationDialogFragment.ILocationDialog() {
                                @Override
                                public void onSelect(int id, String name) {
                                    registerFragmentEdtRegion.setText(name);
                                    regionId = id;
                                }
                            },
                            null,
                            regions,
                            "اختر الحي:            "
                    );
                    locationDialogFragment.show(getActivity().getSupportFragmentManager(), "city");
                }
            }
        });

        registerFragmentBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), cityId + " : " + regionId, Toast.LENGTH_SHORT).show();
                if (Validation.registerValidator1(getActivity(),
                        regions.size(),
                        registerFragmentEdtRestaurantName,
                        registerFragmentEdtEmail,
                        registerFragmentEdtDeliveryTime,
                        registerFragmentEdtCity,
                        registerFragmentEdtRegion,
                        registerFragmentEdtPassword,
                        registerFragmentEdtConfirmPassword,
                        registerFragmentEdtMinCharger,
                        registerFragmentEdtDeliveryCost)) {
                    ToastCreator.onCreateSuccessToast(getActivity(), "كله زا الفل");
                    Bundle restDataBundle = new Bundle();
                    restDataBundle.putString("restaurant_name", registerFragmentEdtRestaurantName.getText().toString());
                    restDataBundle.putString("restaurant_email", registerFragmentEdtEmail.getText().toString());
                    restDataBundle.putInt("delivery_time", Integer.parseInt(registerFragmentEdtDeliveryTime.getText().toString()));
                    restDataBundle.putString("restaurant_city_id", String.valueOf(cityId));
                    restDataBundle.putString("restaurant_region_id", String.valueOf(regionId));
                    restDataBundle.putString("password", registerFragmentEdtPassword.getText().toString().trim());
                    restDataBundle.putString("minimum_charger", registerFragmentEdtMinCharger.getText().toString());
                    restDataBundle.putString("delivery_cost", registerFragmentEdtDeliveryCost.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_secondRegisterFragment, restDataBundle);

                }
            }
        });

    }


    @Override
    public void showProgress() {
        registerFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        registerFragmentProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void onErrorOccurs(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetCitiesSuccess(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public void onGetRegionsSuccess(List<Region> regions) {
        regionId = 0;
        registerFragmentEdtRegion.setText("");
        if (regions == null) {
            this.regions = new ArrayList<>();
            registerFragmentEdtRegion.setVisibility(View.GONE);
        } else {
            this.regions = regions;
            registerFragmentEdtRegion.setVisibility(View.VISIBLE);
        }
    }

}