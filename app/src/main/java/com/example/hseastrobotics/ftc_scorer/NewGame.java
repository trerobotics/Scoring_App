package com.example.hseastrobotics.ftc_scorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class NewGame extends AppCompatActivity {

    EditText teamName, centerAuto, cornerAuto, beaconsAuto, centerTeleOp, cornerTeleOp, beaconsEndGame;
    Button btn_next, centerPartialAuto, centerfullyAuto, cornerPartialAuto, cornerFullyAuto, below30, above30, capBall;
    Switch ballOnPlayingField;

    DbHelper dbHelper;

    //teleOpscore to be put in database
    int TeleOpscore = 0;
    //autoscore to be put in database
    int AutoScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        //allows for posting in database
        dbHelper = DbHelper.getInstance(getApplicationContext());

        //Edit texts to collect user input
        teamName = (EditText)findViewById(R.id.editText_teamName);
        centerAuto = (EditText)findViewById(R.id.particlesInCenterAuto);
        cornerAuto = (EditText)findViewById(R.id.particlesInCornerAuto);
        beaconsAuto = (EditText)findViewById(R.id.NumOfBeaconsPressedAuto);

        centerTeleOp = (EditText)findViewById(R.id.NumOfParticlesCenterTeleOp);
        cornerTeleOp = (EditText)findViewById(R.id.numOfParticlesCornerTeleOp);
        beaconsEndGame = (EditText)findViewById(R.id.numOfBeaconsPressedTeleOp);

        //switch to collect user input
        ballOnPlayingField = (Switch)findViewById(R.id.switch_ballOnplayingField);

        //button to collect user input
        btn_next = (Button)findViewById(R.id.Add_Data);
        centerPartialAuto = (Button)findViewById(R.id.button_partialCenter);
        centerfullyAuto = (Button)findViewById(R.id.button_fullyCenter);
        cornerPartialAuto = (Button)findViewById(R.id.button_partialCorner);
        cornerFullyAuto = (Button)findViewById(R.id.button_fullyCorner);
        below30 = (Button)findViewById(R.id.capBallBelow30);
        above30 = (Button)findViewById(R.id.capBallAbove30);
        capBall = (Button)findViewById(R.id.cappedBall);



        centerPartialAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoScore += 5;

                Toast.makeText(NewGame.this, "Partially Parked", Toast.LENGTH_SHORT).show();
            }
        });

        centerfullyAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoScore += 10;

                Toast.makeText(NewGame.this, "Fully Parked", Toast.LENGTH_SHORT).show();
            }
        });

        cornerPartialAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoScore += 5;

                Toast.makeText(NewGame.this, "Partially Parked", Toast.LENGTH_SHORT).show();
            }
        });

        cornerFullyAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoScore += 5;

                Toast.makeText(NewGame.this, "Fully Parked", Toast.LENGTH_SHORT).show();
            }
        });

        if (ballOnPlayingField.isChecked())
        {
            AutoScore += 5;
        }

        below30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeleOpscore += 10;

                Toast.makeText(NewGame.this, "Ball Below 30 inches", Toast.LENGTH_SHORT).show();
            }
        });

        above30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeleOpscore += 20;

                Toast.makeText(NewGame.this, "Ball Above 30 inches", Toast.LENGTH_SHORT).show();
            }
        });

        capBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeleOpscore += 40;

                Toast.makeText(NewGame.this, "Capped Ball!!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = new UserData();

                //check if the particle ball editText has numbers in it
                if (!centerAuto.getText().toString().isEmpty())
                {
                    //add 15 points to the autonomous score per ball
                    for(int i = 0; i < Integer.parseInt(centerAuto.getText().toString()); i++)
                    {
                        AutoScore += 15;
                    }
                }

                //check if the particle ball editText has numbers in it
                if (!cornerAuto.getText().toString().isEmpty())
                {
                    //add 5 points to the autonomous score per ball
                    for (int i = 0; i < Integer.parseInt(cornerAuto.getText().toString()); i++)
                    {
                        AutoScore += 5;
                    }
                }

                if (!beaconsAuto.getText().toString().isEmpty())
                {
                    for (int i = 0; i < Integer.parseInt(beaconsAuto.getText().toString()); i++)
                    {
                        AutoScore += 30;
                    }
                }

                if (!centerTeleOp.getText().toString().isEmpty())
                {
                    for (int i = 0; i < Integer.parseInt(centerTeleOp.getText().toString()); i++)
                    {
                        TeleOpscore += 5;
                    }
                }

                if (!cornerTeleOp.getText().toString().isEmpty())
                {
                    for (int i = 0; i < Integer.parseInt(cornerTeleOp.getText().toString()); i++)
                    {
                        TeleOpscore += 1;
                    }
                }

                if (!beaconsEndGame.getText().toString().isEmpty())
                {
                    for (int i = 0; i < Integer.parseInt(beaconsEndGame.getText().toString()); i++)
                    {
                        TeleOpscore += 10;
                    }
                }

                if (ballOnPlayingField.isChecked())
                {
                    AutoScore += 5;
                }

                if(!teamName.getText().toString().isEmpty())
                {
                    userData.TeamName = teamName.getText().toString();
                } else
                {
                    userData.TeamName = "";
                }

                userData.autoScore = Integer.toString(AutoScore);

                userData.teleOpScore = Integer.toString(TeleOpscore);

                dbHelper.insertGameInfo(userData);

                Log.d("Button Pressed", "Game data inserted");

                Intent intent = new Intent(NewGame.this, DisplayGames.class);
                startActivity(intent);
            }
        });
    }


}
