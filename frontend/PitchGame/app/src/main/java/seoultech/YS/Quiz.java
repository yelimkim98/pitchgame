package seoultech.YS;

class Chord {
    private String [] chordsStr;
    private int [] chordsInt;
    private int chordLayerNum;

    public void setChord(String pitch1, String pitch2){
        chordLayerNum = 2;

        chordsStr = new String[2];
        chordsInt = new int[2];
        NumPitchMatch npm = new NumPitchMatch();

        chordsStr[0] = pitch1;
        chordsStr[1] = pitch2;

        chordsInt[0] =  npm.getPitchNum(chordsStr[0]);
        chordsInt[1] =  npm.getPitchNum(chordsStr[1]);
    }
    public void setChord(String pitch1, String pitch2, String pitch3){
        chordLayerNum = 3;

        chordsStr = new String[3];
        chordsInt = new int[3];
        NumPitchMatch npm = new NumPitchMatch();

        chordsStr[0] = pitch1;
        chordsStr[1] = pitch2;
        chordsStr[2] = pitch3;

        chordsInt[0] =  npm.getPitchNum(chordsStr[0]);
        chordsInt[1] =  npm.getPitchNum(chordsStr[1]);
        chordsInt[2] =  npm.getPitchNum(chordsStr[2]);
    }
    public void setChord(String pitch1, String pitch2, String pitch3, String pitch4){
        chordLayerNum = 4;

        chordsStr = new String[4];
        chordsInt = new int[4];
        NumPitchMatch npm = new NumPitchMatch();

        chordsStr[0] = pitch1;
        chordsStr[1] = pitch2;
        chordsStr[2] = pitch3;
        chordsStr[3] = pitch4;

        chordsInt[0] =  npm.getPitchNum(chordsStr[0]);
        chordsInt[1] =  npm.getPitchNum(chordsStr[1]);
        chordsInt[2] =  npm.getPitchNum(chordsStr[2]);
        chordsInt[3] =  npm.getPitchNum(chordsStr[3]);
    }

    public String[] getChordsStr(){
        return chordsStr;
    }

    public int[] getChordsInt(){
        return chordsInt;
    }

    public boolean equals(Chord ch) {
        for(int i=0;i<chordsInt.length;i++){
            if (chordsInt[i] != ch.chordsInt[i]) return false;
        }
        return true;
    }
}

class NumPitchMatch {
    private String [] pitch;
    NumPitchMatch(){
        pitch = new String[12];
        pitch[0] = "C";
        pitch[1] = "C#";
        pitch[2] = "D";
        pitch[3] = "D#";
        pitch[4] = "E";
        pitch[5] = "F";
        pitch[6] = "F#";
        pitch[7] = "G";
        pitch[8] = "G#";
        pitch[9] = "A";
        pitch[10] = "A#";
        pitch[11] = "B";
     }
    public String getPitchStr(int pitchNum){
        return pitch[pitchNum];
    }
    public int getPitchNum(String pitchStr){
        for (int i=0;i<12;i++)
            if(pitchStr == pitch[i]) return i;
        return -1;
    }
}