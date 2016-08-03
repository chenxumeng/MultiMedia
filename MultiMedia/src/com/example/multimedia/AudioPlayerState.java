package com.example.multimedia;

public class AudioPlayerState {
	public final int STATEIDEL = 0;
	public final int STATEPLAY = 1;
	public final int STATEPAUSE = 2;
	public final int STATESTOP = 3;
	private static AudioPlayerState single=null;
	private int mState;
	
	 public static AudioPlayerState getInstance() {
         if (single == null) {  
             single = new AudioPlayerState();
         }  
        return single;
    }
	public void setAudioPlayerState(int state)
	{
		mState = state;
	}
	public int getAudioPlayerState()
	{
		return mState;
	}
}
