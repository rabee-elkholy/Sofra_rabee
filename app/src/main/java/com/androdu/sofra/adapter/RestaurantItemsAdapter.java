package com.androdu.sofra.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.R;
import com.androdu.sofra.data.models.retaurantItem.RestaurantItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class RestaurantItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<RestaurantItem> modelList;
    private RestaurantItemsAdapter.OnItemClickListener mItemClickListener;


    public RestaurantItemsAdapter(Activity activity, List<RestaurantItem> modelList) {
        this.activity = activity;
        this.modelList = modelList;
    }


    @Override
    public RestaurantItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_restaurant_recycler_item, viewGroup, false);

        return new RestaurantItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof RestaurantItemsAdapter.ViewHolder) {
            final RestaurantItem model = getItem(position);
            RestaurantItemsAdapter.ViewHolder genericViewHolder = (RestaurantItemsAdapter.ViewHolder) holder;


            Glide.with(activity)
                    .load(model.getPhotoUrl())
                    .into(genericViewHolder.itemImage);

            genericViewHolder.itemName.setText(model.getName());
            genericViewHolder.itemDescription.setText(model.getDescription());
            genericViewHolder.itemPrice.setText("السعر: " + model.getPrice() + " ج");

        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final RestaurantItemsAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private RestaurantItem getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, RestaurantItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView itemImage;
        private TextView itemName, itemDescription, itemPrice;

        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.itemImage = itemView.findViewById(R.id.restaurant_fragment_iv_item_img);
            this.itemName = itemView.findViewById(R.id.restaurant_fragment_tv_item_name);
            this.itemDescription = itemView.findViewById(R.id.restaurant_fragment_tv_item_description);
            this.itemPrice = itemView.findViewById(R.id.restaurant_fragment_tv_item_price);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });

        }
    }

}
