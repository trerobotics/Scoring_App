package com.example.hseastrobotics.ftc_scorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;

public class NewGame extends AppCompatActivity {

    EditText teamName, autoScore, teleOpScore;
    Button btn_next;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        teamName = (EditText)findViewById(R.id.editText_teamName);
        autoScore = (EditText)findViewById(R.id.AutoPointsScored);
        teleOpScore = (EditText)findViewById(R.id.TeleOpPointsScored);
        btn_next = (Button)findViewById(R.id.Add_data);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = new UserData();

                if(!teamName.getText().toString().isEmpty())
                {
                    userData.TeamName = teamName.getText().toString();
                } else
                {
                    userData.TeamName = "";
                }
                if (!teleOpScore.getText().toString().isEmpty())
                {
                    userData.teleOpScore = teleOpScore.getText().toString();
                } else
                {
                    userData.teleOpScore = "";
                }
                if (!autoScore.getText().toString().isEmpty())
                {
                    userData.autoScore = autoScore.getText().toString();
                } else
                {
                    userData.autoScore = "";
                }

                dbHelper.insertGameInfo(userData);

                Log.d("Button Pressed", "Game data inserted");

                Intent intent = new Intent(NewGame.this, DisplayGames.class);
                startActivity(intent);
            }
        });
    }


}
