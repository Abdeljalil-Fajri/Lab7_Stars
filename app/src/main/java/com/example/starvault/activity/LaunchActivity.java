package com.example.starvault.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.starvault.R;

public class LaunchActivity extends AppCompatActivity {

    private ImageView imgBrand;
    private TextView txtAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        imgBrand = findViewById(R.id.imgBrand);
        txtAppName = findViewById(R.id.txtAppName);

        imgBrand.setScaleX(0f);
        imgBrand.setScaleY(0f);
        imgBrand.setAlpha(0f);
        txtAppName.setAlpha(0f);
        txtAppName.setTranslationY(80f);

        ObjectAnimator growX = ObjectAnimator.ofFloat(imgBrand, "scaleX", 0f, 1f);
        ObjectAnimator growY = ObjectAnimator.ofFloat(imgBrand, "scaleY", 0f, 1f);
        ObjectAnimator spin = ObjectAnimator.ofFloat(imgBrand, "rotation", 0f, 720f);
        ObjectAnimator fadeInLogo = ObjectAnimator.ofFloat(imgBrand, "alpha", 0f, 1f);

        AnimatorSet logoSet = new AnimatorSet();
        logoSet.playTogether(growX, growY, spin, fadeInLogo);
        logoSet.setDuration(2000);

        ObjectAnimator slideUp = ObjectAnimator.ofFloat(txtAppName, "translationY", 80f, 0f);
        ObjectAnimator fadeInTitle = ObjectAnimator.ofFloat(txtAppName, "alpha", 0f, 1f);

        AnimatorSet titleSet = new AnimatorSet();
        titleSet.playTogether(slideUp, fadeInTitle);
        titleSet.setDuration(1200);
        titleSet.setStartDelay(1500);

        AnimatorSet fullAnimation = new AnimatorSet();
        fullAnimation.playTogether(logoSet, titleSet);
        fullAnimation.start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(LaunchActivity.this, BrowseActivity.class));
            finish();
        }, 3500);
    }
}