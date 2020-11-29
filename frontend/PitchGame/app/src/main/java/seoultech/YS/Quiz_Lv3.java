package seoultech.YS;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;

public class Quiz_Lv3 {
    private int wrongCnt; // 틀린갯수
    private int currentSoundIdx; // 현재 문제로 출제될 사운드리스트 인덱스

    private String[] usedMonoPitch;
    protected Chord [] allQuizSoundChordList; // 화음 문제에 대한 문제 사운드 리스트(문제은행)
    protected Chord [] quizSoundChordList; // 문제은행에서 선택된 문제 사운드 리스트

    // 객체가 만들어지면 퀴즈가 자동 생성되고, 첫번째 문제 출제에 대한 준비가 끝난다.
    Quiz_Lv3(){
        currentSoundIdx = 0;
        generateQuizList();
    }

    // 퀴즈 사운드 리스트
    void setAllQuizSoundChordList(){
        usedMonoPitch = new String[7];
        usedMonoPitch[0] = "C";
        usedMonoPitch[1] = "D";
        usedMonoPitch[2] = "E";
        usedMonoPitch[3] = "F";
        usedMonoPitch[4] = "G";
        usedMonoPitch[5] = "A";
        usedMonoPitch[6] = "B";

        allQuizSoundChordList = new Chord [21];
        for(int i=0;i<allQuizSoundChordList.length;i++) allQuizSoundChordList[i] = new Chord();
        allQuizSoundChordList[0].setChord("C", "D");
        allQuizSoundChordList[1].setChord("C", "E");
        allQuizSoundChordList[2].setChord("C", "F");
        allQuizSoundChordList[3].setChord("C", "G");
        allQuizSoundChordList[4].setChord("C", "A");
        allQuizSoundChordList[5].setChord("C", "B");

        allQuizSoundChordList[6].setChord("D", "E");
        allQuizSoundChordList[7].setChord("D", "F");
        allQuizSoundChordList[8].setChord("D", "G");
        allQuizSoundChordList[9].setChord("D", "A");
        allQuizSoundChordList[10].setChord("D", "B");

        allQuizSoundChordList[11].setChord("E", "F");
        allQuizSoundChordList[12].setChord("E", "G");
        allQuizSoundChordList[13].setChord("E", "A");
        allQuizSoundChordList[14].setChord("E", "B");

        allQuizSoundChordList[15].setChord("F", "G");
        allQuizSoundChordList[16].setChord("F", "A");
        allQuizSoundChordList[17].setChord("F", "B");

        allQuizSoundChordList[18].setChord("G", "A");
        allQuizSoundChordList[19].setChord("G", "B");

        allQuizSoundChordList[20].setChord("A", "B");
    }

    protected void generateQuizList(){
        setAllQuizSoundChordList();

        Collections.shuffle(Arrays.asList(allQuizSoundChordList));
        quizSoundChordList = new Chord[10];
        for(int i=0;i<quizSoundChordList.length;i++){
            quizSoundChordList[i] = allQuizSoundChordList[i];
        }
        Log.e("3단계생성", "generateQuizList: " + quizSoundChordList[0].getChordsStr()[0] + " & " + quizSoundChordList[0].getChordsStr()[1]);
    }

    // 문제 사운드리스트 순서 섞어서 얻기
    public Chord[] getSuffledSoundList(){
        Chord [] ret = new Chord[quizSoundChordList.length];
        System.arraycopy(quizSoundChordList, 0, ret, 0, quizSoundChordList.length);

        Collections.shuffle(Arrays.asList(ret));

        return ret;
    }

    // 문제에서 사용되는 단음정 리스트 순서 섞어서 얻기 (버튼글자 셋팅할때 필요함)
    public String[] getSuffledUsedMonoPitches(){
        String[] ret = new String[usedMonoPitch.length];
        System.arraycopy(usedMonoPitch, 0, ret, 0, usedMonoPitch.length);

        Collections.shuffle(Arrays.asList(ret));

        return ret;
    }

    // 게임이 끝났는지 판별
    public boolean isEnded(){
        if(currentSoundIdx == quizSoundChordList.length) return true;
        else return false;
    }

    // 답안 입력하기
    public boolean putAnswer(Chord ans){
        Chord ansReverse = new Chord();
        String [] tmpForReverse = new String[2];
        tmpForReverse[0] = ans.getChordsStr()[1];
        tmpForReverse[1] = ans.getChordsStr()[0];
        ansReverse.setChord(tmpForReverse[0], tmpForReverse[1]);

        if(isEnded()) {
            // 게임이 이미 끝난 경우
            Log.e("game ended", "putAnswer: Game was already ended");
            return false;
        }
        else if(ans.equals(getCurrentSoundPitch()) || ansReverse.equals(getCurrentSoundPitch())) {
            // 정답을 맞춘 경우 1
            Log.e("맞음", "정답 : " + getCurrentSoundPitch() + ", 선택한 답 : " + ans);
            currentSoundIdx++;
            return true;
        }
        else {
            // 답이 틀린 경우
            Log.e("틀림", "정답 : " + getCurrentSoundPitch() + ", 선택한 답 : " + ans);
            wrongCnt++;
            return false;
        }
    }

    // 현재 문제 사운드 얻기
    public Chord getCurrentSoundPitch(){
        if(isEnded()){
            Log.e("game ended", "getCurrentSoundPitch: Game was already ended");
            return null;
        }
        return quizSoundChordList[currentSoundIdx];
    }

    // 문제 길이 얻기
    public int getQuizSoundListLength(){
        return quizSoundChordList.length;
    }

    // 현재 틀린갯수 얻기
    public int getWrongCnt(){
        return wrongCnt;
    }

    // 남은 문제수 얻기
    public int getRemainingNum(){
        return quizSoundChordList.length - currentSoundIdx;
    }
}
