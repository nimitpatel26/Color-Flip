package com.nbpapplications.colorflip;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Main extends AppCompatActivity implements View.OnClickListener{

    public static double DISPlAY_WIDTH;
    public static double RATIO;
    public static int TILE_COLOR0 = Color.GRAY;
    //public static int TILE_COLOR1 = Color.rgb(75, 75, 255);
    public static int TILE_COLOR1 = Color.BLUE;
    public static String TILE_COLOR0_STRNG = "GREY";
    public static String TILE_COLOR1_STRNG = "BLUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Add_Level_Data add_level_data = new Add_Level_Data(getApplicationContext());
        findViewById(R.id.button_main_play).setOnClickListener(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        DISPlAY_WIDTH = ((double) displayMetrics.widthPixels);
        RATIO = DISPlAY_WIDTH/((double) 1080);

    }

    @Override
    public void onClick(View view){
        Button button = (Button) view;
        button.setTextColor(Color.BLUE);
        int buttonID = button.getId();
        Intent intent;


        switch(buttonID){
            case (R.id.button_main_play):
                intent = new Intent(this, Levels.class);
                startActivity(intent);
                break;

            case (R.id.button_main_settings):
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;

        }



    }

    private void setText(){

        TextView title = ((TextView) findViewById(R.id.textView_main_title));
        Button play = ((Button) findViewById(R.id.button_main_play));
        Button settings = ((Button) findViewById(R.id.button_main_settings));
        Button about = ((Button) findViewById(R.id.button_main_about));

        int newSize = (int)(65 * RATIO);
        title.setTextSize(newSize);
        newSize = (int)((double) 30 * RATIO);
        play.setTextSize(newSize);
        settings.setTextSize(newSize);
        about.setTextSize(newSize);
    }




}
