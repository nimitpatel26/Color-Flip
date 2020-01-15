package com.nbpapplications.colorflip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ((Button) findViewById(R.id.button_result_back)).setOnClickListener(this);
        ((Button) findViewById(R.id.button_result_refresh)).setOnClickListener(this);
        ((Button) findViewById(R.id.button_result_next)).setOnClickListener(this);

        TextView conclusion = (TextView) findViewById(R.id.textView_result_conclusion);
        conclusion.setTextSize((float) ((double)70 * Main.RATIO));
        conclusion.setText("level " + String.valueOf(Levels.levelSelected) + " cleared!");
        Add_Level_Data add_level_data = new Add_Level_Data(getApplicationContext());
        if (Levels.levelSelected <= 24) {
            add_level_data.setLevelCleared(Levels.levelSelected + 1);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this, Levels.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        Button buttonSelected = (Button) view;

        if (view.getId() == R.id.button_result_back) {
            Intent intent = new Intent(this, Levels.class);
            startActivity(intent);
        }else if (view.getId() == R.id.button_result_refresh){

            Intent intent = new Intent(this, Game.class);
            startActivity(intent);
        }else if (view.getId() == R.id.button_result_next) {
            Levels.levelSelected = Levels.levelSelected + 1;
            Intent intent = new Intent(this, Game.class);
            startActivity(intent);
        }

    }
}
