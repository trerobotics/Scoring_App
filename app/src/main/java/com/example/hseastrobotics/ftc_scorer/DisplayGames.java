package com.example.hseastrobotics.ftc_scorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class DisplayGames extends AppCompatActivity implements Listener{

    InterstitialAd mInterstitialAd;

    RecyclerView recyclerView;
    DbHelper dbHelper;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_games);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Previous Games");
        setSupportActionBar(toolbar);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("@strings/ad_id_interstitial");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

                Intent intent = new Intent(DisplayGames.this, NewGame.class);
                startActivity(intent);
            }
        });

        requestNewInterstitial();

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mInterstitialAd.isLoaded())
                {
                    mInterstitialAd.show();
                } else
                {
                    Intent intent = new Intent(DisplayGames.this, NewGame.class);
                    startActivity(intent);
                }
            }
        });

        dbHelper = DbHelper.getInstance(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.rv_show_games);
        adapter = new ListAdapter(this, dbHelper.getAllGames());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(this, DisplayGames.class);
        startActivity(intent);
        finish();
    }

    public void nameToChnge(String name) {
        dbHelper.deleteRow(name);

        adapter = new ListAdapter(this, dbHelper.getAllGames());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_games, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
