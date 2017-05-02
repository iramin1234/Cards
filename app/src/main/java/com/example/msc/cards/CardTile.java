package com.example.msc.cards;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

/**
 * Created by micha on 4/29/2017.
 */

public class CardTile extends TextView {
    public static final int CARD_HEIGHT = 150;
    public static final int CARD_WIDTH = 100;

    private Integer value;
    private Integer suit;
    private boolean lock;

    public CardTile(Context context, Integer val, Integer su) {
        super(context);
        value = val;
        suit = su;
        if(val < 11 && val > 1) setText(val.toString());
        else {
            if(val == 11) setText("J");
            else if(val == 12) setText("Q");
            else if(val == 13) setText("K");
            else if(val == 14) setText("A");
            else setText("Jo");
        }
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setHeight(CARD_HEIGHT);
        setWidth(CARD_WIDTH);
        setTextSize(30);
        if(su % 2 == 0) setBackgroundColor(Color.rgb(255, 0, 0));
        else setBackgroundColor(Color.rgb(0, 0, 255));
    }
    public void moveToViewGroup(ViewGroup targetview) {
        ViewParent parent = getParent();
        if(parent instanceof StackedLayout) Log.i("Parent", "Stacked Layout");
        if(parent instanceof LinearLayout) Log.i("Parent", "Linear Layout");
        LinearLayout owner = (LinearLayout) parent;
        owner.removeView(this);
        targetview.addView(this);


    }


    public void lock() {
        lock = true;
    }

    public void unlock() {
        lock = false;
    }

    public Integer getSuit() {
        return suit;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(!lock) {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                startDragAndDrop(ClipData.newPlainText("", ""), new View.DragShadowBuilder(this), this, 0);
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setSuit(Integer val) {
        suit = val;
    }
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer val) {
        value = val;
    }
}
