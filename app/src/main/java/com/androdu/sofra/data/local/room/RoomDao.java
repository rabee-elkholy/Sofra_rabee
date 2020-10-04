package com.androdu.sofra.data.local.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomDao {
    @Insert
    void addCartItem(cartItem cartItem);

    @Update
    void updateCartItem(cartItem cartItem);


    @Query("select * from cartItem")
    List<cartItem> getAllItems();


    @Delete
    void deleteItem(cartItem cartItems);

    @Query("DELETE FROM cartItem")
    void deleteAllItems();

    @Insert
    void addCartRestaurant(CartRestaurant cartRestaurant);

    @Update
    void updateCartRestaurant(CartRestaurant cartRestaurant);


    @Query("SELECT * FROM CartRestaurant LIMIT 1")
    List<CartRestaurant> getRestaurant();


    @Query("DELETE FROM CartRestaurant")
    void deleteCartRestaurant();


}
