package com.androdu.sofra.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.R;
import com.androdu.sofra.data.local.room.Item;
import com.androdu.sofra.data.local.room.RoomDao;
import com.androdu.sofra.data.local.room.RoomManger;
import com.androdu.sofra.ui.fragments.home.myCart.MyCartFragment;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.Executors;

public class CartItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Item> modelList;


    private RoomDao roomDao;
    private int quantity;
    MyCartFragment.updateUi uiListener;

    public CartItemsAdapter(Activity activity, List<Item> modelList, MyCartFragment.updateUi uiListener) {
        this.activity = activity;
        this.modelList = modelList;
        this.uiListener = uiListener;
        roomDao = RoomManger.getInstance(activity).roomDao();
    }


    @Override
    public CartItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_cart_list, viewGroup, false);

        return new CartItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof CartItemsAdapter.ViewHolder) {
            final Item model = getItem(position);
            final CartItemsAdapter.ViewHolder genericViewHolder = (CartItemsAdapter.ViewHolder) holder;


            Glide.with(activity)
                    .load(model.getImage())
                    .into(genericViewHolder.itemImage);

            genericViewHolder.itemName.setText(model.getItemName());
            genericViewHolder.itemCounter.setText(model.getQuantity() + "");
            genericViewHolder.itemPrice.setText("السعر: " + (int) (model.getCost()) + " ج");


            genericViewHolder.removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            roomDao.delete(model);

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    modelList.remove(model);
                                    uiListener.onItemDeleted(modelList);
                                    notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
            });

            genericViewHolder.increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity = modelList.get(position).getQuantity() + 1;
                    genericViewHolder.itemCounter.setText(quantity + "");
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            model.setQuantity(quantity);
                            roomDao.onUpdate(model);
                            uiListener.onItemDeleted(modelList);
                        }
                    });
                }
            });

            genericViewHolder.decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modelList.get(position).getQuantity() > 1) {
                        quantity = modelList.get(position).getQuantity() - 1;
                        genericViewHolder.itemCounter.setText(quantity + "");
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                model.setQuantity(quantity);
                                roomDao.onUpdate(model);
                                uiListener.onItemDeleted(modelList);
                            }
                        });
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }


    private Item getItem(int position) {
        return modelList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView itemImage;
        private TextView itemName, itemCounter, itemPrice;
        private ImageButton  increment, decrement;
        private Button removeItem;

        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.itemImage = itemView.findViewById(R.id.item_cart_list_iv_image);
            this.itemName = itemView.findViewById(R.id.item_cart_list_tv_name);
            this.itemCounter = itemView.findViewById(R.id.item_cart_list_tv_counter);
            this.itemPrice = itemView.findViewById(R.id.item_cart_list_tv_cost);
            this.removeItem = itemView.findViewById(R.id.item_cart_list_bt_cancel);
            this.increment = itemView.findViewById(R.id.item_cart_list_ibtn_increase);
            this.decrement = itemView.findViewById(R.id.item_cart_list_ibtn_decrease);


        }
    }

}
