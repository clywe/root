package com.cl1vve.androidapp;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    public String systemofadown_info, rammstein_info, slipknot_info, metallica_info,
            acdc_info, deeppurple_info;


    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
        systemofadown_info = context.getString(R.string.system_of_a_down_info);
        rammstein_info = context.getString(R.string.rammstein_info);
        acdc_info = context.getString(R.string.acdc_info);
        slipknot_info = context.getString(R.string.slipknot_info);
        deeppurple_info = context.getString(R.string.deeppurple_info);
        metallica_info = context.getString(R.string.metallica_info);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_custom_list_layout, null);
        }

        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));
        Button infoBtn = (Button)view.findViewById(R.id.info_button);
        infoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int screenWidth = (int) (metrics.widthPixels * 0.95);
                dialog.setContentView(R.layout.info);
                dialog.getWindow().setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView text = (TextView) dialog.findViewById(R.id.text);
                TextView text1 = (TextView) dialog.findViewById(R.id.textView3);
                String artist = list.get(position);
                if (artist.contains("\n")) {
                    artist = artist.substring(0, artist.indexOf('\n'));
                }
                ImageView image = (ImageView) dialog.findViewById(R.id.image1);
                String str = new String();
                switch(artist.toLowerCase()){
                    case "system of a down":
                        str = systemofadown_info;
                        image.setImageResource(R.drawable.systemofadown);
                        break;
                    case "metallica":
                        str = metallica_info;
                        image.setImageResource(R.drawable.metallica);
                        break;
                    case "ac/dc":
                        str = acdc_info;
                        image.setImageResource(R.drawable.acdc);
                        break;
                    case "deep purple":
                        str = deeppurple_info;
                        image.setImageResource(R.drawable.deeppurple);
                        break;
                    case "slipknot":
                        str = slipknot_info;
                        image.setImageResource(R.drawable.slipknot);
                        break;
                    case "rammstein":
                        str = rammstein_info;
                        image.setImageResource(R.drawable.rammstein);
                        break;
                }
                text.setText(str);
                text1.setText(artist);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return view;
    }
}