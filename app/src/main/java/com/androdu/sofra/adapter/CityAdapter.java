package com.androdu.sofra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.city.City;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<City> modelList;
    private CityAdapter.OnItemClickListener mItemClickListener;


    public CityAdapter(List<City> modelList) {
        this.modelList = modelList;
    }


    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_dialog_location_recycler, viewGroup, false);

        return new CityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof CityAdapter.ViewHolder) {
            final City model = getItem(position);
            CityAdapter.ViewHolder genericViewHolder = (CityAdapter.ViewHolder) holder;

            genericViewHolder.cityTitle.setText(model.getName());

        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final CityAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private City getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, City model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView cityTitle;

        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.cityTitle = itemView.findViewById(R.id.location_fragment_recycle_item_tv_title);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });

        }
    }

}
