package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantReviews.addReviewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.androdu.sofra.R;
import com.androdu.sofra.data.local.SharedPreferencesManger;
import com.androdu.sofra.ui.fragments.home.resturantTabs.restaurantReviews.IRestaurantReviewsFragment;
import com.androdu.sofra.utils.ToastCreator;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddReviewDialogFragment extends DialogFragment implements IAddReviewDialogFragment.View {

    private IRestaurantReviewsFragment.View mReviewsFragment;
    private View view;

    private CircleImageView addReviewDialogFragmentCivRestaurantPhoto,
            addReviewDialogFragmentCivRate1,
            addReviewDialogFragmentCivRate2,
            addReviewDialogFragmentCivRate3,
            addReviewDialogFragmentCivRate4,
            addReviewDialogFragmentCivRate5;

    private TextView addReviewDialogFragmentTvRestaurantName;

    private EditText addReviewDialogFragmentEtReviewText;
    private Button addReviewDialogFragmentBtnAddReview;
    private ProgressBar addReviewDialogFragmentPbProgressBar;
    private AddReviewDialogFragmentPresenterImpl reviewDialogFragmentPresenter;

    private String restaurantName, photoUrl;
    private int selectedRate = 5;
    private int restaurantId;


    public AddReviewDialogFragment() {
        // Required empty public constructor
    }

    public AddReviewDialogFragment(IRestaurantReviewsFragment.View mReviewsFragment, int restaurantId, String restaurantName, String photoUrl) {
        this.mReviewsFragment = mReviewsFragment;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.photoUrl = photoUrl;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_review_dialog, container, false);

        reviewDialogFragmentPresenter = new AddReviewDialogFragmentPresenterImpl(this);
        reviewDialogFragmentPresenter.created();


        return view;
    }

    @Override
    public void initViews() {
        addReviewDialogFragmentCivRestaurantPhoto = view.findViewById(R.id.add_review_fragment_civ_restaurant_photo);
        addReviewDialogFragmentTvRestaurantName = view.findViewById(R.id.add_review_fragment_tv_restaurant_name);
        addReviewDialogFragmentCivRate1 = view.findViewById(R.id.add_review_fragment_civ_rate_1);
        addReviewDialogFragmentCivRate2 = view.findViewById(R.id.add_review_fragment_civ_rate_2);
        addReviewDialogFragmentCivRate3 = view.findViewById(R.id.add_review_fragment_civ_rate_3);
        addReviewDialogFragmentCivRate4 = view.findViewById(R.id.add_review_fragment_civ_rate_4);
        addReviewDialogFragmentCivRate5 = view.findViewById(R.id.add_review_fragment_civ_rate_5);
        addReviewDialogFragmentEtReviewText = view.findViewById(R.id.add_review_fragment_et_review_text);
        addReviewDialogFragmentBtnAddReview = view.findViewById(R.id.add_review_fragment_btn_add_review);
        addReviewDialogFragmentPbProgressBar = view.findViewById(R.id.add_review_fragment_pb_progress_bar);

        Glide.with(getActivity())
                .load(photoUrl)
                .into(addReviewDialogFragmentCivRestaurantPhoto);

        addReviewDialogFragmentTvRestaurantName.setText(restaurantName);
    }

    @Override
    public void initListeners() {
        addReviewDialogFragmentCivRate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRate = 1;
                addReviewDialogFragmentCivRate1.setBackground(getActivity().getDrawable(R.drawable.shape_btn4));
                addReviewDialogFragmentCivRate2.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate3.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate4.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate5.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
            }
        });

        addReviewDialogFragmentCivRate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRate = 2;
                addReviewDialogFragmentCivRate1.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate2.setBackground(getActivity().getDrawable(R.drawable.shape_btn4));
                addReviewDialogFragmentCivRate3.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate4.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate5.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
            }
        });

        addReviewDialogFragmentCivRate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRate = 3;
                addReviewDialogFragmentCivRate1.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate2.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate3.setBackground(getActivity().getDrawable(R.drawable.shape_btn4));
                addReviewDialogFragmentCivRate4.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate5.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
            }
        });

        addReviewDialogFragmentCivRate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRate = 4;
                addReviewDialogFragmentCivRate1.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate2.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate3.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate4.setBackground(getActivity().getDrawable(R.drawable.shape_btn4));
                addReviewDialogFragmentCivRate5.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
            }
        });

        addReviewDialogFragmentCivRate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRate = 1;
                addReviewDialogFragmentCivRate1.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate2.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate3.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate4.setBackground(getActivity().getDrawable(R.drawable.shape_btn2));
                addReviewDialogFragmentCivRate5.setBackground(getActivity().getDrawable(R.drawable.shape_btn4));
            }
        });

        addReviewDialogFragmentBtnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addReviewDialogFragmentEtReviewText.getText().toString().isEmpty()){
                    reviewDialogFragmentPresenter.addReview(selectedRate,
                            addReviewDialogFragmentEtReviewText.getText().toString().trim(),
                            restaurantId,
                            SharedPreferencesManger.loadClientData(getActivity()).getApiToken());
                }
            }
        });
    }

    @Override
    public void showProgress() {
        addReviewDialogFragmentPbProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        addReviewDialogFragmentPbProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onAddReviewSuccess(String msg) {
        mReviewsFragment.onAddReviewSuccess(msg);
        dismiss();
    }

    @Override
    public void onError(String error) {
        ToastCreator.onCreateErrorToast(getActivity(), error, R.drawable.cancel);
    }
}
