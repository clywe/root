package com.cl1vve.androidapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private static final String mytag = "***************************";
    public String genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        genre = intent.getStringExtra("genre");
        ListView list = (ListView) findViewById(R.id.listView);
        String[] artists = new String[10];
        switch (genre){
            case "metal":
                artists = getString(R.string.metal_artists).split(",");

                break;
            case "rock":
                artists = getString(R.string.rock_artists).split(",");
                break;
        }
        ArrayList<String> new_list1 = new ArrayList<String>();
        for (String str1 : artists) {
            new_list1.add(str1);
        }
        MyCustomAdapter new_adapter = new MyCustomAdapter(new_list1, GameActivity.this);
        list.setAdapter(new_adapter);
        final String [] artists_final = artists;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(GameActivity.this, GameActivity2.class);

                intent1.putExtra("artist",artists_final[position]);
                intent1.putExtra("mix?","no");
                startActivity(intent1);
            }
        });
    }
    protected void MixAll(View view){
        Intent intent = new Intent(GameActivity.this, GameActivity2.class);
        intent.putExtra("genre",genre);
        intent.putExtra("mix?","yes");
        startActivity(intent);
    }

}
