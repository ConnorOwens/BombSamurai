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
 * Created by Aragorn on 2/12/2017.
 */

public class BombAnimationLayout extends SurfaceView implements Runnable {
    Thread thread = null;
    Boolean canDraw = false;
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    int pointerX;
    int pointerDirection = 1;
    protected int bombX;
    protected int bombY;
    protected int screenWidth;
    protected int screenHeight;
    protected boolean bombSlashed =false;
    Bitmap background;
    protected int slashHeight;
    public boolean bombLive = false;
    protected Display display;
    int frameDuration = 3;
    int frameDurationCount = 0;
    Bitmap fuseAnimation1;
    Bitmap fuseAnimation2;
    Bitmap fuseAnimation3;
    Bitmap fuseAnimation4;
    Bitmap fuseAnimation5;
    Bitmap fuseAnimation6;
    Bitmap fuseAnimation7;
    Bitmap fuseAnimation8;
    Bitmap fuseAnimation9;
    Bitmap fuseAnimation10;
    Bitmap fuseAnimation11;
    Bitmap fuseAnimation12;
    Bitmap fuseAnimation13;
    Bitmap fuseAnimation14;
    Bitmap[] fuseAnimation;
    Bitmap pointer;
    Bitmap slashTheBomb;
    protected Bitmap horizantalBomb;
    protected Bitmap verticalBomb;
    protected int bombType = 0;
    protected boolean firstTime = true;
    int fuseAnimationFrameCount = 0;
    Game parentActivity;

