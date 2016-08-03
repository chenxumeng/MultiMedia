package com.example.multimedia;

import java.util.ArrayList;
import java.util.List;





import com.example.adapter.MusicListAdapter;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class audioPlayer extends Activity implements OnClickListener{
	private final String  TAG = "audioPlayer";
	private MediaPlayer mMediaPlayer;
	private ImageButton mBtnStart;
	private ImageButton mBtnPause;
	private ImageButton mBtnStop;
	private SeekBar mSbProgress;
	private int mCurrentTime;
	private int mDurationTime;
	private TextView mTvCurTime;
	private TextView mTvDurTime;
	private Handler mProgessHander;
	private ListView mMusicList;
	// 音乐文件列表
	private List<MusicData> m_MusicFileList;
	private MusicListAdapter mMusicAdater;
	
	private final int START = 0;
	private final int UPDATE = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 加载布局文件
        setContentView(R.layout.audioplayer);
        this.getWindow().setBackgroundDrawableResource(R.drawable.main_bg01);
        
		// 开始
		mBtnStart = (ImageButton)findViewById(R.id.Start);
		mBtnStart.setOnClickListener(this);
		// 暂停
		mBtnPause = (ImageButton)findViewById(R.id.Pause);
		mBtnPause.setOnClickListener(this);
		// 停止
		mBtnStop = (ImageButton)findViewById(R.id.Stop);
		mBtnStop.setOnClickListener(this);
		mSbProgress = (SeekBar)findViewById(R.id.Progress);
		// 当前时间
		mTvCurTime = (TextView)findViewById(R.id.CurrentProgress);
		// 总时间
		mTvDurTime = (TextView)findViewById(R.id.DurationProgress);
		mProgessHander = new Handler(){
			public void handleMessage(Message msg) {  
	            super.handleMessage(msg);  
	            switch (msg.what) {  
	                case START: 
	        			mDurationTime = mMediaPlayer.getDuration();
	        			mTvDurTime.setText(Ms2s(mDurationTime));
	                	mSbProgress.setMax(mDurationTime);  
	                    break;  
	                case UPDATE:  
	                    try { 
	                    	mCurrentTime = mMediaPlayer.getCurrentPosition();
	                    	mTvCurTime.setText(Ms2s(mCurrentTime));
	                    	mSbProgress.setProgress(mCurrentTime);  
	                    } catch (Exception e) {  
	                        e.printStackTrace();  
	                    }  
	                    mProgessHander.sendEmptyMessageDelayed(UPDATE,1000);  
	                    break;  
	            }  
	        }  
		};
		// 获得数据
		mMusicList = (ListView)findViewById(R.id.MusicList);
		Log.e(TAG, "getMusicFileList");
		m_MusicFileList = getMusicFileList();
		mMusicAdater = new MusicListAdapter(this, m_MusicFileList);
		mMusicList.setAdapter(mMusicAdater);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Start:
			 // 创建对象
			mMediaPlayer = MediaPlayer.create(this, R.raw.music1);
			mMediaPlayer.setLooping(true);//设置循环播放
			mMediaPlayer.start();
			mProgessHander.sendEmptyMessage(START);
			mProgessHander.sendEmptyMessage(UPDATE);
			break;
		case R.id.Pause:
			Log.d(TAG, "audio pause");
			mMediaPlayer.pause();
			break;
		case R.id.Stop:
			Log.d(TAG, "audio stop");
			mMediaPlayer.stop();
			break;

		default:
			break;
		}
		
	}
	private String Ms2s(int ms)
	{
		int Times = 0;
		int TimeM = 0;
		String Time;
		TimeM = ms/1000/60;
		Times = ms/1000 - TimeM *60;
		if (TimeM <10 ) {
			Time = "0"+TimeM+":"+Times;
		}else{
			Time = TimeM+":"+Times;
		}
		return Time;	
	}
	//读取音乐列表
    private List<MusicData> getMusicFileList()
    {
    	List<MusicData> list = new ArrayList<MusicData>();
    	//获取专辑封面的Uri
   
    	String[] projection = new String[]{MediaStore.Audio.Media._ID, 
    									MediaStore.Audio.Media.TITLE, 
    									MediaStore.Audio.Media.DURATION,
    									MediaStore.Audio.Media.DATA,
    									MediaStore.Audio.Media.ARTIST,
    									MediaStore.Audio.Media.ALBUM_ID,
    									};   
    	
    	long time1 = System.currentTimeMillis();
    	Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI , projection, null, null, null);
    	if (cursor != null)
    	{
    		cursor.moveToFirst();
    		int colIdIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
		    int colNameIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int colTimeIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int colPathIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int colArtistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int fileNum = cursor.getCount(); 
            Log.d(TAG,"fileNum" +fileNum);
            for(int counter = 0; counter < fileNum; counter++){        
                
                MusicData data = new MusicData();
                data.mMusicName = cursor.getString(colNameIndex);
                data.mMusicTime = cursor.getInt(colTimeIndex);
                data.mMusicPath = cursor.getString(colPathIndex);
                data.mMusicAritst = cursor.getString(colArtistIndex);
                data.MusicAlbumId = cursor.getInt(albumId);
                data.MusicId= cursor.getInt(colIdIndex);
                Log.d(TAG,"##############");
                Log.d(TAG, "name "+data.mMusicName);
                Log.d(TAG, "time "+data.mMusicTime);
                Log.d(TAG, "path "+data.mMusicPath);
                Log.d(TAG, "aritst "+data.mMusicAritst);
                Log.d(TAG, "albumid "+data.MusicAlbumId);
                Log.d(TAG, "id "+data.MusicId);
                Log.d(TAG,"##############");
                list.add(data);
                cursor.moveToNext();
            }
            
            cursor.close();
    	}
    	long time2 = System.currentTimeMillis();
    	
    	Log.i(TAG, "seach filelist cost = " + (time2 - time1));
    	return list;
    }

}
