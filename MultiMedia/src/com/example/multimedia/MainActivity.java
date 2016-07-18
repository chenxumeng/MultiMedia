package com.example.multimedia;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btnStart;
	private Button btnPause;
	private Button btnStop;
	private Button btnForward;
	private Button btnRewind;
	private MediaPlayer mPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnStart = (Button)findViewById(R.id.Start);
		btnStart.setOnClickListener(new ButtonLister());
		
		btnPause = (Button)findViewById(R.id.Pause);
		btnPause.setOnClickListener(new ButtonLister());
		
		btnStop = (Button)findViewById(R.id.Stop);
		btnStop.setOnClickListener(new ButtonLister());
		
		btnForward = (Button)findViewById(R.id.Forward);
		btnForward.setOnClickListener(new ButtonLister());
		
		btnRewind = (Button)findViewById(R.id.Rewind);
		btnRewind.setOnClickListener(new ButtonLister());
		
		// 创建mediaPlayer 对象
		mPlayer = MediaPlayer.create(this, R.raw.music1);
		
		
	}
	class ButtonLister implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.Start:			
				break;
			case R.id.Pause:
				break;
			case R.id.Stop:
				break;
			case R.id.Forward:
				break;
			case R.id.Rewind:
				break;
			default:
				break;
			}
		}
		
	}
	
}
