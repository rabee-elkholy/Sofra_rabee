package com.androdu.sofra.ui.fragments.userCycle.restaurantRegister.secondRegister;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androdu.sofra.R;
import com.androdu.sofra.utils.HelperMethod;
import com.androdu.sofra.utils.ToastCreator;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static com.androdu.sofra.utils.Constants.PICK_IMAGE_GALLERY_REQUEST;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondRegisterFragment extends Fragment implements ISecondRegisterFragment.View {
    private Bitmap imageBitmap;

    // Uri indicates, where the image will be picked from
    private Uri imageUri;
    private String imagePath;
    private TextView secondRegisterFragmentEdtPhoneNumber, secondRegisterFragmentEdtWhatsappNumber;
    private ProgressBar registerFragmentProgressBar;
    private CircleImageView secondRegisterFragmentCivRestaurantPhoto;
    private Button secondRegisterFragmentBtnRegister;
    private View view;
    private SecondRegisterPresenterImpl presenter;
    private Bundle restData;

    public SecondRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_second_register, container, false);
        presenter = new SecondRegisterPresenterImpl(this);
        presenter.created();
        restData = getArguments();
        HelperMethod.requestPermission(getActivity());

        return view;
    }

    @Override
    public void initViews() {
        secondRegisterFragmentEdtPhoneNumber = view.findViewById(R.id.second_register_fragment_edt_phone_number);
        secondRegisterFragmentEdtWhatsappNumber = view.findViewById(R.id.second_register_fragment_edt_whatsapp_number);
        secondRegisterFragmentCivRestaurantPhoto = view.findViewById(R.id.second_register_fragment_iv_restaurant_photo);
        secondRegisterFragmentBtnRegister = view.findViewById(R.id.second_register_fragment_btn_login);
        registerFragmentProgressBar = view.findViewById(R.id.second_register_fragment_pb_progress);
    }

    @Override
    public void initListeners() {
        secondRegisterFragmentCivRestaurantPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_IMAGE_GALLERY_REQUEST);
            }
        });

        secondRegisterFragmentBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!secondRegisterFragmentEdtPhoneNumber.getText().toString().isEmpty() &&
                        !secondRegisterFragmentEdtWhatsappNumber.getText().toString().isEmpty() &&
                        !imagePath.isEmpty()) {
                    if (secondRegisterFragmentEdtPhoneNumber.getText().toString().length() == 11 &&
                            secondRegisterFragmentEdtWhatsappNumber.getText().toString().length() == 11) {

                        if (!imagePath.isEmpty()) {
                            ToastCreator.onCreateSuccessToast(getActivity(), "كله تمام يامعلم!", R.drawable.checked);
                            presenter.onLoginButtonClick(restData.getString("restaurant_name"),
                                    restData.getString("restaurant_email"),
                                    restData.getInt("delivery_time"),
                                    restData.getString("restaurant_city_id"),
                                    restData.getString("restaurant_region_id"),
                                    restData.getString("password"),
                                    restData.getString("minimum_charger"),
                                    restData.getString("delivery_cost"),
                                    secondRegisterFragmentEdtPhoneNumber.getText().toString(),
                                    secondRegisterFragmentEdtWhatsappNumber.getText().toString(),
                                    imagePath);
                        } else {
                            ToastCreator.onCreateErrorToast(getActivity(), "يجب اختيار صورة شخصية!", R.drawable.cancel);

                        }

                    } else {
                        ToastCreator.onCreateErrorToast(getActivity(), "يجب ادخال 10 ارقام!", R.drawable.cancel);

                    }

                } else {
                    ToastCreator.onCreateErrorToast(getActivity(), "تأكد من ادخال جميع الحقول!", R.drawable.cancel);

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
    public void onRegisterSuccess(String successMessage) {
        getActivity().onBackPressed();
        getActivity().onBackPressed();
        ToastCreator.onCreateSuccessToast(getActivity(), successMessage, R.drawable.checked);

    }

    @Override
    public void onErrorOccurs(String errorMsg) {
        ToastCreator.onCreateErrorToast(getActivity(), errorMsg, R.drawable.cancel);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (data != null) {
            imagePath = HelperMethod.getRealPathFromURIPath(data.getData(), getActivity());
            Glide.with(getActivity())
                    .load(imagePath)
                    .into(secondRegisterFragmentCivRestaurantPhoto);
            secondRegisterFragmentCivRestaurantPhoto.setBackground(null);
        }
    }
}
