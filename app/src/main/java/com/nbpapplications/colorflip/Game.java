package com.nbpapplications.colorflip;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Game extends AppCompatActivity implements View.OnClickListener{

    private int finalCount = 0;
    private int[] size = new int[2];
    private int color0 = 0;
    private int color1 = 0;
    private int color0Percent = 0;
    private int color1Percent = 0;
    private int[][] listOfBlueTiles;
    private int numOfBlueTiles = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findViewById(R.id.button_game_back).setOnClickListener(this);
        findViewById(R.id.button_game_refresh).setOnClickListener(this);

        setText();
        createGrid();
        setCount();


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

        if (buttonSelected.getId() == R.id.button_game_back) {
            Intent intent = new Intent(this, Levels.class);
            startActivity(intent);
        } else if(buttonSelected.getId() == R.id.button_game_refresh){
            Intent intent = new Intent(this, Game.class);
            startActivity(intent);
        } else {

            if (R.id.gridLayout_game == ((GridLayout) buttonSelected.getParent()).getId()) {
                String buttonInfo = (String) buttonSelected.getTag();;
                int x = Character.getNumericValue(buttonInfo.charAt(12));
                int y = Character.getNumericValue(buttonInfo.charAt(14));
                flipTiles(x, y);

            }
        }
    }

    private void flipTiles(int posX, int posY){
        int[] start = new int[2];
        int[] end = new int[2];

        int[] pos = {posX, posY};
        for (int i = 0; i < pos.length; i++) {
            if (pos[i] - 1 < 0) {
                start[i] = 0;
            }else{
                start[i] = pos[i] - 1;
            }

            if (pos[i] + 1 >= size[i]) {
                end[i] = size[i];
            }else{
                end[i] = pos[i] + 1;
            }
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout_game);

        for (int i = 0; i < finalCount; i++){
            Button button = (Button) gridLayout.findViewById(i);
            String tag = (String) button.getTag();
            int x = Character.getNumericValue(tag.charAt(12));
            int y = Character.getNumericValue(tag.charAt(14));
            if (x >= start[0] && x <= end[0] && y >= start[1] && y <= end[1]){
                int color = Character.getNumericValue(tag.charAt(16));
                int flipColor = flipColor(color);
                button.setBackgroundColor(flipColor);
                int newColor = -1;
                if (color == 0){
                    newColor = 1;
                    color0--;
                    color1++;
                    setCount();
                }else{
                    newColor = 0;
                    color0++;
                    color1--;
                    setCount();
                }
                String buttonTag = "button_game_" + String.valueOf(x) + "_" + String.valueOf(y) + "_" + String.valueOf(newColor);
                button.setTag(buttonTag);
            }
        }

        if (checkWin()){
            Intent intent = new Intent(this, Result.class);
            startActivity(intent);

        }

    }

    private int flipColor(int currColor){
        int flipColor = -1;
        switch (currColor){
            case (0):
                flipColor = Main.TILE_COLOR1;
                break;
            case (1):
                flipColor = Main.TILE_COLOR0;
                break;
        }
        return flipColor;
    }

    private boolean checkWin(){
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout_game);
        boolean blue = true;
        for (int i = 0; i < finalCount && blue; i++){
            Button button = (Button) gridLayout.findViewById(i);
            String tag = (String) button.getTag();
            if (Character.getNumericValue(tag.charAt(16)) != 1){
                blue = false;
            }

        }
        return blue;
    }

    private void setText(){
        TextView showLevel = (TextView) findViewById(R.id.textView_game_showLevel);
        TextView showLevelInfo = (TextView) findViewById(R.id.textView_game_showLevelInfo);
        TextView color0Title = (TextView) findViewById(R.id.textView_game_color0_title);
        TextView color1Title = (TextView) findViewById(R.id.textView_game_color1_title);
        TextView color0Info = (TextView) findViewById(R.id.textView_game_color0_info);
        TextView color1Info = (TextView) findViewById(R.id.textView_game_color1_info);

        float sizeText = (float) ((double)30 * Main.RATIO);
        showLevel.setTextSize(sizeText);
        showLevelInfo.setTextSize(sizeText);
        sizeText = (float) ((double)20 * Main.RATIO);
        color0Title.setTextSize(sizeText);
        color1Title.setTextSize(sizeText);
        color0Info.setTextSize(sizeText);
        color1Info.setTextSize(sizeText);

        String text1 = Main.TILE_COLOR0_STRNG + ":  ";
        String text2 = Main.TILE_COLOR1_STRNG + ":  ";
        color0Title.setText(text1);
        color1Title.setText(text2);
        color0Info.setText("100%");
        color1Info.setText("0%");

        color0Info.setTextColor(Main.TILE_COLOR0);
        color1Info.setTextColor(Main.TILE_COLOR1);

        Add_Level_Data add_level_data = new Add_Level_Data(getApplicationContext());
        String textToSet = "level " + String.valueOf(Levels.levelSelected);
        showLevel.setText(textToSet);
        if (add_level_data.getLevelInfo(Levels.levelSelected).equals("")){
            ((LinearLayout) showLevelInfo.getParent()).removeView(showLevelInfo);

        }else {
            showLevelInfo.setText(add_level_data.getLevelInfo(Levels.levelSelected));
        }

    }

    private void createGrid(){
        Add_Level_Data add_level_data = new Add_Level_Data(getApplicationContext());


        String levelDesign = add_level_data.getLevelDesign(Levels.levelSelected);
        int length = Character.getNumericValue(levelDesign.charAt(0));
        int width = Character.getNumericValue(levelDesign.charAt(2));
        double defaultSize = Double.valueOf(levelDesign.substring(4, 7));
        numOfBlueTiles = Integer.parseInt(levelDesign.substring(8, 10));
        if (numOfBlueTiles != 0){
            listOfBlueTiles = new int[numOfBlueTiles][2];
            int currentMarker = 11;
            int finalMarker = currentMarker + (numOfBlueTiles * 4) + (numOfBlueTiles - 1);
            int counter = 0;
            for (int i = currentMarker; i < finalMarker; i = i + 5){
                listOfBlueTiles[counter][0] = Integer.valueOf(levelDesign.substring(i, i + 2));
                listOfBlueTiles[counter][1] = Integer.valueOf(levelDesign.substring(i + 2, i + 4));
                counter++;
            }

        }

        int newSize = (int)(defaultSize * Main.RATIO);

        size[0] = length;
        size[1] = width;
        int counter = 0;

        GridLayout gl = (GridLayout) findViewById(R.id.gridLayout_game);
        Button[][] buttonArray = new Button[length][width];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                buttonArray[i][j] = new Button(this);
                GridLayout.LayoutParams glP = new GridLayout.LayoutParams();
                glP.rowSpec = GridLayout.spec(i);
                glP.columnSpec = GridLayout.spec(j);
                glP.setMargins(10, 10, 10, 10);
                glP.height = newSize;
                glP.width = newSize;

                String buttonInfo = "button_game_" + String.valueOf(i) + "_" + String.valueOf(j);
                buttonArray[i][j].setId(counter);
                buttonArray[i][j].setTag(buttonInfo + "_0");
                buttonArray[i][j].setOnClickListener(this);
                buttonArray[i][j].setBackgroundColor(Main.TILE_COLOR0);
                if (numOfBlueTiles != 0){
                    if (checkIfTileBlue(i, j)){
                        buttonArray[i][j].setBackgroundColor(Main.TILE_COLOR1);
                        buttonArray[i][j].setTag(buttonInfo + "_1");
                    }
                }
                gl.addView(buttonArray[i][j], glP);
                counter++;
            }

        }

        finalCount = counter;
        color0 = finalCount - color1;

    }

    private void setCount(){
        TextView color0Info = (TextView) findViewById(R.id.textView_game_color0_info);
        TextView color1Info = (TextView) findViewById(R.id.textView_game_color1_info);
        color0Percent = (int) Math.round(((double) color0 * 100.00) / ((double) finalCount));
        color1Percent = 100 - color0Percent;


        color0Info.setText(String.valueOf(String.valueOf(color0Percent)) + "%");
        color1Info.setText(String.valueOf(String.valueOf(color1Percent)) + "%");
    }

    private boolean checkIfTileBlue(int currX, int currY){
        int x;
        int y;
        for (int i = 0 ; i < numOfBlueTiles; i++){
            x = listOfBlueTiles[i][0];
            y = listOfBlueTiles[i][1];
            if (x == currX && y == currY){
                color0--;
                color1++;
                return true;
            }

        }


        return false;
    }

    /*
    * POSSIBLE NEW ADDISIONS TO THE GAME:
    *   REQUIRE THE USER TO GET A CERTAIN PERCENTAGE OF TILE TO BLUE TO CLEAR THE LEVEL
    *
    *   GIVE THE USER A BOARD THAT IS ALREADY SOME BLUE
    *
    *
    * */



}
