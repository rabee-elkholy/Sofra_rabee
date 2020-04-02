package com.androdu.sofra.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.sofra.data.models.city.City;
import com.androdu.sofra.data.models.region.Region;
import com.androdu.sofra.ui.fragments.userCycle.restaurantRegister.firstRegister.IFirstRegisterFragment;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.BasePermissionListener;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HelperMethod {
    public static ProgressDialog progressDialog;
    public static AlertDialog alertDialog;


    public static Date convertDateString(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date parse = format.parse(date);

            return parse;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void onLoadImageFromUrl(ImageView imageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .into(imageView);
    }


    public static void showCitySpinnerDialog(final Activity activity, String listTitle, final List<City> items, final EditText editText, final IFirstRegisterFragment.Presenter presenter) {
        // setup the alert builder
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setTitle(listTitle);

        // add a list
        final String[] cities = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            cities[i] = items.get(i).getName();
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, cities);
        builder.setAdapter(itemsAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editText.setText(items.get(which).getName());
                editText.setTag(items.get(which).getId());
                presenter.getRegions(items.get(which).getId());
            }
        });

        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // create and show the alert dialog
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showRegionSpinnerDialog(final Activity activity, String listTitle, final List<Region> items, final EditText editText) {
        // setup the alert builder
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setTitle(listTitle);

        // add a list
        final String[] regions = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            regions[i] = items.get(i).getName();
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, regions);
        builder.setAdapter(itemsAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editText.setText(items.get(which).getName());
                editText.setTag(items.get(which).getId());
            }
        });

        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // create and show the alert dialog
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void setInitRecyclerViewAsLinearLayoutManager(Context context, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public static void setInitRecyclerViewAsGridLayoutManager(Activity activity, RecyclerView recyclerView, GridLayoutManager gridLayoutManager, int numberOfColumns) {
        gridLayoutManager = new GridLayoutManager(activity, numberOfColumns);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public static void showProgressDialog(Activity activity, String title) {
        try {

            progressDialog = new ProgressDialog(activity);
            progressDialog.setIndeterminateDrawable(new DoubleBounce());
            progressDialog.setCancelable(false);

            progressDialog.show();

        } catch (Exception e) {

        }
    }

    public static void dismissProgressDialog() {
        try {

            progressDialog.dismiss();

        } catch (Exception e) {

        }
    }

    public static void disappearKeypad(Activity activity, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }


    public static RequestBody convertToRequestBody(String part) {
        try {
            if (!part.equals("")) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
                return requestBody;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("error", "convertToRequestBody: ");

            return null;
        }
    }

    public static String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }



    public static MultipartBody.Part convertFileToMultipart(String pathImageFile, String Key) {
        if (pathImageFile != null) {
            File file = new File(pathImageFile);
            RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, "image", reqFileselect);
            return Imagebody;
        } else {
            Log.d("error", "convertFileToMultipart: ");
            return null;
        }
    }

    public static void requestPermission(final Activity activity) {
        Dexter.withActivity(activity)
                .withPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new BasePermissionListener()).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(activity, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }



}
