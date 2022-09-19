package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    static int currPlayer=1;
    // 0 -->O turn
    // 1--> X turn

    int winningPattern[][]={
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };
    int gridCurrState[]={2,2,2,2,2,2,2,2,2};
     //  2-->grid is empty now
    //   1-->x is present
    //   0-->O is present
    public void pressed(View view){
        ImageView img=(ImageView) view;
        TextView status=findViewById(R.id.status);

           int pressedIdx=Integer.parseInt(img.getTag().toString());
           //pressedidx --> we got the index of the cell pressed by user
           if(gridCurrState[pressedIdx]==1 || gridCurrState[pressedIdx]==0){
               return;
           }
            if(currPlayer==1){
                status.setText("O Turn");
            }else{
                status.setText("X Turn");
            }
           gridCurrState[pressedIdx]=currPlayer;
           img.setTranslationY(-1000f);
           if(currPlayer==1){
               img.setImageResource(R.drawable.cross);
               boolean win=didWin(currPlayer);
               if(win){
                   //display won
                    status.setText("X Won --Will Restart in 2 seconds");
                   //restart
                   new Handler().postDelayed(new Runnable() {
                       public void run() {
                           finish();
                           startActivity(getIntent());
                       }
                   }, 2000);
               }
               boolean filled=allCellFilled();
               if(filled){
                   status.setText("None Won -- Will restart in 2 Seconds");
                   //all cell filled but no one won, restart
                   new Handler().postDelayed(new Runnable() {
                       public void run() {
                           finish();
                           startActivity(getIntent());
                       }
                   }, 2000);

               }
               currPlayer=0;
           }else{
               img.setImageResource(R.drawable.zero);
               boolean win=didWin(currPlayer);
               if(win){
                   //display win

                   status.setText("O Won --Will Restart in 2 seconds");
                   new Handler().postDelayed(new Runnable() {
                       public void run() {
                           finish();
                           startActivity(getIntent());
                       }
                   }, 2000);
               }
               boolean filled=allCellFilled();
               if(filled){
                   status.setText("None Won-- Will Restart in 2 Seconds");
                   //all cell filled but no one won, restart
                   new Handler().postDelayed(new Runnable() {
                       public void run() {
                           finish();
                           startActivity(getIntent());
                       }
                   }, 2000);
               }
               currPlayer=1;
           }
        img.animate().translationYBy(1000f).setDuration(200);




    }
    public boolean allCellFilled(){
        int n=gridCurrState.length;
        for(int i=0;i<n;i++){
            int a=gridCurrState[i];
            if(a==2){
                return false;
            }
        }
        currPlayer=1;
        return true;
    }
    public boolean didWin(int active){
        int n=winningPattern.length;
        for(int i=0;i<n;i++){
            boolean check=true;
            for(int j=0;j<3;j++){
                int a=gridCurrState[winningPattern[i][j]];
                if(a!=active){
                    check=false;
                }
            }
            if(check){
                currPlayer=1;
                return true;
            }
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}