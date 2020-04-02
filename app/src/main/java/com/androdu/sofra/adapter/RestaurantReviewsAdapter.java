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
import com.androdu.sofra.data.models.review.RestaurantReview;
import com.bumptech.glide.Glide;

import java.util.List;

public class RestaurantReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<RestaurantReview> modelList;

    public RestaurantReviewsAdapter(Activity activity, List<RestaurantReview> modelList) {
        this.activity = activity;
        this.modelList = modelList;
    }


    @Override
    public RestaurantReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_restaurant_reviews_recycler_review, viewGroup, false);

        return new RestaurantReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof RestaurantReviewsAdapter.ViewHolder) {
            final RestaurantReview model = getItem(position);
            RestaurantReviewsAdapter.ViewHolder genericViewHolder = (RestaurantReviewsAdapter.ViewHolder) holder;


            switch (model.getRate()){
                case"1":
                    genericViewHolder.reviewRate.setImageResource(R.drawable.ic_emoji_rate_1);
                    break;
                case"2":
                    genericViewHolder.reviewRate.setImageResource(R.drawable.ic_emoji_rate_2);
                    break;
                case"3":
                    genericViewHolder.reviewRate.setImageResource(R.drawable.ic_emoji_rate_3);
                    break;
                case"4":
                    genericViewHolder.reviewRate.setImageResource(R.drawable.ic_emoji_rate_4);
                    break;
                case"5":
                    genericViewHolder.reviewRate.setImageResource(R.drawable.ic_emoji_rate_5);
                    break;

            }

            genericViewHolder.reviewerName.setText(model.getClient().getName());
            genericViewHolder.reviewText.setText(model.getComment());
        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }


    private RestaurantReview getItem(int position) {
        return modelList.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView reviewRate;
        private TextView reviewerName, reviewText;

        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.reviewRate = itemView.findViewById(R.id.restaurant_reviews_fragment_recycler_iv_review_rate);
            this.reviewerName = itemView.findViewById(R.id.restaurant_reviews_fragment_recycler_tv_reviewer_name);
            this.reviewText = itemView.findViewById(R.id.restaurant_reviews_fragment_recycler_iv_review_text);

        }
    }

}
