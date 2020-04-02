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
    void add(Item... items);

    @Update
    void onUpdate(Item... items);

    @Delete
    void delete(Item... items);

    @Query("select * from Item")
    List<Item> getAll();
    @Query("DELETE FROM Item")
    void deleteAll();
}
