package com.androdu.sofra.data.local;

import android.app.Activity;
import android.content.SharedPreferences;

import com.androdu.sofra.data.models.client.ClientLoginData;
import com.androdu.sofra.data.models.restaurant.RestaurantLoginData;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesManger {

    public static SharedPreferences sharedPreferences = null;
    public static final String USER_DATA = "USER_DATA";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String REMEMBER = "REMEMBER";
    public static final String TYPE = "TYPE";

    public static void setSharedPreferences(Activity activity) {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences(
                    "Blood", MODE_PRIVATE);
        }
    }

    public static void SaveData(Activity activity, String data_Key, String data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(data_Key, data_Value);
            editor.apply();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void SaveData(Activity activity, String data_Key, boolean data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(data_Key, data_Value);
            editor.apply();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void SaveData(Activity activity, String data_Key, Object data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String StringData = gson.toJson(data_Value);
            editor.putString(data_Key, StringData);
            editor.apply();
        }
    }

    public static void saveRestaurantData(Activity activity, RestaurantLoginData restaurantLoginData) {

        if (restaurantLoginData.getApiToken() == null) {
            restaurantLoginData.setApiToken(loadRestaurantData(activity).getApiToken());
        }

        SaveData(activity, USER_DATA, restaurantLoginData);
    }

    public static void saveClientData(Activity activity, ClientLoginData clientLoginData) {

        if (clientLoginData.getApiToken() == null) {
            clientLoginData.setApiToken(loadRestaurantData(activity).getApiToken());
        }

        SaveData(activity, USER_DATA, clientLoginData);
    }

    public static String LoadData(Activity activity, String data_Key) {
        setSharedPreferences(activity);

        return sharedPreferences.getString(data_Key, null);
    }

    public static boolean LoadBoolean(Activity activity, String data_Key) {
        setSharedPreferences(activity);

        return sharedPreferences.getBoolean(data_Key, false);
    }

    public static RestaurantLoginData loadRestaurantData(Activity activity) {
        RestaurantLoginData userData = null;

        Gson gson = new Gson();
        userData = gson.fromJson(LoadData(activity, USER_DATA), RestaurantLoginData.class);

        return userData;
    }

    public static ClientLoginData loadClientData(Activity activity) {
        ClientLoginData userData = null;

        Gson gson = new Gson();
        userData = gson.fromJson(LoadData(activity, USER_DATA), ClientLoginData.class);

        return userData;
    }

    public static void clean(Activity activity) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }

}
