package com.nbpapplications.colorflip;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Levels extends AppCompatActivity implements View.OnClickListener{

    public static int levelSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        Button backButton = (Button) findViewById(R.id.button_levels_back);

        ((TextView) findViewById(R.id.textView_levels)).setTextSize((float) ((double) 25 * Main.RATIO));

        backButton.setOnClickListener(this);
        backButton.setHeight((int) ((double) backButton.getHeight() * Main.RATIO));
        backButton.setWidth((int) ((double) backButton.getWidth() * Main.RATIO));
        Add_Level_Data add_level_data = new Add_Level_Data(getApplicationContext());

        GridLayout gl = (GridLayout) findViewById(R.id.gridLayout_levels);
        Button[][] buttonArray = new Button[5][5];
        int counter = 1;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                buttonArray[i][j] = new Button(this);
                GridLayout.LayoutParams glP = new GridLayout.LayoutParams();
                glP.rowSpec = GridLayout.spec(i);
                glP.columnSpec = GridLayout.spec(j);
                glP.setMargins(5, 5, 5, 5);
                glP.height = (int) ((double) 170 * Main.RATIO);
                glP.width = (int) ((double) 170 * Main.RATIO);

                buttonArray[i][j].setOnClickListener(this);
                buttonArray[i][j].setText(String.valueOf(counter));
                buttonArray[i][j].setTextSize((int) ((double) 25 * Main.RATIO));
                buttonArray[i][j].setClickable(false);

                if (add_level_data.getLevelCleared(counter) == 1){
                    buttonArray[i][j].setClickable(true);
                    buttonArray[i][j].setOnClickListener(this);
                    buttonArray[i][j].setBackgroundColor(Color.rgb(240, 240, 240));
                }

                gl.addView(buttonArray[i][j], glP);

                counter++;
            }
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this, Main.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        Button buttonSelected = (Button) view;
        if (view.getId() == R.id.button_levels_back) {
            Intent intent = new Intent(this, Main.class);
            startActivity(intent);
        }else{

            Intent intent = new Intent(this, Game.class);
            levelSelected = Integer.valueOf(buttonSelected.getText().toString());
            startActivity(intent);
        }

    }

}
