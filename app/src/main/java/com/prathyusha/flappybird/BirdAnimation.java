package com.prathyusha.flappybird;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BirdAnimation extends ImageView {

    public BirdAnimation(Context context) {
        super(context);
    }

    public BirdAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BirdAnimation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private AnimationDrawable animation() {
        return (AnimationDrawable) getBackground();
    }

    public void start() {
        animation().start();
    }

    public void stop() {
        animation().stop();
    }
}

