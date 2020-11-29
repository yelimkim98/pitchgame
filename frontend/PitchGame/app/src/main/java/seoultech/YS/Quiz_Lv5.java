package seoultech.YS;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;

public class Quiz_Lv5 {
    private int wrongCnt; // 틀린갯수
    private int currentSoundIdx; // 현재 문제로 출제될 사운드리스트 인덱스

    protected int[] quizSoundList; // 출제될 사운드 리스트 숫자
    protected String [] quizSoundStrList; // 출제될 사운드 리스트 한글

    // 객체가 만들어지면 퀴즈가 자동 생성되고, 첫번째 문제 출제에 대한 준비가 끝난다.
    Quiz_Lv5(){
        currentSoundIdx = 0;
        generateQuizList();
    }

    // 퀴즈 사운드 리스트
    void setQuizSoundStrList(){
        String quizSoundListStrL1[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        quizSoundStrList = quizSoundListStrL1;
    }
    protected void generateQuizList(){
        setQuizSoundStrList();

        Collections.shuffle(Arrays.asList(quizSoundStrList));

        NumPitchMatch numPitchMatch = new NumPitchMatch();
        quizSoundList = new int[quizSoundStrList.length];

        for (int i = 0; i < quizSoundList.length; i++) {
            quizSoundList[i] = numPitchMatch.getPitchNum(quizSoundStrList[i]);
        }
    }

    // 문제 사운드리스트 순서 섞어서 얻기
    public String[] getSuffledSoundList(){
        String [] ret = new String[quizSoundStrList.length];
        System.arraycopy(quizSoundStrList, 0, ret, 0, quizSoundStrList.length);

        Collections.shuffle(Arrays.asList(ret));

        return ret;
    }

    // 게임이 끝났는지 판별
    public boolean isEnded(){
        if(currentSoundIdx == quizSoundList.length) return true;
        else return false;
    }

    // 답안 입력하기
    public boolean putAnswer(String pitch){

        if(isEnded()) {
            // 게임이 이미 끝난 경우
            Log.e("game ended", "putAnswer: Game was already ended");
            return false;
        }
        else if(pitch.equals(getCurrentSoundPitch())) {
            // 정답을 맞춘 경우
            Log.e("맞음", "정답 : " + getCurrentSoundPitch() + ", 선택한 답 : " + pitch);
            currentSoundIdx++;
            return true;
        }
        else {
            // 답이 틀린 경우
            Log.e("틀림", "정답 : " + getCurrentSoundPitch() + ", 선택한 답 : " + pitch);
            wrongCnt++;
            return false;
        }
    }

    // 현재 문제 사운드 얻기
    public String getCurrentSoundPitch(){
        if(isEnded()){
            Log.e("game ended", "getCurrentSoundPitch: Game was already ended");
            return "";
        }
        NumPitchMatch numPitchMatch = new NumPitchMatch();
        return numPitchMatch.getPitchStr(quizSoundList[currentSoundIdx]);
    }

    // 문제 길이 얻기
    public int getQuizSoundListLength(){
        return quizSoundList.length;
    }

    // 현재 틀린갯수 얻기
    public int getWrongCnt(){
        return wrongCnt;
    }

    // 남은 문제수 얻기
    public int getRemainingNum(){
        return quizSoundStrList.length- currentSoundIdx;
    }
}
