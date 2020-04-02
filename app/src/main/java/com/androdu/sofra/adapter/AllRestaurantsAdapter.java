package com.androdu.sofra.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.restaurant.Restaurant;
import com.bumptech.glide.Glide;

import java.util.List;

public class AllRestaurantsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Restaurant> modelList;
    private Activity activity;
    private AllRestaurantsAdapter.OnItemClickListener mItemClickListener;


    public AllRestaurantsAdapter(Activity activity, List<Restaurant> modelList) {
        this.modelList = modelList;
        this.activity = activity;
    }


    @Override
    public AllRestaurantsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_all_restaurants_item, viewGroup, false);

        return new AllRestaurantsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof AllRestaurantsAdapter.ViewHolder) {
            final Restaurant model = getItem(position);
            AllRestaurantsAdapter.ViewHolder genericViewHolder = (AllRestaurantsAdapter.ViewHolder) holder;

            genericViewHolder.restName.setText(model.getName());
            genericViewHolder.minCharger.setText("الحد الادني للطلب " + model.getMinimumCharger() + " ج");
            genericViewHolder.deliveryCost.setText("تكلفة التوصيل " + model.getDeliveryCost() + " ج");
            genericViewHolder.rating.setText(model.getRate() + "");
            genericViewHolder.ratingBar.setRating(model.getRate());
            if (model.getAvailability().equals("open")) {
                genericViewHolder.availability.setText("مفتوح");
            } else {
                genericViewHolder.availability.setText("مغلق");
            }
            if (model.getActivated().equals("1")) {
                genericViewHolder.state.setBackgroundResource(R.drawable.checked);
            } else {
                genericViewHolder.state.setBackgroundResource(R.drawable.cancel);
            }

            Glide.with(activity)
                    .load(model.getPhotoUrl())
                    .into(genericViewHolder.photo);

        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final AllRestaurantsAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private Restaurant getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, Restaurant model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView restName, minCharger, deliveryCost, rating, availability;
        private ImageView photo;
        private RatingBar ratingBar;
        private View state;

        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.restName = itemView.findViewById(R.id.restaurants_fragment_recycler_item_tv_name);
            this.deliveryCost = itemView.findViewById(R.id.restaurants_fragment_recycler_item_tv_delivery_cost);
            this.minCharger = itemView.findViewById(R.id.restaurants_fragment_recycler_item_tv_minimum_charger);
            this.rating = itemView.findViewById(R.id.restaurants_fragment_recycler_item_tv_rating);
            this.availability = itemView.findViewById(R.id.restaurants_fragment_recycler_item_tv_availability);
            this.ratingBar = itemView.findViewById(R.id.restaurants_fragment_recycler_item_rb_rating);
            this.photo = itemView.findViewById(R.id.restaurants_fragment_recycler_item_civ_photo);
            this.state = itemView.findViewById(R.id.restaurants_fragment_recycler_item_v_state);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });

        }
    }
}
