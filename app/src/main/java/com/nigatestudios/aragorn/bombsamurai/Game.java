package com.nigatestudios.aragorn.bombsamurai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.Random;

/**
 * Created by Aragorn on 2/11/2017.
 */

public class Game extends Activity implements GestureDetector.OnGestureListener {
    protected int currentScore=0;
    protected GestureDetectorCompat inputDetector;
    protected Random rand = new Random();
    protected static BombAnimationLayout bombAnimation;

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
        bombAnimation.setParentActivity(this);
        if (bombAnimation.getParent()==null) {
            setContentView(bombAnimation);
        } else {
            ((ViewGroup) bombAnimation.getParent()).removeView(bombAnimation);
            setContentView(bombAnimation);
        }
        bombAnimation.frameDuration=3;
        bombAnimation.bombType=0;
        inputDetector = new GestureDetectorCompat(this, this);
    }
    protected void endGame() {
        Intent endGame = new Intent(this, GameOverScreen.class);
        endGame.putExtra("currentScore",currentScore);
        startActivity(endGame);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bombAnimation.pause();
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
        bombAnimation.resume();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

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
        if (bombAnimation.bombLive) {
            if (bombAnimation.bombType==0) {
                if (e1.getY()<(bombAnimation.screenHeight/2)+ (bombAnimation.horizantalBomb.getHeight() * (0.0882352941176471)) && e1.getY()>(bombAnimation.screenHeight/2)- (bombAnimation.horizantalBomb.getHeight() * (0.0882352941176471)) && e2.getY()<(bombAnimation.screenHeight/2)+ (bombAnimation.horizantalBomb.getHeight() *0.0882352941176471) && e2.getY()>(bombAnimation.screenHeight/2)-(bombAnimation.horizantalBomb.getHeight() *0.0882352941176471)){
                    if (Math.abs(velocityX) > 2 * Math.abs(velocityY)) {
                        bombAnimation.bombY = (bombAnimation.background.getHeight()) + (bombAnimation.horizantalBomb.getHeight() / 2);
                        bombAnimation.bombLive = false;
                        bombAnimation.fuseAnimationFrameCount = 0;
                        bombAnimation.firstTime = false;
                        bombAnimation.bombType = rand.nextInt(2);
                        currentScore++;
                        if (currentScore == 5) {
                            bombAnimation.frameDuration=2;
                        }
                        //if (currentScore == 50) {
                        //    bombAnimation.frameDuration=1;
                        //}
                        bombAnimation.frameDurationCount=0;
                    }
                }
            } else if (bombAnimation.bombType==1) {
                if (e1.getX()<(bombAnimation.screenWidth/2)+((bombAnimation.verticalBomb.getWidth())*(.15)) && e1.getX()>(bombAnimation.screenWidth/2)-((bombAnimation.verticalBomb.getWidth())*(.15)) && e2.getX()<(bombAnimation.screenWidth/2)+((bombAnimation.verticalBomb.getWidth())*(.15)) && e2.getX()>(bombAnimation.screenWidth/2)-((bombAnimation.verticalBomb.getWidth())*(.15))) {
                    if (Math.abs(velocityY) > 2 * Math.abs(velocityX)) {
                        bombAnimation.bombY = (bombAnimation.background.getHeight()) + (bombAnimation.horizantalBomb.getHeight() / 2);
                        bombAnimation.bombLive = false;
                        bombAnimation.fuseAnimationFrameCount = 0;
                        bombAnimation.firstTime = false;
                        bombAnimation.bombType = rand.nextInt(2);
                        currentScore++;
                        if (currentScore == 5) {
                            bombAnimation.frameDuration=2;
                        }
                        //if (currentScore == 50) {
                        //    bombAnimation.frameDuration=1;
                        //}
                        bombAnimation.frameDurationCount=0;
                    }
                }
            }

        }
        return false;
    }
    @Override
    public boolean onTouchEvent (MotionEvent event){
        this.inputDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    @Override
    public void onBackPressed() {
    }
}

