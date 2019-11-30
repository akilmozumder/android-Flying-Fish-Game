package com.example.theflyingfishgameapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {

    private Bitmap fish[] = new Bitmap[2];
    private int fishX=10;
    private int fishY;
    private int fishSpeed;

    private int canvasHeight,canvasWidth;

    private int yellowX, yellowY, yellowSpeed=10;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed=16;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed=20;
    private Paint redPaint = new Paint();

    private int score,lifeCounterFish;

    private boolean touch = false;

    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[]=new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY=550;
        score=0;
        lifeCounterFish=3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();



        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishy = fish[0].getHeight();
        int maxFishy = canvasHeight - fish[0].getHeight() + 3;
        fishY = fishY+fishSpeed;
        if (fishY<minFishy){
            fishY = minFishy;
        }
        if(fishY>maxFishy){
            fishY = maxFishy;
        }
        fishSpeed = fishSpeed+2;
        if(touch){
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch=false;
        }else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }

        yellowX = yellowX-yellowSpeed;

        if (hitBallChecker(yellowX,yellowY)){
            score=score+10;
            yellowX= -100;
        }

        if (yellowX<0){
            yellowX= canvasWidth+21;
            yellowY=(int)Math.floor(Math.random() * (maxFishy - minFishy)) + minFishy;
        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);

        greenX = greenX-greenSpeed;

        if (hitBallChecker(greenX,greenY)){
            score=score+20;
            greenX= -100;
        }

        if (greenX<0){
            greenX= canvasWidth+21;
            greenY=(int)Math.floor(Math.random() * (maxFishy - minFishy)) + minFishy;
        }
        canvas.drawCircle(greenX,greenY,30,greenPaint);

        redX = redX-redSpeed;

        if (hitBallChecker(redX,redY)){

            redX= -100;
            lifeCounterFish--;

            if (lifeCounterFish==0){
                Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                gameOverIntent.putExtra("total",score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if (redX<0){
            redX= canvasWidth+21;
            redY=(int)Math.floor(Math.random() * (maxFishy - minFishy)) + minFishy;
        }
        canvas.drawCircle(redX,redY,35,redPaint);

        canvas.drawText("Score : " + score,20,60,scorePaint);

        for (int i=0; i<3; i++){
            int x = (int) (380 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i<lifeCounterFish){
                canvas.drawBitmap(life[0],x,y,null);

            }else{
                canvas.drawBitmap(life[1],x,y,null);
            }

        }







    }

    public boolean hitBallChecker(int x, int y){
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight())){
            return true;
        }
        return false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            touch=true;
            fishSpeed=-22;
        }
        return true;
    }
}
