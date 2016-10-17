package com.cl1vve.androidapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class Main2Activity extends Activity {
    private static final String mytag = "***************************";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
    }

    public void MetalClick(View view){
        Intent intent = new Intent(Main2Activity.this, GameActivity.class);
        intent.putExtra("genre","metal");
        startActivity(intent);
    }
    public void RockClick(View view){
        Intent intent = new Intent(Main2Activity.this, GameActivity.class);
        intent.putExtra("genre","rock");
        startActivity(intent);
    }
}

