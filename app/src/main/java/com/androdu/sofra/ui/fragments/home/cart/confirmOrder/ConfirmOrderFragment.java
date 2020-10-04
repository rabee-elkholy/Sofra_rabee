package com.androdu.sofra.ui.fragments.home.cart.confirmOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.androdu.sofra.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmOrderFragment extends Fragment {

    public ConfirmOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_order, container, false);
    }
}
