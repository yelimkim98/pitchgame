package seoultech.YS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class GameResultDialogActivity extends AppCompatActivity {
    private Intent getIntent;
    private int level;
    private int result;
    private int bestRecord;
    private TextView levelText;
    private TextView resultText;
    private TextView bestRecordText;
    private SharedPreferences sp;
    Button retryBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result_dialog);

        sp = getSharedPreferences("pitchGameRecord", MODE_PRIVATE);

        getIntent = getIntent();
        level = getIntent.getIntExtra("난이도", 0);

        levelText = findViewById(R.id.levelText_in_GameResultDialog);
        levelText.setText("난이도 " + level);

        resultText = findViewById(R.id.gameResultTextView);
        result = sp.getInt("lv"+level+"_recentWrongCnt", -1);
        if(result == 0) resultText.setText("Clear");
        else resultText.setText("틀린갯수 " + result);

        bestRecordText = findViewById(R.id.bestRecordTextView);
        bestRecord = sp.getInt("lv"+level+"_leastWrongCnt", -1);
        if(bestRecord == 0) bestRecordText.setText("Clear");
        else bestRecordText.setText("틀린갯수 " + bestRecord);

        retryBtn = findViewById(R.id.retryBtn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameResultDialogActivity.this, GameMainActivity.class);
                intent.putExtra("level", level);
                Log.e("보내려는 level : ", ""+level);
                startActivity(intent);
                finish();
            }
        });

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
        theme.applyStyle(android.R.style.Theme_Panel, true);
        this.setFinishOnTouchOutside(true);
        this.setTitle(null);
    }
}
