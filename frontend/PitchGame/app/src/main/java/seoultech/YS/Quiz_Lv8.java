package seoultech.YS;

import android.util.Log;
import java.util.Arrays;
import java.util.Collections;

public class Quiz_Lv8 {
    private int wrongCnt; // 틀린갯수
    private int currentSoundIdx; // 현재 문제로 출제될 사운드리스트 인덱스

    private String[] usedMonoPitch;
    protected Chord [] allQuizSoundChordList; // 화음 문제에 대한 문제 사운드 리스트(문제은행)
    protected Chord [] quizSoundChordList; // 문제은행에서 선택된 문제 사운드 리스트

    // 객체가 만들어지면 퀴즈가 자동 생성되고, 첫번째 문제 출제에 대한 준비가 끝난다.
    Quiz_Lv8(){
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

        allQuizSoundChordList = new Chord [220];
        for(int i=0;i<allQuizSoundChordList.length;i++) allQuizSoundChordList[i] = new Chord();

        allQuizSoundChordList[0].setChord("C", "C#","D");
        allQuizSoundChordList[1].setChord("C", "C#","D#");
        allQuizSoundChordList[2].setChord("C", "C#","E");
        allQuizSoundChordList[3].setChord("C", "C#","F");
        allQuizSoundChordList[4].setChord("C", "C#","F#");
        allQuizSoundChordList[5].setChord("C", "C#","G");
        allQuizSoundChordList[6].setChord("C", "C#","G#");
        allQuizSoundChordList[7].setChord("C", "C#","A");
        allQuizSoundChordList[8].setChord("C", "C#","A#");
        allQuizSoundChordList[9].setChord("C", "C#","B");

        allQuizSoundChordList[10].setChord("C", "D","D#");
        allQuizSoundChordList[11].setChord("C", "D","E");
        allQuizSoundChordList[12].setChord("C", "D","F");
        allQuizSoundChordList[13].setChord("C", "D","F#");
        allQuizSoundChordList[14].setChord("C", "D","G");
        allQuizSoundChordList[15].setChord("C", "D","G#");
        allQuizSoundChordList[16].setChord("C", "D","A");
        allQuizSoundChordList[17].setChord("C", "D","A#");
        allQuizSoundChordList[18].setChord("C", "D","B");

        allQuizSoundChordList[19].setChord("C", "D#","E");
        allQuizSoundChordList[20].setChord("C", "D#","F");
        allQuizSoundChordList[21].setChord("C", "D#","F#");
        allQuizSoundChordList[22].setChord("C", "D#","G");
        allQuizSoundChordList[23].setChord("C", "D#","G#");
        allQuizSoundChordList[24].setChord("C", "D#","A");
        allQuizSoundChordList[25].setChord("C", "D#","A#");
        allQuizSoundChordList[26].setChord("C", "D#","B");

        allQuizSoundChordList[27].setChord("C", "E","F");
        allQuizSoundChordList[28].setChord("C", "E","F#");
        allQuizSoundChordList[29].setChord("C", "E","G");
        allQuizSoundChordList[30].setChord("C", "E","G#");
        allQuizSoundChordList[31].setChord("C", "E","A");
        allQuizSoundChordList[32].setChord("C", "E","A#");
        allQuizSoundChordList[33].setChord("C", "E","B");

        allQuizSoundChordList[34].setChord("C", "F","F#");
        allQuizSoundChordList[35].setChord("C", "F","G");
        allQuizSoundChordList[36].setChord("C", "F","G#");
        allQuizSoundChordList[37].setChord("C", "F","A");
        allQuizSoundChordList[38].setChord("C", "F","A#");
        allQuizSoundChordList[39].setChord("C", "F","B");

        allQuizSoundChordList[40].setChord("C", "F#","G");
        allQuizSoundChordList[41].setChord("C", "F#","G#");
        allQuizSoundChordList[42].setChord("C", "F#","A");
        allQuizSoundChordList[43].setChord("C", "F#","A#");
        allQuizSoundChordList[44].setChord("C", "F#","B");

        allQuizSoundChordList[45].setChord("C", "G","G#");
        allQuizSoundChordList[46].setChord("C", "G","A");
        allQuizSoundChordList[47].setChord("C", "G","A#");
        allQuizSoundChordList[48].setChord("C", "G","B");

        allQuizSoundChordList[49].setChord("C", "G#","A");
        allQuizSoundChordList[50].setChord("C", "G#","A#");
        allQuizSoundChordList[51].setChord("C", "G#","B");

        allQuizSoundChordList[52].setChord("C", "A","A#");
        allQuizSoundChordList[53].setChord("C", "A","B");

        allQuizSoundChordList[54].setChord("C", "A#","B");

        /* chords starting with C# */
        allQuizSoundChordList[55].setChord("C#", "D","D#");
        allQuizSoundChordList[56].setChord("C#", "D","E");
        allQuizSoundChordList[57].setChord("C#", "D","F");
        allQuizSoundChordList[58].setChord("C#", "D","F#");
        allQuizSoundChordList[59].setChord("C#", "D","G");
        allQuizSoundChordList[60].setChord("C#", "D","G#");
        allQuizSoundChordList[61].setChord("C#", "D","A");
        allQuizSoundChordList[62].setChord("C#", "D","A#");
        allQuizSoundChordList[63].setChord("C#", "D","B");

        allQuizSoundChordList[64].setChord("C#", "D#","E");
        allQuizSoundChordList[65].setChord("C#", "D#","F");
        allQuizSoundChordList[66].setChord("C#", "D#","F#");
        allQuizSoundChordList[67].setChord("C#", "D#","G");
        allQuizSoundChordList[68].setChord("C#", "D#","G#");
        allQuizSoundChordList[69].setChord("C#", "D#","A");
        allQuizSoundChordList[70].setChord("C#", "D#","A#");
        allQuizSoundChordList[71].setChord("C#", "D#","B");

        allQuizSoundChordList[72].setChord("C#", "E","F");
        allQuizSoundChordList[73].setChord("C#", "E","F#");
        allQuizSoundChordList[74].setChord("C#", "E","G");
        allQuizSoundChordList[75].setChord("C#", "E","G#");
        allQuizSoundChordList[76].setChord("C#", "E","A");
        allQuizSoundChordList[77].setChord("C#", "E","A#");
        allQuizSoundChordList[78].setChord("C#", "E","B");

        allQuizSoundChordList[79].setChord("C#", "F","F#");
        allQuizSoundChordList[80].setChord("C#", "F","G");
        allQuizSoundChordList[81].setChord("C#", "F","G#");
        allQuizSoundChordList[82].setChord("C#", "F","A");
        allQuizSoundChordList[83].setChord("C#", "F","A#");
        allQuizSoundChordList[84].setChord("C#", "F","B");

        allQuizSoundChordList[85].setChord("C#", "F#","G");
        allQuizSoundChordList[86].setChord("C#", "F#","G#");
        allQuizSoundChordList[87].setChord("C#", "F#","A");
        allQuizSoundChordList[88].setChord("C#", "F#","A#");
        allQuizSoundChordList[89].setChord("C#", "F#","B");

        allQuizSoundChordList[90].setChord("C#", "G","G#");
        allQuizSoundChordList[91].setChord("C#", "G","A");
        allQuizSoundChordList[92].setChord("C#", "G","A#");
        allQuizSoundChordList[93].setChord("C#", "G","B");

        allQuizSoundChordList[94].setChord("C#", "G#","A");
        allQuizSoundChordList[95].setChord("C#", "G#","A#");
        allQuizSoundChordList[96].setChord("C#", "G#","B");

        allQuizSoundChordList[97].setChord("C#", "A","A#");
        allQuizSoundChordList[98].setChord("C#", "A","B");

        allQuizSoundChordList[99].setChord("C#", "A#","B");

        /* chords starting with D*/
        allQuizSoundChordList[100].setChord("D", "D#","E");
        allQuizSoundChordList[101].setChord("D", "D#","F");
        allQuizSoundChordList[102].setChord("D", "D#","F#");
        allQuizSoundChordList[103].setChord("D", "D#","G");
        allQuizSoundChordList[104].setChord("D", "D#","G#");
        allQuizSoundChordList[105].setChord("D", "D#","A");
        allQuizSoundChordList[106].setChord("D", "D#","A#");
        allQuizSoundChordList[107].setChord("D", "D#","B");

        allQuizSoundChordList[108].setChord("D", "E","F");
        allQuizSoundChordList[109].setChord("D", "E","F#");
        allQuizSoundChordList[110].setChord("D", "E","G");
        allQuizSoundChordList[111].setChord("D", "E","G#");
        allQuizSoundChordList[112].setChord("D", "E","A");
        allQuizSoundChordList[113].setChord("D", "E","A#");
        allQuizSoundChordList[114].setChord("D", "E","B");

        allQuizSoundChordList[115].setChord("D", "F","F#");
        allQuizSoundChordList[116].setChord("D", "F","G");
        allQuizSoundChordList[117].setChord("D", "F","G#");
        allQuizSoundChordList[118].setChord("D", "F","A");
        allQuizSoundChordList[119].setChord("D", "F","A#");
        allQuizSoundChordList[120].setChord("D", "F","B");

        allQuizSoundChordList[121].setChord("D", "F#","G");
        allQuizSoundChordList[122].setChord("D", "F#","G#");
        allQuizSoundChordList[123].setChord("D", "F#","A");
        allQuizSoundChordList[124].setChord("D", "F#","A#");
        allQuizSoundChordList[125].setChord("D", "F#","B");

        allQuizSoundChordList[126].setChord("D", "G","G#");
        allQuizSoundChordList[127].setChord("D", "G","A");
        allQuizSoundChordList[128].setChord("D", "G","A#");
        allQuizSoundChordList[129].setChord("D", "G","B");

        allQuizSoundChordList[130].setChord("D", "G#","A");
        allQuizSoundChordList[131].setChord("D", "G#","A#");
        allQuizSoundChordList[132].setChord("D", "G#","B");

        allQuizSoundChordList[133].setChord("D", "A","A#");
        allQuizSoundChordList[134].setChord("D", "A","B");

        allQuizSoundChordList[135].setChord("D", "A#","B");

        /* chords starting with D#*/
        allQuizSoundChordList[136].setChord("D#", "E","F");
        allQuizSoundChordList[137].setChord("D#", "E","F#");
        allQuizSoundChordList[138].setChord("D#", "E","G");
        allQuizSoundChordList[139].setChord("D#", "E","G#");
        allQuizSoundChordList[140].setChord("D#", "E","A");
        allQuizSoundChordList[141].setChord("D#", "E","A#");
        allQuizSoundChordList[142].setChord("D#", "E","B");

        allQuizSoundChordList[143].setChord("D#", "F","F#");
        allQuizSoundChordList[144].setChord("D#", "F","G");
        allQuizSoundChordList[145].setChord("D#", "F","G#");
        allQuizSoundChordList[146].setChord("D#", "F","A");
        allQuizSoundChordList[147].setChord("D#", "F","A#");
        allQuizSoundChordList[148].setChord("D#", "F","B");

        allQuizSoundChordList[149].setChord("D#", "F#","G");
        allQuizSoundChordList[150].setChord("D#", "F#","G#");
        allQuizSoundChordList[151].setChord("D#", "F#","A");
        allQuizSoundChordList[152].setChord("D#", "F#","A#");
        allQuizSoundChordList[153].setChord("D#", "F#","B");

        allQuizSoundChordList[154].setChord("D#", "G","G#");
        allQuizSoundChordList[155].setChord("D#", "G","A");
        allQuizSoundChordList[156].setChord("D#", "G","A#");
        allQuizSoundChordList[157].setChord("D#", "G","B");

        allQuizSoundChordList[158].setChord("D#", "G#","A");
        allQuizSoundChordList[159].setChord("D#", "G#","A#");
        allQuizSoundChordList[160].setChord("D#", "G#","B");

        allQuizSoundChordList[161].setChord("D#", "A","A#");
        allQuizSoundChordList[162].setChord("D#", "A","B");

        allQuizSoundChordList[163].setChord("D#", "A#","B");

        /* chords starting with E */
        allQuizSoundChordList[164].setChord("E", "F","F#");
        allQuizSoundChordList[165].setChord("E", "F","G");
        allQuizSoundChordList[166].setChord("E", "F","G#");
        allQuizSoundChordList[167].setChord("E", "F","A");
        allQuizSoundChordList[168].setChord("E", "F","A#");
        allQuizSoundChordList[169].setChord("E", "F","B");

        allQuizSoundChordList[170].setChord("E", "F#","G");
        allQuizSoundChordList[171].setChord("E", "F#","G#");
        allQuizSoundChordList[172].setChord("E", "F#","A");
        allQuizSoundChordList[173].setChord("E", "F#","A#");
        allQuizSoundChordList[174].setChord("E", "F#","B");

        allQuizSoundChordList[175].setChord("E", "G","G#");
        allQuizSoundChordList[176].setChord("E", "G","A");
        allQuizSoundChordList[177].setChord("E", "G","A#");
        allQuizSoundChordList[178].setChord("E", "G","B");

        allQuizSoundChordList[179].setChord("E", "G#","A");
        allQuizSoundChordList[180].setChord("E", "G#","A#");
        allQuizSoundChordList[181].setChord("E", "G#","B");

        allQuizSoundChordList[182].setChord("E", "A","A#");
        allQuizSoundChordList[183].setChord("E", "A","B");

        allQuizSoundChordList[184].setChord("E", "A#","B");

        /* chords starting with F */
        allQuizSoundChordList[185].setChord("F", "F#","G");
        allQuizSoundChordList[186].setChord("F", "F#","G#");
        allQuizSoundChordList[187].setChord("F", "F#","A");
        allQuizSoundChordList[188].setChord("F", "F#","A#");
        allQuizSoundChordList[189].setChord("F", "F#","B");

        allQuizSoundChordList[190].setChord("F", "G","G#");
        allQuizSoundChordList[191].setChord("F", "G","A");
        allQuizSoundChordList[192].setChord("F", "G","A#");
        allQuizSoundChordList[193].setChord("F", "G","B");

        allQuizSoundChordList[194].setChord("F", "G#","A");
        allQuizSoundChordList[195].setChord("F", "G#","A#");
        allQuizSoundChordList[196].setChord("F", "G#","B");

        allQuizSoundChordList[197].setChord("F", "A","A#");
        allQuizSoundChordList[198].setChord("F", "A","B");

        allQuizSoundChordList[199].setChord("F", "A#","B");

        /* chords staring with F#*/
        allQuizSoundChordList[200].setChord("F#", "G","G#");
        allQuizSoundChordList[201].setChord("F#", "G","A");
        allQuizSoundChordList[202].setChord("F#", "G","A#");
        allQuizSoundChordList[203].setChord("F#", "G","B");

        allQuizSoundChordList[204].setChord("F#", "G#","A");
        allQuizSoundChordList[205].setChord("F#", "G#","A#");
        allQuizSoundChordList[206].setChord("F#", "G#","B");

        allQuizSoundChordList[207].setChord("F#", "A","A#");
        allQuizSoundChordList[208].setChord("F#", "A","B");

        allQuizSoundChordList[209].setChord("F#", "A#","B");

        /* chords starting with G*/
        allQuizSoundChordList[210].setChord("G", "G#","A");
        allQuizSoundChordList[211].setChord("G", "G#","A#");
        allQuizSoundChordList[212].setChord("G", "G#","B");

        allQuizSoundChordList[213].setChord("G", "A","A#");
        allQuizSoundChordList[214].setChord("G", "A","B");

        allQuizSoundChordList[215].setChord("G", "A#","B");

        /* chords starting with G# */
        allQuizSoundChordList[216].setChord("G#", "A","A#");
        allQuizSoundChordList[217].setChord("G#", "A","B");

        allQuizSoundChordList[218].setChord("G#", "A#","B");

        /* chords starting with A */
        allQuizSoundChordList[219].setChord("A", "A#","B");
    }

    protected void generateQuizList(){
        setAllQuizSoundChordList();

        Collections.shuffle(Arrays.asList(allQuizSoundChordList));
        quizSoundChordList = new Chord[10];
        for(int i=0;i<quizSoundChordList.length;i++){
            quizSoundChordList[i] = allQuizSoundChordList[i];
        }
        Log.e("8단계생성", "generateQuizList: " + quizSoundChordList[0].getChordsStr()[0]
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
                    + getCurrentSoundPitch().getChordsStr()[2] + ", 선택한 답 : "
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
                    + getCurrentSoundPitch().getChordsStr()[2] + ", 선택한 답 : "
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
