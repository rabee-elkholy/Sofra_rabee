package com.androdu.sofra.ui.fragments.home.myCart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.R;
import com.androdu.sofra.adapter.CartItemsAdapter;
import com.androdu.sofra.data.local.room.Item;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment implements IMyCartFragment.View {
    private RecyclerView fragmentCartRv;
    private TextView fragmentCartTvTCost;
    private Button fragmentCartBtConfirm;
    private Button fragmentCartBtMore;
    private View view;

    private MyCartFragmentPresenterImpl myCartFragmentPresenter;
    private double total;

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        myCartFragmentPresenter = new MyCartFragmentPresenterImpl(this);
        myCartFragmentPresenter.onCreated();
        myCartFragmentPresenter.getCartItems();
        return view;
    }

    @Override
    public void initViews() {
        fragmentCartRv = view.findViewById(R.id.fragment_cart_rv);
        fragmentCartTvTCost = view.findViewById(R.id.fragment_cart_tv_t_cost);
        fragmentCartBtConfirm = view.findViewById(R.id.fragment_cart_bt_confirm);
        fragmentCartBtMore = view.findViewById(R.id.fragment_cart_bt_more);

    }

    @Override
    public void initListeners() {
        fragmentCartBtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onGetItemsSuccess(List<Item> items) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentCartRv.setLayoutManager(linearLayoutManager);
        CartItemsAdapter cartAdapter = new CartItemsAdapter(getActivity(), items, new updateUi() {
            @Override
            public void onItemDeleted(List<Item> items) {
                updateUi(items);
            }
        });
        fragmentCartRv.setAdapter(cartAdapter);

        updateUi(items);
    }

    @Override
    public void onError(String msg) {

    }

    public interface updateUi {
        void onItemDeleted(List<Item> items);
    }

    private void updateUi(List<Item> items){
        total = 0.0;
        for (int i = 0; i < items.size(); i++) {
            total = total + items.get(i).getQuantity() * items.get(i).getCost();
        }
        fragmentCartTvTCost.setText((int)total + " ج");
    }
}