    public BombAnimationLayout(Context context) {
        super(context);
        display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        surfaceHolder=getHolder();
        background = BitmapFactory.decodeResource(getResources(), R.drawable.white_background);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, true);
        slashHeight = screenHeight/2;
        pointer = BitmapFactory.decodeResource(getResources(), R.drawable.pointer);
        pointer = Bitmap.createScaledBitmap(pointer,screenWidth/6,(((screenWidth/6)*431)/300),true);
        slashTheBomb = BitmapFactory.decodeResource(getResources(), R.drawable.slashthebomb);
        slashTheBomb = Bitmap.createScaledBitmap(slashTheBomb, 7*screenWidth/8, (int) (((7*screenWidth)/8)*.145),true);
        fuseAnimation1 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_1);
        fuseAnimation1 = Bitmap.createScaledBitmap(fuseAnimation1,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation2 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_2);
        fuseAnimation2 = Bitmap.createScaledBitmap(fuseAnimation2,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation3 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_3);
        fuseAnimation3 = Bitmap.createScaledBitmap(fuseAnimation3,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation4 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_4);
        fuseAnimation4 = Bitmap.createScaledBitmap(fuseAnimation4,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation5 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_5);
        fuseAnimation5 = Bitmap.createScaledBitmap(fuseAnimation5,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation6 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_6);
        fuseAnimation6 = Bitmap.createScaledBitmap(fuseAnimation6,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation7 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_7);
        fuseAnimation7 = Bitmap.createScaledBitmap(fuseAnimation7,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation8 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_8);
        fuseAnimation8 = Bitmap.createScaledBitmap(fuseAnimation8,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation9 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_9);
        fuseAnimation9 = Bitmap.createScaledBitmap(fuseAnimation9,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation10 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_10);
        fuseAnimation10 = Bitmap.createScaledBitmap(fuseAnimation10,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation11 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_11);
        fuseAnimation11 = Bitmap.createScaledBitmap(fuseAnimation11,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation12 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_12);
        fuseAnimation12 = Bitmap.createScaledBitmap(fuseAnimation12,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation13 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_13);
        fuseAnimation13 = Bitmap.createScaledBitmap(fuseAnimation13,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation14 = BitmapFactory.decodeResource(getResources(), R.drawable.fuse_14);
        fuseAnimation14 = Bitmap.createScaledBitmap(fuseAnimation14,4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        fuseAnimation = new Bitmap[]{fuseAnimation1, fuseAnimation2, fuseAnimation3, fuseAnimation4, fuseAnimation5, fuseAnimation6,
                fuseAnimation7, fuseAnimation8, fuseAnimation9, fuseAnimation10, fuseAnimation11, fuseAnimation12, fuseAnimation13, fuseAnimation14};
        horizantalBomb = BitmapFactory.decodeResource(getResources(), R.drawable.horizantal_bomb);
        horizantalBomb = Bitmap.createScaledBitmap(horizantalBomb, 4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        verticalBomb = BitmapFactory.decodeResource(getResources(), R.drawable.vertical_bomb);
        verticalBomb = Bitmap.createScaledBitmap(verticalBomb, 4*screenWidth/5,(int) ((4*screenWidth/5)*1.7),true);
        bombX=(screenWidth/2);
        bombY=(screenHeight)+(horizantalBomb.getHeight()/2);
        pointerX = (screenWidth/2)-(horizantalBomb.getWidth()/2);
    }
    @Override
    public void run() {
        while (canDraw) {
            if (!surfaceHolder.getSurface().isValid()){
                continue;
            }
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(background,0,0,null);
            if (firstTime) {
                bombLive = false;
                movePointer();
                canvas.drawBitmap(slashTheBomb,(screenWidth/2)-(slashTheBomb.getWidth()/2),6*screenHeight/7,null);
                moveBomb();
                canvas.drawBitmap(fuseAnimation1,bombX-(horizantalBomb.getWidth()/2),bombY-(horizantalBomb.getHeight()/2),null);
                canvas.drawBitmap(horizantalBomb,bombX-(horizantalBomb.getWidth()/2),bombY-(horizantalBomb.getHeight()/2),null);
                canvas.drawBitmap(pointer,pointerX-(pointer.getWidth()/2),(screenHeight/2)+(pointer.getHeight()/2),null);
            } else if (bombLive) {
                fuseAnimation();
                drawBomb();
            } else {
                moveBomb();
                canvas.drawBitmap(fuseAnimation1,bombX-(horizantalBomb.getWidth()/2),bombY-(horizantalBomb.getHeight()/2),null);
                drawBomb();

            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawBomb() {
        if (bombType == 0) {
            canvas.drawBitmap(horizantalBomb,bombX-(horizantalBomb.getWidth()/2),bombY-(horizantalBomb.getHeight()/2),null);
        }
        if (bombType == 1) {
            canvas.drawBitmap(verticalBomb,bombX-(horizantalBomb.getWidth()/2),bombY-(horizantalBomb.getHeight()/2),null);
        }
    }

    private void movePointer() {
        if (pointerDirection == 1) {
            pointerX+=(screenWidth/24);
            if ((screenWidth/2)+(horizantalBomb.getWidth()/2)<pointerX) {
                pointerDirection = -1;
            }
        }
        if (pointerDirection == -1) {
            pointerX-=screenWidth/24;
            if (pointerX<(screenWidth/2)-(horizantalBomb.getWidth()/2)) {
                pointerDirection = 1;
            }
        }
    }

    private void fuseAnimation() {
        if (fuseAnimationFrameCount<fuseAnimation.length) {
            canvas.drawBitmap(fuseAnimation[fuseAnimationFrameCount], bombX-(horizantalBomb.getWidth()/2),bombY-(horizantalBomb.getHeight()/2),null);
        }
        frameDurationCount++;
        if (frameDurationCount==frameDuration) {
            fuseAnimationFrameCount++;
            frameDurationCount=0;
        }

        if (fuseAnimationFrameCount == fuseAnimation.length+1) {
            bombLive=false;
            canDraw=false;
            firstTime=true;
            parentActivity.endGame();
        }
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
    protected void moveBomb() {
        if ((screenHeight/2)<bombY) {
            bombY-=50;

        } else {
            bombLive=true;
            bombY=screenHeight/2;
        }
    }
    protected void setParentActivity(Game a) {
        parentActivity = a;
    }
}
