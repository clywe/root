package com.cl1vve.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class full_result extends Activity {
    public int i, last = 1;
    public String genre;
    TextView title, stats;
    ListView list;
    Button next, prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_result);
        i = 0;
        title = (TextView) findViewById(R.id.textView);
        stats = (TextView) findViewById(R.id.textView2);
        list = (ListView) findViewById(R.id.listView2);
        next = (Button) findViewById(R.id.next);
        prev = (Button) findViewById(R.id.prev);
        m();
    }

    protected void m() {
        ArrayList<String> new_list = new ArrayList<String>();
        String[] artists = new String[10];
        prev.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        switch (i) {
            case 0:
                prev.setVisibility(View.GONE);
                next.setText("Rock->");
                artists = getString(R.string.metal_artists).split(",");
                genre = "metal";
                break;
            case 1:
                prev.setText("<-Metal");
                artists = getString(R.string.rock_artists).split(",");
                genre = "rock";
                break;
        }
        if (i == last) next.setVisibility(View.GONE);
        for (String str : artists) {
            new_list.add(str);
        }
        MyCustomAdapter new_adapter = new MyCustomAdapter(new_list, this);//
        list.setAdapter(new_adapter);
        title.setText(genre.toUpperCase());
        try {
            BufferedReader ge = new BufferedReader(new InputStreamReader(
                    openFileInput("results")));
            String str;
            while ((str = ge.readLine()) != null) {
                List<String> results = Arrays.asList((str).split(","));
                if (str.contains(genre)) {
                    String max = results.get(1), games = results.get(2);
                    int percent;
                    if (Integer.parseInt(games) == 0) percent = 0;
                    else percent = Math.round(Integer.parseInt(results.get(3)) /
                            (Integer.parseInt(games) * 4) * 100);
                    stats.setText("Максимум очков: " + max + '\n' + "Всего игр: " +
                            games + '\n' + "Правильно отгадано: " + percent + "%");
                }
            }
            ge.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String finalart[] = artists;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String art[] = finalart;
                try {
                    BufferedReader ge = new BufferedReader(new InputStreamReader(
                            openFileInput("results")));
                    String str;
                    while ((str = ge.readLine()) != null) {
                        if (str.contains(art[position].toLowerCase())) {
                            List<String> artist = Arrays.asList((str).split(","));
                            String max = artist.get(1);
                            int games = Integer.parseInt(artist.get(2)),
                                    win = Integer.parseInt(artist.get(3));
                            int percent;
                            if (games == 0) percent = 0;
                            else percent = win * 100 / (games * 4);
                            art[position] += '\n' + "Максимум очков: " + max + '\n' + "Всего игр: " +
                                    games + '\n' + "Правильно отгадано: " + percent + "%";
                            ArrayList<String> new_list1 = new ArrayList<String>();
                            for (String str1 : art) {
                                new_list1.add(str1);
                            }
                            MyCustomAdapter new_adapter = new MyCustomAdapter(new_list1, full_result.this);//
                            list.setAdapter(new_adapter);
                            break;
                        }
                    }
                    ge.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    protected void next(View view) {
        i++;
        m();

    }

    protected void prev(View view) {
        i--;
        m();
    }

    protected void back(View view) {
        Intent intent = new Intent(full_result.this, APPMAIN.class);
        startActivity(intent);
    }

}
