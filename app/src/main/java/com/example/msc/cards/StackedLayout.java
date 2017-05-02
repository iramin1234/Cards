package com.example.msc.cards;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Stack;

/**
 * Created by micha on 4/29/2017.
 */

public class StackedLayout extends LinearLayout {

    private Stack<View> deck = new Stack();

    public StackedLayout(Context context) {
        super(context);
    }

    public void push(View card) {
        //if(!deck.empty()) {
        //    removeView(deck.peek());
        //}
        deck.push(card);
    }

    public View pop() {
        View poppedCard = null;
        if(!deck.empty()) {
            poppedCard = deck.pop();
            removeView(poppedCard);
            //if(!deck.empty()) {
            //    addView(deck.peek());
            //}
        }
        return poppedCard;
    }

    public View peek() {
        return deck.peek();
    }

    public boolean empty() {
        return deck.empty();
    }

    public void clear() {
        View poppedCard;
        while(!deck.empty()) {
            poppedCard = deck.pop();
            removeView(poppedCard);
        }
    }
}
