package com.androdu.sofra.ui.fragments.userCycle.clientRegister;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androdu.sofra.R;
import com.androdu.sofra.data.local.SharedPreferencesManger;
import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.client.ClientLoginGeneralResponse;
import com.androdu.sofra.data.models.region.Region;
import com.androdu.sofra.ui.fragments.LocationDialogFragment;
import com.androdu.sofra.utils.Constants;
import com.androdu.sofra.utils.HelperMethod;
import com.androdu.sofra.utils.ToastCreator;
import com.androdu.sofra.utils.Validation;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static com.androdu.sofra.data.local.SharedPreferencesManger.TYPE;
import static com.androdu.sofra.data.local.SharedPreferencesManger.USER_PASSWORD;
import static com.androdu.sofra.utils.Constants.PICK_IMAGE_GALLERY_REQUEST;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientRegisterFragment extends Fragment implements IClientRegisterFragment.View {

    private View view;
    private CircleImageView clientRegisterFragmentCivImg;
    private EditText clientRegisterFragmentEdtName,
            clientRegisterFragmentEdtEmail,
            clientRegisterFragmentEdtPhone,
            clientRegisterFragmentEdtCity,
            clientRegisterFragmentEdtRegion,
            clientRegisterFragmentEdtPassword,
            clientRegisterFragmentEdtConfirmPassword;
    private Button clientRegisterFragmentBtnLogin;
    private ProgressBar clientRegisterFragmentProgressBar;
    private ClientRegisterPresenterImpl clientRegisterFragmentPresenter;
    private List<City> cities = new ArrayList<>();
    private List<Region> regions = new ArrayList<>();
    private int cityId = 0;
    private int regionId = 0;
    private String imgPath = "";
    private Bitmap imageBitmap;

    public ClientRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_client_register, container, false);
        clientRegisterFragmentPresenter = new ClientRegisterPresenterImpl(this);
        clientRegisterFragmentPresenter.created();
        clientRegisterFragmentPresenter.getCities();
        HelperMethod.requestPermission(getActivity());
        return view;
    }

    @Override
    public void initViews() {
        clientRegisterFragmentCivImg = view.findViewById(R.id.client_register_fragment_civ_client_photo);
        clientRegisterFragmentEdtName = view.findViewById(R.id.client_register_fragment_edt_client_name);
        clientRegisterFragmentEdtEmail = view.findViewById(R.id.client_register_fragment_edt_client_email);
        clientRegisterFragmentEdtPhone = view.findViewById(R.id.client_register_fragment_edt_phone_number);
        clientRegisterFragmentEdtCity = view.findViewById(R.id.client_register_fragment_Edt_city);
        clientRegisterFragmentEdtRegion = view.findViewById(R.id.client_register_fragment_edt_region);
        clientRegisterFragmentEdtPassword = view.findViewById(R.id.client_register_fragment_edt_password);
        clientRegisterFragmentEdtConfirmPassword = view.findViewById(R.id.client_register_fragment_edt_confirm_password);
        clientRegisterFragmentBtnLogin = view.findViewById(R.id.client_register_fragment_btn_login);
        clientRegisterFragmentProgressBar = view.findViewById(R.id.client_register_fragment_pb_progress);
    }

    @Override
    public void initListeners() {
        clientRegisterFragmentCivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_IMAGE_GALLERY_REQUEST);
            }
        });

        clientRegisterFragmentEdtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.disappearKeypad(getActivity(), v);
                LocationDialogFragment locationDialogFragment = new LocationDialogFragment(
                        getActivity(),
                        new LocationDialogFragment.ILocationDialog() {
                            @Override
                            public void onSelect(int id, String name) {
                                clientRegisterFragmentEdtCity.setText(name);
                                cityId = id;
                                clientRegisterFragmentPresenter.getRegions(cityId);
                            }
                        },
                        cities,
                        null,
                        "اختر المدينة:            "
                );
                locationDialogFragment.show(getActivity().getSupportFragmentManager(), "city");
            }
        });

        clientRegisterFragmentEdtRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.disappearKeypad(getActivity(), v);
                if (clientRegisterFragmentEdtCity.getText().toString().isEmpty()) {
                    ToastCreator.onCreateErrorToast(getActivity(), "اختر المدينة اولا");
                } else {
                    LocationDialogFragment locationDialogFragment = new LocationDialogFragment(
                            getActivity(),
                            new LocationDialogFragment.ILocationDialog() {
                                @Override
                                public void onSelect(int id, String name) {
                                    clientRegisterFragmentEdtRegion.setText(name);
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

        clientRegisterFragmentBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), cityId + " : " + regionId, Toast.LENGTH_SHORT).show();
                if (!imgPath.isEmpty()) {
                    if (Validation.registerValidator1(getActivity(),
                            regions.size(),
                            clientRegisterFragmentEdtName,
                            clientRegisterFragmentEdtEmail,
                            clientRegisterFragmentEdtPhone,
                            clientRegisterFragmentEdtCity,
                            clientRegisterFragmentEdtRegion,
                            clientRegisterFragmentEdtPassword,
                            clientRegisterFragmentEdtConfirmPassword)) {
                        ToastCreator.onCreateSuccessToast(getActivity(), "كله زا الفل");
                        clientRegisterFragmentPresenter.onLoginButtonClick(
                                clientRegisterFragmentEdtName.getText().toString().trim(),
                                clientRegisterFragmentEdtEmail.getText().toString().trim(),
                                clientRegisterFragmentEdtPhone.getText().toString().trim(),
                                String.valueOf(cityId),
                                String.valueOf(regionId),
                                clientRegisterFragmentEdtPassword.getText().toString().trim(),
                                imgPath);
                    }
                }else {
                    ToastCreator.onCreateErrorToast(getActivity(), "الصورة");
                }
            }
        });

    }

    @Override
    public void showProgress() {
        clientRegisterFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        clientRegisterFragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onErrorOccurs(String errorMsg) {
        ToastCreator.onCreateErrorToast(getActivity(), errorMsg, R.drawable.cancel);
    }

    @Override
    public void onGetCitiesSuccess(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public void onGetRegionsSuccess(List<Region> regions) {
        regionId = 0;
        clientRegisterFragmentEdtRegion.setText("");
        if (regions == null) {
            this.regions = new ArrayList<>();
            clientRegisterFragmentEdtRegion.setVisibility(View.GONE);
        } else {
            this.regions = regions;
            clientRegisterFragmentEdtRegion.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClientRegisterSuccess(ClientLoginGeneralResponse clientLoginGeneralResponse) {
        SharedPreferencesManger.SaveData(getActivity(), USER_PASSWORD, clientRegisterFragmentEdtPassword.getText().toString().trim());
        SharedPreferencesManger.SaveData(getActivity(), TYPE, Constants.USER_TYPE_RESTAURANT);
        SharedPreferencesManger.saveClientData(getActivity(), clientLoginGeneralResponse.getData());
        ToastCreator.onCreateSuccessToast(getActivity(), clientLoginGeneralResponse.getMsg(), R.drawable.checked);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }


        if (data != null) {
            imgPath = HelperMethod.getRealPathFromURIPath(data.getData(), getActivity());
            Glide.with(getActivity())
                    .load(imgPath)
                    .into(clientRegisterFragmentCivImg);
            clientRegisterFragmentCivImg.setBackground(null);

        }

    }
}
