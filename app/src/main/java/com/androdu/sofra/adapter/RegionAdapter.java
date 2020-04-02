package com.androdu.sofra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.region.Region;

import java.util.List;

public class RegionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Region> modelList;
    private RegionAdapter.OnItemClickListener mItemClickListener;


    public RegionAdapter(List<Region> modelList) {
        this.modelList = modelList;
    }


    @Override
    public RegionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_dialog_location_recycler, viewGroup, false);

        return new RegionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof RegionAdapter.ViewHolder) {
            final Region model = getItem(position);
            RegionAdapter.ViewHolder genericViewHolder = (RegionAdapter.ViewHolder) holder;

            genericViewHolder.regionTitle.setText(model.getName());

        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final RegionAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private Region getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, Region model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView regionTitle;

        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.regionTitle = itemView.findViewById(R.id.location_fragment_recycle_item_tv_title);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });

        }
    }

}
