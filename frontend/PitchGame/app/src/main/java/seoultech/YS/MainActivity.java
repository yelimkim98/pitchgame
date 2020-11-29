package seoultech.YS;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.service.autofill.CustomDescription;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static Activity _Main_Activity;
    Button playBtn;
    TextView bestScoreText, recentScoreText;
    SharedPreferences sp;
    int maxClearLevel = -1;
    final int levelCnt = 8;
    int [] records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _Main_Activity = MainActivity.this;

        playBtn = (Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LevelChooseDialogActivity.class));
            }
        });
/*
        bestScoreText = findViewById(R.id.bestScoreTextView_main);

        records = new int[levelCnt];
        sp = getSharedPreferences("pitchGameRecord", MODE_PRIVATE);
        sp.registerOnSharedPreferenceChangeListener(this);

        records[0] = sp.getInt("lv"+1+"_leastWrongCnt", -1);
        records[1] = sp.getInt("lv"+2+"_leastWrongCnt", -1);
        records[2] = sp.getInt("lv"+3+"_leastWrongCnt", -1);
        records[3] = sp.getInt("lv"+4+"_leastWrongCnt", -1);
        records[4] = sp.getInt("lv"+5+"_leastWrongCnt", -1);
        records[5] = sp.getInt("lv"+6+"_leastWrongCnt", -1);
        records[6] = sp.getInt("lv"+7+"_leastWrongCnt", -1);
        records[7] = sp.getInt("lv"+8+"_leastWrongCnt", -1);

        // best record textview
        for(int i=0;i<levelCnt;i++){
            if(records[i] == 0) maxClearLevel = i+1;
        }
        if(maxClearLevel == -1) bestScoreText.setText("None");
        else bestScoreText.setText("Level " + maxClearLevel + " Clear");*/
    }

    public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1)
    {
        records[0] = sp.getInt("lv"+1+"_leastWrongCnt", -1);
        records[1] = sp.getInt("lv"+2+"_leastWrongCnt", -1);
        records[2] = sp.getInt("lv"+3+"_leastWrongCnt", -1);
        records[3] = sp.getInt("lv"+4+"_leastWrongCnt", -1);
        records[4] = sp.getInt("lv"+5+"_leastWrongCnt", -1);
        records[5] = sp.getInt("lv"+6+"_leastWrongCnt", -1);
        records[6] = sp.getInt("lv"+7+"_leastWrongCnt", -1);
        records[7] = sp.getInt("lv"+8+"_leastWrongCnt", -1);

        // best record textview
        for(int i=0;i<levelCnt;i++){
            if(records[i] == 0) maxClearLevel = i+1;
        }
        if(maxClearLevel == -1) bestScoreText.setText("None");
        else bestScoreText.setText("난이도 " + maxClearLevel + " Clear");
    }
}