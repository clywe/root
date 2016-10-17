package com.cl1vve.androidapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class APPMAIN extends Activity {
    private String[] scope = new String[]{VKScope.AUDIO};
    private static long back_pressed;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appmain);
        prefs = getSharedPreferences("com.cl1vve.androidapp", MODE_PRIVATE);
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(this, scope);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun3", true)) {
            Log.d("111", "только 1 раз!");
            try {

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                        openFileOutput("results", MODE_PRIVATE)));
                String[] str = getString(R.string.artists).split(",");
                for (String str1 : str) {
                    Log.d("111", str1);
                    bw.write(str1.toLowerCase() + ",0,0,0" + '\n');
                }
                bw.flush();
                bw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            prefs.edit().putBoolean("firstrun3", false).commit();
        }
    }

    protected void GenreButtonClick(View view) {
        Intent intent = new Intent(APPMAIN.this, Main2Activity.class);
        startActivity(intent);
    }

    protected void info(View view) {
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.master);
        AlertDialog.Builder builder = new AlertDialog.Builder(APPMAIN.this);
        builder.setTitle("Информация")
                .setView(image)
                .setMessage("Программа разрабатывается в качестве курсового проекта на основе " +
                        "программы обучения студентов кафедры ИУ6 МГТУ им Н.Э.Баумана.\n" +
                        "Автор: Черепов Д.С. \n" +
                        "Группа: ИУ6-51")
                .setNegativeButton("Нифигасе",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void Show_results(View view) {
        Intent intent = new Intent(APPMAIN.this, full_result.class);
        startActivity(intent);
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
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Нажмите еще раз, чтобы выйти!",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}
