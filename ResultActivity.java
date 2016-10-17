package com.cl1vve.androidapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        TextView result = (TextView) findViewById(R.id.GainedPoints);
        String songs[] = new String[25],choices,res = new String();
        songs = intent.getStringArrayExtra("songs");
        choices = intent.getStringExtra("choices");
        Log.d("*******", choices);
        int d=0, e = 1,win=0;
        for(int i=0;i<=3;i++){
            d = choices.indexOf("-");
            if (d==2) {

                res += "<font color=red>"+String.valueOf(i+1) + ") " + songs[Integer.parseInt(choices.substring(0,1))+4*i]+"</font><br>";
                res += "<font color=green>" + songs[Integer.parseInt(choices.substring(1,2))+4*i]+"</font><br>";
                e+=2;
            }else{
                win++;
                res += "<font color=green>"+String.valueOf(i+1) + ") " + songs[Integer.parseInt(choices.substring(0,1))+4*i]+"</font><br>";
                e++;
            }
            choices = choices.substring(d+1);
        }
        res+="<font color=black>"+"Points:"+intent.getStringExtra("points")+"</font>";
        result.setText(Html.fromHtml(res));
        ImageView iv = (ImageView) findViewById(R.id.imageView2);
        switch (win){
            case 0:
                iv.setImageResource(R.drawable.sudar);
                break;
            case 1:
                iv.setImageResource(R.drawable.pytalsya);
                break;
            case 2:
                iv.setImageResource(R.drawable.notbad);
                break;
            case 3:
                iv.setImageResource(R.drawable.master);
                break;
            case 4:
                iv.setImageResource(R.drawable.from4);
                break;
        }
    }
    public void TryAgainClick(View view){
        Intent intent = new Intent(ResultActivity.this, APPMAIN.class);
        startActivity(intent);
    }
}
