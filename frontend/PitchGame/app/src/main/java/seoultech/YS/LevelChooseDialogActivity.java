package seoultech.YS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LevelChooseDialogActivity extends AppCompatActivity {

    private LinearLayout levelChooseLayer, levelSpecLayer;
    private Button [] levelBtn = new Button[8];

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private int level;
    private TextView levelText, bestScoreText, recentScoreText;
    private Button back, start;
    private TextView reset;

    final int levelCnt = 8;
    int [] records;

    // related to finish
    private boolean isGoHome = true;
    private MainActivity ma = (MainActivity) MainActivity._Main_Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_level_choose_dialog);

        levelChooseLayer = (LinearLayout) findViewById(R.id.levelChooseLayer);
        levelSpecLayer = (LinearLayout) findViewById(R.id.levelSpecificationLayer);
        sp = getSharedPreferences("pitchGameRecord", MODE_PRIVATE);
        editor = sp.edit();

        levelBtn[0] = (Button) findViewById(R.id.L1btn);
        levelBtn[1] = (Button) findViewById(R.id.L2btn);
        levelBtn[2] = (Button) findViewById(R.id.L3btn);
        levelBtn[3] = (Button) findViewById(R.id.L4btn);
        levelBtn[4] = (Button) findViewById(R.id.L5btn);
        levelBtn[5] = (Button) findViewById(R.id.L6btn);
        levelBtn[6] = (Button) findViewById(R.id.L7btn);
        levelBtn[7] = (Button) findViewById(R.id.L8btn);

        levelText = findViewById(R.id.levelText);
        bestScoreText = findViewById(R.id.LevelSpecBestRecord);
        recentScoreText = findViewById(R.id.LevelSpecRecentRecord);
        start = (Button) findViewById(R.id.startBtn);
        back = (Button) findViewById(R.id.dialog_backBtn);
        reset = (TextView) findViewById(R.id.resetTextView);

        records = new int[levelCnt];
        sp = getSharedPreferences("pitchGameRecord", MODE_PRIVATE);
        records[0] = sp.getInt("lv"+1+"_leastWrongCnt", -1);
        records[1] = sp.getInt("lv"+2+"_leastWrongCnt", -1);
        records[2] = sp.getInt("lv"+3+"_leastWrongCnt", -1);
        records[3] = sp.getInt("lv"+4+"_leastWrongCnt", -1);
        records[4] = sp.getInt("lv"+5+"_leastWrongCnt", -1);
        records[5] = sp.getInt("lv"+6+"_leastWrongCnt", -1);
        records[6] = sp.getInt("lv"+7+"_leastWrongCnt", -1);
        records[7] = sp.getInt("lv"+8+"_leastWrongCnt", -1);

        for(int i=0;i<levelCnt;i++){
            if(records[i] == 0) {
                levelBtn[i].setTextColor(Color.parseColor("#DDFFD700"));
                levelBtn[i].setText("★");
            }
            else levelBtn[i].setText("" + (i+1));
        }

        levelBtn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 1;
                levelText.setText("1단계");
                reset.setText("기록 초기화");

                setBestScoreText();
                setRecentScoreText();

                levelChooseLayer.setVisibility(View.INVISIBLE);
                levelSpecLayer.setVisibility(View.VISIBLE);
            }
        });

        levelBtn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 2;
                levelText.setText("2단계");
                reset.setText("기록 초기화");

                setBestScoreText();
                setRecentScoreText();

                levelChooseLayer.setVisibility(View.INVISIBLE);
                levelSpecLayer.setVisibility(View.VISIBLE);
            }
        });

        levelBtn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 3;
                levelText.setText("3단계");
                reset.setText("기록 초기화");

                setBestScoreText();
                setRecentScoreText();

                levelChooseLayer.setVisibility(View.INVISIBLE);
                levelSpecLayer.setVisibility(View.VISIBLE);
            }
        });

        levelBtn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 4;
                levelText.setText("4단계");
                reset.setText("기록 초기화");

                // read best record and show it
                setBestScoreText();
                setRecentScoreText();

                levelChooseLayer.setVisibility(View.INVISIBLE);
                levelSpecLayer.setVisibility(View.VISIBLE);
            }
        });

        levelBtn[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 5;
                levelText.setText("5단계");
                reset.setText("기록 초기화");

                setBestScoreText();
                setRecentScoreText();

                levelChooseLayer.setVisibility(View.INVISIBLE);
                levelSpecLayer.setVisibility(View.VISIBLE);
            }
        });

        levelBtn[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 6;
                levelText.setText("6단계");
                reset.setText("기록 초기화");

                setBestScoreText();
                setRecentScoreText();

                levelChooseLayer.setVisibility(View.INVISIBLE);
                levelSpecLayer.setVisibility(View.VISIBLE);
            }
        });

        levelBtn[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 7;
                levelText.setText("7단계");
                reset.setText("기록 초기화");

                setBestScoreText();
                setRecentScoreText();

                levelChooseLayer.setVisibility(View.INVISIBLE);
                levelSpecLayer.setVisibility(View.VISIBLE);
            }
        });
        levelBtn[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 8;
                levelText.setText("8단계");
                reset.setText("기록 초기화");

                setBestScoreText();
                setRecentScoreText();

                levelChooseLayer.setVisibility(View.INVISIBLE);
                levelSpecLayer.setVisibility(View.VISIBLE);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGoHome = false;
                Intent intent = new Intent(LevelChooseDialogActivity.this, GameMainActivity.class);
                intent.putExtra("level", level);
                Log.e("보내려는 level : ", ""+level);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelChooseLayer.setVisibility(View.VISIBLE);
                levelSpecLayer.setVisibility(View.INVISIBLE);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("lv"+level+"_leastWrongCnt", -1);
                editor.putInt("lv"+level+"_recentWrongCnt", -1);
                editor.apply();

                // TextView 다시 그리기
                setBestScoreText();
                setRecentScoreText();

                levelBtn[level-1].setText(""+level);
                levelBtn[level-1].setTextColor(Color.parseColor("#FF99AABB"));
            }
        });
    }

    void setBestScoreText(){
        // read best record and show it
        int leastWrongCnt = sp.getInt("lv"+level+"_leastWrongCnt", -1);
        if(leastWrongCnt == -1) {
            bestScoreText.setText("None"); // 기록이 없는 상태
            bestScoreText.setTextColor(Color.parseColor("#FF99AABB"));
        }
        else if(leastWrongCnt == 0) {
            bestScoreText.setTextColor(Color.parseColor("#DDFFD700"));
            bestScoreText.setText("Clear"); // 이 문제를 깬 전적이 있음
        }
        else {
            bestScoreText.setText("틀린갯수 " + leastWrongCnt);
            bestScoreText.setTextColor(Color.parseColor("#FF99AABB"));
        }
    }

    void setRecentScoreText(){
        // read recent record and show it
        int recentWrongCnt = sp.getInt("lv"+level+"_recentWrongCnt", -1);
        if(recentWrongCnt == -1) {
            recentScoreText.setText("None"); // 기록이 없는 상태
            recentScoreText.setTextColor(Color.parseColor("#FF99AABB"));
        }
        else if(recentWrongCnt == 0) {
            recentScoreText.setTextColor(Color.parseColor("#DDFFD700"));
            recentScoreText.setText("Clear"); // 가장 최근에 이 문제를 깼음
        }
        else {
            recentScoreText.setText("틀린갯수 " + recentWrongCnt);
            recentScoreText.setTextColor(Color.parseColor("#FF99AABB"));
        }
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
        theme.applyStyle(android.R.style.Theme_Panel, true);
        this.setFinishOnTouchOutside(true);
        this.setTitle(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
       // if(isGoHome){
         //   Intent intent = new Intent(LevelChooseDialogActivity.this, MainActivity.class);
         //   intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
         //   startActivity(intent);
         //   ma.finish();
       // }
    }
}