package com.androdu.sofra.ui.fragments.home.cart.myCart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.R;
import com.androdu.sofra.adapter.CartItemsAdapter;
import com.androdu.sofra.data.local.room.cartItem;

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
        fragmentCartBtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_myCartFragment_to_confirmOrderFragment);
            }
        });
    }

    @Override
    public void onGetItemsSuccess(List<cartItem> cartItems) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentCartRv.setLayoutManager(linearLayoutManager);
        CartItemsAdapter cartAdapter = new CartItemsAdapter(getActivity(), cartItems, new updateUi() {
            @Override
            public void onItemDeleted(List<cartItem> items) {
                updateUi(items);
            }
        });
        fragmentCartRv.setAdapter(cartAdapter);

        updateUi(cartItems);
    }

    @Override
    public void onError(String msg) {

    }

    public interface updateUi {
        void onItemDeleted(List<cartItem> cartItems);
    }

    private void updateUi(List<cartItem> cartItems){
        total = 0.0;
        for (int i = 0; i < cartItems.size(); i++) {
            total = total + cartItems.get(i).getQuantity() * cartItems.get(i).getCost();
        }
        fragmentCartTvTCost.setText((int)total + " ج");
    }
}
