package com.example.msc.cards;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewParent;
import android.view.MotionEvent;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int LIGHT_BLUE = Color.rgb(176, 200, 255);
    public static final int LIGHT_GREEN = Color.rgb(200, 255, 200);
    private StackedLayout cardDeck;

    static private Integer playerscore;
    static private Integer computerscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout playerLinearLayout = (LinearLayout) findViewById(R.id.playerlinearLayout);
        LinearLayout playerHandLinearLayout = (LinearLayout) findViewById(R.id.playerhandlinearLayout);
        LinearLayout computerLinearLayout = (LinearLayout) findViewById(R.id.computerlinearLayout);
        LinearLayout computerHandLinearLayout = (LinearLayout) findViewById(R.id.computerhandlinearLayout);
        TextView text = (TextView) findViewById(R.id.textView);
        text.setText("Press deal to start.");

        playerLinearLayout.setOnDragListener(new DragListener());
        playerHandLinearLayout.setOnDragListener(new DragListener());
        Button btn_deal = (Button) findViewById(R.id.btn_deal);
        btn_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartGame();
            }
        });

    }

    protected void onStartGame() {


        TextView turntext = (TextView) findViewById(R.id.textView);
        TextView wintext = (TextView) findViewById(R.id.textView3);
        turntext.setText("Player Turn");
        wintext.setText("Game started.");
        LinearLayout playerLinearLayout = (LinearLayout) findViewById(R.id.playerlinearLayout);
        LinearLayout playerHandLinearLayout = (LinearLayout) findViewById(R.id.playerhandlinearLayout);
        LinearLayout computerLinearLayout = (LinearLayout) findViewById(R.id.computerlinearLayout);
        LinearLayout computerHandLinearLayout = (LinearLayout) findViewById(R.id.computerhandlinearLayout);
        LinearLayout deckLinearLayout = (LinearLayout) findViewById(R.id.decklinearLayout);
        playerscore = 0;
        computerscore = 0;

        TextView text2 = (TextView) findViewById(R.id.textView2);
        text2.setText("Score: Player: " + playerscore.toString() + "    Computer: " + computerscore.toString());



        cardDeck = new StackedLayout(this);
        playerLinearLayout.removeAllViews();
        playerHandLinearLayout.removeAllViews();
        computerLinearLayout.removeAllViews();
        computerHandLinearLayout.removeAllViews();
        deckLinearLayout.removeAllViews();
        cardDeck.clear();

        deckLinearLayout.addView(cardDeck);


        ArrayList<CardTile> unsortedDeck = new ArrayList<>();
        for(int i = 0; i < 4; ++i) {
            for(int j = 2; j < 15; ++j) {
                CardTile card = (CardTile) new CardTile(this, j, i);
                //card.setSuit(i);
                //card.setValue(j);
                unsortedDeck.add(card);

            }
        }


        Integer[] tracker = new Integer[52];
        for(int i = 0; i < 52; ++i) tracker[i] = 0;
        Random random = new Random();
        for(int i = 0; i < 52; ++i) {
            boolean done = false;
            while(!done) {
                Integer current = random.nextInt(52);
                if (tracker[current] == 0) {
                    tracker[current] = 1;
                    cardDeck.push(unsortedDeck.get(current));
                    done = true;
                }
            }
        }

        for(int i = 0; i < 52; ++i) {
            CardTile dealtCard = null;
            if(!cardDeck.empty()) {
                dealtCard = (CardTile) cardDeck.pop();
                if(i % 2 == 0) {
                    playerHandLinearLayout.addView(dealtCard);
                }
                else {
                    computerHandLinearLayout.addView(dealtCard);
                }
            }
            else {
                Log.i("Deck", "Deck is empty");
            }
        }
    }

    void computerTurn() {
        TextView turntext = (TextView) findViewById(R.id.textView);
        turntext.setText("Computer Turn");

        TextView wintext = (TextView) findViewById(R.id.textView3);
        String temp;

        ViewGroup computerHandLinearLayout = (ViewGroup) findViewById(R.id.computerhandlinearLayout);
        Random random = new Random();

        CardTile card = (CardTile) computerHandLinearLayout.getChildAt(random.nextInt(computerHandLinearLayout.getChildCount()));
        //Log.d("Test", "Count = " + computerHandLinearLayout.getChildCount());

        ViewGroup computerLinearLayout = (ViewGroup) findViewById(R.id.computerlinearLayout);
        computerHandLinearLayout.removeView(card);
        computerLinearLayout.addView(card);

        ViewGroup playerLinearLayout = (ViewGroup) findViewById(R.id.playerlinearLayout);
        ViewGroup playerHandLinearLayout = (ViewGroup) findViewById(R.id.playerhandlinearLayout);

        if(playerLinearLayout.getChildCount() > 0) {
            CardTile playerCard;
            playerCard = (CardTile) playerLinearLayout.getChildAt(playerLinearLayout.getChildCount() - 1);
            Log.d("Card vals", "Player " + playerCard.getValue().toString() + "    Computer " + card.getValue().toString());
            if (playerCard.getValue() > card.getValue()) {
                //temp = text.getText().toString();
                wintext.setText("Player wins the hand!");
                ++playerscore;

            } else if (card.getValue() > playerCard.getValue()) {

                wintext.setText("Computer wins the hand!");
                ++computerscore;
            } else {
                wintext.setText("War!");
                while(card.getValue() == playerCard.getValue()) {
                    Integer compcount = computerHandLinearLayout.getChildCount();
                    Integer playercount = playerHandLinearLayout.getChildCount();

                    if(compcount > 3 && playercount > 3) {
                        if(compcount > 4) compcount = 4;
                        if(playercount > 4) playercount = 4;
                        for(int i = 0; i < compcount; ++ i) {
                            card = (CardTile) computerHandLinearLayout.getChildAt(random.nextInt(computerHandLinearLayout.getChildCount()));
                            computerHandLinearLayout.removeView(card);
                            computerLinearLayout.addView(card);
                        }
                        for(int i = 0; i < playercount; ++i) {
                            playerCard = (CardTile) playerHandLinearLayout.getChildAt(random.nextInt(playerHandLinearLayout.getChildCount()));
                            playerHandLinearLayout.removeView(playerCard);
                            playerLinearLayout.addView(playerCard);
                        }
                        if (playerCard.getValue() > card.getValue()) {
                            temp = wintext.getText().toString();
                            wintext.setText(temp + "     " + "Player wins the hand");
                            ++playerscore;

                        } else if (card.getValue() > playerCard.getValue()) {
                            temp = wintext.getText().toString();
                            wintext.setText(temp + "     " + "Computer wins the hand");
                            ++computerscore;
                        }

                    }
                }

            }

            TextView scoreboard = (TextView) findViewById(R.id.textView2);
            scoreboard.setText("Score: Player: " + playerscore.toString() + "    Computer: " + computerscore.toString());
            turntext.setText("Player Turn");
        }
    }

    private class DragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            TextView text = (TextView) findViewById(R.id.textView);
            int action = event.getAction();
            switch(event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(LIGHT_GREEN);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.WHITE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:

                    // Dropped, reassign Tile to the target Layout
                    ViewGroup playerHandLinearLayout = (ViewGroup) findViewById(R.id.playerhandlinearLayout);
                    ViewGroup playerLinearLayout = (ViewGroup) findViewById(R.id.playerlinearLayout);
                    ViewGroup computerHandLinearLayout = (ViewGroup) findViewById(R.id.computerhandlinearLayout);
                    ViewGroup computerLinearLayout = (ViewGroup) findViewById(R.id.computerlinearLayout);
                    playerLinearLayout.removeAllViews();
                    computerLinearLayout.removeAllViews();

                    CardTile card = (CardTile) event.getLocalState();
                    card.moveToViewGroup((ViewGroup) v);


                    computerTurn();
                    return true;
            }
            return false;
        }
    }

}
