package seoultech.YS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class GameMainActivity extends AppCompatActivity {

    private Button exitBtn;
    private int level = 1;
    private LinearLayout linearLayout;
    private TextView levelTitle;
    private Intent getIntent;
    private Button soundBtn;
    private Button [] ansBtn = new Button[12];
    private String [] ansBtnTexts;
    private String currentSoundPitch;
    private SoundPool quizSoundPool;
    private int [] sounds;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Chord ans;
    private int numOfSelectedMonoPitches_inChord = 0;
    private TextView bottomText;
    private String [] tmp;
    private boolean isEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        // 탈출버튼 셋팅
        exitBtn = findViewById(R.id.backBtn_in_game_main);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 난이도 셋팅
        getIntent = getIntent();
        level = getIntent.getIntExtra("level", 1);
        Log.e("level : ", ""+level);

        // 결과 기록에 사용될 Shared Preferences 객체 준비
        sp = getSharedPreferences("pitchGameRecord", MODE_PRIVATE);
        editor = sp.edit();

        if(level == 1){
            levelTitle = findViewById(R.id.levelText_in_GameLv1to4);
            levelTitle.setText("Level " + level);

            linearLayout = findViewById(R.id.level1to4Layout);
            linearLayout.setVisibility(View.VISIBLE);

            final Quiz_Lv1 quiz = new Quiz_Lv1();

            ansBtnNum7_ObjectAllocation();

            quizSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            sounds = new int[7];
            sounds[0] = quizSoundPool.load(this, R.raw._do, 1);
            sounds[1] = quizSoundPool.load(this, R.raw._re, 1);
            sounds[2] = quizSoundPool.load(this, R.raw._mi, 1);
            sounds[3] = quizSoundPool.load(this, R.raw._fa, 1);
            sounds[4] = quizSoundPool.load(this, R.raw._sol, 1);
            sounds[5] = quizSoundPool.load(this, R.raw._ra, 1);
            sounds[6] = quizSoundPool.load(this, R.raw._si, 1);

            soundBtn = findViewById(R.id.level1to4_sound_btn);

            soundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    currentSoundPitch = quiz.getCurrentSoundPitch();
                    Log.e("soundBtn Init : ", currentSoundPitch);

                    // 현재 문제가 C이면 도, D이면 레 소리가 나도록 셋팅
                    switch (currentSoundPitch) {
                        case "C":
                            quizSoundPool.play(sounds[0], 1, 1, 1, 0, 1);
                            break;
                        case "D":
                            quizSoundPool.play(sounds[1], 1, 1, 1, 0, 1);
                            break;
                        case "E":
                            quizSoundPool.play(sounds[2], 1, 1, 1, 0, 1);
                            break;
                        case "F":
                            quizSoundPool.play(sounds[3], 1, 1, 1, 0, 1);
                            break;
                        case "G":
                            quizSoundPool.play(sounds[4], 1, 1, 1, 0, 1);
                            break;
                        case "A":
                            quizSoundPool.play(sounds[5], 1, 1, 1, 0, 1);
                            break;
                        case "B":
                            quizSoundPool.play(sounds[6], 1, 1, 1, 0, 1);
                            break;
                    }

                    // soundPool overflow 문제 해결을 위해, 버튼 클릭 직후부터 소리가 끝날때까지 한동안 버튼을 클릭할 수 없게 한다.
                    soundBtn.setClickable(false);
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    soundBtn.setClickable(true);
                                }
                            });
                        }
                    }, 1000);
                }
            });

            ansBtnTexts = quiz.getSuffledSoundList();
            Log.e("ansBtnTexts", ansBtnTexts[0] + ", " + ansBtnTexts[1] + ", " + ansBtnTexts[2]);
            for(int i=0;i<ansBtnTexts.length;i++) {
                switch (ansBtnTexts[i]){
                    case "C":ansBtn[i].setText("도"); break;
                    case "D":ansBtn[i].setText("레"); break;
                    case "E":ansBtn[i].setText("미"); break;
                    case "F":ansBtn[i].setText("파"); break;
                    case "G":ansBtn[i].setText("솔"); break;
                    case "A":ansBtn[i].setText("라"); break;
                    case "B":ansBtn[i].setText("시"); break;
                }
            }

            ansBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 0);
                }
            });

            ansBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 1);
                }
            });
            ansBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 2);
                }
            });
            ansBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 3);
                }
            });
            ansBtn[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean isCorrect = quiz.putAnswer(ansBtnTexts[4]);
                    if(quiz.isEnded()) {
                        soundBtn.setClickable(false);
                        recording_WhenGameEnded(quiz.getWrongCnt());
                        finish();
                    }
                    else if(isCorrect) {
                        Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
                        ansBtn[4].setEnabled(false);
                        ansBtn[4].setTextColor(Color.parseColor("#AAAAAAAA"));
                    }
                }
            });
            ansBtn[5].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 5);
                }
            });
            ansBtn[6].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 6);
                }
            });
        }
        else if(level == 2){
            tmp = new String[1];
            tmp[0] = new String();

            levelTitle = findViewById(R.id.levelText_in_GameLv1to4);
            levelTitle.setText("Level " + level);

            linearLayout = findViewById(R.id.level1to4Layout);
            linearLayout.setVisibility(View.VISIBLE);

            final Quiz_Lv2 quiz = new Quiz_Lv2();

            ansBtnNum7_ObjectAllocation(); // ansBtn 0 ~ 6 에 new Button

            bottomText = findViewById(R.id.game_bottomText_Lv1to4);
            setRemainingNum(quiz, bottomText);

            quizSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            sounds = new int[7];
            sounds[0] = quizSoundPool.load(this, R.raw._do, 1);
            sounds[1] = quizSoundPool.load(this, R.raw._re, 1);
            sounds[2] = quizSoundPool.load(this, R.raw._mi, 1);
            sounds[3] = quizSoundPool.load(this, R.raw._fa, 1);
            sounds[4] = quizSoundPool.load(this, R.raw._sol, 1);
            sounds[5] = quizSoundPool.load(this, R.raw._ra, 1);
            sounds[6] = quizSoundPool.load(this, R.raw._si, 1);

            soundBtn = findViewById(R.id.level1to4_sound_btn);

            soundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chord init = quiz.getCurrentSoundPitch();
                    soundBtn.setClickable(false);

                    for(int i=0; i<init.getChordsInt().length; i++){
                        switch (init.getChordsStr()[i]){
                            case "C":
                                quizSoundPool.play(sounds[0], 1, 1, 1, 0, 1); break;
                            case "D":
                                quizSoundPool.play(sounds[1], 1, 1, 1, 0, 1); break;
                            case "E":
                                quizSoundPool.play(sounds[2], 1, 1, 1, 0, 1); break;
                            case "F":
                                quizSoundPool.play(sounds[3], 1, 1, 1, 0, 1); break;
                            case "G":
                                quizSoundPool.play(sounds[4], 1, 1, 1, 0, 1); break;
                            case "A":
                                quizSoundPool.play(sounds[5], 1, 1, 1, 0, 1); break;
                            case "B":
                                quizSoundPool.play(sounds[6], 1, 1, 1, 0, 1); break;
                        }
                    }
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    soundBtn.setClickable(true);
                                }
                            });
                        }
                    }, 1000);
                }
            });

            ansBtnNum7_ObjectAllocation();
            ansBtnTexts = quiz.getSuffledUsedMonoPitches();

            for(int i=0; i<ansBtnTexts.length; i++) {
                switch (ansBtnTexts[i]){
                    case "C":ansBtn[i].setText("도"); break;
                    case "D":ansBtn[i].setText("레"); break;
                    case "E":ansBtn[i].setText("미"); break;
                    case "F":ansBtn[i].setText("파"); break;
                    case "G":ansBtn[i].setText("솔"); break;
                    case "A":ansBtn[i].setText("라"); break;
                    case "B":ansBtn[i].setText("시"); break;
                }
            }

            // 음 두개씩 입력받기 위해서 필요한 객체들
            ans = new Chord();
            numOfSelectedMonoPitches_inChord = 0;

            ansBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 0);
                }
            });
            ansBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 1);
                }
            });
            ansBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 2);
                }
            });
            ansBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 3);
                }
            });
            ansBtn[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 4);
                }
            });
            ansBtn[5].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 5);
                }
            });
            ansBtn[6].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 6);
                }
            });
        }
        else if(level == 3){
            tmp = new String[1];
            tmp[0] = new String();

            levelTitle = findViewById(R.id.levelText_in_GameLv1to4);
            levelTitle.setText("Level " + level);

            linearLayout = findViewById(R.id.level1to4Layout);
            linearLayout.setVisibility(View.VISIBLE);

            final Quiz_Lv3 quiz = new Quiz_Lv3();

            ansBtnNum7_ObjectAllocation(); // ansBtn 0 ~ 6 에 new Button

            bottomText = findViewById(R.id.game_bottomText_Lv1to4);
            setRemainingNum(quiz, bottomText);

            quizSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            sounds = new int[7];
            sounds[0] = quizSoundPool.load(this, R.raw._do, 1);
            sounds[1] = quizSoundPool.load(this, R.raw._re, 1);
            sounds[2] = quizSoundPool.load(this, R.raw._mi, 1);
            sounds[3] = quizSoundPool.load(this, R.raw._fa, 1);
            sounds[4] = quizSoundPool.load(this, R.raw._sol, 1);
            sounds[5] = quizSoundPool.load(this, R.raw._ra, 1);
            sounds[6] = quizSoundPool.load(this, R.raw._si, 1);

            soundBtn = findViewById(R.id.level1to4_sound_btn);

            soundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chord init = quiz.getCurrentSoundPitch();
                    soundBtn.setClickable(false);

                    for(int i=0; i<init.getChordsInt().length; i++){
                        switch (init.getChordsStr()[i]){
                            case "C":
                                quizSoundPool.play(sounds[0], 1, 1, 1, 0, 1); break;
                            case "D":
                                quizSoundPool.play(sounds[1], 1, 1, 1, 0, 1); break;
                            case "E":
                                quizSoundPool.play(sounds[2], 1, 1, 1, 0, 1); break;
                            case "F":
                                quizSoundPool.play(sounds[3], 1, 1, 1, 0, 1); break;
                            case "G":
                                quizSoundPool.play(sounds[4], 1, 1, 1, 0, 1); break;
                            case "A":
                                quizSoundPool.play(sounds[5], 1, 1, 1, 0, 1); break;
                            case "B":
                                quizSoundPool.play(sounds[6], 1, 1, 1, 0, 1); break;
                        }
                    }
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    soundBtn.setClickable(true);
                                }
                            });
                        }
                    }, 1000);
                }
            });

            ansBtnNum7_ObjectAllocation();
            ansBtnTexts = quiz.getSuffledUsedMonoPitches();

            for(int i=0; i<ansBtnTexts.length; i++) {
                switch (ansBtnTexts[i]){
                    case "C":ansBtn[i].setText("도"); break;
                    case "D":ansBtn[i].setText("레"); break;
                    case "E":ansBtn[i].setText("미"); break;
                    case "F":ansBtn[i].setText("파"); break;
                    case "G":ansBtn[i].setText("솔"); break;
                    case "A":ansBtn[i].setText("라"); break;
                    case "B":ansBtn[i].setText("시"); break;
                }
            }

            // 음 두개씩 입력받기 위해서 필요한 객체들
            ans = new Chord();
            numOfSelectedMonoPitches_inChord = 0;

            ansBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 0);
                }
            });
            ansBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 1);
                }
            });
            ansBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 2);
                }
            });
            ansBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 3);
                }
            });
            ansBtn[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 4);
                }
            });
            ansBtn[5].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 5);
                }
            });
            ansBtn[6].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 6);
                }
            });
        }
        else if(level == 4){
            tmp = new String[2];
            tmp[0] = new String();
            tmp[1] = new String();

            levelTitle = findViewById(R.id.levelText_in_GameLv1to4);
            levelTitle.setText("Level " + level);

            linearLayout = findViewById(R.id.level1to4Layout);
            linearLayout.setVisibility(View.VISIBLE);

            final Quiz_Lv4 quiz = new Quiz_Lv4();

            ansBtnNum7_ObjectAllocation(); // ansBtn 0 ~ 6 에 new Button

            bottomText = findViewById(R.id.game_bottomText_Lv1to4);
            setRemainingNum(quiz, bottomText);

            quizSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            sounds = new int[7];
            sounds[0] = quizSoundPool.load(this, R.raw._do, 1);
            sounds[1] = quizSoundPool.load(this, R.raw._re, 1);
            sounds[2] = quizSoundPool.load(this, R.raw._mi, 1);
            sounds[3] = quizSoundPool.load(this, R.raw._fa, 1);
            sounds[4] = quizSoundPool.load(this, R.raw._sol, 1);
            sounds[5] = quizSoundPool.load(this, R.raw._ra, 1);
            sounds[6] = quizSoundPool.load(this, R.raw._si, 1);

            soundBtn = findViewById(R.id.level1to4_sound_btn);

            soundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chord init = quiz.getCurrentSoundPitch();
                    soundBtn.setClickable(false);

                    for(int i=0; i<init.getChordsInt().length; i++){
                        switch (init.getChordsStr()[i]){
                            case "C":
                                quizSoundPool.play(sounds[0], 1, 1, 1, 0, 1); break;
                            case "D":
                                quizSoundPool.play(sounds[1], 1, 1, 1, 0, 1); break;
                            case "E":
                                quizSoundPool.play(sounds[2], 1, 1, 1, 0, 1); break;
                            case "F":
                                quizSoundPool.play(sounds[3], 1, 1, 1, 0, 1); break;
                            case "G":
                                quizSoundPool.play(sounds[4], 1, 1, 1, 0, 1); break;
                            case "A":
                                quizSoundPool.play(sounds[5], 1, 1, 1, 0, 1); break;
                            case "B":
                                quizSoundPool.play(sounds[6], 1, 1, 1, 0, 1); break;
                        }
                    }
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    soundBtn.setClickable(true);
                                }
                            });
                        }
                    }, 1000);
                }
            });

            ansBtnNum7_ObjectAllocation();
            ansBtnTexts = quiz.getSuffledUsedMonoPitches();

            for(int i=0; i<ansBtnTexts.length; i++) {
                switch (ansBtnTexts[i]){
                    case "C":ansBtn[i].setText("도"); break;
                    case "D":ansBtn[i].setText("레"); break;
                    case "E":ansBtn[i].setText("미"); break;
                    case "F":ansBtn[i].setText("파"); break;
                    case "G":ansBtn[i].setText("솔"); break;
                    case "A":ansBtn[i].setText("라"); break;
                    case "B":ansBtn[i].setText("시"); break;
                }
            }

            // 음 세개씩 입력받기 위해서 필요한 객체들
            ans = new Chord();
            numOfSelectedMonoPitches_inChord = 0;

            ansBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 0);
                }
            });
            ansBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 1);
                }
            });
            ansBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 2);
                }
            });
            ansBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 3);
                }
            });
            ansBtn[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 4);
                }
            });
            ansBtn[5].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 5);
                }
            });
            ansBtn[6].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 6);
                }
            });
        }
        else if(level == 5){
            levelTitle = findViewById(R.id.levelText_in_GameLv5to8);
            levelTitle.setText("Level " + level);

            linearLayout = findViewById(R.id.level5to8Layout);
            linearLayout.setVisibility(View.VISIBLE);

            final Quiz_Lv5 quiz = new Quiz_Lv5();

            ansBtnNum12_ObjectAllocation(); // ansBtn 0 ~ 6 에 new Button

            quizSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            sounds = new int[12];
            sounds[0] = quizSoundPool.load(this, R.raw._do, 1);
            sounds[1] = quizSoundPool.load(this, R.raw._do_shap, 1);
            sounds[2] = quizSoundPool.load(this, R.raw._re, 1);
            sounds[3] = quizSoundPool.load(this, R.raw._re_shap, 1);
            sounds[4] = quizSoundPool.load(this, R.raw._mi, 1);
            sounds[5] = quizSoundPool.load(this, R.raw._fa, 1);
            sounds[6] = quizSoundPool.load(this, R.raw._fa_shap, 1);
            sounds[7] = quizSoundPool.load(this, R.raw._sol, 1);
            sounds[8] = quizSoundPool.load(this, R.raw._sol_shap, 1);
            sounds[9] = quizSoundPool.load(this, R.raw._ra, 1);
            sounds[10] = quizSoundPool.load(this, R.raw._ra_shap, 1);
            sounds[11] = quizSoundPool.load(this, R.raw._si, 1);

            soundBtn = findViewById(R.id.level5to8_sound_btn);

            soundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentSoundPitch = quiz.getCurrentSoundPitch();
                    Log.e("soundBtn Init : ", currentSoundPitch);

                    // 현재 문제가 C이면 도, D이면 레 소리가 나도록 셋팅
                    switch (currentSoundPitch) {
                        case "C":
                            quizSoundPool.play(sounds[0], 1, 1, 1, 0, 1);
                            break;
                        case "C#":
                            quizSoundPool.play(sounds[1], 1, 1, 1, 0, 1);
                            break;
                        case "D":
                            quizSoundPool.play(sounds[2], 1, 1, 1, 0, 1);
                            break;
                        case "D#":
                            quizSoundPool.play(sounds[3], 1, 1, 1, 0, 1);
                            break;
                        case "E":
                            quizSoundPool.play(sounds[4], 1, 1, 1, 0, 1);
                            break;
                        case "F":
                            quizSoundPool.play(sounds[5], 1, 1, 1, 0, 1);
                            break;
                        case "F#":
                            quizSoundPool.play(sounds[6], 1, 1, 1, 0, 1);
                            break;
                        case "G":
                            quizSoundPool.play(sounds[7], 1, 1, 1, 0, 1);
                            break;
                        case "G#":
                            quizSoundPool.play(sounds[8], 1, 1, 1, 0, 1);
                            break;
                        case "A":
                            quizSoundPool.play(sounds[9], 1, 1, 1, 0, 1);
                            break;
                        case "A#":
                            quizSoundPool.play(sounds[10], 1, 1, 1, 0, 1);
                            break;
                        case "B":
                            quizSoundPool.play(sounds[11], 1, 1, 1, 0, 1);
                            break;
                    }

                    // soundPool overflow 문제 해결을 위해, 버튼 클릭 직후부터 소리가 끝날때까지 한동안 버튼을 클릭할 수 없게 한다.
                    soundBtn.setClickable(false);
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    soundBtn.setClickable(true);
                                }
                            });
                        }
                    }, 1000);
                }
            });

            ansBtnTexts = quiz.getSuffledSoundList();
            Log.e("ansBtnTexts", ansBtnTexts[0] + ", " + ansBtnTexts[1] + ", " + ansBtnTexts[2]);
            for(int i=0;i<ansBtnTexts.length;i++) {
                switch (ansBtnTexts[i]){
                    case "C":ansBtn[i].setText("도"); break;
                    case "C#":ansBtn[i].setText("도#"); break;
                    case "D":ansBtn[i].setText("레"); break;
                    case "D#":ansBtn[i].setText("레#"); break;
                    case "E":ansBtn[i].setText("미"); break;
                    case "F":ansBtn[i].setText("파"); break;
                    case "F#":ansBtn[i].setText("파#"); break;
                    case "G":ansBtn[i].setText("솔"); break;
                    case "G#":ansBtn[i].setText("솔#"); break;
                    case "A":ansBtn[i].setText("라"); break;
                    case "A#":ansBtn[i].setText("라#"); break;
                    case "B":ansBtn[i].setText("시"); break;
                }
            }

            bottomText = findViewById(R.id.game_bottomText_Lv1to4);
            setRemainingNum(quiz, bottomText);

            ansBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 0);
                }
            });

            ansBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 1);
                }
            });

            ansBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 2);
                }
            });

            ansBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 3);
                }
            });

            ansBtn[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 4);
                }
            });

            ansBtn[5].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 5);
                }
            });

            ansBtn[6].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 6);
                }
            });

            ansBtn[7].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 7);
                }
            });

            ansBtn[8].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 8);
                }
            });

            ansBtn[9].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 9);
                }
            });

            ansBtn[10].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 10);
                }
            });

            ansBtn[11].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 11);
                }
            });
        }
        else if(level == 6) {
            tmp = new String[1];
            tmp[0] = new String();

            levelTitle = findViewById(R.id.levelText_in_GameLv5to8);
            levelTitle.setText("Level " + level);

            linearLayout = findViewById(R.id.level5to8Layout);
            linearLayout.setVisibility(View.VISIBLE);

            final Quiz_Lv6 quiz = new Quiz_Lv6();

            ansBtnNum12_ObjectAllocation(); // ansBtn 0 ~ 11 에 new Button

            bottomText = findViewById(R.id.game_bottomText_Lv5to8);
            setRemainingNum(quiz, bottomText);

            quizSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            sounds = new int[12];
            sounds[0] = quizSoundPool.load(this, R.raw._do, 1);
            sounds[1] = quizSoundPool.load(this, R.raw._do_shap, 1);
            sounds[2] = quizSoundPool.load(this, R.raw._re, 1);
            sounds[3] = quizSoundPool.load(this, R.raw._re_shap, 1);
            sounds[4] = quizSoundPool.load(this, R.raw._mi, 1);
            sounds[5] = quizSoundPool.load(this, R.raw._fa, 1);
            sounds[6] = quizSoundPool.load(this, R.raw._fa_shap, 1);
            sounds[7] = quizSoundPool.load(this, R.raw._sol, 1);
            sounds[8] = quizSoundPool.load(this, R.raw._sol_shap, 1);
            sounds[9] = quizSoundPool.load(this, R.raw._ra, 1);
            sounds[10] = quizSoundPool.load(this, R.raw._ra_shap, 1);
            sounds[11] = quizSoundPool.load(this, R.raw._si, 1);

            soundBtn = findViewById(R.id.level5to8_sound_btn);

            soundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chord init = quiz.getCurrentSoundPitch();
                    soundBtn.setClickable(false);

                    for(int i=0; i<init.getChordsInt().length; i++){
                        switch (init.getChordsStr()[i]){
                            case "C":
                                quizSoundPool.play(sounds[0], 1, 1, 1, 0, 1);
                                break;
                            case "C#":
                                quizSoundPool.play(sounds[1], 1, 1, 1, 0, 1);
                                break;
                            case "D":
                                quizSoundPool.play(sounds[2], 1, 1, 1, 0, 1);
                                break;
                            case "D#":
                                quizSoundPool.play(sounds[3], 1, 1, 1, 0, 1);
                                break;
                            case "E":
                                quizSoundPool.play(sounds[4], 1, 1, 1, 0, 1);
                                break;
                            case "F":
                                quizSoundPool.play(sounds[5], 1, 1, 1, 0, 1);
                                break;
                            case "F#":
                                quizSoundPool.play(sounds[6], 1, 1, 1, 0, 1);
                                break;
                            case "G":
                                quizSoundPool.play(sounds[7], 1, 1, 1, 0, 1);
                                break;
                            case "G#":
                                quizSoundPool.play(sounds[8], 1, 1, 1, 0, 1);
                                break;
                            case "A":
                                quizSoundPool.play(sounds[9], 1, 1, 1, 0, 1);
                                break;
                            case "A#":
                                quizSoundPool.play(sounds[10], 1, 1, 1, 0, 1);
                                break;
                            case "B":
                                quizSoundPool.play(sounds[11], 1, 1, 1, 0, 1);
                                break;
                        }
                    }
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    soundBtn.setClickable(true);
                                }
                            });
                        }
                    }, 1000);
                }
            });

            ansBtnNum12_ObjectAllocation();
            ansBtnTexts = quiz.getSuffledUsedMonoPitches();

            for(int i=0; i<ansBtnTexts.length; i++) {
                switch (ansBtnTexts[i]){
                    case "C":ansBtn[i].setText("도"); break;
                    case "C#":ansBtn[i].setText("도#"); break;
                    case "D":ansBtn[i].setText("레"); break;
                    case "D#":ansBtn[i].setText("레#"); break;
                    case "E":ansBtn[i].setText("미"); break;
                    case "F":ansBtn[i].setText("파"); break;
                    case "F#":ansBtn[i].setText("파#"); break;
                    case "G":ansBtn[i].setText("솔"); break;
                    case "G#":ansBtn[i].setText("솔#"); break;
                    case "A":ansBtn[i].setText("라"); break;
                    case "A#":ansBtn[i].setText("라#"); break;
                    case "B":ansBtn[i].setText("시"); break;
                }
            }

            // 음 두개씩 입력받기 위해서 필요한 객체들
            ans = new Chord();
            numOfSelectedMonoPitches_inChord = 0;

            ansBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 0);
                }
            });
            ansBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 1);
                }
            });
            ansBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 2);
                }
            });
            ansBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 3);
                }
            });
            ansBtn[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 4);
                }
            });
            ansBtn[5].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 5);
                }
            });
            ansBtn[6].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 6);
                }
            });
            ansBtn[7].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 7);
                }
            });
            ansBtn[8].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 8);
                }
            });
            ansBtn[9].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 9);
                }
            });
            ansBtn[10].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 10);
                }
            });
            ansBtn[11].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 11);
                }
            });
        }
        else if (level == 7){
            tmp = new String[1];
            tmp[0] = new String();

            levelTitle = findViewById(R.id.levelText_in_GameLv5to8);
            levelTitle.setText("Level " + level);

            linearLayout = findViewById(R.id.level5to8Layout);
            linearLayout.setVisibility(View.VISIBLE);

            final Quiz_Lv7 quiz = new Quiz_Lv7();

            ansBtnNum12_ObjectAllocation(); // ansBtn 0 ~ 11 에 new Button

            bottomText = findViewById(R.id.game_bottomText_Lv5to8);
            setRemainingNum(quiz, bottomText);

            quizSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            sounds = new int[12];
            sounds[0] = quizSoundPool.load(this, R.raw._do, 1);
            sounds[1] = quizSoundPool.load(this, R.raw._do_shap, 1);
            sounds[2] = quizSoundPool.load(this, R.raw._re, 1);
            sounds[3] = quizSoundPool.load(this, R.raw._re_shap, 1);
            sounds[4] = quizSoundPool.load(this, R.raw._mi, 1);
            sounds[5] = quizSoundPool.load(this, R.raw._fa, 1);
            sounds[6] = quizSoundPool.load(this, R.raw._fa_shap, 1);
            sounds[7] = quizSoundPool.load(this, R.raw._sol, 1);
            sounds[8] = quizSoundPool.load(this, R.raw._sol_shap, 1);
            sounds[9] = quizSoundPool.load(this, R.raw._ra, 1);
            sounds[10] = quizSoundPool.load(this, R.raw._ra_shap, 1);
            sounds[11] = quizSoundPool.load(this, R.raw._si, 1);

            soundBtn = findViewById(R.id.level5to8_sound_btn);

            soundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chord init = quiz.getCurrentSoundPitch();
                    soundBtn.setClickable(false);

                    for(int i=0; i<init.getChordsInt().length; i++){
                        switch (init.getChordsStr()[i]){
                            case "C":
                                quizSoundPool.play(sounds[0], 1, 1, 1, 0, 1);
                                break;
                            case "C#":
                                quizSoundPool.play(sounds[1], 1, 1, 1, 0, 1);
                                break;
                            case "D":
                                quizSoundPool.play(sounds[2], 1, 1, 1, 0, 1);
                                break;
                            case "D#":
                                quizSoundPool.play(sounds[3], 1, 1, 1, 0, 1);
                                break;
                            case "E":
                                quizSoundPool.play(sounds[4], 1, 1, 1, 0, 1);
                                break;
                            case "F":
                                quizSoundPool.play(sounds[5], 1, 1, 1, 0, 1);
                                break;
                            case "F#":
                                quizSoundPool.play(sounds[6], 1, 1, 1, 0, 1);
                                break;
                            case "G":
                                quizSoundPool.play(sounds[7], 1, 1, 1, 0, 1);
                                break;
                            case "G#":
                                quizSoundPool.play(sounds[8], 1, 1, 1, 0, 1);
                                break;
                            case "A":
                                quizSoundPool.play(sounds[9], 1, 1, 1, 0, 1);
                                break;
                            case "A#":
                                quizSoundPool.play(sounds[10], 1, 1, 1, 0, 1);
                                break;
                            case "B":
                                quizSoundPool.play(sounds[11], 1, 1, 1, 0, 1);
                                break;
                        }
                    }
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    soundBtn.setClickable(true);
                                }
                            });
                        }
                    }, 1000);
                }
            });

            ansBtnTexts = quiz.getSuffledUsedMonoPitches();

            for(int i=0; i<ansBtnTexts.length; i++) {
                switch (ansBtnTexts[i]){
                    case "C":ansBtn[i].setText("도"); break;
                    case "C#":ansBtn[i].setText("도#"); break;
                    case "D":ansBtn[i].setText("레"); break;
                    case "D#":ansBtn[i].setText("레#"); break;
                    case "E":ansBtn[i].setText("미"); break;
                    case "F":ansBtn[i].setText("파"); break;
                    case "F#":ansBtn[i].setText("파#"); break;
                    case "G":ansBtn[i].setText("솔"); break;
                    case "G#":ansBtn[i].setText("솔#"); break;
                    case "A":ansBtn[i].setText("라"); break;
                    case "A#":ansBtn[i].setText("라#"); break;
                    case "B":ansBtn[i].setText("시"); break;
                }
            }

            // 음 두개씩 입력받기 위해서 필요한 객체들
            ans = new Chord();
            numOfSelectedMonoPitches_inChord = 0;

            ansBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 0);
                }
            });
            ansBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 1);
                }
            });
            ansBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 2);
                }
            });
            ansBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 3);
                }
            });
            ansBtn[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 4);
                }
            });
            ansBtn[5].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 5);
                }
            });
            ansBtn[6].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 6);
                }
            });
            ansBtn[7].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 7);
                }
            });
            ansBtn[8].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 8);
                }
            });
            ansBtn[9].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 9);
                }
            });
            ansBtn[10].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 10);
                }
            });
            ansBtn[11].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 11);
                }
            });
        }
        else if (level == 8){

            tmp = new String[2];
            tmp[0] = new String();
            tmp[1] = new String();

            levelTitle = findViewById(R.id.levelText_in_GameLv5to8);
            levelTitle.setText("Level " + level);

            linearLayout = findViewById(R.id.level5to8Layout);
            linearLayout.setVisibility(View.VISIBLE);

            final Quiz_Lv8 quiz = new Quiz_Lv8();

            ansBtnNum12_ObjectAllocation(); // ansBtn 0 ~ 11 에 new Button

            bottomText = findViewById(R.id.game_bottomText_Lv5to8);
            setRemainingNum(quiz, bottomText);

            quizSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            sounds = new int[12];
            sounds[0] = quizSoundPool.load(this, R.raw._do, 1);
            sounds[1] = quizSoundPool.load(this, R.raw._do_shap, 1);
            sounds[2] = quizSoundPool.load(this, R.raw._re, 1);
            sounds[3] = quizSoundPool.load(this, R.raw._re_shap, 1);
            sounds[4] = quizSoundPool.load(this, R.raw._mi, 1);
            sounds[5] = quizSoundPool.load(this, R.raw._fa, 1);
            sounds[6] = quizSoundPool.load(this, R.raw._fa_shap, 1);
            sounds[7] = quizSoundPool.load(this, R.raw._sol, 1);
            sounds[8] = quizSoundPool.load(this, R.raw._sol_shap, 1);
            sounds[9] = quizSoundPool.load(this, R.raw._ra, 1);
            sounds[10] = quizSoundPool.load(this, R.raw._ra_shap, 1);
            sounds[11] = quizSoundPool.load(this, R.raw._si, 1);

            soundBtn = findViewById(R.id.level5to8_sound_btn);

            soundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chord init = quiz.getCurrentSoundPitch();
                    soundBtn.setClickable(false);

                    for(int i=0; i<init.getChordsInt().length; i++){
                        switch (init.getChordsStr()[i]){
                            case "C":
                                quizSoundPool.play(sounds[0], 1, 1, 1, 0, 1);
                                break;
                            case "C#":
                                quizSoundPool.play(sounds[1], 1, 1, 1, 0, 1);
                                break;
                            case "D":
                                quizSoundPool.play(sounds[2], 1, 1, 1, 0, 1);
                                break;
                            case "D#":
                                quizSoundPool.play(sounds[3], 1, 1, 1, 0, 1);
                                break;
                            case "E":
                                quizSoundPool.play(sounds[4], 1, 1, 1, 0, 1);
                                break;
                            case "F":
                                quizSoundPool.play(sounds[5], 1, 1, 1, 0, 1);
                                break;
                            case "F#":
                                quizSoundPool.play(sounds[6], 1, 1, 1, 0, 1);
                                break;
                            case "G":
                                quizSoundPool.play(sounds[7], 1, 1, 1, 0, 1);
                                break;
                            case "G#":
                                quizSoundPool.play(sounds[8], 1, 1, 1, 0, 1);
                                break;
                            case "A":
                                quizSoundPool.play(sounds[9], 1, 1, 1, 0, 1);
                                break;
                            case "A#":
                                quizSoundPool.play(sounds[10], 1, 1, 1, 0, 1);
                                break;
                            case "B":
                                quizSoundPool.play(sounds[11], 1, 1, 1, 0, 1);
                                break;
                        }
                    }
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    soundBtn.setClickable(true);
                                }
                            });
                        }
                    }, 1000);
                }
            });

            ansBtnTexts = quiz.getSuffledUsedMonoPitches();

            for(int i=0; i<ansBtnTexts.length; i++) {
                switch (ansBtnTexts[i]){
                    case "C":ansBtn[i].setText("도"); break;
                    case "C#":ansBtn[i].setText("도#"); break;
                    case "D":ansBtn[i].setText("레"); break;
                    case "D#":ansBtn[i].setText("레#"); break;
                    case "E":ansBtn[i].setText("미"); break;
                    case "F":ansBtn[i].setText("파"); break;
                    case "F#":ansBtn[i].setText("파#"); break;
                    case "G":ansBtn[i].setText("솔"); break;
                    case "G#":ansBtn[i].setText("솔#"); break;
                    case "A":ansBtn[i].setText("라"); break;
                    case "A#":ansBtn[i].setText("라#"); break;
                    case "B":ansBtn[i].setText("시"); break;
                }
            }

            // 음 세개씩 입력받기 위해서 필요한 객체들
            ans = new Chord();
            numOfSelectedMonoPitches_inChord = 0;

            ansBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 0);
                }
            });
            ansBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 1);
                }
            });
            ansBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 2);
                }
            });
            ansBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 3);
                }
            });
            ansBtn[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 4);
                }
            });
            ansBtn[5].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 5);
                }
            });
            ansBtn[6].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 6);
                }
            });
            ansBtn[7].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 7);
                }
            });
            ansBtn[8].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 8);
                }
            });
            ansBtn[9].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 9);
                }
            });
            ansBtn[10].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 10);
                }
            });
            ansBtn[11].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    whenClickedAnsBtn(quiz, 11);
                }
            });
        }
    }

    private void ansBtnNum7_ObjectAllocation(){
        ansBtn[0] = findViewById(R.id.level1to4_ansBtn0); ansBtn[1] = findViewById(R.id.level1to4_ansBtn1);
        ansBtn[2] = findViewById(R.id.level1to4_ansBtn2); ansBtn[3] = findViewById(R.id.level1to4_ansBtn3);
        ansBtn[4] = findViewById(R.id.level1to4_ansBtn4); ansBtn[5] = findViewById(R.id.level1to4_ansBtn5);
        ansBtn[6] = findViewById(R.id.level1to4_ansBtn6);
    }
    private void ansBtnNum12_ObjectAllocation(){
        ansBtn[0] = findViewById(R.id.level5to8_ansBtn0); ansBtn[1] = findViewById(R.id.level5to8_ansBtn1);
        ansBtn[2] = findViewById(R.id.level5to8_ansBtn2); ansBtn[3] = findViewById(R.id.level5to8_ansBtn3);
        ansBtn[4] = findViewById(R.id.level5to8_ansBtn4); ansBtn[5] = findViewById(R.id.level5to8_ansBtn5);
        ansBtn[6] = findViewById(R.id.level5to8_ansBtn6); ansBtn[7] = findViewById(R.id.level5to8_ansBtn7);
        ansBtn[8] = findViewById(R.id.level5to8_ansBtn8); ansBtn[9] = findViewById(R.id.level5to8_ansBtn9);
        ansBtn[10] = findViewById(R.id.level5to8_ansBtn10); ansBtn[11] = findViewById(R.id.level5to8_ansBtn11);
    }


    private void whenClickedAnsBtn(final Quiz_Lv1 quiz, int ansBtnIdx){
        boolean isCorrect = quiz.putAnswer(ansBtnTexts[ansBtnIdx]);
        if(quiz.isEnded()) {
            soundBtn.setClickable(false);
            recording_WhenGameEnded(quiz.getWrongCnt());
            finish();
        }
        else if(isCorrect) {
            Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
            ansBtn[ansBtnIdx].setEnabled(false);
            ansBtn[ansBtnIdx].setTextColor(Color.parseColor("#AAAAAAAA"));
        }
    }
    private void whenClickedAnsBtn(final Quiz_Lv2 quiz, int ansBtnIdx){
        if(numOfSelectedMonoPitches_inChord == 0){
            // 선택해야하는 두 음 중  첫번째 음을 선택할 차례
            // 이 음을 선택한 후에 다른 음을 한개 더 선택해야한다.
            tmp[0] = ansBtnTexts[ansBtnIdx];
            Log.e("tmp 변화", "tmp[0]="+tmp[0]);
            ansBtn[ansBtnIdx].setEnabled(false);
            ansBtn[ansBtnIdx].setTextColor(Color.parseColor("#FFAACCFF"));
            numOfSelectedMonoPitches_inChord++;
        }
        else {
            // 선택해야하는 두 음 중  두번째 음을 선택할 차례
            // 이 음을 선택하면 채점할 차례이다.
            ans.setChord(tmp[0], ansBtnTexts[ansBtnIdx]);
            boolean isCorrect = quiz.putAnswer(ans);
            if(quiz.isEnded()){
                soundBtn.setClickable(false);
                recording_WhenGameEnded(quiz.getWrongCnt());
                finish();
            }
            else if(isCorrect){
                Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
                setRemainingNum(quiz, bottomText);
            }
            for(int i=0;i<ansBtnTexts.length; i++){
                if(ansBtnTexts[i].equals(tmp[0])) {
                    ansBtn[i].setEnabled(true);
                    ansBtn[i].setTextColor(Color.parseColor("#DDFFFFFF"));
                }
            }
            numOfSelectedMonoPitches_inChord--;
        }
    }
    private void whenClickedAnsBtn(final Quiz_Lv3 quiz, int ansBtnIdx){
        if(numOfSelectedMonoPitches_inChord == 0){
            // 선택해야하는 두 음 중  첫번째 음을 선택할 차례
            // 이 음을 선택한 후에 다른 음을 한개 더 선택해야한다.
            tmp[0] = ansBtnTexts[ansBtnIdx];
            ansBtn[ansBtnIdx].setEnabled(false);
            ansBtn[ansBtnIdx].setTextColor(Color.parseColor("#FFAACCFF"));
            numOfSelectedMonoPitches_inChord++;
        }
        else {
            // 선택해야하는 두 음 중  두번째 음을 선택할 차례
            // 이 음을 선택하면 채점할 차례이다.
            ans.setChord(tmp[0], ansBtnTexts[ansBtnIdx]);
            boolean isCorrect = quiz.putAnswer(ans);
            if(quiz.isEnded()){
                soundBtn.setClickable(false);
                recording_WhenGameEnded(quiz.getWrongCnt());
                finish();
            }
            else if(isCorrect){
                Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
                setRemainingNum(quiz, bottomText);
            }
            for(int i=0;i<ansBtnTexts.length; i++){
                if(ansBtnTexts[i].equals(tmp[0])) {
                    ansBtn[i].setEnabled(true);
                    ansBtn[i].setTextColor(Color.parseColor("#DDFFFFFF"));
                }
            }
            numOfSelectedMonoPitches_inChord--;
        }
    }
    private void whenClickedAnsBtn(final Quiz_Lv4 quiz, int ansBtnIdx){
        if(numOfSelectedMonoPitches_inChord == 0){
            // 선택해야하는 세 음 중  첫번째 음을 선택할 차례
            // 이 음을 선택한 후에 다른 음을 두개 더 선택해야한다.
            tmp[0] = ansBtnTexts[ansBtnIdx];
            ansBtn[ansBtnIdx].setEnabled(false);
            ansBtn[ansBtnIdx].setTextColor(Color.parseColor("#FFAACCFF"));
            numOfSelectedMonoPitches_inChord++;
        }
        else if(numOfSelectedMonoPitches_inChord == 1){
            // 이 음을 선택한 후에 다른 음을 한개 더 선택해야한다.
            tmp[1] = ansBtnTexts[ansBtnIdx];
            ansBtn[ansBtnIdx].setEnabled(false);
            ansBtn[ansBtnIdx].setTextColor(Color.parseColor("#FFAACCFF"));
            numOfSelectedMonoPitches_inChord++;
        }
        else {
            // 선택해야하는 세 음 중  세번째 음을 선택할 차례
            // 이 음을 선택하면 채점할 차례이다.
            ans.setChord(tmp[0], tmp[1], ansBtnTexts[ansBtnIdx]);
            boolean isCorrect = quiz.putAnswer(ans);
            if(quiz.isEnded()){
                soundBtn.setClickable(false);
                recording_WhenGameEnded(quiz.getWrongCnt());
                finish();
            }
            else if(isCorrect){ // 끝나진 않았는데 맞았을경우
                Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
                setRemainingNum(quiz, bottomText);
            }
            for(int i=0;i<ansBtnTexts.length; i++){
                if(ansBtnTexts[i].equals(tmp[0]) || ansBtnTexts[i].equals(tmp[1])) {
                    ansBtn[i].setEnabled(true);
                    ansBtn[i].setTextColor(Color.parseColor("#DDFFFFFF"));
                }
            }
            numOfSelectedMonoPitches_inChord = 0;
        }
    }
    private void whenClickedAnsBtn(final Quiz_Lv5 quiz, int ansBtnIdx){
        boolean isCorrect = quiz.putAnswer(ansBtnTexts[ansBtnIdx]);
        if(quiz.isEnded()) {
            soundBtn.setClickable(false);
            recording_WhenGameEnded(quiz.getWrongCnt());
            finish();
        }
        else if(isCorrect) {
            Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
            ansBtn[ansBtnIdx].setEnabled(false);
            ansBtn[ansBtnIdx].setTextColor(Color.parseColor("#AAAAAAAA"));
        }
    }
    private void whenClickedAnsBtn(final Quiz_Lv6 quiz, int ansBtnIdx){
        if(numOfSelectedMonoPitches_inChord == 0){
            // 선택해야하는 두 음 중  첫번째 음을 선택할 차례
            // 이 음을 선택한 후에 다른 음을 한개 더 선택해야한다.
            tmp[0] = ansBtnTexts[ansBtnIdx];
            Log.e("tmp 변화", "tmp[0]="+tmp[0]);
            ansBtn[ansBtnIdx].setEnabled(false);
            ansBtn[ansBtnIdx].setTextColor(Color.parseColor("#FFAACCFF"));
            numOfSelectedMonoPitches_inChord++;
        }
        else {
            // 선택해야하는 두 음 중  두번째 음을 선택할 차례
            // 이 음을 선택하면 채점할 차례이다.
            ans.setChord(tmp[0], ansBtnTexts[ansBtnIdx]);
            boolean isCorrect = quiz.putAnswer(ans);
            if(quiz.isEnded()){
                soundBtn.setClickable(false);
                recording_WhenGameEnded(quiz.getWrongCnt());
                finish();
            }
            else if(isCorrect){
                Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
                setRemainingNum(quiz, bottomText);

            }

            for(int i=0;i<ansBtnTexts.length; i++){
                if(ansBtnTexts[i].equals(tmp[0])) {
                    ansBtn[i].setEnabled(true);
                    ansBtn[i].setTextColor(Color.parseColor("#DDFFFFFF"));
                }
            }

            numOfSelectedMonoPitches_inChord--;
        }
    }
    private void whenClickedAnsBtn(final Quiz_Lv7 quiz, int ansBtnIdx){
        if(numOfSelectedMonoPitches_inChord == 0){
            // 선택해야하는 두 음 중  첫번째 음을 선택할 차례
            // 이 음을 선택한 후에 다른 음을 한개 더 선택해야한다.
            tmp[0] = ansBtnTexts[ansBtnIdx];
            Log.e("tmp 변화", "tmp[0]="+tmp[0]);
            ansBtn[ansBtnIdx].setEnabled(false);
            ansBtn[ansBtnIdx].setTextColor(Color.parseColor("#FFAACCFF"));
            numOfSelectedMonoPitches_inChord++;
        }
        else {
            // 선택해야하는 두 음 중  두번째 음을 선택할 차례
            // 이 음을 선택하면 채점할 차례이다.
            ans.setChord(tmp[0], ansBtnTexts[ansBtnIdx]);
            boolean isCorrect = quiz.putAnswer(ans);
            if(quiz.isEnded()){
                soundBtn.setClickable(false);
                recording_WhenGameEnded(quiz.getWrongCnt());
                finish();
            }
            else if(isCorrect){
                Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
                setRemainingNum(quiz, bottomText);

            }

            for(int i=0;i<ansBtnTexts.length; i++){
                if(ansBtnTexts[i].equals(tmp[0])) {
                    ansBtn[i].setEnabled(true);
                    ansBtn[i].setTextColor(Color.parseColor("#DDFFFFFF"));
                }
            }

            numOfSelectedMonoPitches_inChord--;
        }
    }
    private void whenClickedAnsBtn(final Quiz_Lv8 quiz, int ansBtnIdx){
        if(numOfSelectedMonoPitches_inChord == 0){
            // 선택해야하는 세 음 중  첫번째 음을 선택할 차례
            // 이 음을 선택한 후에 다른 음을 두개 더 선택해야한다.
            tmp[0] = ansBtnTexts[ansBtnIdx];
            ansBtn[ansBtnIdx].setEnabled(false);
            ansBtn[ansBtnIdx].setTextColor(Color.parseColor("#FFAACCFF"));
            numOfSelectedMonoPitches_inChord++;
        }
        else if(numOfSelectedMonoPitches_inChord == 1){
            // 이 음을 선택한 후에 다른 음을 한개 더 선택해야한다.
            tmp[1] = ansBtnTexts[ansBtnIdx];
            ansBtn[ansBtnIdx].setEnabled(false);
            ansBtn[ansBtnIdx].setTextColor(Color.parseColor("#FFAACCFF"));
            numOfSelectedMonoPitches_inChord++;
        }
        else {
            // 선택해야하는 세 음 중  세번째 음을 선택할 차례
            // 이 음을 선택하면 채점할 차례이다.
            ans.setChord(tmp[0], tmp[1], ansBtnTexts[ansBtnIdx]);
            boolean isCorrect = quiz.putAnswer(ans);
            if(quiz.isEnded()){
                soundBtn.setClickable(false);
                recording_WhenGameEnded(quiz.getWrongCnt());
                finish();
            }
            else if(isCorrect){ // 끝나진 않았는데 맞았을경우
                Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
                setRemainingNum(quiz, bottomText);
            }
            for(int i=0;i<ansBtnTexts.length; i++){
                if(ansBtnTexts[i].equals(tmp[0]) || ansBtnTexts[i].equals(tmp[1])) {
                    ansBtn[i].setEnabled(true);
                    ansBtn[i].setTextColor(Color.parseColor("#DDFFFFFF"));
                }
            }
            numOfSelectedMonoPitches_inChord = 0;
        }
    }

    private void setRemainingNum(Quiz_Lv2 quiz, TextView text){
        text.setText( quiz.getRemainingNum() + "문제 남았습니다");
    }
    private void setRemainingNum(Quiz_Lv3 quiz, TextView text){
        text.setText(quiz.getRemainingNum() + "문제 남았습니다");
    }
    private void setRemainingNum(Quiz_Lv4 quiz, TextView text){
        text.setText(quiz.getRemainingNum() + "문제 남았습니다");
    }
    private void setRemainingNum(Quiz_Lv5 quiz, TextView text){
        text.setText(quiz.getRemainingNum() + "문제 남았습니다");
    }
    private void setRemainingNum(Quiz_Lv6 quiz, TextView text){
        text.setText(quiz.getRemainingNum() + "문제 남았습니다");
    }
    private void setRemainingNum(Quiz_Lv7 quiz, TextView text){
        text.setText(quiz.getRemainingNum() + "문제 남았습니다");
    }
    private void setRemainingNum(Quiz_Lv8 quiz, TextView text){
        text.setText(quiz.getRemainingNum() + "문제 남았습니다");
    }

    private void recording_WhenGameEnded(int wrongCnt){
        editor.putInt("lv"+level+"_recentWrongCnt", wrongCnt);
        int best = sp.getInt("lv"+level+"_leastWrongCnt", -1);
        if(best == -1 || best > wrongCnt) {
            editor.putInt("lv"+level+"_leastWrongCnt", wrongCnt);

            Log.e("Game Finished : ", "원래기록 " + best + ", 틀린갯수 " + wrongCnt + "로 최고기록 갱신");
        }
        else Log.e("Game Finished : ", "원래기록 " + best + ", 틀린갯수 " + wrongCnt + "로 기록갱신 실패");
        editor.apply();
        isEnded = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isEnded){
            Intent intent = new Intent(GameMainActivity.this, GameResultDialogActivity.class);
            intent.putExtra("level", level);
            startActivity(intent);
        }
    }
}