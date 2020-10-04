package com.androdu.sofra.ui.fragments.home.resturantTabs.restaurant.restaurantItemDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androdu.sofra.R;
import com.androdu.sofra.data.local.room.CartRestaurant;
import com.androdu.sofra.data.local.room.RoomDao;
import com.androdu.sofra.data.local.room.RoomManger;
import com.androdu.sofra.data.local.room.cartItem;
import com.androdu.sofra.utils.ToastCreator;
import com.bumptech.glide.Glide;

import java.util.concurrent.Executors;

public class RestaurantItemDetailsFragment extends Fragment {


    private View view;
    private String name;
    private String description;
    private String photo_url;
    private int restaurant_id;
    private int item_id;
    private float price;
    private float offer_price;
    private boolean has_offer;
    private int itemCounter = 1;

    private RoomDao roomDao;


    private ImageView itemImageIv;
    private TextView itemNameTv, itemDescriptionTv, itemPriceTv, itemsCounterTv;
    private EditText privateOrderEt;
    private ImageButton addItemBtn, removeItemBtn, myCartBtn;


    public RestaurantItemDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        description = getArguments().getString("description");
        photo_url = getArguments().getString("photo_url");
        restaurant_id = getArguments().getInt("restaurant_id");
        item_id = getArguments().getInt("item_id");
        price = getArguments().getFloat("price");
        offer_price = getArguments().getFloat("offer_price");
        has_offer = getArguments().getBoolean("has_offer");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant_item_details, container, false);
        initViews();
        setValues();
        initListeners();

        return view;
    }

    private void setValues() {
        Glide.with(getActivity())
                .load(photo_url)
                .into(itemImageIv);
        itemNameTv.setText(name);
        itemDescriptionTv.setText(description);
        itemPriceTv.setText("السعر: " + price + " ج");
    }

    private void initViews() {
        itemImageIv = view.findViewById(R.id.restaurant_item_details_fragment_iv_item_img);
        itemNameTv = view.findViewById(R.id.restaurant_item_details_fragment_tv_item_name);
        itemDescriptionTv = view.findViewById(R.id.restaurant_item_details_fragment_tv_item_description);
        itemPriceTv = view.findViewById(R.id.restaurant_item_details_fragment_tv_item_price);
        privateOrderEt = view.findViewById(R.id.restaurant_item_details_fragment_et_private_order);
        itemsCounterTv = view.findViewById(R.id.restaurant_item_details_fragment_tv_items_counter);
        addItemBtn = view.findViewById(R.id.restaurant_item_details_fragment_ibtn_add_item);
        removeItemBtn = view.findViewById(R.id.restaurant_item_details_fragment_ibtn_remove_item);
        myCartBtn = view.findViewById(R.id.restaurant_item_details_fragment_ibtn_my_cart);
    }

    private void initListeners() {
        removeItemBtn.setEnabled(false);

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCounter++;
                itemsCounterTv.setText(itemCounter + "");
                if (itemCounter > 1)
                    removeItemBtn.setEnabled(true);
            }
        });

        removeItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCounter--;
                itemsCounterTv.setText(itemCounter + "");
                if (itemCounter == 1)
                    removeItemBtn.setEnabled(false);
            }
        });

        myCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomDao = RoomManger.getInstance(getActivity()).roomDao();
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (roomDao.getRestaurant().isEmpty()){
                            CartRestaurant cartRestaurant = new CartRestaurant(restaurant_id);
                            roomDao.addCartRestaurant(cartRestaurant);
                            Log.d("room", "run: rest added: " + roomDao.getRestaurant().get(0).getId());
                        }
                        cartItem cartItem = new cartItem(item_id,
                                name,
                                itemCounter,
                                photo_url,
                                privateOrderEt.getText().toString(),
                                price
                        );
                        roomDao.addCartItem(cartItem);
                        Log.d("room", "run: item added: " + cartItem.getId());
                    }
                });
                ToastCreator.onCreateSuccessToast(getActivity(), "تم الاضافة الي العربة", R.drawable.checked);
            }
        });
    }
}
