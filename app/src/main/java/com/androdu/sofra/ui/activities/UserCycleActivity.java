package com.androdu.sofra.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androdu.sofra.R;

public class UserCycleActivity extends AppCompatActivity {
    public int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);
        getWindow().setBackgroundDrawableResource(R.drawable.user_cycle_background);
    }

    @Override
    protected void onStart() {
        userType = getIntent().getIntExtra("userType", 0);
        super.onStart();
    }
}
