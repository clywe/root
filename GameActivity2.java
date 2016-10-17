package com.cl1vve.androidapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameActivity2 extends AppCompatActivity {
    public int RandomSongNumber,newRandomSongNumber, oo = 0, o = 0,win=0;
    public long Start, End, Points;
    private static final String mytag = "***************************";
    public MediaPlayer mediaPlayer;
    public AudioManager am;
    private static long back_pressed;
    public Button button1, button2, button3, button4;
    public String SONGS[][] = new String[25][2], were = new String(), WORD,
            RIGHT_CHOICES = new String(), song,genre;
    public List<String>
            systemofadown_list,
            slipknot_list ,
            rammstein_list ,
            metallica_list ,
            acdc_list,
            deeppurple_list ,
            list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(mytag,"oncreate"+String.valueOf(System.currentTimeMillis()));
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game2);
        Intent intent = getIntent();
        systemofadown_list = Arrays.asList(getString(R.string.system_of_a_down).split(","));
                slipknot_list = Arrays.asList(getString(R.string.slipknot).split(","));
                rammstein_list = Arrays.asList(getString(R.string.rammstein).split(","));
                metallica_list = Arrays.asList(getString(R.string.metallica).split(","));
                acdc_list = Arrays.asList(getString(R.string.acdc).split(","));
                deeppurple_list = Arrays.asList(getString(R.string.deep_purple).split(","));
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button1.setClickable(false);
        button2.setClickable(false);
        button3.setClickable(false);
        button4.setClickable(false);
        Random randomGenerator = new Random();
        RandomSongNumber = randomGenerator.nextInt(4);
        newRandomSongNumber = RandomSongNumber;
        String yesorno = intent.getStringExtra("mix?");
        if (yesorno.equalsIgnoreCase("no")) {
            WORD = intent.getStringExtra("artist").toLowerCase();
            switch (WORD) {
                case "system of a down":
                    list = systemofadown_list;
                    genre = "metal";
                    break;
                case "rammstein":
                    list = rammstein_list;
                    genre = "metal";
                    break;
                case "slipknot":
                    list = slipknot_list;
                    genre = "metal";
                    break;
                case "ac/dc":
                    list = acdc_list;
                    genre = "rock";
                    break;
                case "deep purple":
                    list = deeppurple_list;
                    genre = "rock";
                    break;
                case "metallica":
                    list = metallica_list;
                    genre = "rock";
                    break;
            }
        } else {
            WORD = intent.getStringExtra("genre");
        }
        o = 0;
        MyTask abc = new MyTask();
        abc.execute();
        Log.d(mytag,"oncreate"+String.valueOf(System.currentTimeMillis()));
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.d(mytag,"mytask"+String.valueOf(System.currentTimeMillis()));
            Random ran = new Random();
            String artist, title;
            int metal_random_group = ran.nextInt(3),//если добавил групп, то тут менять
                    rock_random_group = ran.nextInt(3);//число групп может отличаться
            artist = WORD + " - ";
            switch (WORD) {
                case "metal":
                    genre = "metal";
                    switch (metal_random_group) {
                        case 0:
                            artist = "system of a down - ";
                            list = systemofadown_list;
                            break;
                        case 1:
                            artist = "slipknot - ";
                            list = slipknot_list;
                            break;
                        case 2:
                            artist = "rammstein - ";
                            list = rammstein_list;
                            break;
                    }
                    break;
                case "rock":
                    genre = "rock";
                    switch (rock_random_group) {
                        case 0:
                            artist = "metallica - ";
                            list = metallica_list;
                            break;
                        case 1:
                            artist = "AC DC - ";
                            list = acdc_list;
                            break;
                        case 2:
                            artist = "deep purple - ";
                            list = deeppurple_list;
                            break;
                    }
                    break;
            }
            Random ran1 = new Random();
            do {
                title = list.get(ran1.nextInt(list.size() - 1));
            } while (were.contains(artist + title));
            song = artist + title;
            were += song + ' ';
            song = song.toLowerCase();
            if (o == newRandomSongNumber) {
                VKRequest request = VKApi.audio().search(VKParameters.from(
                        VKApiConst.Q, song, VKApiConst.COUNT, 10)
                );
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        Log.d(mytag, "Запрос в вк выполнился");
                        super.onComplete(response);
                        try {
                            VKApiAudio audio = ((VKList<VKApiAudio>) response.parsedModel).get(0);
                            SONGS[oo * 4 + o][0] = song;
                            SONGS[oo * 4 + o][1] = audio.url;
                            o++;
                            if (o <= 3) {
                                doInBackground();
                            } else {
                                if (oo == 0)
                                    DOITMOTHERFUCKER();
                            }
                        } catch (IndexOutOfBoundsException e) {
                            doInBackground();
                        }
                    }
                });
            } else {
                SONGS[oo * 4 + o][0] = song;
                o++;
                if (o <= 3) {
                    doInBackground();
                } else {
                    if (oo == 0)
                        DOITMOTHERFUCKER();
                }
            }
            Log.d(mytag,"mytask"+String.valueOf(System.currentTimeMillis()));
            return null;
        }
    }

    class MyTask1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.d(mytag,"mytask1"+String.valueOf(System.currentTimeMillis()));
            Random randomGenerator = new Random();
            String url = null;
            while (url == null) {
                url = SONGS[oo * 4 + newRandomSongNumber][1];
            }
            mediaPlayer = MediaPlayer.create(GameActivity2.this, Uri.parse(url));
            int length = mediaPlayer.getDuration();
            mediaPlayer.setAudioStreamType(am.STREAM_MUSIC);
            mediaPlayer.seekTo(length / (randomGenerator.nextInt(28) + 2) + length / (randomGenerator.nextInt(28) + 4));
            mediaPlayer.start();
            Start = System.currentTimeMillis();
            Log.d(mytag,"mytask1"+String.valueOf(System.currentTimeMillis()));
            return null;
        }
    }

    public void DOITMOTHERFUCKER() {
        Log.d(mytag,"doit"+String.valueOf(System.currentTimeMillis()));
        MyTask1 mv1 = new MyTask1();
        mv1.execute();
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                button1.setText(SONGS[oo * 4][0]);
                button2.setText(SONGS[oo * 4 + 1][0]);
                button3.setText(SONGS[oo * 4 + 2][0]);
                button4.setText(SONGS[oo * 4 + 3][0]);
                button1.setBackgroundColor(Color.GRAY);
                button2.setBackgroundColor(Color.GRAY);
                button3.setBackgroundColor(Color.GRAY);
                button4.setBackgroundColor(Color.GRAY);
                button1.setClickable(true);
                button2.setClickable(true);
                button3.setClickable(true);
                button4.setClickable(true);
            }
        }, 1000);
        o = 0;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                were = new String();
                Random randomGenerator = new Random();
                newRandomSongNumber = randomGenerator.nextInt(4);
                oo++;
                MyTask abc = new MyTask();
                abc.execute();
            }
        }, 2000);
        Log.d(mytag,"do it"+String.valueOf(System.currentTimeMillis()));
    }

    public void Button1Click(View view) {
        ButtonClicked(button1, 0);
    }

    public void Button2Click(View view) {
        ButtonClicked(button2, 1);
    }

    public void Button3Click(View view) {
        ButtonClicked(button3, 2);
    }

    public void Button4Click(View view) {
        ButtonClicked(button4, 3);
    }

    public void ButtonClicked(final Button button, int i) {
        Log.d(mytag,"Buttonclicked");
        End = System.currentTimeMillis();
        if (RandomSongNumber == i) {
            button.setBackgroundColor(Color.GREEN);
            Points += Math.pow(10, ((Start - End)*0.0001)) *10000;
            win++;
            RIGHT_CHOICES += String.valueOf(i) + '-';
        } else {
            RIGHT_CHOICES += String.valueOf(i) + String.valueOf(RandomSongNumber) + '-';
            button.setBackgroundColor(Color.RED);
        }
        button1.setClickable(false);
        button2.setClickable(false);
        button3.setClickable(false);
        button4.setClickable(false);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Log.d(mytag,String.valueOf(System.currentTimeMillis()-End));
        toBegin();
    }

    public void toBegin() {
        Log.d(mytag,"tobegin"+String.valueOf(System.currentTimeMillis()));
        switch (RandomSongNumber) {
            case 0:
                button1.setBackgroundColor(Color.GREEN);
                break;
            case 1:
                button2.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                button3.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                button4.setBackgroundColor(Color.GREEN);
                break;
        }
        if (oo <= 3) {
            RandomSongNumber = newRandomSongNumber;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DOITMOTHERFUCKER();
                }
            }, 1000);
        } else {
            mediaPlayer.release();
            SaveResults abc = new SaveResults();
            abc.execute();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String songs_to_intent[] = new String[16];
                    for (int f = 0; f <= 15; f++) {
                        songs_to_intent[f] = SONGS[f][0];
                    }
                    Intent intent = new Intent(GameActivity2.this, ResultActivity.class);
                    intent.putExtra("points", String.valueOf(Points));
                    intent.putExtra("songs", songs_to_intent);
                    intent.putExtra("choices", RIGHT_CHOICES);
                    startActivity(intent);
                }
            }, 1000);

        }
        Log.d(mytag,"tobegin"+String.valueOf(System.currentTimeMillis()));
    }
    class SaveResults extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.d(mytag,"saveresults"+String.valueOf(System.currentTimeMillis()));
            try {
                BufferedReader ge = new BufferedReader(new InputStreamReader(
                        openFileInput("results")));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                        openFileOutput("temp_results",MODE_PRIVATE)));
                String str;
                int games, win_games;
                long max;
                while ((str = ge.readLine()) != null) {
                    List<String> results = Arrays.asList((str).split(","));
                    if (results.get(0).equalsIgnoreCase(WORD)) {
                        max = Integer.parseInt(results.get(1));//максимальное количество очков
                        games = Integer.parseInt(results.get(2));//количество игр
                        win_games = Integer.parseInt(results.get(3));//отгаданные песни
                        if (max < Points) max = Points;
                        games++;
                        win_games += win;
                        bw.write(WORD + ',' + max + ',' + games + ',' + win_games+'\n');
                    }
                    else{
                        bw.write(str+'\n');
                    }

                }
                bw.close();
                ge.close();
                BufferedReader ge1 = new BufferedReader(new InputStreamReader(
                        openFileInput("temp_results")));
                BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(
                        openFileOutput("results",MODE_PRIVATE)));
                while ((str = ge1.readLine()) != null) {
                    bw1.write(str+'\n');
                }
                bw1.close();
                ge1.close();
            }  catch (IOException e){

            }
            Log.d(mytag,"saveresults"+String.valueOf(System.currentTimeMillis()));
            return null;
        }
    }
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else
            Toast.makeText(getBaseContext(), "Нажмите еще раз, чтобы закончить игру!",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onUserLeaveHint() {
        mediaPlayer.stop();
        mediaPlayer.release();
        Intent intent = new Intent(GameActivity2.this,APPMAIN.class);
        startActivity(intent);
        super.onUserLeaveHint();
    }
}
