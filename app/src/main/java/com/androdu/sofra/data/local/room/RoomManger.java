package com.androdu.sofra.data.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {cartItem.class, CartRestaurant.class}, version = 1, exportSchema = false)
@TypeConverters(DataTypeConverter.class)
public abstract class RoomManger extends RoomDatabase {
    public abstract RoomDao roomDao();

    private static RoomManger roomManger;

    public static synchronized RoomManger getInstance(Context context) {
        if (roomManger == null) {
            roomManger = Room.databaseBuilder(context.getApplicationContext(), RoomManger.class,
                    "sofra_database2")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomManger;
    }

}
