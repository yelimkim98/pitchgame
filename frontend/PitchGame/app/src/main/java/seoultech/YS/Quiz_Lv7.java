package seoultech.YS;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;

public class Quiz_Lv7 {
    private int wrongCnt; // 틀린갯수
    private int currentSoundIdx; // 현재 문제로 출제될 사운드리스트 인덱스
    protected int level; // 난이도

    private String[] usedMonoPitch;
    protected Chord [] allQuizSoundChordList; // 화음 문제에 대한 문제 사운드 리스트(문제은행)
    protected Chord [] quizSoundChordList; // 문제은행에서 선택된 문제 사운드 리스트

    // 객체가 만들어지면 퀴즈가 자동 생성되고, 첫번째 문제 출제에 대한 준비가 끝난다.
    Quiz_Lv7(){
        currentSoundIdx = 0;
        generateQuizList();
    }

    // 퀴즈 사운드 리스트
    void setAllQuizSoundChordList(){
        usedMonoPitch = new String[12];
        usedMonoPitch[0] = "C";
        usedMonoPitch[1] = "C#";
        usedMonoPitch[2] = "D";
        usedMonoPitch[3] = "D#";
        usedMonoPitch[4] = "E";
        usedMonoPitch[5] = "F";
        usedMonoPitch[6] = "F#";
        usedMonoPitch[7] = "G";
        usedMonoPitch[8] = "G#";
        usedMonoPitch[9] = "A";
        usedMonoPitch[10] = "A#";
        usedMonoPitch[11] = "B";

        allQuizSoundChordList = new Chord [66];
        for(int i=0;i<allQuizSoundChordList.length;i++) allQuizSoundChordList[i] = new Chord();
        allQuizSoundChordList[0].setChord("C", "C#");
        allQuizSoundChordList[1].setChord("C", "D");
        allQuizSoundChordList[2].setChord("C", "D#");
        allQuizSoundChordList[3].setChord("C", "E");
        allQuizSoundChordList[4].setChord("C", "F");
        allQuizSoundChordList[5].setChord("C", "F#");
        allQuizSoundChordList[6].setChord("C", "G");
        allQuizSoundChordList[7].setChord("C", "G#");
        allQuizSoundChordList[8].setChord("C", "A");
        allQuizSoundChordList[9].setChord("C", "A#");
        allQuizSoundChordList[10].setChord("C", "B");

        allQuizSoundChordList[11].setChord("C#", "D");
        allQuizSoundChordList[12].setChord("C#", "D#");
        allQuizSoundChordList[13].setChord("C#", "E");
        allQuizSoundChordList[14].setChord("C#", "F");
        allQuizSoundChordList[15].setChord("C#", "F#");
        allQuizSoundChordList[16].setChord("C#", "G");
        allQuizSoundChordList[17].setChord("C#", "G#");
        allQuizSoundChordList[18].setChord("C#", "A");
        allQuizSoundChordList[19].setChord("C#", "A#");
        allQuizSoundChordList[20].setChord("C#", "B");

        allQuizSoundChordList[21].setChord("D", "D#");
        allQuizSoundChordList[22].setChord("D", "E");
        allQuizSoundChordList[23].setChord("D", "F");
        allQuizSoundChordList[24].setChord("D", "F#");
        allQuizSoundChordList[25].setChord("D", "G");
        allQuizSoundChordList[26].setChord("D", "G#");
        allQuizSoundChordList[27].setChord("D", "A");
        allQuizSoundChordList[28].setChord("D", "A#");
        allQuizSoundChordList[29].setChord("D", "B");

        allQuizSoundChordList[30].setChord("D#", "E");
        allQuizSoundChordList[31].setChord("D#", "F");
        allQuizSoundChordList[32].setChord("D#", "F#");
        allQuizSoundChordList[33].setChord("D#", "G");
        allQuizSoundChordList[34].setChord("D#", "G#");
        allQuizSoundChordList[35].setChord("D#", "A");
        allQuizSoundChordList[36].setChord("D#", "A#");
        allQuizSoundChordList[37].setChord("D#", "B");

        allQuizSoundChordList[38].setChord("E", "F");
        allQuizSoundChordList[39].setChord("E", "F#");
        allQuizSoundChordList[40].setChord("E", "G");
        allQuizSoundChordList[41].setChord("E", "G#");
        allQuizSoundChordList[42].setChord("E", "A");
        allQuizSoundChordList[43].setChord("E", "A#");
        allQuizSoundChordList[44].setChord("E", "B");

        allQuizSoundChordList[45].setChord("F", "F#");
        allQuizSoundChordList[46].setChord("F", "G");
        allQuizSoundChordList[47].setChord("F", "G#");
        allQuizSoundChordList[48].setChord("F", "A");
        allQuizSoundChordList[49].setChord("F", "A#");
        allQuizSoundChordList[50].setChord("F", "B");

        allQuizSoundChordList[51].setChord("F#", "G");
        allQuizSoundChordList[52].setChord("F#", "G#");
        allQuizSoundChordList[53].setChord("F#", "A");
        allQuizSoundChordList[54].setChord("F#", "A#");
        allQuizSoundChordList[55].setChord("F#", "B");

        allQuizSoundChordList[56].setChord("G", "G#");
        allQuizSoundChordList[57].setChord("G", "A");
        allQuizSoundChordList[58].setChord("G", "A#");
        allQuizSoundChordList[59].setChord("G", "B");

        allQuizSoundChordList[60].setChord("G#", "A");
        allQuizSoundChordList[61].setChord("G#", "A#");
        allQuizSoundChordList[62].setChord("G#", "B");

        allQuizSoundChordList[63].setChord("A", "A#");
        allQuizSoundChordList[64].setChord("A", "B");

        allQuizSoundChordList[65].setChord("A#", "B");
    }

    //void setLevel() {
    //    level = 2;
    //}

    protected void generateQuizList(){
        //  setLevel();
        setAllQuizSoundChordList();

        Collections.shuffle(Arrays.asList(allQuizSoundChordList));
        quizSoundChordList = new Chord[8];
        for(int i=0;i<quizSoundChordList.length;i++){
            quizSoundChordList[i] = allQuizSoundChordList[i];
        }
        Log.e("2단계생성", "generateQuizList: " + quizSoundChordList[0].getChordsStr()[0] + " & " + quizSoundChordList[0].getChordsStr()[1]);
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
            Log.e("맞음", "정답 : " + getCurrentSoundPitch().getChordsStr()[0] +
                    "&" + getCurrentSoundPitch().getChordsStr()[1]
                    + ", 선택한 답 : " + ans.getChordsStr()[0]+ "&" + ans.getChordsStr()[1]);
            currentSoundIdx++;
            return true;
        }
        else {
            // 답이 틀린 경우
            Log.e("틀림", "정답 : " + getCurrentSoundPitch().getChordsStr()[0] +
                    "&" + getCurrentSoundPitch().getChordsStr()[1]
                    + ", 선택한 답 : " + ans.getChordsStr()[0] + "&" + ans.getChordsStr()[1]);
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

    // 현재 문제의 난이도 얻기
    //public int getLevel(){
    //    return this.level;
    //}

    // 문제 길이 얻기
    //public int getQuizSoundListLength(){
    //   return quizSoundChordList.length;
    //}

    // 현재 틀린갯수 얻기
    public int getWrongCnt(){
        return wrongCnt;
    }

    // 남은 문제수 얻기
    public int getRemainingNum(){
        return quizSoundChordList.length - currentSoundIdx;
    }
}
