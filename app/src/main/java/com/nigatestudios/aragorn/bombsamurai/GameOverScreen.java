package com.nigatestudios.aragorn.bombsamurai;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aragorn on 3/6/2017.
 */

public class GameOverScreen extends Activity implements GestureDetector.OnGestureListener {
    protected static GameOverLayout gameOver;
    protected GestureDetectorCompat inputDetector;
    protected int currentScore;
    protected int highScore;

    public GameOverScreen() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        if (gameOver.getParent()==null) {
            setContentView(gameOver);
        } else {
            ((ViewGroup) gameOver.getParent()).removeView(gameOver);
            setContentView(gameOver);
        }

        inputDetector = new GestureDetectorCompat(this, this);
        currentScore = getIntent().getIntExtra("currentScore",0);
        gameOver.currentScore=currentScore;
        checkHighScore();
    }
    @Override
    protected void onPause() {
        super.onPause();
        gameOver.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions =
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        gameOver.resume();
    }
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
    @Override
    public boolean onTouchEvent (MotionEvent event){
        this.inputDetector.onTouchEvent(event);
        if ((gameOver.screenWidth/2)-(gameOver.retryButton.getWidth()/2)<event.getX() && event.getX()<(gameOver.screenWidth/2)+(gameOver.getWidth()/2)) {
            if (gameOver.screenHeight-gameOver.retryButton.getHeight()-(int)gameOver.pixelSize<event.getY() && event.getY()<gameOver.screenHeight-(int)gameOver.pixelSize) {
                retryGame();
            }
        }
        return super.onTouchEvent(event);
    }

    private void retryGame() {
        Intent openGame = new Intent(this, Game.class);
        startActivity(openGame);
    }
    @Override
    public void onBackPressed() {
    }
    public void checkHighScore() {
        SharedPreferences prefs = this.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        highScore = prefs.getInt("savedHighScore", 0);
        if (currentScore>highScore) {
            highScore=currentScore;
        }
        gameOver.highScore=highScore;
        editor.putInt("savedHighScore", highScore);
        editor.commit();
    }
}
