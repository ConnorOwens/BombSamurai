package com.nigatestudios.aragorn.bombsamurai;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Aragorn on 3/6/2017.
 */

public class GameOverLayout extends SurfaceView implements Runnable {
    Thread thread = null;
    Canvas canvas;
    SurfaceHolder surfaceHolder;
    protected Display display;
    protected int screenWidth;
    protected int screenHeight;
    protected Bitmap background;
    protected Bitmap resizedBackground;
    protected Bitmap gameOverPanel;
    protected Bitmap numberOne;
    protected Bitmap numberTwo;
    protected Bitmap numberThree;
    protected Bitmap numberFour;
    protected Bitmap numberFive;
    protected Bitmap numberSix;
    protected Bitmap numberSeven;
    protected Bitmap numberEight;
    protected Bitmap numberNine;
    protected Bitmap numberZero;
    protected Bitmap retryButton;
    protected boolean canDraw = false;
    protected double pixelSize;
    protected int highScore;
    protected int currentScore;
    protected int[] numberDrawWidth;
    protected Bitmap[] numbers;

    public GameOverLayout(Context context) {
        super(context);
        display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        pixelSize = screenWidth/42;
        numberDrawWidth = new int[] {(int)(pixelSize*34),(int)(pixelSize*30),(int)(pixelSize*26),(int)(pixelSize*22),(int)(pixelSize*18),(int)(pixelSize*14),(int)(pixelSize*10),(int)(pixelSize*6)};
        surfaceHolder=getHolder();
        background = BitmapFactory.decodeResource(getResources(), R.drawable.white_background);
        resizedBackground = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, true);
        gameOverPanel = BitmapFactory.decodeResource(getResources(), R.drawable.end_screen);
        gameOverPanel = Bitmap.createScaledBitmap(gameOverPanel, screenWidth, (53*screenWidth)/42,true);
        retryButton = BitmapFactory.decodeResource(getResources(), R.drawable.retry_button);
        retryButton = Bitmap.createScaledBitmap(retryButton, (int)(23*pixelSize), (int)(11*pixelSize), true);
        numberOne = BitmapFactory.decodeResource(getResources(), R.drawable.number_1);
        numberOne = Bitmap.createScaledBitmap(numberOne, (int)(3*pixelSize), (int) (5*pixelSize),true);
        numberTwo = BitmapFactory.decodeResource(getResources(), R.drawable.number_2);
        numberTwo = Bitmap.createScaledBitmap(numberTwo, (int)(3*pixelSize), (int) (5*pixelSize),true);
        numberThree = BitmapFactory.decodeResource(getResources(), R.drawable.number_3);
        numberThree = Bitmap.createScaledBitmap(numberThree, (int)(3*pixelSize), (int) (5*pixelSize),true);
        numberFour = BitmapFactory.decodeResource(getResources(), R.drawable.number_4);
        numberFour = Bitmap.createScaledBitmap(numberFour, (int)(3*pixelSize), (int) (5*pixelSize),true);
        numberFive = BitmapFactory.decodeResource(getResources(), R.drawable.number_5);
        numberFive = Bitmap.createScaledBitmap(numberFive, (int)(3*pixelSize), (int) (5*pixelSize),true);
        numberSix = BitmapFactory.decodeResource(getResources(), R.drawable.number_6);
        numberSix = Bitmap.createScaledBitmap(numberSix, (int)(3*pixelSize), (int) (5*pixelSize),true);
        numberSeven = BitmapFactory.decodeResource(getResources(), R.drawable.number_7);
        numberSeven = Bitmap.createScaledBitmap(numberSeven, (int)(3*pixelSize), (int) (5*pixelSize),true);
        numberEight = BitmapFactory.decodeResource(getResources(), R.drawable.number_8);
        numberEight = Bitmap.createScaledBitmap(numberEight, (int)(3*pixelSize), (int) (5*pixelSize),true);
        numberNine = BitmapFactory.decodeResource(getResources(), R.drawable.number_9);
        numberNine = Bitmap.createScaledBitmap(numberNine, (int)(3*pixelSize), (int) (5*pixelSize),true);
        numberZero = BitmapFactory.decodeResource(getResources(), R.drawable.number_0);
        numberZero = Bitmap.createScaledBitmap(numberZero, (int)(3*pixelSize), (int) (5*pixelSize),true);
        numbers = new Bitmap[]{numberZero,numberOne,numberTwo,numberThree,numberFour,numberFive,numberSix,numberSeven,numberEight,numberNine};
    }
    @Override
    public void run() {
        while (canDraw) {
            if (!surfaceHolder.getSurface().isValid()){
                continue;
            }
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(resizedBackground,0,0,null);
            canvas.drawBitmap(gameOverPanel,0,(int)((((double)screenHeight/2)-((double)gameOverPanel.getHeight()/2))),null);
            canvas.drawBitmap(retryButton, (int)((((double)screenWidth/2)-((double)retryButton.getWidth()/2))), screenHeight-retryButton.getHeight()-(int)pixelSize, null);
            drawHighScore();
            drawCurrentScore();
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawCurrentScore() {
        int drawHeight = (int)(((double)screenHeight/2)-((double)gameOverPanel.getHeight()/2)+(pixelSize*28));
        int currentScoreBackup = currentScore;
        int[] currentScoreArray = {currentScoreBackup%10};
        currentScoreBackup/=10;
        while (currentScoreBackup/10>=1) {
            currentScoreArray = addToArray(currentScoreBackup%10 ,currentScoreArray);
            currentScoreBackup/=10;
        }
        if (currentScore/10>=1) {
            currentScoreArray = addToArray(currentScoreBackup,currentScoreArray);
        }
        for (int i=0; i<currentScoreArray.length; i++) {
            canvas.drawBitmap(numbers[currentScoreArray[i]], numberDrawWidth[i], drawHeight, null);
        }
    }

    private void drawHighScore() {
        int drawHeight = (int)(((double)screenHeight/2)-((double)gameOverPanel.getHeight()/2)+(pixelSize*44.5));
        int highScoreBackup = highScore;
        int[] highScoreArray = {highScoreBackup%10};
        highScoreBackup/=10;
        while (highScoreBackup/10>=1) {
            highScoreArray = addToArray(highScoreBackup%10 ,highScoreArray);
            highScoreBackup/=10;
        }
        if (highScore/10>=1) {
            highScoreArray=addToArray(highScoreBackup,highScoreArray);
        }

        for (int i=0; i<highScoreArray.length; i++) {
            canvas.drawBitmap(numbers[highScoreArray[i]], numberDrawWidth[i], drawHeight, null);
        }
    }

    private int[] addToArray(int a, int[] b) {
        int[] newScoreArray = new int[b.length+1];
        for (int i = 0; i<b.length; i++) {
            newScoreArray[i]=b[i];
        }
        newScoreArray[b.length] = a;
        return newScoreArray;
    }

    protected void pause() {
        canDraw=false;

        while (true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread=null;
    }
    protected void resume() {
        canDraw=true;
        thread = new Thread(this);
        thread.start();
    }

}
