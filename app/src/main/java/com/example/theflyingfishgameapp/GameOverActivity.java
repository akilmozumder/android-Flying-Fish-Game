package com.example.theflyingfishgameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameOverActivity extends AppCompatActivity {
    private Button gameOverBtn;
    private TextView displayScore;
    private String totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        gameOverBtn = (Button)findViewById(R.id.play_again_btn);
        displayScore = (TextView)findViewById(R.id.tvScore);

        totalScore=getIntent().getExtras().get("total").toString();




        gameOverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });



       displayScore.setText("Your Score: "+totalScore);
    }
}
