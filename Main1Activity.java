package com.cl1vve.androidapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main1Activity extends AppCompatActivity {
    private String[] scope = new String[]{VKScope.AUDIO};
    private TextView textView;
    private static final String mytag = "***************************";
    public String strrrr;
    public JSONObject res, result = new JSONObject(), FINAL_RESULT = new JSONObject();
    public JSONArray ar, array = new JSONArray();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main1);
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(this, scope);
        }
    }
    public void StartGame(View view){
        Intent intent = new Intent(Main1Activity.this, Main2Activity.class);
        //Button button = (Button) findViewById(R.id.button);
        //intent.putExtra("name",button.getText());
        startActivity(intent);
    }
    public void StartGame1(View view){
        Intent intent = new Intent(Main1Activity.this, GameActivity.class);
        Button button = (Button) findViewById(R.id.button5);
        intent.putExtra("name",button.getText());
        startActivity(intent);

    }
    public void StartGame2(View view) throws JSONException {
        EditText editText = (EditText) findViewById(R.id.EditText);
        //Intent intent = new Intent(Main1Activity.this, GameActivity.class);
        //intent.putExtra("name",String.valueOf(editText.getText()));
        // startActivity(intent);
        DeleteThis(String.valueOf(editText.getText()));
        editText.setText("");
    }
    // ЗДЕСЬ
    // ПОИСК
    // ПЕСНИ
    // НАДО 2 РАЗА ЗАМЕНИТЬ ИСПОЛНИТЕЛЯ В КОДЕ при добавлении нового!!!
    //
    void DeleteThis(final String song) throws JSONException {
        VKRequest request = VKApi.audio().search(VKParameters.from(
                VKApiConst.Q, song+" system of a down", VKApiConst.COUNT, 100)
        );
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                String[] url = new String[5];
                JSONObject obj = new JSONObject();
                try {
                    obj.put("title", song);
                    for (int i = 0; i <= 3; i++) {
                        try{
                            VKApiAudio vkApiAudio = ((VKList<VKApiAudio>) response.parsedModel).get(i);
                            Log.d(mytag,vkApiAudio.url);
                            url[i] = vkApiAudio.url;
                            obj.put(String.valueOf("url" + String.valueOf(i)), url[i]);
                        }
                        catch (IndexOutOfBoundsException e){
                            break;
                        }

                    }
                    ar.put(obj);
                    Log.d(mytag,"YEs");

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
        EditText e = (EditText) findViewById(R.id.EditText);
        e.setFocusable(true);

    }
    ///////////////////////
    //
    //
    //ЗАПИСЫВАЕМ В ФАЙЛ, НАДО СДЕЛАТЬ НАЗВАНИЯ ФАЙЛА
    //АППЕНД  - ДОПИСАТЬ, МОДЕ ДРУГОЙ МОЖНО
    public void record(View view) throws JSONException, IOException {

        res.put("system of a down", ar);
        //надо в файл записать!
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput("poooop", MODE_PRIVATE)));
            // пишем данные
            bw.write(res.toString());
            // закрываем поток
            bw.close();
            Log.d(mytag, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String u,title;
    JSONObject obj;
    int i = 0;
    BufferedReader br;
    
    public void on1(View view) throws IOException, JSONException {
        br = new BufferedReader(new InputStreamReader(
                openFileInput("rammstein.json")));
        open();
    }
    public void open() throws JSONException, IOException {
        String str = "";
        br.readLine();
        if ((str = br.readLine()) != null) {

            obj = new JSONObject(str);
            Log.d(mytag,String.valueOf(obj.get("url0")));
            play(obj,"url0");
        }
        else{
            array.put(result);
            FINAL_RESULT.put("rammstein",array);
            try {
                // отрываем поток для записи
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                        openFileOutput("ramm", MODE_WORLD_READABLE)));
                // пишем данные
                bw.write(FINAL_RESULT.toString());
                // закрываем поток
                bw.close();
                Log.d(mytag, "Файл записан");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public MediaPlayer mp;
    public void play(JSONObject onj, String url) throws IOException, JSONException {
        mp = MediaPlayer.create(Main1Activity.this, Uri.parse(String.valueOf(onj.get(url))));
        u = String.valueOf(onj.get(url));
        title = String.valueOf(onj.get("title"));
        mp.start();
    }
    public void on2(View view) throws JSONException, IOException {
        mp.stop();
        mp.release();
        i=0;
        result.put("title",title);
        result.put("url",u);
        open();
    }
    public void on3(View view) throws JSONException, IOException {
        i++;
        mp.stop();
        play(obj,String.valueOf("url"+i));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKRequest request = VKApi.users().get();
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        VKList list = (VKList) response.parsedModel;
                    }

                });


            }
            @Override
            public void onError(VKError error) {
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
