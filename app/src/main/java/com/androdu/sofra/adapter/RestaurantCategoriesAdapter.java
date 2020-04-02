package com.androdu.sofra.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.restaurantCategory.RestaurantCategory;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantCategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<RestaurantCategory> modelList;
    private RestaurantCategoriesAdapter.OnItemClickListener mItemClickListener;


    public RestaurantCategoriesAdapter(Activity activity, List<RestaurantCategory> modelList) {
        this.activity = activity;
        this.modelList = modelList;
    }


    @Override
    public RestaurantCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_restaurant_recycler_category, viewGroup, false);

        return new RestaurantCategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof RestaurantCategoriesAdapter.ViewHolder) {
            final RestaurantCategory model = getItem(position);
            RestaurantCategoriesAdapter.ViewHolder genericViewHolder = (RestaurantCategoriesAdapter.ViewHolder) holder;

            if (model.getId() == 0) {
                genericViewHolder.categoryImage.setImageResource(R.drawable.checked);
            } else {
                Glide.with(activity)
                        .load(model.getPhotoUrl())
                        .into(genericViewHolder.categoryImage);
            }
            genericViewHolder.categoryTitle.setText(model.getName());

        }
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final RestaurantCategoriesAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private RestaurantCategory getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, RestaurantCategory model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private CircleImageView categoryImage;
        private TextView categoryTitle;


        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);
            this.categoryImage = itemView.findViewById(R.id.restaurant_fragment_civ_category_img);
            this.categoryTitle = itemView.findViewById(R.id.restaurant_fragment_tv_category_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });

        }
    }

}
