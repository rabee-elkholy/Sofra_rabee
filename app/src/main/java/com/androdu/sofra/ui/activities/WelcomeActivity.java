package com.androdu.sofra.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.androdu.sofra.R;
import com.androdu.sofra.utils.Constants;

public class WelcomeActivity extends AppCompatActivity {

    ImageView img1, img2;
    LinearLayout lin;
    Button makeOrderBtn, makeOfferBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        startAnimation();
        initListeners();
    }

    private void init() {
        img1 = findViewById(R.id.welcome_activity_img1);
        img2 = findViewById(R.id.welcome_activity_img2);
        lin = findViewById(R.id.welcome_activity_lin);
        makeOrderBtn = findViewById(R.id.welcome_activity_btn_make_order);
        makeOfferBtn = findViewById(R.id.welcome_activity_btn_make_offer);
    }

    private void startAnimation() {
        img1.setTranslationY(-500);
        img1.setTranslationX(-500);
        img1.animate().setDuration(3000).rotation(360).translationY(0).translationX(0);

        img2.setTranslationY(500);
        img2.setTranslationX(500);
        img2.animate().setDuration(3000).rotation(360).translationY(0).translationX(0);

        lin.setAlpha(0);
        lin.setScaleX(0);
        lin.setScaleY(0);
        lin.animate().setDuration(3000).alpha(1).scaleX(1).scaleY(1);
    }

    private void initListeners() {
        makeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        makeOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, UserCycleActivity.class);
                intent.putExtra("userType", Constants.USER_TYPE_RESTAURANT);
                startActivity(intent);
            }
        });
    }

}
