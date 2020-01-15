package com.nbpapplications.colorflip;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner s1 = (Spinner) findViewById(R.id.spinner_settings_color1);
        Spinner s2 = (Spinner) findViewById(R.id.spinner_settings_color2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.settings_colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s1.setAdapter(adapter);
        s2.setAdapter(adapter);

        s1.setSelection(getColorNum(Main.TILE_COLOR0));
        s2.setSelection(getColorNum(Main.TILE_COLOR1));



    }

    private int getColorNum(int color){
        int colorIndex = -1;

        switch (color){
            case (Color.RED):
                colorIndex = 0;
                break;
            case (Color.BLUE):
                colorIndex = 1;
                break;
            case (Color.MAGENTA):
                colorIndex = 2;
                break;
            case (Color.GREEN):
                colorIndex = 3;
                break;
            case (Color.GRAY):
                colorIndex = 4;
                break;
        }

        return colorIndex;
    }
}
