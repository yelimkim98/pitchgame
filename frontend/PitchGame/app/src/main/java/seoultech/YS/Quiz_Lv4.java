package seoultech.YS;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;

public class Quiz_Lv4 {
    private int wrongCnt; // 틀린갯수
    private int currentSoundIdx; // 현재 문제로 출제될 사운드리스트 인덱스
    protected int level; // 난이도

    private String[] usedMonoPitch;
    protected Chord [] allQuizSoundChordList; // 화음 문제에 대한 문제 사운드 리스트(문제은행)
    protected Chord [] quizSoundChordList; // 문제은행에서 선택된 문제 사운드 리스트

    // 객체가 만들어지면 퀴즈가 자동 생성되고, 첫번째 문제 출제에 대한 준비가 끝난다.
    Quiz_Lv4(){
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

        allQuizSoundChordList = new Chord [35];
        for(int i=0;i<allQuizSoundChordList.length;i++) allQuizSoundChordList[i] = new Chord();
        allQuizSoundChordList[0].setChord("C", "D","E");
        allQuizSoundChordList[1].setChord("C", "D","F");
        allQuizSoundChordList[2].setChord("C", "D","G");
        allQuizSoundChordList[3].setChord("C", "D","A");
        allQuizSoundChordList[4].setChord("C", "D","B");
        allQuizSoundChordList[5].setChord("C", "E","F");
        allQuizSoundChordList[6].setChord("C", "E","G");
        allQuizSoundChordList[7].setChord("C", "E","A");
        allQuizSoundChordList[8].setChord("C", "E","B");
        allQuizSoundChordList[9].setChord("C", "F","G");
        allQuizSoundChordList[10].setChord("C", "F","A");
        allQuizSoundChordList[11].setChord("C", "F","B");
        allQuizSoundChordList[12].setChord("C", "G","A");
        allQuizSoundChordList[13].setChord("C", "G","B");
        allQuizSoundChordList[14].setChord("C", "A","B");

        allQuizSoundChordList[15].setChord("D", "E","F");
        allQuizSoundChordList[16].setChord("D", "E","G");
        allQuizSoundChordList[17].setChord("D", "E","A");
        allQuizSoundChordList[18].setChord("D", "E","B");
        allQuizSoundChordList[19].setChord("D", "F","G");
        allQuizSoundChordList[20].setChord("D", "F","A");
        allQuizSoundChordList[21].setChord("D", "F","B");
        allQuizSoundChordList[22].setChord("D", "G","A");
        allQuizSoundChordList[23].setChord("D", "G","B");
        allQuizSoundChordList[24].setChord("D", "A","B");

        allQuizSoundChordList[25].setChord("E", "F","G");
        allQuizSoundChordList[26].setChord("E", "F","A");
        allQuizSoundChordList[27].setChord("E", "F","B");
        allQuizSoundChordList[28].setChord("E", "G","A");
        allQuizSoundChordList[29].setChord("E", "G","B");
        allQuizSoundChordList[30].setChord("E", "A","B");

        allQuizSoundChordList[31].setChord("F", "G","A");
        allQuizSoundChordList[32].setChord("F", "G","B");
        allQuizSoundChordList[33].setChord("F", "A","B");

        allQuizSoundChordList[34].setChord("G", "A","B");
    }

    void setLevel() {
        level = 3;
    }

    protected void generateQuizList(){
        //  setLevel();
        setAllQuizSoundChordList();

        Collections.shuffle(Arrays.asList(allQuizSoundChordList));
        quizSoundChordList = new Chord[10];
        for(int i=0;i<quizSoundChordList.length;i++){
            quizSoundChordList[i] = allQuizSoundChordList[i];
        }
        Log.e("4단계생성", "generateQuizList: " + quizSoundChordList[0].getChordsStr()[0]
                + " & " + quizSoundChordList[0].getChordsStr()[1]
                + " & " + quizSoundChordList[0].getChordsStr()[2]);
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
        Chord []ansReverse = new Chord[5];
        for(int i=0;i<5;i++) ansReverse[i] = new Chord();
        String [] tmpForReverse = new String[3];

        tmpForReverse[0] = ans.getChordsStr()[0];
        tmpForReverse[1] = ans.getChordsStr()[2];
        tmpForReverse[2] = ans.getChordsStr()[1];
        ansReverse[0].setChord(tmpForReverse[0], tmpForReverse[1], tmpForReverse[2]);

        tmpForReverse[0] = ans.getChordsStr()[1];
        tmpForReverse[1] = ans.getChordsStr()[0];
        tmpForReverse[2] = ans.getChordsStr()[2];
        ansReverse[1].setChord(tmpForReverse[0], tmpForReverse[1], tmpForReverse[2]);

        tmpForReverse[0] = ans.getChordsStr()[1];
        tmpForReverse[1] = ans.getChordsStr()[2];
        tmpForReverse[2] = ans.getChordsStr()[0];
        ansReverse[2].setChord(tmpForReverse[0], tmpForReverse[1], tmpForReverse[2]);

        tmpForReverse[0] = ans.getChordsStr()[2];
        tmpForReverse[1] = ans.getChordsStr()[0];
        tmpForReverse[2] = ans.getChordsStr()[1];
        ansReverse[3].setChord(tmpForReverse[0], tmpForReverse[1], tmpForReverse[2]);

        tmpForReverse[0] = ans.getChordsStr()[2];
        tmpForReverse[1] = ans.getChordsStr()[1];
        tmpForReverse[2] = ans.getChordsStr()[0];
        ansReverse[4].setChord(tmpForReverse[0], tmpForReverse[1], tmpForReverse[2]);

        Chord currentSoundPitchTmp = getCurrentSoundPitch();

        if(isEnded()) {
            // 게임이 이미 끝난 경우
            Log.e("game ended", "putAnswer: Game was already ended");
            return false;
        }
        else if(ans.equals(currentSoundPitchTmp) || ansReverse[0].equals(currentSoundPitchTmp)
             || ansReverse[1].equals(currentSoundPitchTmp) || ansReverse[2].equals(currentSoundPitchTmp)
             || ansReverse[3].equals(currentSoundPitchTmp) || ansReverse[4].equals(currentSoundPitchTmp)) {
            // 정답을 맞춘 경우 1
            Log.e("맞음", "정답 : " + getCurrentSoundPitch().getChordsStr()[0]
                    + getCurrentSoundPitch().getChordsStr()[1]
                    + getCurrentSoundPitch().getChordsStr()[0] + ", 선택한 답 : "
                    + ans.getChordsStr()[0]
                    + "&" + ans.getChordsStr()[1]
                    + "&" + ans.getChordsStr()[2]);
            currentSoundIdx++;
            return true;
        }
        else {
            // 답이 틀린 경우
            Log.e("틀림", "정답 : " + getCurrentSoundPitch().getChordsStr()[0]
                    + getCurrentSoundPitch().getChordsStr()[1]
                    + getCurrentSoundPitch().getChordsStr()[0] + ", 선택한 답 : "
                    + ans.getChordsStr()[0]
                    + "&" + ans.getChordsStr()[1]
                    + "&" + ans.getChordsStr()[2]);
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
    public int getLevel(){
        return this.level;
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
