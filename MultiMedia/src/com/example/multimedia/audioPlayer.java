package com.example.multimedia;

import java.util.ArrayList;
import java.util.List;












import com.example.adapter.MusicListAdapter;
import com.example.interfaces.IOnSliderHandleViewClickListener;
import com.example.widget.MusicSlidingDrawer;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

public class audioPlayer extends Activity implements OnClickListener{
	private final String  TAG = "audioPlayer";
	// 音乐列表
	private List<MusicData> mMusicLists = new ArrayList<MusicData>();
	private ListView mMusicListView;
	private MusicListAdapter mMusicAdater;
	private SlidingDrawerManger mDrawerManger;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 加载布局文件
        setContentView(R.layout.music_sliding_drawer);
        // 设置背景图
        this.getWindow().setBackgroundDrawableResource(R.drawable.main_bg01);
        // 获取数据
        mMusicLists = getMusicFileList();
        // 显示音乐列表
        mMusicListView = (ListView)findViewById(R.id.listView);
        mMusicAdater = new MusicListAdapter(this, mMusicLists);
        mMusicListView.setAdapter(mMusicAdater);
        //获得抽屉
        mDrawerManger = new SlidingDrawerManger();
        
        	
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	@SuppressWarnings("deprecation")
	class SlidingDrawerManger implements OnClickListener,
	OnDrawerOpenListener, OnDrawerCloseListener, IOnSliderHandleViewClickListener{
		private MusicSlidingDrawer mMusicDrawer;
		private ImageButton mBtnHanderPlay;
		private ImageButton mBtnhanderPause;
		private TextView mTvMusicName;
		
		public SlidingDrawerManger(){
			initView();
		}
		private void initView(){
			mMusicDrawer = (MusicSlidingDrawer)findViewById(R.id.slidingDrawer);
			
			//设置抽屉把手
			mMusicDrawer.setHandleId(R.id.handler_icon);
			//设置其他触摸点
			mMusicDrawer.setOnDrawerOpenListener(this);
			mMusicDrawer.setOnDrawerCloseListener(this);
			mMusicDrawer.setTouchableIds(new int[]{R.id.handler_play, R.id.handler_pause});
			mMusicDrawer.setOnSliderHandleViewClickListener(this);
			mBtnHanderPlay =(ImageButton)findViewById(R.id.handler_play);
			mBtnhanderPause = (ImageButton)findViewById(R.id.handler_pause);
			mTvMusicName = (TextView)findViewById(R.id.textPlaySong);
			mTvMusicName.setText("歌曲名称");
			
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.handler_play:
				Log.d(TAG,"--1handler_play--");
				break;
			}
			
		}
		@Override
		public void onViewClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()){
			case R.id.handler_play:
				Log.d(TAG,"--2handler_play--");
				break;
			}
		}
		@Override
		public void onDrawerClosed() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onDrawerOpened() {
			// TODO Auto-generated method stub
			
		}
	}

}
