package com.androdu.sofra.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.R;
import com.androdu.sofra.adapter.CityAdapter;
import com.androdu.sofra.adapter.RegionAdapter;
import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.region.Region;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationDialogFragment extends DialogFragment {

    private Activity activity;
    private ILocationDialog callBack;
    private List<City> cities;
    private List<Region> regions;
    private String title;
    private RecyclerView mRecyclerView;

    public LocationDialogFragment() {
        // Required empty public constructor
    }

    public LocationDialogFragment(Activity activity, ILocationDialog callBack, List<City> cities, List<Region> regions, String title) {
        this.activity = activity;
        this.callBack = callBack;
        this.cities = cities;
        this.regions = regions;
        this.title = title;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_location, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.location_fragment_rc_recycler_view);
        TextView titleTv = view.findViewById(R.id.location_fragment_tv_title);
        titleTv.setText(title);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
        if (cities != null){
            adapter = new CityAdapter(cities);
            ((CityAdapter) adapter).SetOnItemClickListener(new CityAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, City model) {
                    callBack.onSelect(model.getId(), model.getName());
                    dismiss();
                }
            });
        } else{
            adapter = new RegionAdapter(regions);
            ((RegionAdapter) adapter).SetOnItemClickListener(new RegionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, Region model) {
                    callBack.onSelect(model.getId(), model.getName());
                    dismiss();
                }
            });
        }

        mRecyclerView.setAdapter(adapter);

    }

    public interface ILocationDialog {
        void onSelect(int id, String name);
    }

}
