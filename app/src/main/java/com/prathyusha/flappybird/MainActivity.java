package com.prathyusha.flappybird;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements  OnTouchListener {

    private BirdAnimation mBirdAnimation;
    private Rectangles mRectangles;
    private RelativeLayout mRelativeLayout;
    private ImageView mBgImageView;
    private Dialog mAlertDialog;
    private boolean mIsBirdMoving;
    private boolean mIsRectangleMoving;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mDeltaX = 10;
    private int mDeltaY = 15;
    private int mRectangleWidth;
    private int mGap;
    private int mGroundHeight;
    public boolean colission;
    private static int MAX_NUM_RECTANGLES_REPEAT = 5;
    private int numRectRepeated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScreenHeight = ScreenMetrics.getScreenHeight(this);
        mScreenWidth = ScreenMetrics.getScreenWidth(this);
        mRectangleWidth = ScreenMetrics.dipResourceToPx(this, R.dimen.rectangle_width);
        //mRectangleWidth = getResources().getDimension(R.dimen.rectangle_width);
        mGap = ScreenMetrics.dipResourceToPx(this, R.dimen.rectangle_gap);
        mGroundHeight = ScreenMetrics.dipResourceToPx(this, R.dimen.ground_height);

        mRectangles = (Rectangles) findViewById(R.id.rectangles_view);
        mRectangles.setX(mRectangleWidth);
        mRectangles.setY(0);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        mRelativeLayout.setOnTouchListener(this);

        mBgImageView = (ImageView) findViewById(R.id.bg_hint_imageView);

        mBirdAnimation = (BirdAnimation) findViewById(R.id.bird_imageView);
        startBirdAnimation(mBirdAnimation);
    }

    private void restart() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }

        mBirdAnimation.setX(ScreenMetrics.dipResourceToPx(this, R.dimen.bird_position_x));
        mBirdAnimation.setY(ScreenMetrics.dipResourceToPx(this, R.dimen.bird_position_y));
        mBgImageView.setVisibility(View.VISIBLE);
        startBirdAnimation(mBirdAnimation);

        mRectangles.setX(mScreenWidth);
        mRectangles.setY(0);
    }

    public void startBirdAnimation(View source) {
        mBirdAnimation.start();
    }

    public void stopBirdAnimation(View source) {
        mBirdAnimation.stop();
    }

    public void startMovingBird(View sprite){
        mIsBirdMoving = true;
        moveBird();
    }

    public void stopMovingBird(View sprite){
        mIsBirdMoving = false;
    }

    public void startMovingRectangles(View rectangle){
        mIsRectangleMoving = true;
        moveRectangles();
    }

    public void stopMovingRectangles(View rectangle){
        mIsRectangleMoving = false;
    }

    private void moveUp() {
        if(mDeltaY > 0) {
            mDeltaY = mDeltaY * -1;
        }
    }

    private void moveDown() {
        if(mDeltaY < 0) {
            mDeltaY = mDeltaY * -1;
        }
    }

    private void moveBird() {
        onBirdCollison();
        if (mIsBirdMoving)
            mBirdAnimation.postDelayed(new BirdMover(), 50);
    }

    private void moveRectangles() {
        onRectanglesCollison();
        if (mIsRectangleMoving)
            mRectangles.postDelayed(new RectanglesMover(), 50);
    }

    private void onBirdCollison() {
        if (yIsOutOfBounds(mBirdAnimation)) {
            stopMovingRectangles(mRectangles);
            stopMovingBird(mBirdAnimation);
            stopBirdAnimation(mBirdAnimation);
            showRestartDialog("Lost");
            colission = true;
        } else {
            mBirdAnimation.setY(mBirdAnimation.getY() + mDeltaY);
        }
    }

    private void onRectanglesCollison() {
        if (xIsOutOfBounds(mRectangles)) {
            if (numRectRepeated >= MAX_NUM_RECTANGLES_REPEAT) {
                stopMovingRectangles(mRectangles);
                stopMovingBird(mBirdAnimation);
                stopBirdAnimation(mBirdAnimation);
                showRestartDialog("Won");
            }
            numRectRepeated++;
            //Reset the rectangle position to right most point
            mRectangles.setX(mScreenWidth);
        } else {
            mRectangles.setX(mRectangles.getX() - mDeltaX);
        }

        if (isHit(mBirdAnimation)) {
            stopMovingRectangles(mRectangles);
            stopMovingBird(mBirdAnimation);
            stopBirdAnimation(mBirdAnimation);
            showRestartDialog("Lost");
            colission = true;
        }
    }

    private boolean xIsOutOfBounds(View widget) {
        float x = widget.getX();
        if (x < -150) {
            return true;
        }
        return false;
    }

    private boolean yIsOutOfBounds(View widget) {
        float y = widget.getY();
        if (y + mDeltaY < 0) return true;
        if (y + mDeltaY + widget.getHeight() + mGroundHeight > mScreenHeight) return true;
        return false;
    }

    public class BirdMover implements Runnable {
        @Override
        public void run() {
            moveBird();
        }
    }

    public class RectanglesMover implements Runnable {
        @Override
        public void run() {
            moveRectangles();
        }
    }

    private boolean isHit(BirdAnimation bird) {
        int birdTop = (int) bird.getY();
        int birdBottom = (int) bird.getY() + bird.getHeight();
        int birdRight = bird.getRight();
        int birdLeft = bird.getLeft();
        int left = (int) mRectangles.getX();
        int right = (int) mRectangles.getX() + mRectangleWidth;
        float rectBottom = ((mScreenHeight - mGroundHeight) / 2) - (mGap / 2);
        float rectTop = ((mScreenHeight - mGroundHeight) / 2) + (mGap / 2);

        return (birdTop + mDeltaY < rectBottom || birdBottom + mDeltaY > rectTop)
                && ((birdRight > left && birdRight < right) || (birdLeft > left && birdLeft < right));
    }

    public boolean onTouch(View arg0, MotionEvent event) {
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
                if(!mIsBirdMoving) {
                    mBgImageView.setVisibility(View.GONE);
                    startMovingBird(mBirdAnimation);

                    if(!mIsRectangleMoving) {
                        startMovingRectangles(mRectangles);
                    }
                }
                moveUp();
                return true;
            case MotionEvent.ACTION_UP:
                moveDown();
                return true;
            case MotionEvent.ACTION_MOVE:
        }
        return false;
    }

    public void showRestartDialog(String msg) {
        if(mAlertDialog == null || !mAlertDialog.isShowing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Game Over");
            builder.setMessage(msg);
            builder.setPositiveButton("Play again", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    restart();
                }
            });
            builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false);
            if(!this.isFinishing()) {
                mAlertDialog = builder.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.flappy_bird, menu);
        return true;
    }

}
